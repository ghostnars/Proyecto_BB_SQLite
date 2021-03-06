package mypackage;

import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import ws.WsMovilSoap_Stub;

import com.twmacinta.util.Autenticacion;

import estilos.BitmapButtonField;
import estilos.Metodos;

public class login extends Metodos implements FieldChangeListener {
		BitmapButtonField boton;
		WLabelField lbluser , lblpwd;
		LabelField  label1;
		BasicEditField name;
		PasswordEditField pwd;
		ButtonField btm;
		protected String _login;
		protected String _password;
		protected String _usuario;
		protected String _claveus;
		String carnet="";
		String code="";
		String value="";
		String token = "";
		String userufg = "";
		String pwdufg  = "";
    	Autenticacion verificar = new Autenticacion();
    	Config path = new Config();
		Config statement = new Config();
		
	public  login (){
		

		getMainManager().setBackground(BackgroundFactory.createLinearGradientBackground(Color.BLACK, Color.BLACK,Color.BLACK,Color.BLACK));
 		
		Bitmap bitmapfondo = Bitmap.getBitmapResource("fondonoticias.png");
		VerticalFieldManager fondo = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH);
		fondo.setBorder(BorderFactory.createBitmapBorder(new XYEdges(10,10,0,10), bitmapfondo));
		fondo.setMargin(5, 5, 5, 5);
		label1 = new LabelField ("APUNTES UFG");
        label1.setMargin(0, 0, 0, 90);
        fondo.add(label1);
		/**
		label1 = new LabelField ("ACCEDER");
        label1.setMargin(0, 0, 10, 110);
        fondo.add(label1);
	*/
        HorizontalFieldManager contenido = new HorizontalFieldManager(HorizontalFieldManager.USE_ALL_WIDTH);
        VerticalFieldManager contenidov0 = new VerticalFieldManager(VerticalFieldManager.FIELD_RIGHT);
        VerticalFieldManager contenidov1 = new VerticalFieldManager(VerticalFieldManager.FIELD_LEFT);
        
        Bitmap _bitmap = Bitmap.getBitmapResource("rounded-border1.png");
        VerticalFieldManager vfm = new VerticalFieldManager(VerticalFieldManager.FIELD_RIGHT);
	    vfm.setBorder(BorderFactory.createBitmapBorder(new XYEdges(10,15,10,15), _bitmap));
	    vfm.setMargin(20, 0, 0, 0);
	       

        lbluser=new WLabelField ("Usuario");
        lbluser.setMargin(30, 0, 0, 28);
        name = new BasicEditField("", "ia.ghostnars", 100, EditField.NO_NEWLINE){
        	public int getPreferredHeight(){
                return 30;
            }

            public int getPreferredWidth(){
                return 140;
            }

            public void layout(int width, int height){
            	setExtent(getPreferredWidth(), getPreferredHeight());
                super.layout(getPreferredWidth(), getPreferredHeight());
            }
        };
        
      
        name.setFont(getFont());
        name.setBackground( BackgroundFactory.createSolidTransparentBackground( Color.WHITE, 50 ) );
        name.setMargin(0, 0, 0, 0);
        name.setPadding(0,0,0,0);
      
        VerticalFieldManager vfm1 = new VerticalFieldManager(VerticalFieldManager.FIELD_RIGHT);
	    vfm1.setBorder(BorderFactory.createBitmapBorder(new XYEdges(10,15,10,15), _bitmap));
	    vfm1.setMargin(25, 0, 0, 0);
        lblpwd = new WLabelField ("Contraseņa");
        lblpwd.setMargin(42, 0, 0, 0);
        pwd = new PasswordEditField("", "phantomb", 100, EditField.NO_NEWLINE){
        	public int getPreferredHeight(){
                return 30;
            }

            public int getPreferredWidth(){
                return 140;
            }

            public void layout(int width, int height){
                setExtent(getPreferredWidth(), getPreferredHeight());
                super.layout(getPreferredWidth(), getPreferredHeight());
            }
        };
        pwd.setFont(getFont());
        pwd.setBackground( BackgroundFactory.createSolidTransparentBackground( Color.WHITE, 50 ) );
        pwd.setMargin(5, 0, 0, 0);
        pwd.setPadding(0,0,0,0);
        
        
        vfm.add(name);
        
        
        vfm1.add(pwd);
        
        contenidov0.setMargin(12, 0, 30, 0);
        contenidov1.setMargin(10, 0, 10, 0);
        contenidov0.add(lbluser);
        contenidov0.add(lblpwd);
        contenidov1.add(vfm);
        contenidov1.add(vfm1);
        
        contenido.add(contenidov0);
        contenido.add(contenidov1);
        fondo.add(contenido);
		
		boton = new BitmapButtonField(Bitmap.getBitmapResource("acceder4.png"), Bitmap.getBitmapResource("acceder3.png"),BitmapButtonField.FIELD_BOTTOM);
		boton.setChangeListener(this);
		boton.setMargin(5,0,11,90);
		fondo.add(boton);
      	add(fondo);
      	
      


		
	}
	
	

	public void fieldChanged(Field field, int context) {
	if ( boton == field){
			final String destination = this.name.getText();
			final String destination1 = this.pwd.getText();
			
	        if ((destination == null) || (destination1 == null)   ){
	            Dialog.alert("Inserte su Usuario");
	            return;
	        }
	        
            if (CoverageInfo. isOutOfCoverage ()){
            	Dialog.alert ("No hay conexion. Por favor, intente de nuevo.");
            	// return "";
            }else if (WLANInfo. getWLANState () == WLANInfo. WLAN_STATE_CONNECTED ){
            	//Dialog. alert ("Inalambricos encontrados");
            	token = verificar.validar(name.getText(), pwd.getText());
            	if (token == ""){
            		Dialog. alert ("Usuario y/o Contraseņa no son Validos");
            	}else if(token == "error"){
            		Dialog. alert ("Usted no posee conexion a internet. Tiene que estar conectado a WI-FI o tener plan de datos habilitado");
            	}else{
   
            		value="deviceside=true";
            		
            	}
            	value="interface=wifi";
            	// return "";
            }else{
            	// Web Service...
            	token = verificar.validar(name.getText(), pwd.getText());
            	
            	if (token == ""){
            		Dialog. alert ("Usuario y/o Contraseņa no son Validos");
            	}else if(token == "error"){
            		Dialog. alert ("Usted no posee conexion a internet. Tiene que estar conectado a WI-FI o tener plan de datos habilitado");
            	}else{
            		TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
        	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
        	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_LEFT);
        	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
        	        UiEngineInstance engine = Ui.getUiEngineInstance();
        	        engine.setTransition(this, null, UiEngineInstance.TRIGGER_PUSH, transition);
            		

            		
            		value="deviceside=true";
            		
            		
            		_login = token;
            		_password = "d8dc4f3276a19391c160b7e6b65c4a10";
            		_usuario = name.getText();
            		_claveus = pwd.getText();
            		
            		
            		try{ 		
            			URI uri = URI.create(path.Path());
            			Database sqliteDB = DatabaseFactory.open(uri);
            			carnet = _login;
            		    code   =_password; 
            		    userufg = _usuario;
            		    pwdufg  = _claveus;
            			
            			WsMovilSoap_Stub service= new WsMovilSoap_Stub();    	
            			ws.ArrayOfAsignaturaInscrita result= service.recuperarAsignaturasInscritas(carnet, code);
            			
            			token = verificar.validar(userufg, pwdufg);
            			
            				for(int i=0;i<result.getAsignaturaInscrita().length ;i++){	
            					String nombre = result.getAsignaturaInscrita()[i].getNombreAsignatura();
            					String codigo = result.getAsignaturaInscrita()[i].getIdAsignatura();
            					String grupo = result.getAsignaturaInscrita()[i].getGrupo();
            					//CREA EL STATEMENT PARA GUARDAR POR CADA REPETICION
            					Statement st1 = sqliteDB.createStatement(statement.InsertMateria()+"('"+ nombre +"','"+ codigo +"','" + grupo +"')");
            					st1.prepare();
            					st1.execute();
            					st1.close();
            				}
            				
            			sqliteDB.close();
            			openScreen(new materiaLista());
            		}catch(Exception z ){
            			Dialog.alert(z.toString());
            			}
            		
            		
            	}
            }
		}
	}

	
}

