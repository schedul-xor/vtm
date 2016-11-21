package org.oscim.tiling.source.oscimap4.local;

import org.oscim.layers.tile.MapTile;
import org.oscim.tiling.ITileDataSink;
import org.oscim.tiling.ITileDataSource;
import org.oscim.tiling.QueryResult;
import org.oscim.tiling.source.oscimap4.TileDecoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xor on 15/11/30.
 */
public class SQLite3Tile2blobDataSource implements ITileDataSource {
    ISQLite3Tile2blobHandler handler;
    TileDecoder tileDecoder;

    public SQLite3Tile2blobDataSource(ISQLite3Tile2blobHandler handler, TileDecoder tileDecoder) {
        this.handler = handler;
        this.tileDecoder = tileDecoder;
    }

    @Override
    public void query(MapTile tileJob, ITileDataSink sink) {
        InputStream is = this.handler.inputStreamForTile(tileJob);
        if (is == null) {
            sink.completed(QueryResult.SUCCESS);
        } else {
            try {
                if (tileDecoder.decode(tileJob, sink, is)) {
                    sink.completed(QueryResult.SUCCESS);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void cancel() {

    }
}
