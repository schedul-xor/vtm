/*
 * Copyright 2013 Hannes Janetzek
 * Copyright 2016-2017 devemux86
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
package org.oscim.android.test;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import org.oscim.android.canvas.AndroidGraphics;
import org.oscim.backend.CanvasAdapter;
import org.oscim.backend.GLAdapter;
import org.oscim.core.Tile;
import org.oscim.gdx.AndroidGL;
import org.oscim.gdx.GdxAssets;
import org.oscim.gdx.GdxMap;
import org.oscim.tiling.TileSource;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

public class GdxMapActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidGraphics.init();
        GdxAssets.init("");
        GLAdapter.init(new AndroidGL());

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        CanvasAdapter.dpi = (int) (metrics.scaledDensity * CanvasAdapter.DEFAULT_DPI);
        Tile.SIZE = Tile.calculateTileSize();

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.stencil = 8;
        cfg.numSamples = 2;

        new SharedLibraryLoader().load("vtm-jni");

        initialize(new GdxMapAndroid(), cfg);
    }

    class GdxMapAndroid extends GdxMap {
        @Override
        public void createLayers() {
            TileSource ts = new OSciMap4TileSource();
            initDefaultLayers(ts, true, true, true, getResources().getDisplayMetrics().density);
        }
    }
}
