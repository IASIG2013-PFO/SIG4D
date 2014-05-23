/**
 * 
 */
package iasig.dao;

import iasig.dao.*;
import iasig.dao.user.Buffer;
import iasig.dao.user.Objet_Postgre;
import iasig.dao.user.Raster_img_mnt;

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

import org.postgis.MultiLineString;
import org.postgis.PGgeometry;

/**
 * @author Francois
 *
 */
public class Raster_img_mntDAO extends ObjectDao<Raster_img_mnt> {


	@Override
	public Raster_img_mnt create(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raster_img_mnt update(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Raster_img_mnt obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Raster_img_mnt find(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public void selection_geographique_par_polygone(Objet_Postgre<Raster_img_mnt> obj,
			PGgeometry polygone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selection_geographique(Buffer obj, Float Xobs, Float Yobs,
			int interval_de_maille) {
		// TODO Auto-generated method stub
		
	}

}

