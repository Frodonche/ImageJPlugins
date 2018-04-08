// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import java.awt.*; 						// pour classe Rectangle

// Nom de la classe = nom du fichier.  Implémente l'interface PlugInFilter
public class Flip_Horizontal implements PlugInFilter {
	
    public int setup(String arg, ImagePlus imp) {
		// Accepte tous types d'images, piles d'images et RoIs, même non rectangulaires
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING;
    }

    public void run(ImageProcessor ip) {
		int[][] pixels = ip.getIntArray();

        for (int i = 0; i < (pixels.length / 2); i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				int tmp = pixels[i][j];
				ip.putPixel(i, j, pixels[pixels.length-i-1][j]);
				ip.putPixel(pixels.length-i-1, j, tmp);
			} 
		}
    }
}
