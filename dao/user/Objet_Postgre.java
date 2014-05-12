package iasig.dao.user;


public abstract class Objet_Postgre<T> {
	
	

	/**
	 * Permet d'ajouter un objet 
	 * @param obj Un objet
	 */
	public abstract  void AjoutObjet(T obj);
	
	/**
	 * Permet de récupérer un objet via son niveau
	 * @param niveau	entier représentant le niveau
	 */
	public abstract  void getObjet_par_niveau(int niveau);
	
	/**
	 * Permet de vider le vecteur d'objet 
	 */
	public abstract void VideObjets();

	/**
	 * Retourne le nombre d'objets stockés
	 */
	public abstract  int NbreObjets();
	
	public abstract T getElement(int Index);
	
	//public abstract T getElement_par_distance(float rayon);

}