/**
 * 
 */
package iasig.dao;

import iasig.dao.user.Buffer;
import iasig.dao.user.Objet_Postgre;
import iasig.dao.user.Raster_img_mnt;

import org.postgis.PGgeometry;

/**
 * @author Francois
 *
 */
public class Raster_img_mntDAO extends ObjectDao<Raster_img_mnt> {


	@Override
	public Raster_img_mnt create(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raster_img_mnt update(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Raster_img_mnt find(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public void selection_geographique_par_polygone(Objet_Postgre<Raster_img_mnt> obj,
			PGgeometry polygone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selection_geographique(Buffer obj, Float Xobs, Float Yobs,
			int interval_de_maille) {
		// TODO Auto-generated method stub
		
	}

}

