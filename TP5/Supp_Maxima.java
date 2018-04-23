 
// Importation des paquets nécessaires. Le plugin n'est pas lui-même un paquet (pas de mot-clé package)
import ij.*; 							// pour classes ImagePlus et IJ
import ij.plugin.filter.PlugInFilter; 	// pour interface PlugInFilter
import ij.process.*; 					// pour classe ImageProcessor
import ij.gui.*;						// pour classe GenericDialog et Newimage

public class Supp_Maxima implements PlugInFilter {

	public int setup(String arg, ImagePlus imp) {

		return DOES_8G;
	}

	public void run( ImageProcessor ip){
		int w = ip.getWidth ();
		int h = ip.getHeight ();
		byte [] pixels = ( byte []) ip.getPixels ();
		
		ImagePlus resultX = NewImage.createByteImage (" Norme Gradient Seuil", w, h, 1,NewImage.FILL_BLACK );
		ImageProcessor iprX = resultX.getProcessor ();
		byte [] pixelsrX = ( byte []) iprX.getPixels ();
		
        int [][] sobelX = {{1,0,-1},{2,0,-2},{1,0,-1}};
        int [][] sobelY = {{-1,-2,-1},{0,0,0},{1,2,1}};
        
        int n = 1;
		
		int seuil = 130;

		//int minD = 720, maxD = -720;
		
        int sommeX, sommeY, sommeTotale;
		int direction; // en degres
		int normeVoisin1, normeVoisin2;
		int xVoisin, yVoisin; // definis par la direction
		for (int y = n; y < h-n; y++){
			for ( int x = n; x < w-n; x++) {
                    sommeX = 0;
                    sommeY = 0;
                    for (int i = 0; i< 3; i++){
                        for (int j = 0; j < 3; j++){
                                sommeX = sommeX + ip.getPixel(x-n+j,y-n+i) * sobelX[i][j];
                                sommeY = sommeY + ip.getPixel(x-n+j,y-n+i) * sobelY[i][j];
                        }
                    }
				if(sommeX != 0)	
					direction = (int)Math.toDegrees(Math.atan(sommeY / sommeX));	
                else{
					direction = (int)Math.toDegrees(Math.atan(sommeY));
				}
				
				/*if (direction > maxD)
					maxD = direction;
				if (direction < minD)
					minD = direction;
				*/
				if(direction >= -180){
					if(direction < -135){ // -180 <= direction < -135
						//TODO
					}else{
						if(direction < -90){ // -135 <= direction < -90
							//TODO
						}else{
							if(direction < -45){ // -90 <= direction < -45
								//TODO
							}else{
								if(direction < 0){ // -45 <= direction < 0
									//TODO
								}else{
									if(direction < 45){ // 0 <= direction < 45
										//TODO
									}else{
										if(direction < 90){ // 45 <= direction < 90
											//TODO
										}else{
											if(direction < 135){ // 90 <= direction < 135
												//TODO
											}else{
												if(direction <= 180){ // 135 <= direction <= 180
													//TODO
												}else{
													IJ.log("Erreur : direction trop grande : "+direction);
												}
											}
										}
									}																		
								}
							}
						}
					}
				}else{
					IJ.log("Erreur : direction trop petite : "+direction);
				}
				
				sommeTotale = (Math.abs(sommeX)+Math.abs(sommeY));
                if(sommeTotale < seuil)
                    sommeTotale = 0;
                if(sommeTotale >= seuil)
                   sommeTotale = 255;
                iprX.putPixel(x,y, sommeTotale);
			}
        }

		resultX.show ();
		resultX.updateAndDraw ();

	}
}
