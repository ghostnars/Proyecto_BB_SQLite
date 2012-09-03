package mypackage;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public abstract class Metodos extends MainScreen implements FieldChangeListener{
	
		Metodos prevScreen ;
	
	public Metodos(){
		
	}
	
	protected void openScreen(Metodos nextScreen) {
		nextScreen.prevScreen = this;
		UiApplication.getUiApplication().popScreen(this);
		UiApplication.getUiApplication().pushScreen(nextScreen);
	}
	
	public class WLabelField extends LabelField
	{
		public WLabelField(Object text)
		{
			super(text,Field.FIELD_VCENTER);
		}
		
		public void paint(Graphics g)
		{      
			g.setColor(Color.WHITE);
			super.paint(g);
		}
	}
	
	public class WRichTextField extends RichTextField
	{
		public WRichTextField(String string)
		{
			super(string);
		}
		
		public void paint(Graphics g)
		{      
			g.setColor(Color.WHITE);
			super.paint(g);
		}
	}
}
