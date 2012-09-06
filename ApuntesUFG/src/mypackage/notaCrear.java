package mypackage;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import estilos.Metodos;


public class notaCrear extends Metodos implements FieldChangeListener {
	BasicEditField nota;
	EditField efTitulo,efNota;	
	ObjectChoiceField ocfPrioridad;
	String choices[] = {"Alta","Media","Baja"};
	Config path = new Config();
	Config statement = new Config();
	int idApunte;
	int idMateria;
	DateField fecha;
	private LabelField materia;
    public notaCrear(int id_materia)
    {   
    	

    	idMateria = id_materia;
    	
    	Bitmap bitmapfondo = Bitmap.getBitmapResource("notepad4.png");
    	//Bitmap bitmaptexto = Bitmap.getBitmapResource("fondotexto.png");
		getMainManager().setBackground(BackgroundFactory.createBitmapBackground(bitmapfondo));    	
    	
		VerticalFieldManager allContent = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH);
        
        int iSetTo = 2;
        ocfPrioridad = new ObjectChoiceField("Prioridad: ", choices, iSetTo);
    	 setTitle(ocfPrioridad);
		
        try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(statement.SelectNomMateria()+idMateria);
            se.prepare();
            Cursor c = se.getCursor();
            Row r;
           
            
            while(c.next()){
                r = c.getRow();
                materia = new LabelField(r.getString(0)) ;
                materia.setMargin(3, 0, 0, 25);
                allContent.add(materia);
            }
            
            se.close();
            sqliteDB.close(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
             fecha = new DateField("", System.currentTimeMillis(), dateFormat, Field.FIELD_LEFT);
            fecha.setMargin(5,155,0,0);
            allContent.add(fecha);
            
            VerticalFieldManager contentTitulo = new VerticalFieldManager(VerticalFieldManager.FIELD_LEFT);
            contentTitulo.setMargin(5,0,0,25);
            
    	    efTitulo = new EditField("Titulo: ", "", 32, EditField.FILTER_FILENAME){
            	public int getPreferredHeight(){
                    return 40;
                }

                public int getPreferredWidth(){
                    return 310;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };

            contentTitulo.add(efTitulo);
            allContent.add(contentTitulo);
           
            
           
            
            VerticalFieldManager contentNota = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            contentNota.setMargin(22,0,0,22);
            //EDITFIELD TITULO  

    	    efNota = new EditField("", "", 500, EditField.FILTER_DEFAULT){
            	public int getPreferredHeight(){
                    return 90;
                }

                public int getPreferredWidth(){
                    return 300;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };
           
            contentNota.add(efNota);
            allContent.add(contentNota);
            add(allContent);
   
        }catch (Exception e){
            Dialog.alert(e.getMessage().toString());
            e.printStackTrace();
            } 
        
        
        
        
        
        
      
        
    	MenuItem miGuardar = new MenuItem("Guardar" , 100, 10){
    	    public void run(){
    	    	  String textoTitulo = efTitulo.getText();
    	          String textoApunte = efNota.getText();
    	          String textoPrioridad = (String) ocfPrioridad.getChoice(ocfPrioridad.getSelectedIndex());
    	          String textoFecha = fecha.toString();
    	          if(textoTitulo.length()==0){
            		  Dialog.alert("Ingrese un titulo para el apunte"); 
            	  }else{
	    	    	 try{
	    	    		 
	    	         	URI uri = URI.create(path.Path());
	    	         	Database sqliteDB = DatabaseFactory.open(uri);
	    	         	Statement it = sqliteDB.createStatement(statement.InsertApunte()+"("+idMateria+",'"+textoTitulo+"','"+textoApunte+"','"+textoPrioridad+"','"+textoFecha+"')");
						it.prepare();
						it.execute();
						it.close();
	    	                    
	    	                  
						it.close();
	    	            sqliteDB.close();
	    	            efTitulo.setText("");
	    	             efNota.setText(""); 
	    	       
	
	    	     		Dialog.alert("Guardado con exito");
	    	         }catch (Exception e){
	    	         Dialog.alert("error guardar "+e.getMessage().toString());
	    	         e.printStackTrace();
	    	         }
            	  }
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
