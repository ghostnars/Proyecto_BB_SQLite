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
	//Instancias de Config,java Clase que contiene los parametros SQL y la version de la base de datos
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
    	
    //Al iniciar la aplicacion comienza creando la base de datos y las tablas
    	 
	 try{	
		 //URI es la direccion en donde se va a crear la base de datos
		 //Estos parametros estan en Config.java que contiene estos parametros
	     	URI uri = URI.create(path.Path());
	     //sqliteDB variable tipo Database
	     	Database sqliteDB = DatabaseFactory.create(uri);
	     		//Statement st utilizado para crear las sentencias SQL
		     	Statement st = sqliteDB.createStatement(statement.CreateMateria());
				//se prepara la sentencia st a ser ejecutada
		     	st.prepare();
		     	//Ejecuta la sentencia en este caseo CreateMateria() que en config.java es crear la tabla materia
				st.execute();
				//Cierra la sentencia
				st.close();
				
				Statement ct = sqliteDB.createStatement(statement.CreateApunte());
				ct.prepare();
				ct.execute();
				ct.close();
				//Cierra la base de datos "si no permanece abierta hasta que se cierre generando errores"
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
