package project;

import java.io.IOException;
import java.sql.SQLException;
//Esta clase servir� para gestionar los datos de los pabellones
public class pabellon {
	//declara los datos del pabell�n
	private String pabellon;
	private String localidad;
	
	//m�todo utilizado para que el usuario introduzca los datos del pabell�n
	public void pedir(BSDATOS b) throws IOException, SQLException {
		//instancia la clase menu para que el usuario pueda interactuar con el programa a trav�s de los m�todos de la clase
		menus m=new menus();
		do {
			pabellon=m.psl("Introduce el pabellon");
		}while(b.comp("pabellon", "pabellones", pabellon));
		
		localidad=m.psl("Introduce la localidad del pabellon");
	}
	
	//conjunto de m�todod para devolver los datos del pabell�n
	public String getpav() {return pabellon;}
	public String getloc() {return localidad;}

}
