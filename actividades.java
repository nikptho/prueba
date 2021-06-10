package project;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//esta clase servirá para gestionar los datos de las actividades

public class actividades {
	//declaración de los datos de la actividad
	private int id;
	private String nombre;
	private String nombre_pab;
	private String descripcion;
	private String  inicio;
	private float precio;
	private int plazas_t;
	private int plazas_ocup;
	
	
	//método utilizado para que el usuario introduzca los datos de la actividad
	public void pedir(BSDATOS b) throws SQLException, IOException {
		//declaración de la clase menus
		menus m=new menus();
		//metodo n_id de la misma clase para obtener el siguiente id
		id=n_id(b);
		nombre=m.psl("Introduce el nombre de la actividad");
		//bucle que se repetirá mientras no se encuentre el pabellón introducido por el usuario en la tabla pabellones
		do {
		nombre_pab=m.psl("Introduce el nombre del pabellon");
		if(!b.comp("pabellon", "pabellones", nombre_pab)) {System.out.println("No se ha encontrado el pabellon en la base de datos");}
		}while(!b.comp("pabellon", "pabellones", nombre_pab));
		descripcion=m.psl3("Introduce la descripcion de la actividad");
		//bucle que se repetirá mientras las fecha no tenga el formato correcto
		do {
			inicio=m.ps("Introduce la fecha de inicio de la actividad (yyyy-mm-dd)");
		}while(fec(inicio));
		
		precio=m.pd("Introduce el precio de la actividad", 0);
		
		
		plazas_t=m.pn("Introduce el numero de plazas totales",0);
		
		//bucle que se repetirá mientras las plazas ocupadas sean mayores a las plazas totales
		do {
		plazas_ocup=m.pn("Introduce el numero de plazas ocupadas",0);
		if(plazas_ocup>plazas_t) {System.out.println("Tienes que inroducir un numero inferior a las plazas totales");}
		}while(plazas_ocup>plazas_t);
		}
	
	//conjunto de metodos utilizados para devolver los datos de la actividad
	//************************************************
	public int getid() {return id;}
	public int getotal() {return plazas_t;}
	public int getocupadas() {return plazas_ocup;}
	public String getnombre() {return nombre;}
	public String getpab() {return nombre_pab;}
	public String getdescrip() {return descripcion;}
	public String getfecha() {return inicio;}
	public float getprecio() {return precio;}
	//***********************************************
	
	//método utilizado para comprobar que la fecha se introduzca en el formato adecuado
	private boolean fec(String cad) {
		boolean f=false;
		try {
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		s.setLenient(false);
		s.parse(cad);
		}catch (ParseException e) {f=true;}
		return f;
		}
	
	//método utilizado para sacar el id máximo de la tabla actividades, para despues devolverlo sumandole una unidad
	private int n_id(BSDATOS b) throws SQLException {
		ResultSet s=b.p_r("select max(id) as total from actividades");
		s.next();
		return s.getInt("total")+1;
	}

}
