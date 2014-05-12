package iasig.dao.user;

import org.postgis.PGgeometry;
import org.postgis.Point;


public class Lampadaire {
	
	private Integer id;
	private Integer gid;
	private float X;
	private float Y;	
	private PGgeometry centroid;
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
	
}

