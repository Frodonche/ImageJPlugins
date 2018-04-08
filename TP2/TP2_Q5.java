// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor

import java.lang.Math;
import java.util.Arrays;

public class TP2_Q5 implements PlugInFilter {
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
		double HI1[] = new double[256];
		double HI2[] = new double[256]; 
		double HI3[] = new double[256]; 		
		for (int ng=0; ng < 256;  ng++){
			HI1[ng] = 0;
			HI2[ng] = 0;
			HI3[ng] = 0;
		}
		
		int[] rgb = new int[3];
		int temp1=0;
		int temp2=0;
		int temp3=0;
		// Remplissage de HI
		int l = 0, h = 0;
		for (l = 0; l < L; l++){
			for (h = 0; h < H; h++){
				ip.getPixel(l,h,rgb);
				temp1 = (int) rgb[0];
				temp2 = (int) rgb[1];
				temp3 = (int) rgb[2];
				HI1[temp1] += 1;
				HI2[temp2] += 1;
				HI3[temp3] += 1;
			}
		}

		// Initialisation et Remplissage de PI
		double PI1[] = new double[256];
		double PI2[] = new double[256];
		double PI3[] = new double[256];
		for (int i = 0; i < 256; i++){
			PI1[i] = HI1[i] / (L*H);
			PI2[i] = HI2[i] / (L*H);
			PI3[i] = HI3[i] / (L*H);
			//IJ.log(PI[i]+"");
		}
		
		// Initialisation de RI
		double RI1[] = new double[256];
		double RI2[] = new double[256];
		double RI3[] = new double[256];
		for (int r = 0; r < 256; r++){
			RI1[r] = 0;
			RI2[r] = 0;
			RI3[r] = 0;
		}
		
		// Remplissage de RI
		for (int p = 0; p < 256; p++){
			if(p == 0){
				RI1[p] = PI1[p];
				RI2[p] = PI2[p];
				RI3[p] = PI3[p];
			}
			else{
				RI1[p] = RI1[p-1] + PI1[p];
				RI2[p] = RI2[p-1] + PI2[p];
				RI3[p] = RI3[p-1] + PI3[p];
			}
			//IJ.log(RI[p]+"");
		}
		
		// Calcul de la transformation
		for(int i = 0; i < L; i++){
			for(int j = 0; j < H; j++){
				ip.getPixel(i,j,rgb);
				rgb[0] = (int)(255 * RI1[rgb[0]]);
				rgb[1] = (int)(255 * RI2[rgb[1]]);
				rgb[2] = (int)(255 * RI3[rgb[2]]);
				ip.putPixel(i, j, rgb);
			}
		}
	}
}