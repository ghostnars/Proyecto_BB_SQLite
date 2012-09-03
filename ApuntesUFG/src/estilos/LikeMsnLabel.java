package estilos;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class LikeMsnLabel extends LabelField {
	
	    private static final int BACKGROUND = Color.STEELBLUE;

	    private static final int BACKGROUND2 = Color.WHITESMOKE;
	
	    private int customHeight = 40;

	    private final int[] colors = new int[] { BACKGROUND, BACKGROUND,BACKGROUND2, BACKGROUND };

	    public LikeMsnLabel() {

	        super("", LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH | LabelField.FOCUSABLE);

	    }

	   

		protected void layout(int width, int height) {

	        setExtent(width, customHeight);

	    }

	    protected void paint(Graphics graphics) {
	
	        int y = graphics.getClippingRect().y;

	        int y2 = graphics.getClippingRect().Y2();

	        int x2 = graphics.getClippingRect().X2();

	        int[] yIndexes = new int[] { y, y, y2, y2 };

	        int[] xIndexes = new int[] { 0, x2, x2, 0 };

	        graphics.drawShadedFilledPath(xIndexes, yIndexes, null, colors, null);

	    }

	}
