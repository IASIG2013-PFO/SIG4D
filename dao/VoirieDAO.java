package iasig.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import iasig.dao.user.Buffer;
import iasig.dao.user.Lampadaire;
import iasig.dao.user.Objet_Postgre;
import iasig.dao.user.Voirie;

import org.postgis.PGgeometry;

public class VoirieDAO extends ObjectDao<Voirie> {

	public Vector<Voirie> LoadVoirie(){
		Vector<Voirie> voirie74 = new Vector<Voirie>();

		Voirie voirie = new Voirie();
		ResultSet result;
		try {
			result = this .connect
			        .createStatement(
			                	ResultSet.TYPE_SCROLL_INSENSITIVE, 
			                    ResultSet.TYPE_SCROLL_INSENSITIVE
			                 ).executeQuery(
			                		 "SELECT id,nature,numero,nom_rue_g,sens,importance,geom FROM routes74 ;"
			                		 );
		
		

			while(result.next()){
				if(result.absolute(result.getRow()))
	
					voirie = new Voirie(
			result.getString("id"),
			result.getString("nature"),
			result.getString("numero"),
			result.getString("nom_rue_g"),
			result.getString("sens"),
			result.getString("importance"),
			(PGgeometry)result.getObject("geom")
		 	);
			voirie74.add(voirie);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voirie74;
		
	}
	
	
	@Override
	public Voirie find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Voirie create(Voirie obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Voirie update(Voirie obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Voirie obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selection_geographique(Buffer obj, Float Xobs, Float Yobs,
			int interval_de_maille) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void selection_geographique_par_polygone(Objet_Postgre<Voirie> obj,
			PGgeometry polygone) {
		// TODO Auto-generated method stub
		
	}

}
