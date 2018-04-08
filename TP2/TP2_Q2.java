// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor

import java.lang.Math;

public class TP2_Q2 implements PlugInFilter {
	ImagePlus imp;
	static int MAX_COLORS = 256*256*256;
	int[] counts = new int[MAX_COLORS]; 		// Histogramme des couleurs
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp; 						// Rem.: pas utilise dans run()
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING; 	// Traite les images couleurs ; pas d'annulation possible
	}
	
	public void run(ImageProcessor ip) {
		byte[] pixels = (byte[])ip.getPixels(); //recuperation des pixels de l'image dans le tableau pixels
		int L = ip.getWidth ();
		int H = ip.getHeight ();
		int min = 0;
		int max = 0;
		int current;
		
		for(int p = 0; p < pixels.length; p++){
			 current = pixels[p];
			if (current > max)
				max = current;
			if (current < min)
				min = current;
		}
		
		IJ.log("Min : "+min+" Max : "+max);
		
		// Initialisation de la LUT
		int LUT[] = new int[256]; 
		for (int ng=0; ng < 256;  ng++){
			LUT[ng] = 255 * (ng - min) / (max - min);
		//	IJ.log("LUT["+ng+"] : "+LUT[ng]);
		}
		
		// Calcul de la transformation
		for(int i = 0; i < L; i++){
			for(int j = 0; j < H; j++){
				ip.set(i, j, LUT[ip.get(i,j)]);
			}
		}
		
		// nouveau min et max (post transformation)
		for(int p = 0; p < pixels.length; p++){
			 current = pixels[p];
			if (current > max)
				max = current;
			if (current < min)
				min = current;
		}
		IJ.log("Nouveau Min : "+min+" Nouveau Max : "+max);
	}
}