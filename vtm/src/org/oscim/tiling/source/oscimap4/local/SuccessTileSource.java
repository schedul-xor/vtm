package org.oscim.tiling.source.oscimap4.local;

import org.oscim.tiling.TileSource;

/**
 * Created by xor on 15/11/11.
 */
public abstract class SuccessTileSource extends TileSource {
    public SuccessTileSource(int zoomMin, int zoomMax) {
        super(zoomMin, zoomMax);
    }

    @Override
    public OpenResult open() {
        // Since it's a local file, it always exist. Return true.
        return OpenResult.SUCCESS;
    }

    @Override
    public void close() {

    }
}
