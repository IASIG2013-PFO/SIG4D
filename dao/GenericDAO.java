package iasig.dao;

import iasig.dao.DAO;
import iasig.dao.user.Maison;
import iasig.dao.user.Buffer;
import iasig.dao.user.Objet_Postgre;
import iasig.dao.user.Raster_img_mnt;

import org.postgis.PGgeometry;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;



public class GenericDAO extends DAO {

 
	
	//constructeur vide
	public GenericDAO(){
		
	}
	
	/**
	 * Méthode Statique pour l'initialisation du Buffer
	 */
	public static void selection_geographique_init(Buffer obj, Float Xobs, Float Yobs, int interval_de_maille){
		
		//recuperation de la maille observateur
		//TODO ajouter False!!
		int maille_observateur_i = (int)(Xobs/interval_de_maille);
		int maille_observateur_j = (int)(Yobs/interval_de_maille);
		
		//inscription de la maille observateur dans L'objet en mémoire
		obj.set_Maille_Observateur(maille_observateur_i, maille_observateur_j);
		
		//ecriture du Polygone de requête selon paramètre de génération
		//1-récupération des mailles extremes de l'espace à mettre en mémoire
		int mailleMax_i = maille_observateur_i + obj.demi_espace_mémoire_maille();
		int mailleMin_i = maille_observateur_i - obj.demi_espace_mémoire_maille();
		int mailleMax_j = maille_observateur_j + obj.demi_espace_mémoire_maille();
		int mailleMin_j = maille_observateur_j - obj.demi_espace_mémoire_maille();
		//2-passage en coordonnées géographiques
		int Xmin = mailleMin_i * interval_de_maille;
		int Ymin = mailleMin_j * interval_de_maille;
		int Xmax = (mailleMax_i + 1) * interval_de_maille; 
		int Ymax = (mailleMax_j + 1) * interval_de_maille; 
		
		
		
		
		String Polygone = "SRID=" + "4326" + ";" + "POLYGON(("+Xmin+" "+ Ymin+ ","+Xmax+" "+Ymin+","+ Xmax+" "+ Ymax+","+ Xmin+" "+ Ymax+","+ Xmin+" "+ Ymin+"))";
		PGgeometry polygone;
		try {
			polygone = new PGgeometry(Polygone);			
	        ResultSet result = connection_statique
	                                .createStatement(
	                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            ResultSet.TYPE_SCROLL_INSENSITIVE
	                                         ).executeQuery(
	                                        		
	     //" SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('POLYGON((2000 2000,8000 2000, 8000 8000, 2000 8000, 2000 2000))', 4326) )  ;" 
	      " SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('"+polygone.getValue()+"', 4326) ) ;"
	                                        		 );
	        Maison home1 = new Maison();
			while(result.next()){
				if(result.absolute(result.getRow()))
										
	        		home1 = new Maison(
	        					result.getInt("maison_id"),
	        					result.getString("maison_x"),
	        					result.getString("maison_y"),
	        					result.getString("maison_z"),
	        					result.getString("maison_nom"),
	        					(PGgeometry)result.getObject("centroid"),
	        					result.getInt("niveau"),
	        					result.getInt("i"),
	        					result.getInt("j")
	        				 	);
	        		//retourne adresses objets	
					//System.out.print("Maison_id "+home1.getId()+" ");System.out.println("@ "+home1.toString());
					//on passe en paramètre la maille contenant l'observateur
			

					obj.AjoutObjet(home1, maille_observateur_i, maille_observateur_i);
	        					}
	        	
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }

		
	}
	/**
	 * Méthode Statique pour la MAJ globale du Buffer
	 * le Buffer ne recharge pas les objets swappés dans le buffer des objets visibles
	 */
	public static void selection_geographique(Buffer obj, Float Xobs, Float Yobs, int interval_de_maille){
			
		
			//Vidage du buffer
			obj.vide_Objet_en_memoire();
		
			//recuperation de la maille observateur
			//TODO ajouter False!!
			int maille_observateur_i = (int)(Xobs/interval_de_maille);
			int maille_observateur_j = (int)(Yobs/interval_de_maille);
			
			//inscription de la maille observateur dans L'objet en mémoire
			obj.set_Maille_Observateur(maille_observateur_i, maille_observateur_j);
			
			//ecriture du Polygone de requête selon paramètre de génération
			//1-récupération des mailles extremes de l'espace à mettre en mémoire
			int mailleMax_i = maille_observateur_i + obj.demi_espace_mémoire_maille();
			int mailleMin_i = maille_observateur_i - obj.demi_espace_mémoire_maille();
			int mailleMax_j = maille_observateur_j + obj.demi_espace_mémoire_maille();
			int mailleMin_j = maille_observateur_j - obj.demi_espace_mémoire_maille();
			
			//Decommenter si tu veux le swap
//			//2-Calcul de l'espace de swap ( zone du buffer correspondant à l'espace de "SWAP" du visible)
//			int mailleSwapMax_i = maille_observateur_i + (int)obj.dimension_espace_visible/2;
//			int mailleSwapMin_i = maille_observateur_i - (int)obj.dimension_espace_visible/2;
//			int mailleSwapMax_j = maille_observateur_j + (int)obj.dimension_espace_visible/2;
//			int mailleSwapMin_j = maille_observateur_j - (int)obj.dimension_espace_visible/2;	
			
			//3-passage en coordonnées géographiques - Extension Buffer
			int Xmin = mailleMin_i * interval_de_maille;
			int Ymin = mailleMin_j * interval_de_maille;
			int Xmax = (mailleMax_i + 1) * interval_de_maille; 
			int Ymax = (mailleMax_j + 1) * interval_de_maille; 
			
			//Decommenter si tu veux le swap
//			//4-passage en coordonnées géographiques - Extension Swap
//			int Xswapmin = mailleSwapMin_i * interval_de_maille;
//			int Yswapmin = mailleSwapMin_j * interval_de_maille;
//			int Xswapmax = (mailleSwapMax_i +1) * interval_de_maille;
//			int Yswapmax = (mailleSwapMax_j +1) * interval_de_maille;

			//Decommenter si tu veux le swap
//			String Polygone = "SRID=" + "4326" + ";" + "POLYGON(("+Xmin+" "+ Ymin+ ","+Xmax+" "+Ymin+","+ Xmax+" "+ Ymax+","+ Xmin+" "+ Ymax+","+ Xmin+" "+ Ymin+")"
//					+ ",("+Xswapmin+" "+ Yswapmin+ ","+Xswapmax+" "+Yswapmin+","+ Xswapmax+" "+ Yswapmax+","+ Xswapmin+" "+ Yswapmax+","+ Xswapmin+" "+ Yswapmin +"))";
			
			//commenter si tu veux le swap
			String Polygone = "SRID=" + "4326" + ";" + "POLYGON(("+Xmin+" "+ Ymin+ ","+Xmax+" "+Ymin+","+ Xmax+" "+ Ymax+","+ Xmin+" "+ Ymax+","+ Xmin+" "+ Ymin+"))";

			PGgeometry polygone;
			try {
				polygone = new PGgeometry(Polygone);			
		        ResultSet result = connection_statique
		                                .createStatement(
		                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
		                                            ResultSet.TYPE_SCROLL_INSENSITIVE
		                                         ).executeQuery(
		                                        		
		     //" SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('POLYGON((2000 2000,8000 2000, 8000 8000, 2000 8000, 2000 2000))', 4326) )  ;" 
		      " SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('"+polygone.getValue()+"', 4326) ) ;"
		                                        		 );
		        Maison home1 = new Maison();
				while(result.next()){
					if(result.absolute(result.getRow()))
											
		        		home1 = new Maison(
		        					result.getInt("maison_id"),
		        					result.getString("maison_x"),
		        					result.getString("maison_y"),
		        					result.getString("maison_z"),
		        					result.getString("maison_nom"),
		        					(PGgeometry)result.getObject("centroid"),
		        					result.getInt("niveau"),
		        					result.getInt("i"),
		        					result.getInt("j")
		        				 	);
		        		//retourne adresses objets	
						//System.out.print("Maison_id "+home1.getId()+" ");System.out.println("@ "+home1.toString());
						//on passe en paramètre la maille contenant l'observateur
				
	
						obj.AjoutObjet(home1, maille_observateur_i, maille_observateur_i);
		        					}
		        	
			    } catch (SQLException e) {
			            e.printStackTrace();
			    }
	
			
		}
	
	
	public static void selection_raster(Objet_Postgre<Raster_img_mnt> obj, int i, int j){
		
	    byte[] content = null;
//	    Dataset dattaset = null;
	    //type de raster enregistr� (img ou rast
	    String type_raster="img";
	    //conversion des valeurs de i et j vers l'�quivalent en indice
	    int id=(i-1)*99+j;
	    
		try {
			
			//vidage préalable du vecteur
	
	        ResultSet result = connection_statique
	                                .createStatement(
	                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            ResultSet.TYPE_SCROLL_INSENSITIVE
	                                         ).executeQuery(
	                                        		
	     //" SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('POLYGON((2000 2000,8000 2000, 8000 8000, 2000 8000, 2000 2000))', 4326) )  ;" 
//	      " SELECT ID, ST_AsPNG(rast) as image, I, J, type_Raster_img_mnt, Resolution FROM route WHERE ST_WITHIN(rast, ST_GeomFromText('"+polygone.getValue()+"', 2154) ) ;"// WHERE gid>="+a+" AND gid<="+b+";"//
	      " SELECT rid, ST_AsPNG(rast) as img, ST_DumpAsPolygons(rast) AS Raster FROM mnt_img WHERE rid='"+ id +"';"
	                                        		 );
//	        System.out.println(result.toString());
	        Raster_img_mnt home1;

			while(result.next()){
				if(result.absolute(result.getRow()))
				//System.out.println(result.getInt("maison_id"));
					
					content = result.getBytes("img");
                	ByteArrayInputStream bis = new ByteArrayInputStream(content);
                	BufferedImage image=null;
					try {
						image = ImageIO.read(bis);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(type_raster=="img"){
		        		home1 = new Raster_img_mnt(
		        					result.getInt("rid"),
		        					image,i,j,"img",1
//		        					result.getInt("I"),
//		        					result.getInt("J"),
//		        					result.getString("type_Raster_img_mnt"),
//		        					result.getInt("Resolution")
		        		);
					}
					else{
						home1 = new Raster_img_mnt(
	        					result.getInt("rid"),
	        					(PGgeometry)result.getObject("Raster"),
	        					i,j,"mnt",1
//	        					result.getInt("I"),
//	        					result.getInt("J"),
//	        					result.getString("type_Raster_img_mnt"),
//	        					result.getInt("Resolution")
						);
					}
	        		//retourne adresses objets	
//					System.out.print("Maison_id "+home1.get_ID()+" ");System.out.println("@ "+home1.toString());
					obj.AjoutObjet(home1);
					System.out.println("id_ortho "+obj.getElement(0).get_ID()+" ");
	        	}
	        	
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		
	}

	
	
	public void selection_geographique_par_polygone(){
		//Pas encore implanté
		
	}
	
}












