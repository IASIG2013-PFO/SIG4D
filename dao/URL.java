package iasig.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class URL extends DAO {
	
	private int id;
	private String nom;
	private String[] url;
	
	public URL(){}
	
	public URL(int id, String nom, String[] url){
		this.nom = nom;
		this.url = url;
		this.id = id;
	}
	
	public String[] getURL(){
		return url;
	}
	
	public String getNom(){
		return nom;
	}
	
	public int getID(){
		return id;
	}
	
	public static void creer_table_url(String nom_table){
		
		try {
			
			//1
			//PreparedStatement prepare = table_gestion.getInstance().prepareStatement("SELECT * FROM table_test;");
			//2
			Statement state = connection_statique.createStatement();
			//3
			connection_statique.setAutoCommit(false);
			//Et 4 appels � la m�thode getInstance()
			//DatabaseMetaData meta = table_gestion.getInstance().getMetaData();
			String sql = "CREATE TABLE "+ nom_table + 
	        		 "(id Serial PRIMARY KEY, nom text,"+
	        		    "URL text);";
			state.executeUpdate(sql);
			connection_statique.commit();
	       } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	    }
	}
	
	public static void insert_table_url(String nom_table, String nom, String url[]){
		
		try {
			//1
			//PreparedStatement prepare = table_gestion.getInstance().prepareStatement("SELECT * FROM table_test;");
			//2
			Statement state = connection_statique.createStatement();
			//3
			connection_statique.setAutoCommit(false);
			//Et 4 appels � la m�thode getInstance()
			//DatabaseMetaData meta = table_gestion.getInstance().getMetaData();
			String coordonnees="";
			for(int i=0;i<url.length-1; i++){
				String x=url[i];
				coordonnees=coordonnees+x+" ";
			}
			String x=url[url.length-1];
			coordonnees=coordonnees+x;
			System.out.println(url.length);
			
			String sql = "INSERT INTO " +nom_table+ "(nom, URL) "
		               + "VALUES ('"+nom+"', '"+coordonnees+"');";
	         
			state.executeUpdate(sql);
			
			connection_statique.commit();
	       } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	       }
		
	}
	
	public static Vector extraire_url(){
		
		Vector URL_tot=new Vector();
		Vector URL_element=new Vector();
		try {
			//1
			//PreparedStatement prepare = table_gestion.getInstance().prepareStatement("SELECT * FROM table_test;");
			//2
			Statement state = connection_statique.createStatement();
			//3
			connection_statique.setAutoCommit(false);
			//Et 4 appels � la m�thode getInstance()
			//DatabaseMetaData meta = table_gestion.getInstance().getMetaData();
			
			
			for(int i=1; i<=20;i++){
				String sql="select id,nom, unnest(string_to_array(url, ' ')) AS URL_ from table_db_url WHERE id="+i+";";
				ResultSet rs = state.executeQuery( sql );
				URL_element.removeAllElements();
				
				int id=0;
				String name="";
				while ( rs.next() ) {
		            name = rs.getString("nom");
		            id = rs.getInt("id");
		            String url = rs.getString("URL_");
		            URL_element.addElement(url);
		        }
				
				String url[]=new String[URL_element.size()];
				for(int j=0; j<URL_element.size(); j++){
					url[j]=(String)URL_element.elementAt(j);
				}
				if(url.length!=0){
					URL_tot.addElement(new URL(id, name, url));
				}

			}
		} catch ( Exception e ) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		}
		
		return URL_tot;
		
	}
	
//	public static void main(String[] args) {
//		Vector URL_tot=new Vector();
//		URL_tot=extraire_url();
//		for(int i=0; i<URL_tot.size(); i++){
//			URL url=((URL)(URL_tot.elementAt(i)));
//			System.out.print(url.getNom()+"  "+url.getID()+" : ");
//			for(int j=0; j<url.getURL().length; j++){
//				System.out.print(url.getURL()[j]+" - ");
//			}
//			System.out.println();
//		}
//	}

}
