// Importation des paquets necessaires. Le plugin n'est pas lui-même un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import java.awt.*; 						// pour classe Rectangle
import ij.gui.*;						// pour classe GenericDialog et Newimage

// Nom de la classe = nom du fichier.  Implemente l'interface PlugInFilter
public class Rotation_AntiHoraireCustom implements PlugInFilter {
	
    public int setup(String arg, ImagePlus imp) {
		// Accepte tous types d'images, piles d'images et RoIs, meme non rectangulaires
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING;
    }

    public void run(ImageProcessor ip) {
        GenericDialog gd = new GenericDialog ( " Rotation ",IJ.getInstance () );
	
        gd.addSlider ( " facteur ", 0.0 , 360.0 , 2.0 );
        gd.showDialog ();
        // Trouver comment recuperer la valeur 
        if ( gd.wasCanceled () ) {
            IJ.error ( " PlugIn cancelled " );
        return ;
        }
    
    
		int[][] pixels = ip.getIntArray();
		int H = ip.getHeight();
		int L = ip.getWidth();
		int teta = 90; // l'angle de rotation
        int max;
		
		ImagePlus result = NewImage.createByteImage (" Rotation antihoraire 90 ", H, L, 1,NewImage.FILL_BLACK );
		ImageProcessor ipr = result.getProcessor ();
		
		//byte [] pixelsr = ( byte []) ipr.getPixels ();
		
		int xp, yp, tmp;
		
		double angle_cos = Math.cos(Math.toRadians(teta));
        double angle_sin = Math.sin(Math.toRadians(teta));

        int distx, disty;
        int cx = L / 2;
        int cy = H / 2;
		
        for (int x = 0; x < (pixels.length); x++) {
            distx = x - cx ;//- (H/5);
			for (int y = 0; y < pixels[0].length; y++) {
				tmp = pixels[x][y];
				//xp = y;
				//yp = L - x;
				
				disty = y - cy ;//- (L/4);
				
				xp = (int) (distx * angle_cos - disty * angle_sin + cx);
                yp = (int) (distx * angle_sin + disty * angle_cos + cy);
				
                //IJ.log("x : "+ x + " y : "+ y + " xp : "+ xp +" yp : " +yp);
				ipr.putPixel(xp, yp, tmp);
			} 
		}
		
		result.show ();
		result.updateAndDraw ();
    }
    
}
 
