 
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
		
        int sommeX, sommeY, sommeTotale;
		int direction; // en degres
		int[] directionVoisin = new int[2]; // xVoisin, yVoisin
		int xVoisin, yVoisin; // definis par la direction
		int sommeXVoisin1, sommeYVoisin1, normeVoisin1;
		int sommeXVoisin2, sommeYVoisin2, normeVoisin2;
		boolean infVoisin;
		int cpt = 0;
		for (int y = n; y < h-n; y++){
			for (int x = n; x < w-n; x++) {
                    sommeX = 0;
                    sommeY = 0;
                    for (int i = 0; i< 3; i++){
                        for (int j = 0; j < 3; j++){
                            sommeX = sommeX + ip.getPixel(x-n+j,y-n+i) * sobelX[i][j];
                            sommeY = sommeY + ip.getPixel(x-n+j,y-n+i) * sobelY[i][j];
                        }
                    }
				// On prend garde a ne pas diviser par 0
				if(sommeX != 0)	
					direction = (int)Math.toDegrees(Math.atan(sommeY / sommeX));	
                else{
					direction = (int)Math.toDegrees(Math.atan(sommeY));
				}
				
				// On calcule l'arrondi de la direction a un multiple de 45 degres
				direction = encadrerEtArrondir(direction);
				
				// On definit la valeur de xVoisin et yVoisin pour calculer les cases voisines
				directionVoisin = getDirectionVoisin(direction);
				xVoisin = directionVoisin[0]; // reassignation aux variables pas necessaire
				yVoisin = directionVoisin[1]; // mais utile pour des questions de clarte
				
				sommeTotale = (Math.abs(sommeX)+Math.abs(sommeY));
                if(sommeTotale < 0)
                    sommeTotale = 0;
                if(sommeTotale > 255)
                   sommeTotale = 255;
				
				
				// On calcule la norme des voisins et on la compare a celle du pixel courant
				sommeXVoisin1 = 0; sommeYVoisin1 = 0;
				sommeXVoisin2 = 0; sommeYVoisin2 = 0;
				
				for (int i = 0; i< 3; i++){
					for (int j = 0; j < 3; j++){
						sommeXVoisin1 = sommeXVoisin1 + ip.getPixel(x-n+j+xVoisin,y-n+i+yVoisin) * sobelX[i][j];
						sommeYVoisin1 = sommeYVoisin1 + ip.getPixel(x-n+j+xVoisin,y-n+i+yVoisin) * sobelY[i][j];
						
						sommeYVoisin2 = sommeYVoisin2 + ip.getPixel(x-n+j-xVoisin,y-n+i-yVoisin) * sobelY[i][j];
						sommeXVoisin2 = sommeXVoisin2 + ip.getPixel(x-n+j-xVoisin,y-n+i-yVoisin) * sobelX[i][j];
					}
				}
				normeVoisin1 = (Math.abs(sommeXVoisin1)+Math.abs(sommeYVoisin1));
				normeVoisin2 = (Math.abs(sommeXVoisin2)+Math.abs(sommeYVoisin2));
				
				 if(normeVoisin1 < 0)
                    normeVoisin1 = 0;
                if(normeVoisin1 > 255)
                   normeVoisin1 = 255;
			   
			    if(normeVoisin2 < 0)
                    normeVoisin2 = 0;
                if(normeVoisin2 > 255)
                   normeVoisin2 = 255;
				
				
				infVoisin = (normeVoisin1 > sommeTotale || normeVoisin2 > sommeTotale);
				
				if(infVoisin)
					iprX.putPixel(x,y, 0);
				else
					iprX.putPixel(x,y, sommeTotale);
			
			}
        }

		resultX.show ();
		resultX.updateAndDraw ();

	}
	
	/**
	*	Encadre la direction à 45 degres pres
	*	Puis l'arrondi au multiple de 45 degres le plus proche
	**/
	public int encadrerEtArrondir(int direction){
		int borneSup = 720, borneInf = -720;
		int temp = 180;
		
		// On cherche à encadrer la valeur de la direction à 45 degres pres
		while(borneInf == -720){
			if(temp - direction == 0){
				return direction;
			}else if(temp - direction > 0){// on n'a pas encore trouve de borne superieure
					temp -= 45;
				}else{ // on l'a trouve, donc on peut encadrer le nombre
					borneSup = temp + 45;
					borneInf = temp;
				}
		}
		// Une fois trouvee, on arrondi la direction à 45 degres pres, en prenant la valeur la plus proche
		if((borneSup - direction) >= (direction - borneInf))
			direction = borneInf;
		else
			direction = borneSup;
		return direction;
	}
	
	/**
	*	Defini les valeurs x et y des voisins en fonction de l'angle de direction
	**/
	public int[] getDirectionVoisin(int direction){
		int[] result = new int[2];
		if(direction == 45 || direction == -135){
			result[0] = 1;
			result[1] = 1;
		}
		if(direction == 90 || direction == -90){
			result[0] = 0;
			result[1] = 1;
		}
		if(direction == 135 || direction == -45){
			result[0] = 1;
			result[1] = -1;
		}
		if(direction == 0 || direction == 180 || direction == -180){
			result[0] = 1;
			result[1] = 0;
		}
		return result;
	}
	
}