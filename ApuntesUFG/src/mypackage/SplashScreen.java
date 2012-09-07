package mypackage;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import estilos.Metodos.WLabelField;
import estilos.Utils;

public class SplashScreen extends MainScreen {
	Config path = new Config();
	Config statement = new Config();
	int var =0;
    public SplashScreen() {
        super(SplashScreen.USE_ALL_HEIGHT | SplashScreen.NO_VERTICAL_SCROLL);
        setTitle((LabelField)null) ; // hide screen title

       
        BitmapField bmp = new BitmapField(Utils.getFitBitmapImage("bg_login2.png",MyApp.g_nScreenWidth, MyApp.g_nScreenHeight),
        		BitmapField.FIELD_HCENTER | BitmapField.FIELD_VCENTER);  
        
        HorizontalFieldManager rowHolder = new HorizontalFieldManager(NO_HORIZONTAL_SCROLL | NO_VERTICAL_SCROLL|
                Field.FIELD_HCENTER | USE_ALL_HEIGHT );
        rowHolder.add(bmp);
        add(rowHolder);
        
    	try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(statement.SelectMateria());
                se.prepare();
                Cursor c = se.getCursor();
                	
                Row r;
                
                int i = 0;
                while(c.next()) 
                {
                    r = c.getRow();
               
                  i++;
                    }
                    sqliteDB.close();
            		
                    if (i==0)
                    {
                    	var =1;
                		
                    }
                   
            }catch (Exception e){
            e.printStackTrace();
            }

        // wait then open new screen
        MyApp.homeScreen = new materiaLista();
        MyApp.homeScreen1 = new login();
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run() {
            	if(var == 1){
            	UiApplication.getUiApplication().pushScreen(MyApp.homeScreen1);
                UiApplication.getUiApplication().popScreen(MyApp.splashScreen);	
            	}else{
            	UiApplication.getUiApplication().pushScreen(MyApp.homeScreen);
                UiApplication.getUiApplication().popScreen(MyApp.splashScreen);		
            	}
                
            }
        }, 1000, false);
    }
}
