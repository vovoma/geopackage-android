package mil.nga.geopackage.test.tiles.retriever;

import android.graphics.Bitmap;

import junit.framework.TestCase;

import java.sql.SQLException;

import mil.nga.geopackage.BoundingBox;
import mil.nga.geopackage.core.srs.SpatialReferenceSystem;
import mil.nga.geopackage.io.BitmapConverter;
import mil.nga.geopackage.projection.Projection;
import mil.nga.geopackage.projection.ProjectionConstants;
import mil.nga.geopackage.projection.ProjectionFactory;
import mil.nga.geopackage.test.TestConstants;
import mil.nga.geopackage.test.TilesGeoPackageTestCase;
import mil.nga.geopackage.tiles.TileBoundingBoxUtils;
import mil.nga.geopackage.tiles.matrixset.TileMatrixSet;
import mil.nga.geopackage.tiles.retriever.GeoPackageTile;
import mil.nga.geopackage.tiles.retriever.TileCreator;
import mil.nga.geopackage.tiles.user.TileDao;

/**
 * Test GeoPackage Overlay from a created database
 *
 * @author osbornb
 */
public class TileCreatorTest extends TilesGeoPackageTestCase {

    /**
     * Constructor
     */
    public TileCreatorTest() {

    }

    /**
     * Test get tile
     *
     * @throws SQLException
     */
    public void testGetTile() throws SQLException {

        TileDao tileDao = geoPackage.getTileDao(TestConstants.TILES_DB_TABLE_NAME);
        TestCase.assertEquals(tileDao.getProjection().getEpsg(), ProjectionConstants.EPSG_WEB_MERCATOR);

        tileDao.adjustTileMatrixLengths();

        Projection wgs84 = ProjectionFactory
                .getProjection(ProjectionConstants.EPSG_WORLD_GEODETIC_SYSTEM);

        SpatialReferenceSystem srs = tileDao.getTileMatrixSet().getSrs();
        Projection projection = ProjectionFactory.getProjection(srs);

        TileMatrixSet tileMatrixSet = tileDao.getTileMatrixSet();

        int width = 256;
        int height = 140;
        TileCreator tileCreator = new TileCreator(tileDao, width, height, tileMatrixSet, wgs84, projection);

        BoundingBox boundingBox = new BoundingBox();
        boundingBox = TileBoundingBoxUtils.boundWgs84BoundingBoxWithWebMercatorLimits(boundingBox);
        TestCase.assertFalse(tileCreator.hasTile(boundingBox));

        boundingBox = new BoundingBox(-180.0, 0.0, 0.0, 90.0);
        boundingBox = TileBoundingBoxUtils.boundWgs84BoundingBoxWithWebMercatorLimits(boundingBox);
        TestCase.assertTrue(tileCreator.hasTile(boundingBox));

        GeoPackageTile tile = tileCreator.getTile(boundingBox);

        TestCase.assertNotNull(tile);
        TestCase.assertEquals(width, tile.getWidth());
        TestCase.assertEquals(height, tile.getHeight());

        byte[] tileBytes = tile.getData();
        TestCase.assertNotNull(tileBytes);
        Bitmap bitmap = BitmapConverter.toBitmap(tileBytes);

        TestCase.assertEquals(width, bitmap.getWidth());
        TestCase.assertEquals(height, bitmap.getHeight());
        validateBitmap(bitmap);

        boundingBox = new BoundingBox(-90.0, 0.0, 0.0, 45.0);
        TestCase.assertTrue(tileCreator.hasTile(boundingBox));

        tile = tileCreator.getTile(boundingBox);

        TestCase.assertNotNull(tile);
        TestCase.assertEquals(width, tile.getWidth());
        TestCase.assertEquals(height, tile.getHeight());

        tileBytes = tile.getData();
        TestCase.assertNotNull(tileBytes);
        bitmap = BitmapConverter.toBitmap(tileBytes);

        TestCase.assertEquals(width, bitmap.getWidth());
        TestCase.assertEquals(height, bitmap.getHeight());
        validateBitmap(bitmap);
    }

    /**
     * Validate that the bitmap has no transparency
     *
     * @param bitmap
     */
    private void validateBitmap(Bitmap bitmap) {

        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                TestCase.assertTrue(bitmap.getPixel(x, y) != 0);
            }
        }

    }

}
