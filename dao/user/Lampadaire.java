package iasig.dao.user;

import org.postgis.PGgeometry;
import org.postgis.Point;


public class Lampadaire {
	
	private Integer id;
	private Integer gid;
	private String nom;
	private float rotz;
	private float echelle;
	//attribut typés Postgis
	private PGgeometry geom;
	private PGgeometry centroid;
	//Rattachement à une maille
	private int maillei;
	private int maillej;

	
	private float X;
	private float Y;
	

	private Integer niveau;
	protected String table = "lampadaires";
	
	
	
		//constructeur1 - vide
		public Lampadaire(){}
		//constructeur2
		public Lampadaire(Integer id, Integer gid, float X, float Y, PGgeometry centroid){
			this();
			this.id = id; this.gid = gid;
			this.X = X ; this.Y = Y; 
			this.centroid = centroid;
			}
		//constructeur3
				public Lampadaire(Integer id, String nom, float rotz,float echelle, PGgeometry geom, PGgeometry centroid){
					this();
					this.id = id;
					this.nom = nom; 
					this.rotz = rotz;
					this.echelle = echelle;
					this.geom = geom;
					this.centroid = centroid;
					Point pt = (Point)centroid.getGeometry();
					this.maillei = (int) ((int)pt.x/100); this.maillej = (int) ((int)pt.y/100); 
				}
		

	
		//méthodes publique Accesseur
		public PGgeometry getCentroid()
		{
			return centroid;
		}
		
		public PGgeometry getGeom()
		{
			return geom;
		}
		
		public float getRotz()
		{
			return rotz;	
		}
		
		public float getEchelle()
		{
			return echelle;	
		}
		
		public double getX1(){
			Point pt = (Point)centroid.getGeometry();
			return pt.x;
		}
		
		public double getY1(){
			Point pt = (Point)centroid.getGeometry();
			return pt.y;
		}


		public Integer getId()
		{
			return id;
		}
		
		public Integer getGid()
		{
			return gid;
		}
		
		public Integer getNiveau()
		{
			return niveau;
		}
		
		public float getX()
		{
			return X;
		}
		
		public float getY()
		{
			return Y; 
		}
	
		public String getNom()
		{
			return nom;
		}
		
		public Integer getMaille_i(){
			return maillei;
		}
		
		public Integer getMaille_j(){
			return maillej;
		}
}

