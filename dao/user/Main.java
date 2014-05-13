package iasig.dao.user;

import java.sql.SQLException;

import org.postgis.PGgeometry;

import iasig.dao.LampadaireDAO;
import iasig.dao.MaisonDAO;
import iasig.dao.RasterDao;

public class Main {

//	public Objet_Maison objets_maisons = new Objet_Maison();
//	public Raster_Postgre tuiles_mnt = new Raster_Postgre();
//	public Raster_Postgre tuiles_orthophoto = new Raster_Postgre();
	
	public static void main(String[] args) {
		
		//Instanciation des classes d'objets DAO
		MaisonDAO maisonDao = new MaisonDAO();
		LampadaireDAO lampadaireDao = new LampadaireDAO();
		RasterDao rasterDao = new RasterDao();

		//Instanciation des Vecteurs d'Objets
		Objet_Maison objets_maisons = new Objet_Maison();
		
		Raster_Postgre tuiles_mnt = new Raster_Postgre();
		Raster_Postgre tuiles_orthophoto = new Raster_Postgre();
	
				
		
		//* ECRIRE UN POLYGONE STYLE POSTGIS
		try {
			String Polygone = "SRID=" + "4326" + ";" + "POLYGON((2000 2000,8000 2000, 8000 8000, 2000 8000, 2000 2000))";
			PGgeometry polygone = new PGgeometry(Polygone);
			System.out.println(polygone.getGeometry());
			
			//SELECTION GEOMETRIQUE SUR LA BASE DE DONNEES
			
			maisonDao.selection_geographique(objets_maisons, polygone);
			//EXPLORE VECTEUR DES OBJETS INSTANCIES
			objets_maisons.getObjet_par_niveau(1);
			//AFFICHAGE DES COORDONNEES X et Y des Objets
			for (Integer i = 0; i < objets_maisons.NbreObjets(); i++){
				System.out.print(objets_maisons.getElement(i).getX1()+" ");	System.out.println(objets_maisons.getElement(i).getY1());
				}
				System.out.print(objets_maisons.NbreObjets());
		
			
				
				
		//* REMPLISSAGE DU VECTEUR TUILE - POSTGIS
		
		//ST_GeomFromText('MULTIPOLYGON(((100 100, 100 0, 0 0, 0 100, 100 100))
		String PolygoneRaster = "SRID=" + "4326" + ";" + "POLYGON((100 100, 100 0, 0 0, 0 100, 100 100))";
		PGgeometry polygoneR = new PGgeometry(PolygoneRaster);
		RasterDao.Recup_tuiles_depuis_BDD(tuiles_mnt, polygoneR);
		
		for (int i = 0 ; i < tuiles_mnt.nbreElements(); i++){
		System.out.print(tuiles_mnt.getXorigine(i)+" ");	System.out.println(tuiles_mnt.getYorigine(i));
		}
		
		
		System.out.print("nbres de tuiles en mémoires: "+tuiles_mnt.nbreElements());

		
	
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	//*/

		//for(long i = 1; i <= 95; i++)
				//System.out.println(maisonDao.find(i).getCentroid());
				
				//*
				for (Integer j = 1; j <= 10; j++){
				for(Integer i = 1; i <= 10000; i++){
					//public Maison(Integer id, String X, String Y, String Z, String nom, PGgeometry centroid)
		//
		//
					Integer X = 1 + (int)(Math.random() * ((10000 - 1) + 1));
					Integer Y = 1 + (int)(Math.random() * ((10000 - 1) + 1));
					Integer Z = 1 + (int)(Math.random() * ((10000 - 1) + 1));
		//
			
					String PGgeometryStr ="SRID=" + "4326" + ";" + "POINT("+Long.toString(X)+" "+Long.toString(Y)+" "+Long.toString(Z)+")";
					PGgeometry pg = null;
					
					try {
						pg = new PGgeometry(PGgeometry.geomFromString(PGgeometryStr));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					Maison maison = new Maison(0, Long.toString(X), Long.toString(Y), Long.toString(Z), "Maison"+Long.toString(i*j), pg, i%4 );
					maisonDao.create(maison);
				}
				}
				//*/
				
				//* ECRIRE UN POLYGONE STYLE POSTGRESQL - NON POSTGIS
//					PGpoint pt1 = new PGpoint(2000, 2000);
//					PGpoint pt2 = new PGpoint(8000, 2000);
//					PGpoint pt3 = new PGpoint(8000, 8000);
//					PGpoint pt4 = new PGpoint(2000, 8000);
//					PGpoint pt5 = new PGpoint(2000, 2000);
		//
//					PGpoint[] points = {pt1, pt2, pt3, pt4, pt5};
//					PGpolygon polygone = new PGpolygon(points);
				//*/
		
	}



	
}
