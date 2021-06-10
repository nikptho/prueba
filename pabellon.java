package project;

import java.io.IOException;
import java.sql.SQLException;
//Esta clase servirá para gestionar los datos de los pabellones
public class pabellon {
	//declara los datos del pabellón
	private String pabellon;
	private String localidad;
	
	//método utilizado para que el usuario introduzca los datos del pabellón
	public void pedir(BSDATOS b) throws IOException, SQLException {
		//instancia la clase menu para que el usuario pueda interactuar con el programa a través de los métodos de la clase
		menus m=new menus();
		do {
			pabellon=m.psl("Introduce el pabellon");
		}while(b.comp("pabellon", "pabellones", pabellon));
		
		localidad=m.psl("Introduce la localidad del pabellon");
	}
	
	//conjunto de métodod para devolver los datos del pabellón
	public String getpav() {return pabellon;}
	public String getloc() {return localidad;}

}
