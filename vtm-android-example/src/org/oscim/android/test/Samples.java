/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 * Copyright 2013 Hannes Janetzek
 * Copyright 2016-2017 devemux86
 * Copyright 2017 Longri
 * Copyright 2017 nebular
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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Start screen for the sample activities.
 */
public class Samples extends Activity {

    private Button createButton(Class<?> clazz) {
        return this.createButton(clazz, null, null);
    }

    private Button createButton(final Class<?> clazz, String text, View.OnClickListener customListener) {
        Button button = new Button(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            button.setAllCaps(false);
        if (text == null) {
            button.setText(clazz.getSimpleName());
        } else {
            button.setText(text);
        }
        if (customListener == null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Samples.this, clazz));
                }
            });
        } else {
            button.setOnClickListener(customListener);
        }
        return button;
    }

    private TextView createLabel(String text) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        if (text == null) {
            textView.setText("----------");
        } else {
            textView.setText(text);
        }
        return textView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_samples);
        LinearLayout linearLayout = findViewById(R.id.samples);
        linearLayout.addView(createButton(SimpleMapActivity.class));
        linearLayout.addView(createButton(MapsforgeMapActivity.class));
        linearLayout.addView(createButton(MapzenMvtMapActivity.class));
        linearLayout.addView(createButton(MapzenGeojsonMapActivity.class));
        linearLayout.addView(createButton(OpenMapTilesGeojsonMapActivity.class));
        linearLayout.addView(createButton(GdxMapActivity.class));

        linearLayout.addView(createLabel("Features"));
        linearLayout.addView(createButton(null, "GraphHopper Routing", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/graphhopper/graphhopper/tree/master/android")));
            }
        }));
        linearLayout.addView(createButton(PoiSearchActivity.class));

        linearLayout.addView(createLabel("Vector Features"));
        linearLayout.addView(createButton(MapsforgeStyleActivity.class));
        linearLayout.addView(createButton(MapsforgePolyLabelActivity.class));
        linearLayout.addView(createButton(AtlasThemeActivity.class));
        linearLayout.addView(createButton(POTTextureActivity.class));

        linearLayout.addView(createLabel("Raster Maps"));
        linearLayout.addView(createButton(BitmapTileMapActivity.class));

        linearLayout.addView(createLabel("Overlays"));
        linearLayout.addView(createButton(MarkerOverlayActivity.class));
        linearLayout.addView(createButton(RotateMarkerOverlayActivity.class));
        linearLayout.addView(createButton(AtlasMarkerOverlayActivity.class));
        linearLayout.addView(createButton(AtlasMultiTextureActivity.class));
        linearLayout.addView(createButton(ClusterMarkerOverlayActivity.class));
        linearLayout.addView(createButton(PathOverlayActivity.class));
        linearLayout.addView(createButton(LineTexActivity.class));
        linearLayout.addView(createButton(VectorLayerMapActivity.class));
        linearLayout.addView(createButton(LocationActivity.class));

        linearLayout.addView(createLabel("User Interaction"));
        linearLayout.addView(createButton(NewGesturesActivity.class));
        linearLayout.addView(createButton(LayerGroupActivity.class));

        linearLayout.addView(createLabel("Dual Map Views"));
        linearLayout.addView(createButton(MultiMapActivity.class));

        linearLayout.addView(createLabel("Experiments"));
        linearLayout.addView(createButton(ReverseGeocodeActivity.class));
        linearLayout.addView(createButton(S3DBMapActivity.class));
        linearLayout.addView(createButton(ThemeStylerActivity.class));
        linearLayout.addView(createButton(JeoIndoorMapActivity.class));
    }
}
