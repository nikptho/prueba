package project;

import java.io.*;
import java.sql.*;

public class main {
	//clase principal de programa
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		//instancio la clase BSDATOS para manejar la base de datos y la clase menus para que el usuario pueda interactuar con el programa
		char h;menus m=new menus();
		BSDATOS b=new BSDATOS();
		
		do {
			//uso el metodo menu_p() de la clase menus para representar el menú principal y que el usuario pueda elegir una opción
			h=m.menu_p();
			//utilizo el switch para filtrar el resultado.
			switch(h) {
			case 'A':
				//si el usuario ha introducido una "a" declaro el objeto gestor y llamo a su metodo inicio()
				gestor g=new gestor();
				g.inicio();
		
				break;
			case 'B':
				//si el usuario ha introducido una "b" declaro el objeto de la clase cliente y llamo a su metodo inicio() pasandole el objeto BSDATOS y menus
				cliente c=new cliente();
				c.inicio(b, m);
		
				break;
			case 'C':
				//si el usuario ha introducido una "c" declaro otro objeto de la clase cliente y llamo a su metodo n_cliente()
				cliente c1=new cliente();
				c1.n_client(b);
				break;
			case 'D':System.out.println("Gracias por utilizar nuestra aplicacion");break;
			default:System.out.println("opcion no disponible");break;
			}
		}while(h!='D');
		
		

	}

}
