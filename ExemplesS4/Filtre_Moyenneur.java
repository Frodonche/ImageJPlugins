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
		
		int dimensions_masque = 7; // taille du masque (ex : 3 donne masque 3*3)
		int [][] masque = new int[dimensions_masque][dimensions_masque];
		int sommeCoeffValeurAbs = 0;


		// remplissage du masque
		for(int ligne = 0; ligne < dimensions_masque; ligne++){
            for(int colonne = 0; colonne < dimensions_masque; colonne ++){
                masque[ligne][colonne] = 1;
                sommeCoeffValeurAbs += 1;
            }
		}
		
		int n = dimensions_masque/2; // taille du demi - masque
        // masque 3*3 donne n = 1, 5*5 donne n = 2, 7*7 donne n = 3...
		
		int somme;
		for (int y = 0; y < h; y++){
			for ( int x = 0; x < w; x++) {
                    somme = 0;
                    for (int i = -n; i<= n; i++){
                        for (int j = -n; j <= n; j++){
                            if(x+i+(y+j)*w >= 0 && x+i+(y+j)*w < pixelsr.length && x+y*w < pixelsr.length){
                                somme = somme + (pixels[x+i+(y+j)*w]&0xFF) * masque[n+i][n+j];
                            }
                        
                        
                        }
                    }
                pixelsr[x+y*w] = (byte) Math.round((double)(somme/sommeCoeffValeurAbs));
			
			}
        }
		result.show ();
		result.updateAndDraw ();
	}
}
