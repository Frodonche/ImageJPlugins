// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour classe GenericDialog et Newimage

public class Filtre_Moyenneur implements PlugInFilter {

	public int setup(String arg, ImagePlus imp) {

		return DOES_8G;
	}

	public void run( ImageProcessor ip){
		int w = ip.getWidth ();
		int h = ip.getHeight ();
		byte [] pixels = ( byte []) ip.getPixels ();
		ImagePlus result = NewImage.createByteImage (" Filtrage ", w, h, 1,NewImage.FILL_BLACK );
		ImageProcessor ipr = result.getProcessor ();
		byte [] pixelsr = ( byte []) ipr.getPixels ();
		int [][] masque = {{1 , 1, 1}, {1, 1, 1}, {1, 1, 1}};
		int n = 1; // taille du demi - masque

		//A COMPLETER
		for (int y = ...; y < ...; y++)
			for ( int x = ...; x < ...; x++) {
				//A COMPLETER
				pixelsr [x+y*w] = ...;
			}
		result.show ();
		result.updateAndDraw ();
	}
