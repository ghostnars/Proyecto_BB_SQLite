package mypackage;


public class Config {
	private String version = "0.0.9.3.sqlite";
	private String path;
	private String Createmateria;
	private String Createapunte;
	private String Insertmateria;
	private String Insertapunte;
	private String Selectnommateria;
	private String Selectmateria;
	private String Selecttitulo;
	private String Selectapunte;
	private String Updateapunte;
	private String Deleteapunte;
	private String Deletetodo;
	public String Path(){
		path = "file:///SDCard/Databases/NOTAS/NotasDB_"+version;
		return path;
	}
	public String CreateMateria(){
		Createmateria = "CREATE TABLE IF NOT EXISTS MATERIA(id_materia INTEGER PRIMARY KEY, nombre_materia TEXT)";
		return Createmateria;
	}
	public String CreateApunte(){
		Createapunte = "CREATE TABLE IF NOT EXISTS APUNTE(id_materia INTEGER, id_apunte INTEGER PRIMARY KEY, titulo TEXT, apunte TEXT, prioridad TEXT, fecha DATETIME)";
		return Createapunte;
	}
	public String InsertMateria(){
		Insertmateria = "INSERT INTO MATERIA(nombre_materia)values";
		return Insertmateria;
	}
		public String InsertApunte(){
		Insertapunte = "INSERT INTO APUNTE(id_materia,titulo,apunte,prioridad,fecha)values";
		return Insertapunte;
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
		Selectapunte = "SELECT titulo,apunte,prioridad,fecha FROM APUNTE WHERE id_materia=";
		return Selectapunte;
	}
	public String UpdateApunte(){
		Updateapunte = "UPDATE APUNTE SET titulo = ?, apunte = ?, prioridad = ?, fecha = ?  WHERE id_materia = ? AND id_apunte = ?";
		return Updateapunte;
	}
	public String DeleteApunte(){
		Deleteapunte = "DELETE FROM APUNTE WHERE id_apunte = ";
		return Deleteapunte;
	}
	public String DeleteTodo(){
		Deletetodo = "DELETE FROM APUNTE WHERE id_materia = ";
		return Deletetodo;
	}
}
