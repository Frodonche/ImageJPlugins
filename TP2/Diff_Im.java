import ij .*;
import ij. process .*;
import ij.gui .*;
import java .awt .*;
import ij. plugin . frame .*;

public class Diff_Im extends PlugInFrame {
	public Diff_Im(){
		super ( "Soustraction entre images ");
	}

	public void run (String arg) {
		ImagePlus imgDiffA = new ImagePlus ("./plugins/TP2/imA.pgm");
		ImageProcessor ipDiffA = imgDiffA . getProcessor ();
		byte [] pixelsDiffA = ( byte []) ipDiffA.getPixels ();
	
		ImagePlus imgDiffB = new ImagePlus ("./plugins/TP2/imB.pgm");
		ImageProcessor ipDiffB = imgDiffB . getProcessor ();
		byte [] pixelsDiffB = ( byte []) ipDiffB.getPixels ();

		int w = ipDiffB.getWidth ();
		int h = ipDiffB.getHeight ();

		ImageProcessor ipRes = new ByteProcessor (w,h);
		ImagePlus imgRes = new ImagePlus (" Soustraction ",ipRes );
		
		byte [] pixelsRes = ( byte []) ipRes.getPixels ();
		
		int max;
		for (int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				max = ipDiffA.get(i,j)-ipDiffB.get(i,j);
				if(max<0)
					max = 0;
				ipRes.set(i, j, max);
			}
		}
		
		imgRes.show ();
		imgRes.updateAndDraw ();
	}	
}	
		
		