/*
 * Copyright 2014 Hannes Janetzek
 * Copyright 2017 devemux86
 *
 * This file is part of the OpenScienceMap project (http://www.opensciencemap.org).
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.tiling.source.geojson;

import org.oscim.core.MapElement;
import org.oscim.core.Tile;
import org.oscim.tiling.ITileDataSink;
import org.oscim.tiling.source.ITileDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.oscim.core.MercatorProjection.latitudeToY;
import static org.oscim.core.MercatorProjection.longitudeToX;

public class GeojsonTileDecoder extends GeojsonDecoder implements ITileDecoder {

    private final GeojsonTileSource mTileSource;

    private ITileDataSink mTileDataSink;

    private double mTileY, mTileX, mTileScale;

    public GeojsonTileDecoder(GeojsonTileSource tileSource) {
        super();
        mTileSource = tileSource;
    }

    @Override
    public boolean decode(Tile tile, ITileDataSink sink, InputStream is) throws IOException {
        mTileDataSink = sink;
        mTileScale = 1 << tile.zoomLevel;
        mTileX = tile.tileX / mTileScale;
        mTileY = tile.tileY / mTileScale;
        mTileScale *= Tile.SIZE;

        super.decode(is);

        return true;
    }

    @Override
    protected void decodeTags(MapElement mapElement, Map<String, Object> properties) {
        mTileSource.decodeTags(mMapElement, properties);
    }

    @Override
    public void postGeomHook(MapElement mapElement) {
        mTileSource.postGeomHook(mMapElement);
    }

    @Override
    public void process(MapElement mapElement) {
        mTileDataSink.process(mMapElement);
    }

    @Override
    protected void addPoint(double x, double y, MapElement mapElement) {
        mapElement.addPoint((float) ((longitudeToX(x) - mTileX) * mTileScale),
                (float) ((latitudeToY(y) - mTileY) * mTileScale));
    }
}
