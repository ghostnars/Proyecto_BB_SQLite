package mypackage;

import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.Field;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends Metodos
{
    /**
     * Creates a new MyScreen object
     */
	Config ver = new Config();
	Config statement = new Config();
    public MyScreen()
    {        
    
        setTitle("Cargado de materias");
    	
		try{ 
			
			
			URI uri = URI.create(ver.Path());
			Database sqliteDB = DatabaseFactory.create(uri);
			
			Statement st = sqliteDB.createStatement(statement.CreateMateria());
			st.prepare();
			st.execute();
			st.close();
			
			Statement ct = sqliteDB.createStatement(statement.CreateApunte());
			ct.prepare();
			ct.execute();
			ct.close();
		}catch(Exception z ){
			
		}finally{
			openScreen(new materiaLista());
		}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    }

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		
	}
}
