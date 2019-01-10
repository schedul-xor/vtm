package org.oscim.tiling.source.geojson;

import com.google.gwt.core.client.JavaScriptObject;
import org.oscim.core.GeometryBuffer;
import org.oscim.core.MapElement;

import java.util.LinkedHashMap;
import java.util.Map;

abstract public class GeojsonDecoder {
    private final MapElement mapElement;

    final static LinkedHashMap<String, Object> mProperties = new LinkedHashMap<String, Object>(10);

    public GeojsonDecoder() {
        mapElement = new MapElement();
        mapElement.layer = 5;
    }

    public void decode(JavaScriptObject jso) {

        FeatureCollection c = (FeatureCollection) jso;

        for (Feature f : c.getFeatures()) {
            mapElement.clear();
            mapElement.tags.clear();

            /* add tag information */
            decodeTags(mapElement, f.getProperties(mProperties));
            if (mapElement.tags.size() == 0)
                continue;

            /* add geometry information */
            decodeGeometry(f.getGeometry());

            if (mapElement.type == GeometryBuffer.GeometryType.NONE)
                continue;

            process(mapElement);
        }
    }

    abstract protected void decodeTags(MapElement mapElement, Map<String, Object> properties);

    abstract public void postGeomHook(MapElement mapElement);

    abstract public void process(MapElement mapElement);

    private void decodeGeometry(Geometry<?> geometry) {
        String type = geometry.type();

        if ("Polygon".equals(type)) {
            Polygon p = (Polygon) geometry.getCoordinates();
            decodePolygon(p);
        } else if ("MultiPolygon".equals(type)) {
            MultiPolygon mp = (MultiPolygon) geometry.getCoordinates();
            for (int k = 0, l = mp.getNumGeometries(); k < l; k++)
                decodePolygon(mp.getGeometryN(k));

        } else if ("LineString".equals(type)) {
            LineString ls = (LineString) geometry.getCoordinates();
            decodeLineString(ls);

        } else if ("MultiLineString".equals(type)) {
            MultiLineString ml = (MultiLineString) geometry.getCoordinates();
            for (int k = 0, n = ml.getNumGeometries(); k < n; k++)
                decodeLineString(ml.getGeometryN(k));
        }
    }

    private void decodeLineString(LineString l) {
        mapElement.startLine();
        for (int j = 0, m = l.length(); j < m; j++) {
            LngLat ll = l.get(j);
            addPoint(ll.getLongitude(), ll.getLatitude(), mapElement);
        }
    }

    private void decodePolygon(Polygon p) {
        for (int i = 0, n = p.getNumRings(); i < n; i++) {
            if (i > 0)
                mapElement.startHole();
            else
                mapElement.startPolygon();

            LineString ls = p.getRing(i);
            for (int j = 0, m = ls.length() - 1; j < m; j++) {
                LngLat ll = ls.get(j);
                addPoint(ll.getLongitude(), ll.getLatitude(), mapElement);
            }
        }
    }

    abstract protected void addPoint(double x, double y, MapElement mapElement);
}
