package iasig.dao.user;

import org.postgis.PGgeometry;
import org.postgis.Point;


public class Maison {
	
	private Integer id;
	private String X;
	private String Y;
	private String Z;
	private String nom = "noname";		
	private PGgeometry centroid;
	private Integer niveau;
	protected String table = "maison2";
	
	
	
		//constructeur1 - vide
		public Maison(){}
		//constructeur2
		public Maison(Integer id, String X, String Y, String Z, String nom, PGgeometry centroid, Integer niveau){
			this();
			this.id = id;
			this.X = X ; this.Y = Y ; this.Z = Z ; 
			this.nom = nom; 
			this.niveau = niveau;
			this.centroid = centroid;
			}
	
		//méthodes publique Accesseur
		public PGgeometry getCentroid()
		{
			return centroid;
		}
		
		
		
		public double getX1(){
			Point pt = (Point)centroid.getGeometry();

			return pt.x;
			
		}
		
		public double getY1(){
			Point pt = (Point)centroid.getGeometry();
			return pt.y;
		}
		
		public double getZ1(){
			Point pt = (Point)centroid.getGeometry();
			return pt.z;
		}
		
		public Integer getId()
		{
			return id;
		}
		
		public Integer getNiveau()
		{
			return niveau;
		}
		
		public String getX()
		{
			return X;
		}
		public String getY()
		{
			return Y; 
		}
		public String getZ()
		{
			return Z; 
		}
		
		public String getNom()
		{
			return nom; 
		}
		
		
		//méthodes publique 'Set'
		public void setNom(String nom) {
			this.nom = nom;
		}
}

