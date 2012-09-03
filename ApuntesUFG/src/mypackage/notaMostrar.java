package mypackage;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;


public class notaMostrar extends Metodos implements FieldChangeListener {
	BasicEditField nota;
	EditField efTitulo,efNota;	
	Config path = new Config();
	Config selecttitulo = new Config();
	String idApunte;
	int idMateria;
	String val;
	String direccion;
	Bitmap tagBitmap;
    public notaMostrar(int id_materia, String id_apunte)
    {   
    	idMateria = id_materia;
    	idApunte = id_apunte;
    	
    	try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(selecttitulo.SelectApunte()+idMateria+" AND id_apunte="+idApunte+"");
            se.prepare();
            Cursor c = se.getCursor();
            Row r;
          
            
            while(c.next()){
                r = c.getRow();
                setTitle("TITULO: "+r.getString(0).toUpperCase());
                
                val=r.getString(2);
					if (val.equals("Alta")){
						direccion="tagAlta.png";				
						}else if(val.equals("Media")){
							direccion = "tagMedia.png";
							}else if(val.equals("Baja")){
								direccion = "tagBaja.png"; 
								}
					 
					tagBitmap = Bitmap.getBitmapResource(direccion);
				    BitmapField bfTag = new BitmapField(tagBitmap, Field.FIELD_VCENTER);
                
				    
				RichTextField prioridad = new RichTextField("Prioridad: "+r.getString(2));
                RichTextField titulo = new RichTextField("\nApunte:\n"+r.getString(1));
                HorizontalFieldManager hfm = new HorizontalFieldManager();
                hfm.add(prioridad);
                hfm.add(bfTag);
                add(hfm);
                add(titulo);
            }
            
            se.close();
            sqliteDB.close();
    	 }catch (Exception e){
    	        Dialog.alert("error al cargar el titulo "+e.getMessage().toString());
    	        e.printStackTrace();
    	   }  
    	
    	
		
       
        
    	MenuItem miGuardar = new MenuItem("Modificar" , 100, 10){
    	    public void run(){   
    	    	openScreen(new notaModificar(idMateria,idApunte));
    	    }
    	};
        
    	addMenuItem(miGuardar);
		
    	
    }

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		
	
		
	}
	public boolean onClose() {
		//force the app to quit
		 TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_RIGHT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
		
		openScreen(new notaLista(idMateria));
		return true;
	}

}
