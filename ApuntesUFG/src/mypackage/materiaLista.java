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
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import estilos.BitmapButtonField;
import estilos.Metodos;


public class materiaLista extends Metodos implements FieldChangeListener {
	
		Bitmap caret = Bitmap.getBitmapResource("arrow.png");
		BitmapButtonField materia1,materia2,materia3,materia4,materia5,materia6,materia7;
		BitmapButtonField[] materias={materia1,materia2,materia3,materia4,materia5,materia6};
		Config path = new Config();
		Config statement = new Config();
	
    public materiaLista()
    {   
    	getMainManager().setBackground(BackgroundFactory.createLinearGradientBackground(Color.BLACK, Color.BLACK,Color.BLACK,Color.BLACK));
    	
    	Bitmap elementoBitmap = Bitmap.getBitmapResource("fondomaterias.png"); 
    	setTitle("Lista de materias");

    	 //trata de hacer SELECT de todas las materias guardadas
        	try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(statement.SelectMateria());
                
            se.prepare();
            Cursor c = se.getCursor();
                	
                Row r;
                
                int i = 0;
                //por cada vez que c se mueva a la siguiente fila creara un elemento lista
                while(c.next()) 
                {
                    r = c.getRow();
                    //se asigna la propiedad de consumir click a la imagen nombrado
                    //por la posicion del array materias
                    materias[i] = new BitmapButtonField(Bitmap.getBitmapResource("barraboton0.png"), Bitmap.getBitmapResource("barraboton1.png"), BitmapButtonField.FIELD_LEFT | BitmapButtonField.FIELD_LEADING);
        			materias[i].setChangeListener(this);
        			materias[i].setMargin(0, 0, 0, 0);
        			//ASIGNA TEXTO AL EL ELEMENTO DE LISTA
        			WLabelField text = new WLabelField(r.getString(0));
        			text.setMargin(0, 0, 0, 15);
        			//CREAR ELEMENTO DE LISTA
        			HorizontalFieldManager elementolista = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
        			elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(0,1,0,0), elementoBitmap));
        			//AGREGAR A PANTALLA CADA ELEMENTO
        			elementolista.add(materias[i]);
        			elementolista.add(text);
        			elementolista.setMargin(3, 3, 3, 3);
        			add(elementolista);
                    i++;
                }
                
                se.close();
                sqliteDB.close();
        		
                if (i==0)
                {
                	//si la consulta no trae nada osea i no incrementa en nada muestra mensaje
                	  WLabelField nohay = new WLabelField("No se ha cargado ninguna materia!\n\n"
                              +"Por favor vuelva a abrir la aplicacion \n\n");
                	  add(nohay);
            		
                }
               
        }catch (Exception e){
        	WLabelField text = new WLabelField(e.toString());
			text.setMargin(0, 0, 0, 15);
			add(text);
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
