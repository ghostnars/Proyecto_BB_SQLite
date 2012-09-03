package mypackage;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;


public class notaCrear extends Metodos implements FieldChangeListener {
	BasicEditField nota;
	EditField efTitulo,efNota;	
	ObjectChoiceField ocfPrioridad;
	String choices[] = {"Alta","Media","Baja"};
	Config path = new Config();
	int idApunte;
	int idMateria;
    public notaCrear(int id_materia)
    {   
    	
    	Bitmap _bitmap = Bitmap.getBitmapResource("rounded-border1.png");
    	idMateria = id_materia;
    	
    	
        
        int iSetTo = 2;
        ocfPrioridad = new ObjectChoiceField("Prioridad: ", choices, iSetTo);
    	 add(ocfPrioridad);
		
        try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement("SELECT nombre_materia FROM MATERIA WHERE id_materia ="+idMateria);
            se.prepare();
            Cursor c = se.getCursor();
            Row r;
           
            
            while(c.next()){
                r = c.getRow();
                setTitle(r.getString(0));
            }
            
            se.close();
            sqliteDB.close();
            
            VerticalFieldManager allContent= new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            allContent.setMargin(10,0,0,0);
            
            HorizontalFieldManager contentTitulo = new HorizontalFieldManager(HorizontalFieldManager.FIELD_HCENTER);
            contentTitulo.setMargin(10,0,0,0);
            //EDITFIELD TITULO  
            LabelField titulo = new LabelField ("Titulo:");
            titulo.setMargin(7,0,0,0);
            //EDITFIELD TITULO  
            VerticalFieldManager bordeTitulo = new VerticalFieldManager();
    	    bordeTitulo.setBorder(BorderFactory.createBitmapBorder(new XYEdges(10,10,10,10), _bitmap));
    	    
    	        
    	    efTitulo = new EditField("", "", 27, EditField.FILTER_FILENAME){
            	public int getPreferredHeight(){
                    return 30;
                }

                public int getPreferredWidth(){
                    return 350;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };
            efTitulo.setBackground( BackgroundFactory.createSolidTransparentBackground( Color.WHITE, 50 ) );
            bordeTitulo.add(efTitulo);
            contentTitulo.add(titulo);
            contentTitulo.add(bordeTitulo);
            allContent.add(contentTitulo);
            

            VerticalFieldManager contentNota = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            contentNota.setMargin(10,0,0,0);
            //EDITFIELD TITULO  
            LabelField tituloNota = new LabelField ("Nota:",LabelField.FIELD_LEFT);
            titulo.setMargin(7,0,0,0);
            //EDITFIELD TITULO  
            VerticalFieldManager bordeNota = new VerticalFieldManager();
            bordeNota.setBorder(BorderFactory.createBitmapBorder(new XYEdges(10,10,10,10), _bitmap));
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
            efNota.setBackground( BackgroundFactory.createSolidTransparentBackground( Color.WHITE, 50 ) );
            efNota.setMargin(10, 0, 10, 0);
            bordeNota.add(efNota);
            contentNota.add(tituloNota);
            contentNota.add(bordeNota);
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
    	          
    	    	 try{
    	         	URI uri = URI.create(path.Path());
    	         	Database sqliteDB = DatabaseFactory.open(uri);
    	         	Statement it = sqliteDB.createStatement("INSERT INTO APUNTE(id_materia,titulo,apunte,prioridad)values("+idMateria+",'"+textoTitulo+"','"+textoApunte+"','"+textoPrioridad+"')");
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
