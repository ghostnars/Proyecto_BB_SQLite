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
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
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


public class notaModificar extends Metodos implements FieldChangeListener {
	BasicEditField nota;
	EditField efTitulo,efNota;	
	Config path = new Config();
	String idApunte;
	int idMateria;
	String titulos;
	String apuntes;
	String date;
	DateField fecha;
	ObjectChoiceField ocfPrioridad;
	LabelField materia;
	LabelField lblfecha;
	String choices[] = {"Alta","Media","Baja"};
	Config statement = new Config();
	private Font ftitulo;
	private Font ffecha;
	private Font fmateria;
	private Font fapunte;
    public notaModificar(int id_materia,String id_apunte)
    {   
    	Bitmap bitmapfondo = Bitmap.getBitmapResource("notepad4.png");
		getMainManager().setBackground(BackgroundFactory.createBitmapBackground(bitmapfondo));

		
    	idMateria = id_materia;
    	idApunte = id_apunte;
    	
    	
		VerticalFieldManager allContent = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH);
		

       
   	 try
     {
		FontFamily ffFont = FontFamily.forName("Comic Sans MS");
     	ffecha = ffFont.getFont(Font.ITALIC, 13);
     	fmateria = ffFont.getFont(Font.ANTIALIAS_DEFAULT, 15);
     	ftitulo = ffFont.getFont(Font.BOLD, 14); 
     	fapunte = ffFont.getFont(Font.ANTIALIAS_DEFAULT, 16); 
     }catch (ClassNotFoundException e){
     	   System.out.println(e.getMessage());
     }
    	
    	
    	  int iSetTo = 2;
          ocfPrioridad = new ObjectChoiceField("Prioridad: ", choices, iSetTo);
          //allContent.add(ocfPrioridad);
          setTitle(ocfPrioridad);
    	
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
          fecha = new DateField("", System.currentTimeMillis(), dateFormat, Field.FIELD_RIGHT);
        
          
    	try{
        	URI uri = URI.create(path.Path());
        	Database sqliteDB = DatabaseFactory.open(uri);
            Statement se = sqliteDB.createStatement(statement.SelectNomMateria()+idMateria+"");
            se.prepare();
            Cursor c = se.getCursor();
            Row r;
            int i = 0;
            
            while(c.next()){
                r = c.getRow();
                materia = new LabelField(r.getString(0)) ;
                materia.setMargin(0, 0, 0, 22);
                materia.setFont(fmateria);
                
               
                
            }
            
            se.close();
            sqliteDB.close();
    	  }catch (Exception e){
              Dialog.alert(e.getMessage().toString());
              e.printStackTrace();
              } 
    	

    	
		
        try{
        	URI uri1 = URI.create(path.Path());
        	Database sqliteDB1 = DatabaseFactory.open(uri1);
        	Statement se1 = sqliteDB1.createStatement(statement.SelectApunte()+idMateria+" AND id_apunte="+idApunte+"");
            se1.prepare();
            Cursor c1 = se1.getCursor();
            Row r1;
             
            
            while(c1.next()){
                r1 = c1.getRow();
                
               titulos 		= r1.getString(0).toUpperCase();
               apuntes 		= r1.getString(1);
               date			= r1.getString(3);
                
            }
            
            se1.close();
            sqliteDB1.close();
            
            lblfecha = new LabelField(date,LabelField.FIELD_RIGHT);
            lblfecha.setMargin(6,5,5,25);
            lblfecha.setFont(ffecha);
            allContent.add(lblfecha);
            allContent.add(materia);
            
            VerticalFieldManager contentTitulo = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            contentTitulo.setMargin(9,0,0,22);
            
         
    	    efTitulo = new EditField("", titulos, 30, EditField.FILTER_FILENAME){
            	public int getPreferredHeight(){
                    return 50;
                }

                public int getPreferredWidth(){
                    return 310;
                }

                public void layout(int width, int height){
                	setExtent(getPreferredWidth(), getPreferredHeight());
                    super.layout(getPreferredWidth(), getPreferredHeight());
                }
            };
            efTitulo.setFont(ftitulo);
            contentTitulo.add(efTitulo);
            allContent.add(contentTitulo);
            
            
            VerticalFieldManager contentNota = new VerticalFieldManager(VerticalFieldManager.FIELD_HCENTER);
            contentNota.setMargin(28,0,0,22);
            //EDITFIELD TITULO  

    	    efNota = new EditField("", apuntes, 500, EditField.FILTER_DEFAULT){
            	public int getPreferredHeight(){
                    return 120;
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
            efNota.setFont(fapunte);
            allContent.add(contentNota);
            add(allContent);
   
        }catch (Exception e){
            Dialog.alert(e.getMessage().toString());
            e.printStackTrace();
            } 
        
        
        
        
        
        
      
	
	MenuItem miGuardar = new MenuItem("Modificar" , 100, 10){
	    public void run(){
	    	  String textoTitulo = efTitulo.getText().toLowerCase();
	          String textoApunte = efNota.getText();
	          String textoPrioridad = (String) ocfPrioridad.getChoice(ocfPrioridad.getSelectedIndex()); 
	          
	          if(textoTitulo.length()==0){
        		  Dialog.alert("Ingrese un titulo para el apunte"); 
        	  }else{
		          try
		          {         
		              // Update the record in the DirectoryItems table for the given id
		        	  URI uri1 = URI.create(path.Path());
		          	  Database sqliteDB1 = DatabaseFactory.open(uri1);
		              Statement exe = sqliteDB1.createStatement(statement.UpdateApunte()); 
		              exe.prepare();                
		              exe.bind(1, textoTitulo);
		              exe.bind(2, textoApunte);
		              exe.bind(3, textoPrioridad);
		              exe.bind(4, fecha.toString());
		              exe.bind(5, idMateria);
		              exe.bind(6, idApunte);
		              
		              exe.execute();                                           
		              exe.close();
		              sqliteDB1.close();   
		              Dialog.alert("Guardado con exito");
		              
        	  }catch (Exception e){
                Dialog.alert(e.getMessage().toString());
                e.printStackTrace();
                }  
    	      }    
    	    }
    	};
        
    	
    	
    	MenuItem menuEliminar = new MenuItem("Eliminar Apunte" , 100, 10){
    	    public void run(){
    	    	
    	      
    	    	UiApplication.getUiApplication().invokeLater(new Runnable(){
			public void run(){

				Object[] choices = new Object[] {"Cancelar", "Eliminar" };
				int result = Dialog.ask("Desea eliminar?", choices, 0);

				switch (result) {
				case 0:
					break;
				case 1:
					try{
	    	    		 
	    	    		 
    		         	URI uri1 = URI.create(path.Path());
    		         	Database sqliteDB1 = DatabaseFactory.open(uri1);
    		         	Statement st = sqliteDB1.createStatement(statement.DeleteApunte()+idApunte);
    		            st.prepare();
    		            st.execute();
    		            st.close();
    		            sqliteDB1.close();
    		            Dialog.alert("Eliminado");
    		         }catch (Exception e){
    		         Dialog.alert("Error al eliminar "+e.getMessage().toString());
    		         e.printStackTrace();
    		         }
					openScreen(new notaLista(idMateria));
					break;
				}				
			}});

    	  
    	         }
    	};
    	
    	
    	addMenuItem(miGuardar);
		addMenuItem(menuEliminar);


    	
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
