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
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import estilos.BitmapButtonField;


public class materiaLista extends Metodos implements FieldChangeListener {
	
BitmapButtonField bts1 , bts2 , bts3 , bts4,bts5,boton;
		Bitmap caret = Bitmap.getBitmapResource("arrow.png");
		BitmapButtonField materia1,materia2,materia3,materia4,materia5,materia6,materia7;
		BitmapButtonField[] materias={materia1,materia2,materia3,materia4,materia5,materia6};
		Config path = new Config();
		Config selectmateria = new Config();
    public materiaLista()
    {   
    	getMainManager().setBackground(BackgroundFactory.createLinearGradientBackground(Color.BLACK, Color.BLACK,Color.BLACK,Color.BLACK));
 		
    	 setTitle("Lista de materias");
        try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);

                Statement se = sqliteDB.createStatement(selectmateria.SelectMateria());
                
                se.prepare();
                Cursor c = se.getCursor();
                
                Row r;
                int i = 0;
                while(c.next()) 
                {
                    r = c.getRow();
                    
                   
                    
                    materias[i] = new BitmapButtonField(Bitmap.getBitmapResource("arrow.png"), Bitmap.getBitmapResource("arrow1.png"), BitmapButtonField.FIELD_LEFT);
        			materias[i].setChangeListener(this);
        			materias[i].setMargin(5, 0, 5, 7);
        			
        			//ASIGNA TEXTO AL EL ELEMENTO DE LISTA
        			WLabelField text = new WLabelField(r.getString(0));
        			text.setMargin(7, 0, 7, 15);
        			//CREAR ELEMENTO DE LISTA
        	    	Bitmap elementoBitmap = Bitmap.getBitmapResource("fondomaterias.png");
        			HorizontalFieldManager elementolista = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
        			elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(1,1,1,1), elementoBitmap));
        			
        			//AGREGAR A PANTALLA CADA ELEMENTO
        			
        			elementolista.add(materias[i]);
        			elementolista.add(text);
        			elementolista.setMargin(2, 5, 2, 5);
        			add(elementolista);
                    i++;
                    
                    
                }
                if (i==0)
                {
                    add(new RichTextField("There are no tables in the MyTestDatabase database."));
                }
                se.close();
                sqliteDB.close();
                
      

    		
        }catch (Exception e){
        //Dialog.alert("error al cargar la base"+e.getMessage().toString());
        e.printStackTrace();
        }   
    }

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		if(materias[0]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(1));
		}	
		else if(materias[1]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(2));
		}	
		else if(materias[2]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(3));
		}
		else if(materias[3]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(4));
		}
		else if(materias[4]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(5));
		}	
		else if(materias[5]== field){
			TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 400);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			
			openScreen(new notaLista(6));
		}	
		
	}

}
