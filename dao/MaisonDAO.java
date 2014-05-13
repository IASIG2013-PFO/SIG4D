package iasig.dao;

import iasig.dao.DAO;
import iasig.dao.GDALtest;
import iasig.dao.user.Maison;
import iasig.dao.user.Objet_Maison;
import iasig.dao.user.Objet_Postgre;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BandedSampleModel;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.GCP;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;
import org.postgis.PGgeometry;

public class MaisonDAO extends ObjectDao<Maison> {

	
	
	@Override
	public Maison find(long id) {
		Maison home = new Maison();
		try {

	        ResultSet result = this .connect
	                                .createStatement(
	                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            ResultSet.CONCUR_UPDATABLE
	                                         ).executeQuery(
	                                            "SELECT * FROM maison2 WHERE maison_id = " + id
	                                         );
	        if(result.first())
	        		home = new Maison(
	        					result.getInt("maison_id"),
	        					result.getString("maison_X"),
	        					result.getString("maison_Y"),
	        					result.getString("maison_Z"),
	        					result.getString("maison_nom"),
	        					(PGgeometry)result.getObject("centroid"),
	        					result.getInt("niveau")
	        				 	);
	        
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return home;

	}
	
	@Override
	public Maison create(Maison obj) {
		try {
			
				PreparedStatement prepare = this	.connect
	                                                .prepareStatement(
	                                    	"INSERT INTO maison2 (maison_nom, maison_x, maison_y, maison_z, centroid, niveau, i, j) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
	                                                );
				prepare.setString(1, obj.getNom());
//				System.out.println(obj.getNom());
				
				prepare.setString(2, obj.getX());
				prepare.setString(3, obj.getY());
				prepare.setString(4, obj.getZ());
//				System.out.print(obj.getX());System.out.print(" "+obj.getY());System.out.println(" "+obj.getZ());
				
				prepare.setObject(5, obj.getCentroid());
//				System.out.println(obj.getCentroid());
				prepare.setObject(6, obj.getNiveau());
				prepare.setObject(7, obj.i);
				prepare.setObject(8, obj.j);
//				System.out.println(prepare.toString());

				prepare.executeUpdate();
				//this.connect.commit();
				prepare.close();

			
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}
	
	@Override
	public Maison update(Maison obj) {
		try {

	            this .connect	
	                 .createStatement(
	                	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                    ResultSet.CONCUR_UPDATABLE
	                 ).executeUpdate(
	                	"UPDATE maison SET maison_nom = '" + obj.getNom() + "'"+
	                	" WHERE maison_id = " + obj.getId()
	                 );
	            
			
			obj = this.find(obj.getId());
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    
	    return obj;
	}

	@Override
	public void delete(Maison obj) {
		try {
			
	            this    .connect
	                	.createStatement(
	                         ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                         ResultSet.CONCUR_UPDATABLE
	                    ).executeUpdate(
	                         "DELETE FROM maison WHERE maison_id = " + obj.getId()
	                    );
			
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	
	@Override
	public void selection_geographique(Objet_Postgre<Maison> obj, PGgeometry polygone){
		
		try {
			
			//vidage préalable du vecteur
			obj.VideObjets();
			
	        ResultSet result = this .connect
	                                .createStatement(
	                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            ResultSet.TYPE_SCROLL_INSENSITIVE
	                                         ).executeQuery(
	                                        		
	     //" SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('POLYGON((2000 2000,8000 2000, 8000 8000, 2000 8000, 2000 2000))', 4326) )  ;" 
	      " SELECT * FROM maison2 WHERE ST_WITHIN(centroid, ST_GeomFromText('"+polygone.getValue()+"', 4326) ) ;"
	                                        		 );
	        //System.out.println(result.toString());
			Maison home1 = new Maison();


			while(result.next()){
				if(result.absolute(result.getRow()))
				//System.out.println(result.getInt("maison_id"));
					
	        		home1 = new Maison(
	        					result.getInt("maison_id"),
	        					result.getString("maison_x"),
	        					result.getString("maison_y"),
	        					result.getString("maison_z"),
	        					result.getString("maison_nom"),
	        					(PGgeometry)result.getObject("centroid"),
	        					result.getInt("niveau")
	        				 	);
	        		//retourne adresses objets	
					//System.out.print("Maison_id "+home1.getId()+" ");System.out.println("@ "+home1.toString());
					//on passe en paramètre la maille contenant l'observateur
					obj.AjoutObjet(home1, 50, 50);
	        					}
	        	
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }

		
	}
	

	
}












