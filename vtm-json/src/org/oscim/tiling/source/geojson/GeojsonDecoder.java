package org.oscim.tiling.source.geojson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.oscim.core.GeometryBuffer;
import org.oscim.core.MapElement;
import org.oscim.core.Tag;
import org.oscim.utils.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonToken.*;

abstract public class GeojsonDecoder {
    private final LinkedHashMap<String, Object> mTagMap;
    private final JsonFactory mJsonFactory;
    protected final MapElement mMapElement;

    private final static char[] FIELD_FEATURES = "features".toCharArray();
    private final static char[] FIELD_GEOMETRY = "geometry".toCharArray();
    private final static char[] FIELD_PROPERTIES = "properties".toCharArray();
    private final static char[] FIELD_COORDINATES = "coordinates".toCharArray();
    private final static char[] FIELD_TYPE = "type".toCharArray();

    private final static char[] LINESTRING = "LineString".toCharArray();
    private final static char[] POLYGON = "Polygon".toCharArray();
    private final static char[] POINT = "Point".toCharArray();
    private final static char[] MULTI_LINESTRING = "MultiLineString".toCharArray();
    private final static char[] MULTI_POLYGON = "MultiPolygon".toCharArray();
    private final static char[] MULTI_POINT = "MultiPoint".toCharArray();

    public GeojsonDecoder() {
        mTagMap = new LinkedHashMap<>();
        mJsonFactory = new JsonFactory();

        mMapElement = new MapElement();
        mMapElement.layer = 5;
    }

    public void decode(InputStream is) throws IOException {

        JsonParser jp = mJsonFactory.createParser(new InputStreamReader(is));

        Tag layerTag = null;
        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == FIELD_NAME) {
                if (!match(jp, FIELD_FEATURES) && !match(jp, FIELD_TYPE))
                    layerTag = new Tag("layer", jp.getCurrentName());
                if (match(jp, FIELD_FEATURES)) {
                    if (jp.nextToken() != START_ARRAY)
                        continue;

                    while ((t = jp.nextToken()) != null) {
                        if (t == START_OBJECT)
                            parseFeature(jp, layerTag);

                        if (t == END_ARRAY)
                            break;
                    }
                }
            }
        }
    }

    private void parseFeature(JsonParser jp, Tag layerTag) throws IOException {

        mMapElement.clear();
        mMapElement.tags.clear();
        if (layerTag != null)
            mMapElement.tags.add(layerTag);
        mTagMap.clear();

        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == FIELD_NAME) {
                if (match(jp, FIELD_GEOMETRY)) {
                    if (jp.nextToken() == START_OBJECT)
                        parseGeometry(jp);
                }

                if (match(jp, FIELD_PROPERTIES)) {
                    if (jp.nextToken() == START_OBJECT)
                        parseProperties(jp);
                }
                continue;
            }
            if (t == END_OBJECT)
                break;
        }

        //add tag information
        decodeTags(mMapElement, mTagMap);
        if (mMapElement.tags.size() == 0)
            return;

        postGeomHook(mMapElement);

        if (mMapElement.type == GeometryBuffer.GeometryType.NONE)
            return;

        //process this element
        process(mMapElement);
    }

    abstract protected void decodeTags(MapElement mapElement, Map<String, Object> properties);

    abstract public void postGeomHook(MapElement mapElement);

    abstract public void process(MapElement mapElement);

    private void parseProperties(JsonParser jp) throws IOException {
        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == FIELD_NAME) {
                String text = jp.getCurrentName();

                t = jp.nextToken();
                if (t == VALUE_STRING) {
                    mTagMap.put(text, jp.getText());
                } else if (t == VALUE_NUMBER_INT) {
                    mTagMap.put(text, jp.getNumberValue());
                }
                continue;
            }
            if (t == END_OBJECT)
                break;
        }
    }

    private void parseGeometry(JsonParser jp) throws IOException {

        boolean multi = false;
        GeometryBuffer.GeometryType type = GeometryBuffer.GeometryType.NONE;

        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == FIELD_NAME) {
                if (match(jp, FIELD_COORDINATES)) {
                    if (jp.nextToken() != START_ARRAY)
                        continue;
                    if (multi) {
                        parseMulti(jp, type);
                    } else {
                        if (type == GeometryBuffer.GeometryType.POLY)
                            parsePolygon(jp);

                        if (type == GeometryBuffer.GeometryType.LINE)
                            parseLineString(jp);

                        if (type == GeometryBuffer.GeometryType.POINT)
                            parseCoordinate(jp);

                    }
                } else if (match(jp, FIELD_TYPE)) {
                    multi = false;

                    jp.nextToken();

                    if (match(jp, LINESTRING))
                        type = GeometryBuffer.GeometryType.LINE;
                    else if (match(jp, POLYGON))
                        type = GeometryBuffer.GeometryType.POLY;
                    else if (match(jp, POINT))
                        type = GeometryBuffer.GeometryType.POINT;
                    else if (match(jp, MULTI_LINESTRING)) {
                        type = GeometryBuffer.GeometryType.LINE;
                        multi = true;
                    } else if (match(jp, MULTI_POLYGON)) {
                        type = GeometryBuffer.GeometryType.POLY;
                        multi = true;
                    } else if (match(jp, MULTI_POINT)) {
                        type = GeometryBuffer.GeometryType.POINT;
                        multi = true;
                    }

                    if (type == GeometryBuffer.GeometryType.POINT)
                        mMapElement.startPoints();
                }
                continue;
            }
            if (t == END_OBJECT)
                break;
        }
    }

    private void parseMulti(JsonParser jp, GeometryBuffer.GeometryType type) throws IOException {

        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == END_ARRAY)
                break;

            if (t == START_ARRAY) {
                if (type == GeometryBuffer.GeometryType.POLY)
                    parsePolygon(jp);

                else if (type == GeometryBuffer.GeometryType.LINE)
                    parseLineString(jp);

                else if (type == GeometryBuffer.GeometryType.POINT)
                    parseCoordinate(jp);

            } else {
                //....
            }
        }
    }

    private void parsePolygon(JsonParser jp) throws IOException {
        int ring = 0;

        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == START_ARRAY) {
                if (ring == 0)
                    mMapElement.startPolygon();
                else
                    mMapElement.startHole();

                ring++;
                parseCoordSequence(jp);
                removeLastPoint();
                continue;
            }

            if (t == END_ARRAY)
                break;
        }
    }

    private void removeLastPoint() {
        mMapElement.pointNextPos -= 2;
        mMapElement.index[mMapElement.indexCurrentPos] -= 2;
    }

    private void parseLineString(JsonParser jp) throws IOException {
        mMapElement.startLine();
        parseCoordSequence(jp);
    }

    private void parseCoordSequence(JsonParser jp) throws IOException {

        for (JsonToken t; (t = jp.nextToken()) != null; ) {

            if (t == START_ARRAY) {
                parseCoordinate(jp);
                continue;
            }

            if (t == END_ARRAY)
                break;

        }
    }

    private void parseCoordinate(JsonParser jp) throws IOException {
        int pos = 0;
        double x = 0, y = 0; //, z = 0;

        for (JsonToken t; (t = jp.nextToken()) != null; ) {
            if (t == VALUE_NUMBER_FLOAT || t == VALUE_NUMBER_INT) {

                // avoid String allocation (by getDouble...)
                char[] val = jp.getTextCharacters();
                int offset = jp.getTextOffset();
                int length = jp.getTextLength();
                double c = ArrayUtils.parseNumber(val, offset, offset + length);

                if (pos == 0)
                    x = c;
                if (pos == 1)
                    y = c;
                //if (pos == 2)
                //z = c;

                pos++;
                continue;
            }

            if (t == END_ARRAY)
                break;
        }

        addPoint(x, y, mMapElement);
    }

    abstract protected void addPoint(double x, double y, MapElement mapElement);

    private static boolean match(JsonParser jp, char[] fieldName) throws IOException {

        int length = jp.getTextLength();
        if (length != fieldName.length)
            return false;

        char[] val = jp.getTextCharacters();
        int offset = jp.getTextOffset();

        for (int i = 0; i < length; i++) {
            if (fieldName[i] != val[i + offset])
                return false;
        }

        return true;
    }
}
