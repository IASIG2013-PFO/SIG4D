package iasig.dao.user;

import java.sql.SQLException;
import java.util.Vector;

import org.postgis.PGgeometry;

import iasig.dao.GenericDAO;
import iasig.dao.LampadaireDAO;
import iasig.dao.MaisonDAO;
import iasig.dao.VoirieDAO;

public class Main {

//	public Objet_Maison objets_maisons = new Objet_Maison();
//	public Raster_Postgre tuiles_mnt = new Raster_Postgre();
//	public Raster_Postgre tuiles_orthophoto = new Raster_Postgre();
	
	public static void main(String[] args) {
		
		//Instanciation des classes d'objets DAO
		//Chargement de l'objet pour obtenir la visibilité sur les méthode DAO
		//Devrait passer en Static
		GenericDAO daoObjets = new GenericDAO();
		
		
		
		//INITIALISATION DU BUFFER OBJET
		//Instanciation du l'objet Vecteurs d'Objets en mémoire
		//recupere une surface de X*X maille centrée sur l'obj.	
		Buffer objet_en_memoire2 = new Buffer(10, 9000, 9000, 100, 3);
		

		
		//FIN INITIALISATION
		
		
		
		
		//Simulation Deplacement de l'observateur
		for (int i =0; i<25; i++){
			 float Xobs = 9000 + 100*i;
			 float Yobs = 9000 + 100*i;
			 
			 
		
				GenericDAO.selection_geographique(objet_en_memoire2, Xobs, Yobs, 100);

			 
			 
			
			 
			
				
	
				//STATISTIQUES
				System.out.println(objet_en_memoire2.NbreObjets() + " Objets dans le Buffers");
				//FIN STATISTIQUES
				
				
				
		}
			
	}	
	
}



	
