// Importation des paquets necessaires. Le plugin n'est pas lui-meme un paquet (pas de mot-cle package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor

import java.lang.Math;

public class TP2_Q1 implements PlugInFilter {
	ImagePlus imp;
	static int MAX_COLORS = 256*256*256;
	int[] counts = new int[MAX_COLORS]; 		// Histogramme des couleurs
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp; 						// Rem.: pas utilise dans run()
		return DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING+NO_UNDO+NO_CHANGES; 	// Traite les images couleurs ; pas d'annulation possible
	}
	
	public void run(ImageProcessor ip) {
		byte[] pixels = (byte[])ip.getPixels(); //recuperation des pixels de l'image dans le tableau pixels
		int L = ip.getWidth ();
		int H = ip.getHeight ();
		int total = 0;
		double moyenne;
		double contraste = 0;
		double sum = 0;
		
		for (int i=0; i<pixels.length; i++){
			total += pixels[i]; // Masquage necessaire car pixels[i] est signe
		}
		moyenne = total / pixels.length;
		IJ.log("Valeur d'intensite moyenne : "+moyenne);
		
		for (int i = 0; i < pixels.length; i++){
			sum += ((pixels[i]) - moyenne)*((pixels[i]) - moyenne);
		}
		
		contraste = Math.sqrt(sum/(L*H));
		
		IJ.log("Valeur de contraste : "+contraste);
	}
}