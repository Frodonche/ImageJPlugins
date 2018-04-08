import ij .*;
import ij. process .*;
import ij.gui .*;
import java .awt .*;
import ij. plugin . frame .*;

public class TP2_Q7 extends PlugInFrame {
	public TP2_Q7(){
		super ( "Differences entre images ");
	}

	public void run (String arg) {
		ImagePlus imgDiffA = new ImagePlus ("./plugins/TP2/diff1.jpg");
		ImageProcessor ipDiffA = imgDiffA . getProcessor ();
		byte [] pixelsDiffA = ( byte []) ipDiffA.getPixels ();
	
		ImagePlus imgDiffB = new ImagePlus ("./plugins/TP2/diff2.jpg");
		ImageProcessor ipDiffB = imgDiffB . getProcessor ();
		byte [] pixelsDiffB = ( byte []) ipDiffB.getPixels ();

		int w = ipDiffB.getWidth ();
		int h = ipDiffB.getHeight ();

		ImageProcessor ipRes = new ByteProcessor (w,h);
		ImagePlus imgRes = new ImagePlus (" Soustraction ",ipRes );
		
		byte [] pixelsRes = ( byte []) ipRes.getPixels ();
		
		int max;
		
		int[] rgb = new int[3];
		for (int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				if (ipDiffA.get(i,j)>ipDiffB.get(i,j))
					ipRes.set(i, j, ipDiffA.get(i,j));
				if (ipDiffA.get(i,j)<ipDiffB.get(i,j))
					ipRes.set(i, j, ipDiffB.get(i,j));
			}
		}
		
		imgRes.show ();
		imgRes.updateAndDraw ();
	}	
}	
		
		