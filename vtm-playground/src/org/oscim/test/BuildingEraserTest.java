package org.oscim.test;

import com.badlogic.gdx.assets.AssetManager;
import org.oscim.core.*;
import org.oscim.gdx.GdxAssets;
import org.oscim.gdx.GdxMapApp;
import org.oscim.gdx.poi3d.GdxModelLayer;
import org.oscim.layers.GroupLayer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.TileSource;
import org.oscim.tiling.source.OkHttpEngine;
import org.oscim.tiling.source.geojson.GeojsonDecoder;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import java.io.IOException;

import static org.oscim.core.MercatorProjection.latitudeToY;
import static org.oscim.core.MercatorProjection.longitudeToX;

public class BuildingEraserTest extends GdxMapApp {

    @Override
    public void createLayers() {
        Map map = getMap();

        TileSource tileSource = OSciMap4TileSource.builder()
                .httpFactory(new OkHttpEngine.OkHttpFactory())
                .build();
        VectorTileLayer l = map.setBaseMap(tileSource);

        final BuildingLayer buildingLayer = new BuildingLayer(map, l);

        GeojsonDecoder gjd = new GeojsonDecoder() {
            @Override
            protected void decodeTags(MapElement mapElement, java.util.Map<String, Object> properties) {
            }

            @Override
            public void postGeomHook(MapElement mapElement) {

            }

            @Override
            public void process(MapElement mapElement) {

            }

            @Override
            protected void addPoint(double lon, double lat, MapElement mapElement) {
                GeoPoint gp = new GeoPoint(lat, lon);
                buildingLayer.eraserPoints.add(gp);
            }
        };

        try {
            gjd.decode(getClass().getResourceAsStream("/assets/eraser.geojson"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GroupLayer groupLayer = new GroupLayer(mMap);
        groupLayer.layers.add(buildingLayer);

        GdxModelLayer gdxModelLayer = new GdxModelLayer(mMap);
        mMap.layers().add(gdxModelLayer);
        gdxModelLayer.addModel(GdxAssets.getAssetPath("models/buildings/tokyo_tower.g3db"),35.6595298, 139.7462639,33f,-90f);

        groupLayer.layers.add(new LabelLayer(map, l));
        map.layers().add(groupLayer);

        map.setTheme(VtmThemes.DEFAULT);
        MapPosition pos = MapPreferences.getMapPosition();
        if (pos != null)
            map.setMapPosition(pos);
        else
            map.setMapPosition(53.075, 8.808, 1 << 17);
    }

    @Override
    public void dispose() {
        MapPreferences.saveMapPosition(mMap.getMapPosition());
        super.dispose();
    }

    public static void main(String[] args) {
        GdxMapApp.init();
        GdxMapApp.run(new BuildingEraserTest());
    }
}
