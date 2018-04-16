 
// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour classe GenericDialog et Newimage

public class Filtre_Sobel implements PlugInFilter {

	public int setup(String arg, ImagePlus imp) {

		return DOES_8G;
	}

	public void run( ImageProcessor ip){
		int w = ip.getWidth ();
		int h = ip.getHeight ();
		byte [] pixels = ( byte []) ip.getPixels ();
		
		ImagePlus resultX = NewImage.createByteImage (" SobelX ", w, h, 1,NewImage.FILL_BLACK );
		ImageProcessor iprX = resultX.getProcessor ();
		byte [] pixelsrX = ( byte []) iprX.getPixels ();
		
		ImagePlus resultY = NewImage.createByteImage (" SobelY ", w, h, 1,NewImage.FILL_BLACK );
		ImageProcessor iprY = resultY.getProcessor ();
		byte [] pixelsrY = ( byte []) iprY.getPixels ();
		
        int [][] sobelX = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int [][] sobelY = {{-1,-2,-1},{0,0,0},{1,2,1}};
        
        int n = 1;

        int sommeX;
		for (int y = n; y < h-n; y++){
			for ( int x = n; x < w-n; x++) {
                    sommeX = 0;
                    for (int i = 0; i< 3; i++){
                        for (int j = 0; j < 3; j++){
                                sommeX = sommeX + ip.getPixel(x-n+j,y-n+i) * sobelX[i][j];
                        }
                    }
                iprX.putPixel(x,y, sommeX);
			
			}
        }
        
		resultX.show ();
		resultX.updateAndDraw ();
		
		
		int sommeY;
		for (int y = n; y < h-n; y++){
			for ( int x = n; x < w-n; x++) {
                    sommeY = 0;
                    for (int i = 0; i< 3; i++){
                        for (int j = 0; j < 3; j++){
                                sommeY = sommeY + ip.getPixel(x-n+j,y-n+i) * sobelY[i][j];
                        }
                    }
                iprY.putPixel(x,y, sommeY);
			
			}
        }
		
		resultY.show ();
		resultY.updateAndDraw ();
	}
}
