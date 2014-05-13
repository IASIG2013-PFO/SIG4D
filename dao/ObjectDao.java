package iasig.dao;

import iasig.dao.user.Objet_Postgre;

import java.awt.image.BufferedImage;

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
	 * Méthode abstraite. Permet la selection geographique d'objets Postgis par polygon; ne retourne rien
	 * @param polygone	PGgeometry polygone
	 * @param obj	Objet_Postgre<T> 
	 */
	public abstract void selection_geographique(Objet_Postgre<T> obj, PGgeometry polygone);
	
	//public abstract void selection_par_maille(Objet_Postgre<T> obj, int i_min, int j_min,int i_max, int j_max );

 
}
