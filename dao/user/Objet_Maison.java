package iasig.dao.user;

import java.util.Vector;


public class Objet_Maison extends Objet_Postgre<Maison> {

	private Vector<Maison> objets_instanciated = new Vector<Maison>();
	private int nombre_objets;


	@Override
	public void AjoutObjet(Maison maison){
		objets_instanciated.add(maison);
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
