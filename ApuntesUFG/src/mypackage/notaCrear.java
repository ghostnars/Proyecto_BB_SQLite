package mypackage;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.i18n.SimpleDateFormat;
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
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import estilos.Metodos;

/** try
{
	FontFamily ffFont = FontFamily.forName("Comic Sans MS");
	ffecha = ffFont.getFont(Font.ITALIC, 13);
	fmateria = ffFont.getFont(Font.ANTIALIAS_DEFAULT, 15);
	ftitulo = ffFont.getFont(Font.ITALIC, 16); 
	fapunte = ffFont.getFont(Font.ANTIALIAS_DEFAULT, 17); 
}catch (ClassNotFoundException e){
	   System.out.println(e.getMessage());
}*/

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
	LabelField materia;
	boolean guardado = false;
	  String textoTitulo;
      String textoApunte;
      String textoPrioridad;
      String textoFecha;
    public notaCrear(int id_materia)
    {   
    	Bitmap bitmapfondo = Bitmap.getBitmapResource("fondoapunte.png");
		getMainManager().setBackground(BackgroundFactory.createBitmapBackground(bitmapfondo));
		idMateria = id_materia;
		
		//contenedor maestro en el cual estaran inmersos mas contenedores
		VerticalFieldManager allContent = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
		//contenedor de cabecera que van a estar la fecha el nombre de la materia
		VerticalFieldManager headContent = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
		headContent.setMargin(5, 5, 0, 5 );
        int iSetTo = 2;
        ocfPrioridad = new ObjectChoiceField("Prioridad: ", choices, iSetTo);
    	setTitle(ocfPrioridad);
    	//formato de la fecha
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	//elemento fecha tipo DateField con el formato anterior
    	 	fecha = new DateField("", System.currentTimeMillis(), dateFormat, Field.FIELD_RIGHT){
    	 		//pinta el texto de color blanco
                public void paint(Graphics g){      
                    g.setColor(Color.WHITE);
                    super.paint(g);
               }};;
            fecha.setMargin(5,165,0,0);
            headContent.add(fecha);
        //SELECT nombre de la materia
        try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(statement.SelectNomMateria()+idMateria);
            se.prepare();
            Cursor c = se.getCursor();
            Row r;
           
            //retorna nombre de materia y agrega al head content
            while(c.next()){
                r = c.getRow();
                materia = new LabelField(r.getString(0)){
                    public void paint(Graphics g){      
                        g.setColor(Color.WHITE);
                        super.paint(g);
                   }};;
                materia.setMargin(3, 0, 4, 16);
                headContent.add(materia);
            }
            
            se.close();
            sqliteDB.close(); 
            
             
          
    	    efTitulo = new EditField("", "", 30, EditField.FILTER_FILENAME){
            	public int getPreferredHeight(){
                    return 50;
                }

                public int getPreferredWidth(){
                    return 280;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };
            efTitulo.setBackground(BackgroundFactory.createLinearGradientBackground(Color.GAINSBORO, Color.GAINSBORO,Color.GAINSBORO,Color.GAINSBORO));
            efTitulo.setPadding(5, 5, 5, 5);
            efTitulo.setMargin(5, 5, 15, 5);
 
           
            
            VerticalFieldManager contentNota = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            contentNota.setMargin(0,0,0,0);
            efNota = new EditField("", "", 500, EditField.USE_ALL_HEIGHT){
            	public int getPreferredHeight(){
                    return 120;
                }

                public int getPreferredWidth(){
                    return 280;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };
           // efNota.setFont(fapunte);
            efNota.setBackground(BackgroundFactory.createLinearGradientBackground(Color.GAINSBORO, Color.GAINSBORO,Color.GAINSBORO,Color.GAINSBORO));
            efNota.setPadding(5, 5, 55, 5);
            efNota.setMargin(5, 5, 5, 5);
            contentNota.add(efTitulo);
            contentNota.add(efNota);
            allContent.add(headContent);
            allContent.add(contentNota);
            add(allContent);
   
        }catch (Exception e){
            Dialog.alert(e.getMessage().toString());
            e.printStackTrace();
            } 
        //menuitem utilizado para guardar
    	MenuItem miGuardar = new MenuItem("Guardar" , 100, 1){
    	    public void run(){
    	    	  textoTitulo = efTitulo.getText();
    	          textoApunte = efNota.getText();
    	          textoPrioridad = (String) ocfPrioridad.getChoice(ocfPrioridad.getSelectedIndex());
    	          textoFecha = fecha.toString();
    	          if(textoTitulo.length()==0){
            		  Dialog.alert("Ingrese un titulo para el apunte"); 
            	  }else{
	    	    	 try{
	    	    		 guardado = true;
	    	    		//se inserta con un statement insertApunte de la clase config con los parametros
	    	    		//titulo, apunte, prioridad, fecha
	    	    		URI uri = URI.create(path.Path());
	    	         	Database sqliteDB = DatabaseFactory.open(uri);
	    	         	Statement it = sqliteDB.createStatement(statement.InsertApunte()+"("+idMateria+",'"+textoTitulo+"','"+textoApunte+"','"+textoPrioridad+"','"+textoFecha+"')");
						it.prepare();
						it.execute();
						it.close();
	    	            sqliteDB.close();
	    	            //se coloca en el campo de titulo y en el campo de apunte "vacio"
	    	            efTitulo.setText("");
	    	            efNota.setText(""); 
	    	            
	    	     		Dialog.alert("Guardado con exito");
	    	         }catch (Exception e){
	    	         Dialog.alert("error guardar "+e.getMessage().toString());
	    	         e.printStackTrace();
	    	         guardado = false;
	    	         }
            	  }
    	    }
    	};
        //agregar el elemento al menu
    	addMenuItem(miGuardar);
    }

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub	
	}
	public boolean onClose() {
		//force the app to quit
		if(efTitulo.getText().length()==0){
			guardado = true;
		}
		if(guardado == true){
		 TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_RIGHT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
		openScreen(new notaLista(idMateria));		
		}else{
			UiApplication.getUiApplication().invokeLater(new Runnable(){
				public void run(){

					Object[] choices = new Object[] {"Guardar", "No Guardar" };
					int result = Dialog.ask("Todavía no ha guardado nada?", choices, 0);
					//por medio de case se elige por numero de posicion del array de choices
					switch (result) {
					//si la eleccion es 0 Guarda los datos
					case 0:
						textoTitulo = efTitulo.getText();
		    	          textoApunte = efNota.getText();
		    	          textoPrioridad = (String) ocfPrioridad.getChoice(ocfPrioridad.getSelectedIndex());
		    	          textoFecha = fecha.toString();
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
		    	            sqliteDB.close();
		    	         }catch (Exception e){
		    	         Dialog.alert("error guardar "+e.getMessage().toString());
		    	         e.printStackTrace();
		    	         }finally{
		    	        	 openScreen(new notaLista(idMateria));
		    	         }
		            	  }
						break;
					case 1:
					//si la eleccion es 1 entonces procede eliminar la lista de apuntes por idMateria	
						openScreen(new notaLista(idMateria));
						break;
					}				
				}});
		}
		return true;
	}

}

