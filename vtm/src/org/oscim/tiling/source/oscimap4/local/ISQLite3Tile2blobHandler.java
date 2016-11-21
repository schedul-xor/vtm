package org.oscim.tiling.source.oscimap4.local;

import org.oscim.core.Tile;

import java.io.InputStream;

/**
 * Created by xor on 15/11/30.
 */
public interface ISQLite3Tile2blobHandler {
    InputStream inputStreamForTile(Tile tile);

    void load();
}
