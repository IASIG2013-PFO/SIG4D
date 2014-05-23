package iasig.dao.user;

import iasig.dao.GenericDAO;

import java.util.Vector;

public class Buffer {

	/**
	 * le vecteur de vecteur, portant les objets maisons regroupés par appartenance de maille
	 */
	private Vector<Vector<Object>> objets_instanciated = new Vector<Vector<Object>>();
	/**
	 * la matrice indexant les éléments en mémoire en référence au maillage
	 */
	//La taille du Buffer Mémoire
	private int paramètre_de_generation;
	//les indices de la maille centrale fonction de la taille du buffer memoire
	public int centre_relatif;
	//La matrice indexant le vecteur de de vecteur d'objet relativement à la taille choisie du buffer mémoire
	private int[][] matrice_indexation;
	private int demi_largeur_maillage_memoire;

	//L'objet Buffer porte en attribut les indices de la maille observateur au moment du chargement des objets à
	//partir de la lecture de la BDD
	public int mailleobservateur_i;
	public int mailleobservateur_j;
	
	//La largeur de maille de l'espace visible
	public int dimension_espace_visible;
	//Un vecteur de int[] pour calcul des mailles de l'espaces visible (relatif->monde et vice versa)
	public Vector<int[]> embryon_buffer_visible = new Vector<int[]>();
	
	//un attribut de contrôle
	private int nombre_objets;
	
	
	/**
	 * Constructeur 1 - Vide
	 */
	public Buffer() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructeur 2 - contruit un 2d array pour le stockage des objets
	 * A la construction le Buffer des objets en mémoire s'initialise automatiquement et met à disposition les objets. 
	 * @param paramètre_de_generation - entier, l'interval de maille pris en mémoire (identique selon les deux dimensions)
	 * Il est possible de maintenir des buffers distinct pour différents points de vue.
	 * @param Xinit le point d'entrée dans le monde
	 * @param Yinit le point d'entrée dans le monde
	 * Le pas du maillage doit pouvoir être modifié pour permettre de travailler après une modification de ce paramètres sur les objets de a BDD
	 * @param interval_de_maille le pas du maillage
	 * @param dimension_espace_visible la dimension en maille de l'espace "visible" (les objets préchargé dans le buffer d'affichage)
	 */
	public Buffer(int paramètre_de_generation, float Xinit, float Yinit, int interval_de_maille, int dimension_espace_visible ){
		this();

		//blindage - le paramètre doit être impair
		if (paramètre_de_generation%2 == 0){
			paramètre_de_generation++;
		}
		//inscription des attributs; initialisation des tableaux et des listes
		this.paramètre_de_generation = paramètre_de_generation;
		this.dimension_espace_visible = dimension_espace_visible;
		this.centre_relatif = (int)(paramètre_de_generation/2);
		this.demi_largeur_maillage_memoire = (int)(paramètre_de_generation/2);
		//creation de la matrice 2D; Représentant le maillage relatif au point d'entrée fixé dans la maille centrale.
		this.matrice_indexation = new int[paramètre_de_generation][paramètre_de_generation];
		//creation indexation du vecteur de vecteur d'objet
		//initialisation de la variable de remplissage "index"
		int index = 0;
		for (int i = 0; i < paramètre_de_generation; i++ ){
			for (int j = 0; j < paramètre_de_generation; j++ ){
				//TODO faire vérifier l'indexation
				this.matrice_indexation[j][i] = index;
				//System.out.println(index);
				index++;
				
				this.objets_instanciated.add(new Vector<Object>());	
			}
		}
		//Creation du la liste embryon pour buffer affichage
		
		for (int j =0 ;j < this.dimension_espace_visible; j++ ){
			int l = -1 * (int)((this.dimension_espace_visible)/2);
			int m = -1 * (int)((this.dimension_espace_visible)/2) + j;
			for (int k =0 ;k < this.dimension_espace_visible; k++ ){
				int[] maill = {l, m};
				l = l + 1;
				this.embryon_buffer_visible.add(maill);
				System.out.println(maill[0]+" "+maill[1]);
			}
		}
			
		GenericDAO.selection_geographique_init(this, Xinit, Yinit, interval_de_maille);
	}
	
	//Accesseurs et setters	
	/**
	 * Permet de mettre à jour la maille_observateur courante à la MAJ de l'objet 
	 * @param obj Un objet
	 */
	public void set_Maille_Observateur(Integer i, Integer j){
		this.mailleobservateur_i = i; 
		this.mailleobservateur_j = j;
	}
	
	
	/**
	 * Permet de mettre à jour la dimension de l'espace visible  
	 * @param espace_visible entier, la dimension de l'espace visible en maille
	 */
	public void set_Maille_Observateur(int espace_visible){
		this.dimension_espace_visible = espace_visible;
	}
	
	
	/**
	 * Permet d'ajouter un objet 
	 * @param obj Un objet
	 */
	public   void AjoutObjet(Object obj, int mailleobservateur_i,int mailleobservateur_j ) {
		
		//methode de remplissage d'un vecteur <Object>
		//this.objets_instanciated3.add(null);
		//thisv2.add(new Maison());
		if(obj instanceof Maison){
			
			int deltai = ((Maison) obj).getMaille_i() - mailleobservateur_i;
			int deltaj =((Maison) obj).getMaille_j() - mailleobservateur_j;
			//decommenter pour verifier les mailles mises en mémoires
//			int a =deltai + this.centre_relatif;
//			int b =deltaj + this.centre_relatif;
//			System.out.print(a+ " ");System.out.println(b);
//			System.out.print(deltai+ " ");System.out.println(deltaj);
			this.objets_instanciated.elementAt(this.matrice_indexation[deltai + this.centre_relatif][deltaj + this.centre_relatif]).add(obj);
			nombre_objets++;
		}
		
	}
	
	/**
	 * Permet de récuperer le paramètre de génération de l'espace à mettre en mémoire
	 * valeur en nombre de maille
	 * @param obj Un objet
	 */
	public int get_paramètre_maille(){
		return this.paramètre_de_generation;
	}
	
	/**
	 * Permet de récuperer la largeur de l'interval de l'espace à mettre en mémoire 
	 * de chaque côté de la maille centrale.
	 * valeur en nombre de maille
	 * @param obj Un objet
	 */
	public int demi_espace_mémoire_maille(){
		return this.demi_largeur_maillage_memoire;
	}
	
	
	/**
	 * Retourne le vecteur stockant tous les éléments d'une maille disponible en mémoire
	 */
	public Vector<Object> getElement_parMaille(int maille_i, int maille_j) {
		
		//Passage en référence relative	
		Integer Relatif_i = maille_i - this.mailleobservateur_i + this.centre_relatif;
		Integer Relatif_j = maille_j - this.mailleobservateur_j + this.centre_relatif;
		//Copie
		return this.objets_instanciated.elementAt(this.matrice_indexation[Relatif_i][Relatif_j]);
	}
	/**
	 * Retourne le nombre d'objets disponibles en mémoire
	 */
	public   int NbreObjets(){
		return this.nombre_objets;
	}
	
	/**
	 * Permet de récupérer tous les objets situés dans une(ou plusieurs) mailles via une liste_de_maille
	 * @param liste_de_maille	vector[int] représentant la liste des mailles à interroger
	 */
	public Vector<Vector<Object>> getObjet_par_maille(Vector<int[]> liste_de_maille){
		
		Vector<Vector<Object>> tmp = new Vector<Vector<Object>>();
		//transformation du maillage vers coordonnées relatives et extraction du vecteur coorespondant
		for (int i = 0; i< liste_de_maille.size(); i++){
//			System.out.print(niveau.elementAt(i)[0] - this.mailleobservateur_i + this.centre_relatif + " ");
//			System.out.print(niveau.elementAt(i)[1] - this.mailleobservateur_j + this.centre_relatif + " ");
//			System.out.println(matrice_indexation[niveau.elementAt(i)[0] - this.mailleobservateur_i + this.centre_relatif][niveau.elementAt(i)[1] - this.mailleobservateur_j + this.centre_relatif]);
//			System.out.println(this.objets_instanciated.size());
			tmp.add(this.objets_instanciated.elementAt(matrice_indexation[liste_de_maille.elementAt(i)[0] - this.mailleobservateur_i + this.centre_relatif][liste_de_maille.elementAt(i)[1] - this.mailleobservateur_j + this.centre_relatif]));

		}
		//System.out.println(this.objets_instanciated.size());

		
		//tmp.add(this.objets_instanciated.elementAt(2108));
		return tmp;
		
		
		
	}
	
	/**
	 * Permet de vider le buffer d'objet 
	 */
	public void vide_Objet_en_memoire(){
		
		for (int i =0; i < this.objets_instanciated.size(); i++)
				this.objets_instanciated.elementAt(i).clear();
		
			this.mailleobservateur_i = 0;
			this.mailleobservateur_j = 0;
			this.nombre_objets = 0;
		}
			


	/**
	 * Retourne le nombre d'objets stockés
	 */
	//	public   int NbreObjets();
	
}
