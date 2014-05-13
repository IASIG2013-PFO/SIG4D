package iasig.dao.user;

import java.util.Vector;


public class Objet_Maison extends Objet_Postgre<Maison> {

	private Vector<Maison> objets_instanciated = new Vector<Maison>();
	/**
	 * le vecteur de vecteur, portant les objets maisons regroupés par appartenance de maille
	 */
	private Vector<Vector<Maison>> objets_instanciated2 = new Vector<Vector<Maison>> ();
	/**
	 * la matrice indexant les éléments en mémoire en référence au maillage
	 */
	private int[][] matrice_des_objets_instancies;
	private int nombre_objets;
	
	
	/**
	 * Constructeur 1 - Vide
	 */
	public Objet_Maison() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructeur 2 - contruit un 2d array pour le stockage des objets
	 * @param maille_interval - entier, l'interval de maille pris en mémoire
	 */
	
	public Objet_Maison(int maille_interval){
		this();
		//creation de la matrice
		this.matrice_des_objets_instancies = new int[maille_interval][maille_interval];
		//creation indexation du vecteur de vecteur d'objet
		//initialisation de la variable de remplissage
		int index = 0;
		for (int i = 0; i < maille_interval; i++ )
			for (int j = 0; j < maille_interval; i++ )
				this.matrice_des_objets_instancies[i][j] = index++; 
	}
	
	
	@Override
	public void AjoutObjet(Maison maison){
		objets_instanciated.add(maison);
		objets_instanciated2.elementAt(	matrice_des_objets_instancies[maison.i][maison.j]).add(maison);
		nombre_objets++;
	}
	
	@Override
	public void getObjet_par_niveau(int niveau) {
		for (int i = 0; i<objets_instanciated.size(); i++)
			if (objets_instanciated.elementAt(i).getNiveau() == 1)
				System.out.println(objets_instanciated.elementAt(i).getNom());
	}
	
	@Override
	public void VideObjets() {
		objets_instanciated.clear();
		nombre_objets =0;
	}

	@Override
	public int NbreObjets() {
		return objets_instanciated.size();
		
	}

	@Override
	public Maison getElement(int index) {
		return objets_instanciated.elementAt(index);
	}
	
	

}
