package mil.nga.giat.geopackage;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * GeoPackage Database management
 * 
 * @author osbornb
 */
public interface GeoPackageManager {

	/**
	 * List all GeoPackage databases
	 * 
	 * @return database list
	 */
	public List<String> databaseList();

	/**
	 * Set of all GeoPackage databases
	 * 
	 * @return database set
	 */
	public Set<String> databaseSet();

	/**
	 * Determine if the database exists
	 * 
	 * @param database
	 * @return true if exists
	 */
	public boolean exists(String database);

	/**
	 * Delete a database
	 * 
	 * @param database
	 * @return true if deleted
	 */
	public boolean delete(String database);

	/**
	 * Create a new GeoPackage database
	 * 
	 * @param database
	 * @return
	 */
	public boolean create(String database);

	/**
	 * Import a GeoPackage file
	 * 
	 * @param file
	 *            GeoPackage file to import
	 * @return true if loaded
	 */
	public boolean importGeoPackage(File file);

	/**
	 * Import a GeoPackage file
	 * 
	 * @param name
	 *            database name to save as
	 * @param file
	 *            GeoPackage file to import
	 * @param override
	 *            true to override existing
	 * @return true if created successfully
	 */
	public boolean importGeoPackage(File file, boolean override);

	/**
	 * Import a GeoPackage file
	 * 
	 * @param name
	 *            database name to save as
	 * @param file
	 *            GeoPackage file to import
	 * @return true if created successfully
	 */
	public boolean importGeoPackage(String name, File file);

	/**
	 * Import a GeoPackage file
	 * 
	 * @param name
	 *            database name to save the imported file as
	 * @param file
	 *            GeoPackage file to import
	 * @param override
	 *            true to override existing
	 * @return true if created successfully
	 */
	public boolean importGeoPackage(String name, File file, boolean override);

	/**
	 * Export a GeoPackage database to a file
	 * 
	 * @param database
	 * @param directory
	 */
	public void exportGeoPackage(String database, File directory);

	/**
	 * Export a GeoPackage database to a file
	 * 
	 * @param database
	 * @param name
	 * @param directory
	 */
	public void exportGeoPackage(String database, String name, File directory);

	/**
	 * Open the database
	 * 
	 * @param database
	 * @return
	 */
	public GeoPackage open(String database);

}