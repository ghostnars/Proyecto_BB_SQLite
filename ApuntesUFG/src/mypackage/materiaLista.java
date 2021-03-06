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
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
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
		private Font ffecha;
	
    public materiaLista()
    {   
    	getMainManager().setBackground(BackgroundFactory.createLinearGradientBackground(Color.BLACK, Color.BLACK,Color.BLACK,Color.BLACK));
    	
    	Bitmap elementoBitmap = Bitmap.getBitmapResource("fondomaterias.png"); 
    	setTitle("Lista de materias");

    	 try
    	 {
    	 	FontFamily ffFont = FontFamily.forName("Arial");
    	 	ffecha = ffFont.getFont(Font.ANTIALIAS_STANDARD, 13);
    	 	
    	 }catch (ClassNotFoundException e){
    	 	   System.out.println(e.getMessage());
    	 }
    	 
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
                    materias[i] = new BitmapButtonField(Bitmap.getBitmapResource("barraboton0.png"), Bitmap.getBitmapResource("barraboton1.png"), BitmapButtonField.FIELD_LEFT | BitmapButtonField.FIELD_VCENTER);
        			materias[i].setChangeListener(this);
        			materias[i].setMargin(0, 0, 0, 0);
        			//ASIGNA TEXTO AL EL ELEMENTO DE LISTA
        			LabelField lblnombre = new LabelField(r.getString(0)){
                        public void paint(Graphics g){      
                            g.setColor(Color.WHITE);
                            super.paint(g);
                    }};;
        			LabelField lblcodigo = new LabelField(r.getString(1)+" - Grupo: "+r.getString(2)){
                        public void paint(Graphics g){      
                            g.setColor(Color.WHITE);
                            super.paint(g);
                    }};;
                    lblcodigo.setFont(ffecha);
                    /**
        			LabelField lblgrupo = new LabelField(r.getString(2)){
                        public void paint(Graphics g){      
                            g.setColor(Color.WHITE);
                            super.paint(g);
                    }};;*/
                    
                    lblnombre.setMargin(0, 0, 0, 5);
                    lblcodigo.setMargin(0, 0, 0, 5);
                    //lblgrupo.setMargin(0, 0, 0, 5);
        			//CREAR ELEMENTO DE LISTA
        			HorizontalFieldManager elementolista = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
        			elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(0,1,0,0), elementoBitmap));
        			VerticalFieldManager elemento = new VerticalFieldManager(Field.USE_ALL_WIDTH | Field.FIELD_VCENTER);
        			//AGREGAR A PANTALLA CADA ELEMENTO
        			
        			elementolista.add(materias[i]);
        			elemento.add(lblnombre);
        			elemento.add(lblcodigo);
        			//elemento.add(lblgrupo);
        			
        			elementolista.add(elemento);
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
        	
        	MenuItem mymenu2 = new MenuItem("Reiniciar Aplicacion" , 100, 2){
        	    public void run(){
        	    	//elemento de pregunta si desea eliminar o cancelar la operacion
        	    	UiApplication.getUiApplication().invokeLater(new Runnable(){
        				public void run(){

        					Object[] choices = new Object[] {"Cancelar", "Reiniciar" };
        					int result = Dialog.ask("Desea eliminar todo?", choices, 0);
        					//por medio de case se elige por numero de posicion del array de choices
        					switch (result) {
        					//si la eleccion es 0 osea cancelar solamente cierra el ask
        					case 0:
        						break;
        					case 1:
        					//si la eleccion es 1 entonces procede eliminar la lista de apuntes por idMateria	
        						try{
        		    	    	   	URI uri1 = URI.create(path.Path());
        	    		         	Database sqliteDB1 = DatabaseFactory.open(uri1);
        	    		         	Statement st = sqliteDB1.createStatement(statement.DeleteTableMateria());
        	    		            st.prepare();
        	    		            st.execute();
        	    		            st.close();
        	    		            Statement dt = sqliteDB1.createStatement(statement.DeleteTableApunte());
        	    		            dt.prepare();
        	    		            dt.execute();
        	    		            dt.close();
        	    		            sqliteDB1.close();
        	    		            Dialog.inform("Reiniciando...");
        	    		         }catch (Exception e){
        	    		         Dialog.alert("Error al eliminar "+e.getMessage().toString());
        	    		         e.printStackTrace();
        	    		         }
        						openScreen(new login());
        						break;
        					}				
        				}});

        	    	  
        	    	         }
        	    	};
        	//agrega los dos elementos al menu del dispositivo   	
        	addMenuItem(mymenu2);
        	
        	
        	
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
