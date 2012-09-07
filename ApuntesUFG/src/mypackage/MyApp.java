package mypackage;

import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MyApp extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
	Config path = new Config();
	Config statement = new Config();
	static MainScreen splashScreen = null, homeScreen = null, homeScreen1 = null;
	static int	g_nScreenWidth = 0, g_nScreenHeight = 0;
	
	
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MyApp theApp = new MyApp();       
        theApp.enterEventDispatcher(); 
    }
    /**
     * Creates a new MyApp object
     */
    public MyApp()
    {               
    	
    	 try{
    	     	URI uri = URI.create(path.Path());
    	     	Database sqliteDB = DatabaseFactory.create(uri);

    	     	Statement st = sqliteDB.createStatement(statement.CreateMateria());
    				st.prepare();
    				st.execute();
    				st.close();
    				
    				Statement ct = sqliteDB.createStatement(statement.CreateApunte());
    				ct.prepare();
    				ct.execute();
    				ct.close();
    				sqliteDB.close();
    	     }catch (Exception e){
    	     e.printStackTrace();
    	     }  
    	
    	
    	
    	g_nScreenWidth =  Display.getHeight();
        g_nScreenHeight = Display.getWidth();
        // Push a screen onto the UI stack for rendering.
        splashScreen = new SplashScreen();
        pushScreen( splashScreen );
        // Push a screen onto the UI stack for rendering.
    }    
}
