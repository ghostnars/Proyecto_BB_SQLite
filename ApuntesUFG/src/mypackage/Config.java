package mypackage;


public class Config {
	private String version = "0.0.2.4.sqlite";
	private String path;
	private String Createmateria;
	private String Createapunte;
	private String Selectnommateria;
	private String Selectmateria;
	private String Selecttitulo;
	private String Selectapunte;
	
	public String Path(){
		path = "file:///SDCard/Databases/NOTAS/NotasDB_"+version;
		return path;
	}
	
	public String CreateMateria(){
		Createmateria = "CREATE TABLE MATERIA(id_materia INTEGER PRIMARY KEY, nombre_materia TEXT)";
		return Createmateria;
	}
	
	public String CreateApunte(){
		Createapunte = "CREATE TABLE APUNTE(id_materia INTEGER, id_apunte INTEGER PRIMARY KEY, titulo TEXT, apunte TEXT, prioridad TEXT)";
		return Createapunte;
	}
	
	public String SelectMateria(){
		Selectmateria = "SELECT nombre_materia FROM MATERIA";
		return Selectmateria;
	}
	
	public String SelectNomMateria(){
		Selectnommateria = "SELECT nombre_materia FROM MATERIA WHERE id_materia=";
		return Selectnommateria;
	}
	
	public String SelectTitulo(){
		Selecttitulo = "SELECT titulo,id_apunte,prioridad FROM APUNTE WHERE id_materia=";
		return Selecttitulo;
	}
	
	public String SelectApunte(){
		Selectapunte = "SELECT titulo,apunte,prioridad FROM APUNTE WHERE id_materia=";
		return Selectapunte;
	}
	
}
