package org.oscim.tiling.source.oscimap4.local;

import org.oscim.tiling.ITileDataSource;
import org.oscim.tiling.source.oscimap4.TileDecoder;

/**
 * Created by xor on 15/11/30.
 */
public class SQLite3OSciMap4TileSource extends SuccessTileSource {
    ISQLite3Tile2blobHandler handler;

    public SQLite3OSciMap4TileSource(int zoomMin, int zoomMax, ISQLite3Tile2blobHandler handler) {
        super(zoomMin, zoomMax);
        this.handler = handler;
    }

    @Override
    public ITileDataSource getDataSource() {
        return new SQLite3Tile2blobDataSource(this.handler, new TileDecoder());
    }
}
