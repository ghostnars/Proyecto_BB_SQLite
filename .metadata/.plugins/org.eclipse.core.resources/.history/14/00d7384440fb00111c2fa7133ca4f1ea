package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.decor.BorderFactory;
import estilos.BitmapButtonField;
import estilos.Metodos;


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
	BitmapButtonField materia1,materia2,materia3,materia4,materia5,materia6,materia7;
	BitmapButtonField[] materias={materia1,materia2,materia3,materia4,materia5,materia6};
    public notaMostrar()
    {   
    	Bitmap elementoBitmap = Bitmap.getBitmapResource("fondomaterias.png");
    	for(int i =0;i<6;i++){
    	  materias[i] = new BitmapButtonField(Bitmap.getBitmapResource("barratitulo0.png"), Bitmap.getBitmapResource("barratitulo1.png"), BitmapButtonField.FIELD_LEFT | BitmapButtonField.FIELD_VCENTER );
			materias[i].setChangeListener(this);
			
			
			//ASIGNA TEXTO AL EL ELEMENTO DE LISTA
			WLabelField text = new WLabelField("");
			text.setMargin(0, 0, 0, 15);
			//CREAR ELEMENTO DE LISTA
			
			HorizontalFieldManager elementolista = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
			elementolista.setBorder(BorderFactory.createBitmapBorder(new XYEdges(0,1,0,0), elementoBitmap));
			
			//AGREGAR A PANTALLA CADA ELEMENTO
			
			
			elementolista.add(materias[i]);
			elementolista.add(text);
			elementolista.setMargin(2, 5, 2, 5);
			add(elementolista);
    	}
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
