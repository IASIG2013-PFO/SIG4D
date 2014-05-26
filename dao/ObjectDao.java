package iasig.dao;

import iasig.dao.user.Objet_Postgre;
import iasig.dao.user.Buffer;

import org.postgis.PGgeometry;

public abstract class ObjectDao<T> extends DAO {

	/**
	 * Méthode abstraite. Permet de récupérer un objet via son ID
	 * @param id
	 * @return T; un ou plusieurs objets possédant le même id
	 */
	public abstract T find(long id);
	
	/**
	 * Méthode abstraite. Permet de créer une entrée dans la base de données
	 * @param obj
	 */
	public abstract T create(T obj);
	
	/**
	 * Méthode abstraite. Permet de mettre à jour les données d'une entrée dans la base 
	 * @param	obj
	 */
	public abstract T update(T obj);
	
	/**
	 * Permet la suppression d'une entrée de la base
	 * @param obj
	 */
	public abstract void delete(T obj);
	
	/**
	 * Méthode abstraite. Permet la selection geographique d'objets Postgis par paramtrage de maille; ne retourne rien
	 * @param obj	Objet_en_memoire 
	 * @param Xobs	Position géographique de l'observateur 
	 * @param Yobs	Position géographique de l'observateur 
	 * @param interval_de_maille le pas de maille
	 */
	public abstract void selection_geographique(Buffer obj, Float Xobs, Float Yobs, int interval_de_maille);
	
	/**
	 * Méthode abstraite. Permet la selection geographique d'objets Postgis par polygon; ne retourne rien
	 * @param polygone	PGgeometry polygone
	 * @param obj	Objet_Postgre<T> 
	 */
	public abstract void selection_geographique_par_polygone(Objet_Postgre<T> obj, PGgeometry polygone);


 
}
