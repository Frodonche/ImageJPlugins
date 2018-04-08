// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor

import java.lang.Math;
import java.util.Arrays;

public class TP2_Q4 implements PlugInFilter {
	ImagePlus imp;
	static int MAX_COLORS = 256*256*256;
	int[] counts = new int[MAX_COLORS]; 		// Histogramme des couleurs
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp; 						// Rem.: pas utilise dans run()
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING; 	// Traite les images couleurs ; pas d'annulation possible
	}
	
	public void run(ImageProcessor ip) {
		int[] pixels = (int[])ip.getPixels(); //recuperation des pixels de l'image dans le tableau pixels
		int L = ip.getWidth ();
		int H = ip.getHeight ();
		
		FloatProcessor fp;
		double[] test;
		
		// Initialisation de HI
		double HI[] = new double[256];		
		for (int ng=0; ng < 256;  ng++){
			HI[ng] = 0;
		}
		
		int[] rgb = new int[3];
		int temp=0;
		// Remplissage de HI
		int l = 0, h = 0;
		for (l = 0; l < L; l++){
			for (h = 0; h < H; h++){
				ip.getPixel(l,h,rgb);
				temp = (int) rgb[0];
				HI[temp] += 1;
			}
		}

		// Initialisation et Remplissage de PI
		double PI[] = new double[256];
		for (int i = 0; i < 256; i++){
			PI[i] = HI[i] / (L*H);
			//IJ.log(PI[i]+"");
		}
		
		// Initialisation de RI
		double RI[] = new double[256];
		for (int r = 0; r < 256; r++){
			RI[r] = 0;
		}
		
		// Remplissage de RI
		for (int p = 0; p < 256; p++){
			if(p == 0)
				RI[p] = PI[p];
			else
				RI[p] = RI[p-1] + PI[p];
			//IJ.log(RI[p]+"");
		}
		
		// Calcul de la transformation
		for(int i = 0; i < L; i++){
			for(int j = 0; j < H; j++){
				ip.getPixel(i,j,rgb);
				rgb[0] = (int)(255 * RI[rgb[0]]);
				rgb[1] = (int)(255 * RI[rgb[1]]);
				rgb[2] = (int)(255 * RI[rgb[2]]);
				ip.putPixel(i, j, rgb);
			}
		}
	}
}