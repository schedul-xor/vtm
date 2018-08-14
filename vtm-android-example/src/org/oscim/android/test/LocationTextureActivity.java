/*
 * Copyright 2016-2018 devemux86
 * Copyright 2018 Longri
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

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import org.oscim.backend.CanvasAdapter;
import org.oscim.backend.canvas.Bitmap;
import org.oscim.backend.canvas.Color;
import org.oscim.core.MapPosition;
import org.oscim.layers.LocationTextureLayer;
import org.oscim.renderer.atlas.TextureAtlas;
import org.oscim.renderer.atlas.TextureRegion;
import org.oscim.renderer.bucket.TextureItem;
import org.oscim.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class LocationTextureActivity extends BitmapTileActivity implements LocationListener {
    private LocationTextureLayer locationLayer;
    private LocationManager locationManager;
    private final MapPosition mapPosition = new MapPosition();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // load a Bitmap/SVG from resources
        InputStream is = null;
        Bitmap bmp = null;
        try {
            is = getResources().openRawResource(R.raw.arrow);
            float scale = CanvasAdapter.getScale();
            bmp = CanvasAdapter.decodeSvgBitmap(is, (int) (60 * scale), (int) (60 * scale), 100);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }

        // create TextureRegion from Bitmap
        TextureRegion textureRegion = new TextureRegion(new TextureItem(bmp), new TextureAtlas.Rect(0, 0, bmp.getWidth(), bmp.getHeight()));

        // create LocationTextureLayer with created/loaded TextureRegion
        locationLayer = new LocationTextureLayer(mMap, textureRegion);

        // set color of accuracy circle (Color.BLUE is default)
        locationLayer.locationRenderer.setAccuracyColor(Color.get(50, 50, 255));

        // set color of indicator circle (Color.RED is default)
        locationLayer.locationRenderer.setIndicatorColor(Color.MAGENTA);

        // set billboard rendering for TextureRegion (false is default)
        locationLayer.locationRenderer.setBillboard(false);

        locationLayer.setEnabled(false);
        mMap.layers().add(locationLayer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableAvailableProviders();
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationLayer.setEnabled(true);
        locationLayer.setPosition(location.getLatitude(), location.getLongitude(), location.getBearing(), location.getAccuracy());

        // Follow location
        mMap.getMapPosition(mapPosition);
        mapPosition.setPosition(location.getLatitude(), location.getLongitude());
        mMap.setMapPosition(mapPosition);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void enableAvailableProviders() {
        locationManager.removeUpdates(this);

        for (String provider : locationManager.getProviders(true)) {
            if (LocationManager.GPS_PROVIDER.equals(provider)
                    || LocationManager.NETWORK_PROVIDER.equals(provider)) {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
            }
        }
    }
}
