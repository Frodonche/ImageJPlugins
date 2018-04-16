// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;							// pour GenericDialog
public class Change_Echelle implements PlugInFilter {
	
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}
	
	public void run ( ImageProcessor ip){
        // dialogue permettant de fixer la valeur du facteur d’echelle
        GenericDialog gd = new GenericDialog ( " Facteur d’echelle ",IJ.getInstance () );
	
        gd.addSlider ( " facteur ", 0.0 , 10.0 , 2.0 );
        gd.showDialog ();
        // Trouver comment recuperer la valeur 
        if ( gd.wasCanceled () ) {
            IJ.error ( " PlugIn cancelled " );
        return ;
        }
        
        int facteur_echelle = 2;
        
        double ratio = ( double ) gd.getNextNumber ();
        int w = ip.getWidth ();
        int h = ip.getHeight ();
        
        int wp = facteur_echelle * w;
        int hp = facteur_echelle * h;
        
        byte [] pixels = ( byte []) ip.getPixels ();
        
        
        
        ImagePlus result = NewImage.createByteImage(" Retailler " , wp, hp, 1, NewImage.FILL_BLACK );
        ImageProcessor ipr = result.getProcessor ();
        byte [] pixelsr = ( byte []) ipr.getPixels ();
        
        for(int x = 0; x < wp ; x++){
            for(int y = 0; y < hp; y++){
                ipr.putPixel(x, y, ipr.getPixel(x/facteur_echelle, y/facteur_echelle));
            }
        }
        
        result.show ();
        result.updateAndDraw ();
	}
}



