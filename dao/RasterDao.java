package iasig.dao;

import iasig.dao.user.Raster_Postgre;

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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.GCP;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;
import org.postgis.PGgeometry;

public class RasterDao extends DAO {

	/**
	 *Envoi une requète SQL pour attaquer la BDD et accèder au tuilage des Raster
	 *Remplis le Raster_postgre passé en paramètres (mnt ou orthophoto).
	 * @param	tuile	Raster_Postgre tuile; typiquement le mnt ou l'ortho
	 * @param	polygone	PGgeometry polygone
	 */
	public static void Recup_tuiles_depuis_BDD(Raster_Postgre tuile, PGgeometry polygone) {

		
		byte[] content = null;
		//charge tous les drivers GDAL
        gdal.AllRegister();
        //vidage du tuilage en mémoire
        tuile.videRaster();
		
		try {
			//ST_GeomFromText('MULTIPOLYGON(((100 100, 100 0, 0 0, 0 100, 100 100))
	        ResultSet result = connection_statique
	                                .createStatement(
	                                        	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            ResultSet.CONCUR_UPDATABLE
	                                         ).executeQuery(
                           		 
	             "SELECT ST_AsTiff(rast) as tmp FROM bath50m  WHERE ST_Intersects(rast, ST_GeomFromText('"+polygone.getValue()+"', 4326) ) ;"//rid = 550"//
	                                         );
	       
	    
	        while(result.next()){
				if (result.absolute(result.getRow())){

					content = result.getBytes("tmp"); 
	        		System.out.println(content.getClass());
	        		
	        	    
	      	      //Create in-memory file and initialize it with the content
	       		  gdal.FileFromMemBuffer("/vsimem/tiffinmem", content);
	       		  //Open the in-memory file
	       		  Dataset poDataset = gdal.Open("/vsimem/tiffinmem");
	       		  
	       		double[] adfGeoTransform = new double[6];

		        System.out.println("Driver: " + poDataset.GetDriver().GetDescription());

		        System.out.println("Size is: " + poDataset.getRasterXSize() + "x"
		            + poDataset.getRasterYSize() + "  bands:"
		            + poDataset.getRasterCount());

		        if (poDataset.GetProjectionRef() != null)
		            System.out.println("Projection is `" + poDataset.GetProjectionRef()
		                + "'");

//		        Hashtable dict = poDataset.GetMetadata_Dict("");
//		        Enumeration keys = dict.keys();
//		        System.out.println(dict.size() + " items of metadata found (via Hashtable dict):");
//		        while (keys.hasMoreElements())
//		        {
//		            String key = (String) keys.nextElement();
//		            System.out.println(" :" + key + ":==:" + dict.get(key) + ":");
//		        }
//
//		        Vector list = poDataset.GetMetadata_List("");
//		        Enumeration enumerate = list.elements();
//		        System.out.println(list.size() + " items of metadata found (via Vector list):");
//		        while (enumerate.hasMoreElements())
//		        {
//		            String s = (String) enumerate.nextElement();
//		            System.out.println(" " + s);
//		        }
//
//		        Vector GCPs = new Vector();
//		        poDataset.GetGCPs(GCPs);
//		        System.out.println("Got " + GCPs.size() + " GCPs");
//		        Enumeration e = GCPs.elements();
//		        while (e.hasMoreElements())
//		        {
//		            GCP gcp = (GCP) e.nextElement();
//		            System.out.println(" x:" + gcp.getGCPX() +
//		                " y:" + gcp.getGCPY() +
//		                " z:" + gcp.getGCPZ() +
//		                " pixel:" + gcp.getGCPPixel() +
//		                " line:" + gcp.getGCPLine() +
//		                " line:" + gcp.getInfo());
//		        }

		        poDataset.GetGeoTransform(adfGeoTransform);
		        {
		        	Double[] e1 = {adfGeoTransform[0], adfGeoTransform[3]};
		        	//met a disposition les coordonnées de rattachement d'une tuile
		        	tuile.addXYOrigine(e1);
		        	//Display origine to console for check
//		            System.out.println("Origin = (" + adfGeoTransform[0] + ", "
//		                + adfGeoTransform[3] + ")");
//
//		            System.out.println("Pixel Size = (" + adfGeoTransform[1] + ", "
//		                + adfGeoTransform[5] + ")");
		        }

		        Band poBand = null;
		        //double[] adfMinMax = new double[2];
		        Double[] max = new Double[1];
		        Double[] min = new Double[1];

		        int bandCount = poDataset.getRasterCount();
		        ByteBuffer[] bands = new ByteBuffer[bandCount];
		        int[] banks = new int[bandCount];
		        int[] offsets = new int[bandCount];

		        int xsize = poDataset.getRasterXSize();
		        int ysize = poDataset.getRasterYSize();
		        int pixels = xsize * ysize;
		        int buf_type = 0, buf_size = 0;

		        for (int band = 0; band < bandCount; band++)
		        {
		            /* Bands are not 0-base indexed, so we must add 1 */
		            poBand = poDataset.GetRasterBand(band + 1);

		            buf_type = poBand.getDataType();
		            buf_size = pixels * gdal.GetDataTypeSize(buf_type) / 8;

		            System.out.println(" Data Type = "
		                + gdal.GetDataTypeName(poBand.getDataType()));
		            System.out.println(" ColorInterp = "
		                + gdal.GetColorInterpretationName(poBand
		                .GetRasterColorInterpretation()));

		            System.out.println("Band size is: " + poBand.getXSize() + "x"
		                + poBand.getYSize());

		            poBand.GetMinimum(min);
		            poBand.GetMaximum(max);
		            if (min[0] != null || max[0] != null)
		            {
		                System.out.println("  Min=" + min[0] + " Max="
		                    + max[0]);
		            }
		            else
		            {
		                System.out.println("  No Min/Max values stored in raster.");
		            }

		            if (poBand.GetOverviewCount() > 0)
		            {
		                System.out.println("Band has " + poBand.GetOverviewCount()
		                    + " overviews.");
		            }

		            if (poBand.GetRasterColorTable() != null)
		            {
		                System.out.println("Band has a color table with "
		                    + poBand.GetRasterColorTable().GetCount() + " entries.");
		                for (int i = 0; i < poBand.GetRasterColorTable().GetCount(); i++)
		                {
		                    System.out.println(" " + i + ": " +
		                        poBand.GetRasterColorTable().GetColorEntry(i));
		                }
		            }

		            System.out.println("Allocating ByteBuffer of size: " + buf_size);

		            ByteBuffer data = ByteBuffer.allocateDirect(buf_size);
		            data.order(ByteOrder.nativeOrder());

		            int returnVal = 0;
		            try
		            {
		                returnVal = poBand.ReadRaster_Direct(0, 0, poBand.getXSize(),
		                    poBand.getYSize(), xsize, ysize,
		                    buf_type, data);
		            }
		            catch (Exception ex)
		            {
		                System.err.println("Could not read raster data.");
		                System.err.println(ex.getMessage());
		                ex.printStackTrace();
		            }
		            if (returnVal == gdalconstConstants.CE_None)
		            {
		                bands[band] = data;
		            }
		            else
		            {
		                printLastError();
		            }
		            banks[band] = band;
		            offsets[band] = 0;
		        }

		        
		        
		        DataBuffer imgBuffer = null;
		        SampleModel sampleModel = null;
		        int data_type = 0, buffer_type = 0;

		        if (buf_type == gdalconstConstants.GDT_Byte)
		        {
		            byte[][] bytes = new byte[bandCount][];
		            for (int i = 0; i < bandCount; i++)
		            {
		                bytes[i] = new byte[pixels];
		                bands[i].get(bytes[i]);
		            }
		            imgBuffer = new DataBufferByte(bytes, pixels);
		            buffer_type = DataBuffer.TYPE_BYTE;
		            sampleModel = new BandedSampleModel(buffer_type,
		                xsize, ysize, xsize, banks, offsets);
		            data_type = (poBand.GetRasterColorInterpretation() ==
		                gdalconstConstants.GCI_PaletteIndex) ?
		                BufferedImage.TYPE_BYTE_INDEXED : BufferedImage.TYPE_BYTE_GRAY;
		        }
		        else if (buf_type == gdalconstConstants.GDT_Int16)
		        {
		            short[][] shorts = new short[bandCount][];
		            for (int i = 0; i < bandCount; i++)
		            {
		                shorts[i] = new short[pixels];
		                bands[i].asShortBuffer().get(shorts[i]);
		            }
		            imgBuffer = new DataBufferShort(shorts, pixels);
		            buffer_type = DataBuffer.TYPE_USHORT;
		            sampleModel = new BandedSampleModel(buffer_type,
		                xsize, ysize, xsize, banks, offsets);
		            data_type = BufferedImage.TYPE_USHORT_GRAY;
		        }
		        else if (buf_type == gdalconstConstants.GDT_Int32)
		        {
		            int[][] ints = new int[bandCount][];
		            for (int i = 0; i < bandCount; i++)
		            {
		                ints[i] = new int[pixels];
		                bands[i].asIntBuffer().get(ints[i]);
		            }
		            imgBuffer = new DataBufferInt(ints, pixels);
		            buffer_type = DataBuffer.TYPE_INT;
		            sampleModel = new BandedSampleModel(buffer_type,
		                xsize, ysize, xsize, banks, offsets);
		            data_type = BufferedImage.TYPE_CUSTOM;
		        }
		        WritableRaster raster = Raster.createWritableRaster(sampleModel, imgBuffer, null);
		        
		        BufferedImage img = null;
		        ColorModel cm = null;
		        
		        if (poBand.GetRasterColorInterpretation() ==
			            gdalconstConstants.GCI_PaletteIndex)
			        {
			            data_type = BufferedImage.TYPE_BYTE_INDEXED;
			            cm = poBand.GetRasterColorTable().getIndexColorModel(
			                gdal.GetDataTypeSize(buf_type));
			            img = new BufferedImage(cm, raster, false, null);
			            //Ajout de la tuile
		                tuile.ajoutTuile(img);
			            
			        }
		        else
		        {
		        	 ColorSpace cs = null;
			            if (bandCount > 2)
			            {
			                cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
			                cm = new ComponentColorModel(cs, true, true,
			                    ColorModel.OPAQUE, buffer_type);
			                img = new BufferedImage(cm, raster, false, null);
			                //Ajout de la tuile
			                tuile.ajoutTuile(img);
			            }
			            else
			            {
		                img = new BufferedImage(xsize, ysize,
		                    data_type);
		                //Ajout de la tuile
		                tuile.ajoutTuile(img);
			            
		                //img.setData(raster);
			            }
		            
		        }
		        
		        
	       		  //Close the dataset
	       		  poDataset = null;
	       		  //Release memory associated to the in-memory file
	       		  gdal.Unlink("/vsimem/tiffinmem");
			        

				}
	        }
	        
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }		
	}
	
	/**
	 *Envoi une requète SQL pour recupérer la valeur RGBA  (Z-value) d'un point
	 * @param	X	Coordonnée X interrogée
	 * @param	Y	Coordonnée Y interrogée
	 * @return		tableau de valeur, 1 colonne par Bande
	 */
	public static double[] get_RGBA_value(double X, double Y){ 
		
		//ST_Z — Return the Z coordinate of the point, or NULL if not available. Input must be a point.
			double[] rgba = new double[4];
	        try {
				ResultSet result = connection_statique 
				                        .createStatement(
				                                	ResultSet.TYPE_SCROLL_INSENSITIVE, 
				                                    ResultSet.CONCUR_UPDATABLE
				                                 ).executeQuery(
				                            		 "SELECT rid, ST_Value(rast,1,ST_GeomFromText('POINT("+X+" "+Y+")',4326)) As b1val,"+
				                            		 "ST_Value(rast,2,ST_GeomFromText('POINT("+X+" "+Y+")',4326)) As b2val,"+
				                            		 "ST_Value(rast,3,ST_GeomFromText('POINT("+X+" "+Y+")',4326)) As b3val,"+
				                            		 "ST_Value(rast,4,ST_GeomFromText('POINT("+X+" "+Y+")',4326)) As b4val"+
				                            		 "FROM bath50m"+
				                            		 "WHERE ST_Intersects(rast, ST_GeomFromText('POINT("+X+" "+Y+")',4326));"
				                                 );
				 rgba[0] = (double) result.getDouble("b1val");
				 rgba[1] = (double) result.getDouble("b2val");
				 rgba[3] = (double) result.getDouble("b3val");
				 rgba[4] = (double) result.getDouble("b4val");
				 
				 return rgba;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//tableau null si out-of-bound
				return null;
			}
	}
	
	
	public static void printLastError()
	    {
	        System.out.println("Last error: " + gdal.GetLastErrorMsg());
	        System.out.println("Last error no: " + gdal.GetLastErrorNo());
	        System.out.println("Last error type: " + gdal.GetLastErrorType());
	    }
	
}
