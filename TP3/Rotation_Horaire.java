// Importation des paquets necessaires. Le plugin n'est pas lui-même un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import java.awt.*; 						// pour classe Rectangle

// Nom de la classe = nom du fichier.  Implemente l'interface PlugInFilter
public class Rotation_Horaire implements PlugInFilter {
	
    public int setup(String arg, ImagePlus imp) {
		// Accepte tous types d'images, piles d'images et RoIs, meme non rectangulaires
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING;
    }

    public void run(ImageProcessor ip) {
		int[][] pixels = ip.getIntArray();
		int H = ip.getHeight();
		int L = ip.getWidth();
		int teta = 90; // l'angle de rotation

        for (int i = 0; i < (pixels.length / 2); i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				int tmp = pixels[i][j];
				
				ip.putPixel(i, j, pixels[pixels.length-i-1][j]);
				ip.putPixel(pixels.length-i-1, j, tmp);
			} 
		}
    }
}
