package iasig.dao.user;

import java.sql.SQLException;
import java.util.Vector;

import org.postgis.PGgeometry;

import iasig.dao.GenericDAO;
import iasig.dao.LampadaireDAO;
import iasig.dao.MaisonDAO;
import iasig.dao.RasterDao;
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
		
		
		VoirieDAO voirieDao = new VoirieDAO();
		Vector<Voirie> v = voirieDao.LoadVoirie();
		System.out.println(v.size());
		for (int i =0; i <10; i++){
		System.out.print(v.elementAt(i).getToponyme()+" longueur= ");System.out.println(v.elementAt(i).getMultiLineString().length());
		}

		
		//INITIALISATION DU BUFFER OBJET
		//Instanciation du l'objet Vecteurs d'Objets en mémoire
		//recupere une surface de X*X maille centrée sur l'obj.	
		Buffer objet_en_memoire2 = new Buffer(60, 6000, 6000, 100, 10);
		
		//CREATION D'UN VECTEUR DE COORDONNEES DE MAILLES POUR TEST
		//Cette liste est crée d'après la taille de l'espace visible décidé en utilisant l'embryon_buffer_visible (tableau d'indice de maille relatif)
		Vector<int[]> liste_de_mailles = new Vector<int[]>();
		for (int j = 0; j < objet_en_memoire2.embryon_buffer_visible.size(); j++ ){
			int[] tmp = { objet_en_memoire2.embryon_buffer_visible.elementAt(j)[0] + objet_en_memoire2.mailleobservateur_i, 
					   objet_en_memoire2.embryon_buffer_visible.elementAt(j)[1] + objet_en_memoire2.mailleobservateur_j};
			liste_de_mailles.add(tmp);
		}
		
		Vector<Vector<Object>> objet_visible = objet_en_memoire2.getObjet_par_maille(liste_de_mailles);
		//FIN INITIALISATION
		
		
		//Simulation Deplacement de l'observateur
		for (int i =0; i<25; i++){
			 float Xobs = 6000 + 100*i;
			 float Yobs = 6000 + 100*i;
			 
			 int mobsi = (int)Xobs/100;
			 int mobsj = (int)Yobs/100;
			 
			 //Mise à jour de la liste de maille visible après déplacement de l'observateur
			 liste_de_mailles.clear();
			 
			 //SIMULATION DEPLACEMENT MOBILE
				for (int j = 0; j < objet_en_memoire2.embryon_buffer_visible.size(); j++ ){
					int[] tmp = { objet_en_memoire2.embryon_buffer_visible.elementAt(j)[0] + mobsi, 
							   objet_en_memoire2.embryon_buffer_visible.elementAt(j)[1] + mobsj};
					liste_de_mailles.add(tmp);
				}
				//Penser à automatiser le nettoyage des buffers
				objet_visible.clear();
				objet_visible = objet_en_memoire2.getObjet_par_maille(liste_de_mailles);
			 
				//STATISTIQUES
				int nbreobj = 0;
				for (int j =0; j< objet_visible.size(); j++){
					//System.out.println(ploup.elementAt(i).size()+" objets chargés dans la maille");
					nbreobj = nbreobj + objet_visible.elementAt(j).size();
				}
//				
				System.out.println(objet_en_memoire2.NbreObjets() + " Objets dans le Buffers");
				System.out.println(objet_visible.size()+" Mailles à afficher ");
				System.out.println(nbreobj + " objets chargé depuis le Buffer");
//				//FIN STATISTIQUES
				
				
				
		}
			
			
//		
		
			//SELECTION GEOMETRIQUE SUR LA BASE DE DONNEES
			//passage d'une position observateur et interval de maille
			//maisonDao.selection_geographique(objet_en_memoire, (float)6000.0, (float)6000.0, 100);
			//objet_en_memoire2.vide_Objet_en_memoire();
			
			
		//	daoObjets.selection_geographique(objet_en_memoire2, Xobs, Yobs, 100);

			
//			//CREATION D'UN VECTEUR DE COORDONNEES DE MAILLES POUR TEST
//			Vector<int[]> liste_de_mailles = new Vector<int[]>();
//			for (int j = 56; j < 65; j++ ){
//				for (int k =56; k < 65; k++ ){
//					int[] maill = {j, k};
//					liste_de_mailles.add(maill);
//				}
			
			
		//TETS SELECTION DU SUBSET DU BUFFER
//			Vector<Vector<Object>> ploup = objet_en_memoire2.getObjet_par_maille(liste_de_mailles);
//			
			//STATISTIQUES
			int nbreobj = 0;
			for (int j =0; j< objet_visible.size(); j++){
				//System.out.println(ploup.elementAt(i).size()+" objets chargés dans la maille");
				nbreobj = nbreobj + objet_visible.elementAt(j).size();
			}
//			
			System.out.println(objet_en_memoire2.NbreObjets() + " Objets dans le Buffers");
			System.out.println(objet_visible.size()+" Mailles à afficher ");
			System.out.println(nbreobj + " objets chargé depuis le Buffer");
//			//FIN STATISTIQUES
//
//		}
			
//			
//			for (int i = 0; i< liste_de_mailles.size(); i++){
//				System.out.print(liste_de_mailles.elementAt(i)[0]+" "+ liste_de_mailles.elementAt(i)[1]+ " ");
//				System.out.print(liste_de_mailles.elementAt(i)[0] - objet_en_memoire.mailleobservateur_i + objet_en_memoire.centre_relatif + " ");
//				System.out.println(liste_de_mailles.elementAt(i)[1] - objet_en_memoire.mailleobservateur_j + objet_en_memoire.centre_relatif);
			}
			

			
			
			
			
}



	
