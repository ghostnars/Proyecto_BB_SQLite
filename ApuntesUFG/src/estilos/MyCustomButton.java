package estilos;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

class MyCustomButton extends Field implements DrawStyle{  
  
private Bitmap _currentPicture;  
   private Bitmap _onPicture;  
   private Bitmap _offPicture;   
     
   //Ctor: pass path to on/off images you're using.  
   MyCustomButton (String onImage, String offImage){  
    super();  
    _offPicture = Bitmap.getBitmapResource("notas.png");  
    _onPicture = Bitmap.getBitmapResource("notas.png");  
    _currentPicture = _offPicture;  
          
   }  
     
   public int getPreferredHeight(){       
    return 80;  
   }  
     
   public int getPreferredWidth(){  
    return 80;  
   }  
     
 
   public boolean isFocusable(){  
    return true;  
   }  
     
   //Override function to switch picture  
   protected void onFocus(int direction){  
    _currentPicture = _onPicture;  
    invalidate();  
   }  
   //Override function to switch picture  
   protected void onUnfocus(){  
    _currentPicture = _offPicture;  
    invalidate();  
   }  
     
protected void layout(int width, int height) {  
  setExtent(Math.min( width, getPreferredWidth()), Math.min( height, getPreferredHeight()));  
}  
  
//update the fieldchange  
protected void fieldChangeNotify(int context) {  
    try {  
     this.getChangeListener().fieldChanged(this, context);  
    } catch (Exception exception) {  
    }  
}  
  
//Since button is rounded we need to fill corners with dark color to match  
protected void paint(Graphics graphics) {   
 graphics.setColor(Color.BLACK);   
       graphics.fillRect(0, 0, getWidth(), getHeight());  
       graphics.drawBitmap(0, 0, getWidth(), getHeight(), _currentPicture, 0, 0);  
}  

//Listen for navigation Click  
protected boolean navigationClick(int status, int time){  
 fieldChangeNotify(1);  
 return true;  
}  

}  


  