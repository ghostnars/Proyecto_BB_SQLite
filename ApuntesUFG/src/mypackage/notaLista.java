package mypackage;

import java.util.Vector;

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
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import estilos.BitmapButtonField;
import estilos.Metodos;

public class notaLista extends Metodos implements FieldChangeListener {
	BasicEditField nota;
	EditField efTitulo;	
	Vector idApunte = new Vector();
	Vector Prioridad = new Vector();
	Config path = new Config();
	Config statement = new Config();
	int idMateria;
	Vector bb = new Vector();
	Vector Codigo = new Vector();
	String val;
	String mensaje;
	Bitmap tagBitmap;
	String direccion;
	private Font ffecha;
    public notaLista(int id_materia)
    {  
	 getMainManager().setBackground(BackgroundFactory.createLinearGradientBackground(Color.BLACK, Color.BLACK,Color.BLACK,Color.BLACK));
	 idMateria = id_materia;
	 Bitmap elementoBitmap = Bitmap.getBitmapResource("fondomaterias.png");
	 try
	 {
	 	FontFamily ffFont = FontFamily.forName("Arial");
	 	ffecha = ffFont.getFont(Font.ANTIALIAS_STANDARD, 13);
	 	
	 }catch (ClassNotFoundException e){
	 	   System.out.println(e.getMessage());
	 }
	 try{
     	URI uri1 = URI.create(path.Path());
     	Database sqliteDB1 = DatabaseFactory.open(uri1);
	         Statement se1 = sqliteDB1.createStatement(statement.SelectNomMateria()+idMateria);
	         se1.prepare();
	         Cursor c1 = se1.getCursor();
	         Row r1;
             while(c1.next()){
                 r1 = c1.getRow();
                 setTitle(r1.getString(0));
             	}
	         se1.close();
	         sqliteDB1.close();

	   }catch(Exception e){
	         Dialog.alert("error al hacer la lista "+e.getMessage().toString());
	         e.printStackTrace();
	         } 
	 	
		//Comieza el select de el titulo y la fecha para ser mostrada en cada una de las listas;
     try{
     	URI uri = URI.create(path.Path());
     	Database sqliteDB = DatabaseFactory.open(uri);
             Statement se = sqliteDB.createStatement(statement.SelectTitulo()+idMateria+"");
             se.prepare();
             Cursor c = se.getCursor();
             Row r;
             int i = 0;
             while(c.next()){ 
              r = c.getRow();
              val = r.getString(2);
              //si el resultado de la busqueda retorna el string 
              //direccion toma el valor del string mas la extension de imagen .png
				 if(val.equals("Alta")){
					direccion="tagAlta.png";				
					}else if(val.equals("Media")){
						direccion = "tagMedia.png";
						}else if(val.equals("Baja")){
							direccion = "tagBaja.png"; 
							}
				//asigna a tagBitmap la direccion de la imagen antes asignado
				tagBitmap = Bitmap.getBitmapResource(direccion);

				bb.addElement(new BitmapButtonField(Bitmap.getBitmapResource(direccion), Bitmap.getBitmapResource("barraboton2.png"), BitmapButtonField.FIELD_LEFT | BitmapButtonField.FIELD_VCENTER));
				((Field) bb.elementAt(i)).setChangeListener(this);
				((Field) bb.elementAt(i)).setMargin(0, 0, 0, 0);
				LabelField fech = new LabelField(r.getString(3)){
                    public void paint(Graphics g){      
                        g.setColor(Color.WHITE);
                        super.paint(g);
                   }};;
                   fech.setFont(ffecha);
                   fech.setMargin(0,0,0,5);
				//ASIGNA TEXTO AL EL ELEMENTO DE LISTA
				LabelField titulo = new LabelField(r.getString(0)){
                    public void paint(Graphics g){      
                        g.setColor(Color.WHITE);
                        super.paint(g);
                   }};;
                   titulo.setMargin(0,0,0,5);
				
				/**LabelField fecha = new LabelField(r.getString(3)){
                    public void paint(Graphics g){      
                        g.setColor(Color.WHITE);
                        super.paint(g);
                   }};;*/
				
				idApunte.addElement(""+r.getInteger(1));
				//CREAR ELEMENTO DE LISTA
				HorizontalFieldManager elementolista = new HorizontalFieldManager(Field.USE_ALL_WIDTH | Field.FIELD_VCENTER);
				elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(0,1,0,0), elementoBitmap));
				VerticalFieldManager elemento = new VerticalFieldManager(Field.USE_ALL_WIDTH | Field.FIELD_VCENTER);
				//elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(0,0,0,5), tagBitmap));
				//AGREGAR A PANTALLA CADA ELEMENTO		
				elementolista.setMargin(3,3,3,3);
				elementolista.add((Field)bb.elementAt(i));
				
				//elementolista.add(bfTag);
				elemento.add(titulo);
				elemento.add(fech);
				elementolista.add(elemento);
				add(elementolista);		
				
                 i++;
             }
             se.close();
             sqliteDB.close();
             
             //si el SELECT de notas no retorna nada muestra un error en pantalla
           if(i==0){
            WLabelField nohay = new WLabelField("No hay ninguna nota aun!\n\n"
             +"Para crear una nota \n\n"
             +"precione men� y eliga \n\n"
             +"'Nuevo Apunte'");
             
            nohay.setMargin(39,0,0,65);
			add(nohay);
             }
 		
     }catch (Exception e){
     Dialog.alert("error al hacer la lista "+e.getMessage().toString());
     e.printStackTrace();
     	}   
    	//elemento menu item para crear un Nuevo Apunte
    	MenuItem mymenu = new MenuItem("Nuevo Apunte" , 100, 1){
    	    public void run(){
    	       //al precionar el boton dirige a nota crear y pasa el idMateria a la siguiente pantalla
    	    	openScreen(new notaCrear(idMateria));
    	    }
    	};
    	//elemento menu item para Eliminar toda la lista de apuntes "por materia"
    	MenuItem mymenu2 = new MenuItem("Eliminar todo" , 100, 2){
    	    public void run(){
    	    	//elemento de pregunta si desea eliminar o cancelar la operacion
    	    	UiApplication.getUiApplication().invokeLater(new Runnable(){
    				public void run(){

    					Object[] choices = new Object[] {"Cancelar", "Eliminar" };
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
    	    		         	Statement st = sqliteDB1.createStatement(statement.DeleteTodo()+idMateria);
    	    		            st.prepare();
    	    		            st.execute();
    	    		            st.close();
    	    		            sqliteDB1.close();
    	    		            Dialog.alert("Eliminado");
    	    		         }catch (Exception e){
    	    		         Dialog.alert("Error al eliminar "+e.getMessage().toString());
    	    		         e.printStackTrace();
    	    		         }
    						openScreen(new materiaLista());
    						break;
    					}				
    				}});

    	    	  
    	    	         }
    	    	};
    	//agrega los dos elementos al menu del dispositivo   	
        addMenuItem(mymenu);
    	addMenuItem(mymenu2);
}
	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		for(int j=0;j<=bb.size()-1;j++){
			if( bb.elementAt(j)== field ){
				TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
				transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
				transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
				transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
				UiEngineInstance engine = Ui.getUiEngineInstance();
				engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
			openScreen(new notaModificar(idMateria,(String) idApunte.elementAt(j)));
			}
		}
	}
	
	public boolean onClose() {
		//fuerza la app a cerrar o envia a la page que se desee con tranciciones y direccion
		 TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_RIGHT);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
		openScreen(new materiaLista());
		return true;
	}

}
