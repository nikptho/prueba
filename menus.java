package project;

import java.io.*;

public class menus {
	//Esta clase servirá para cualquier interacción del usuario con el programa
	
	//Declaro BufferedReader para que el programa pueda leer los datos que sean introducidos por el usuario.
	BufferedReader Read=new BufferedReader (new InputStreamReader(System.in));
	
	//Este será el menú principal del programa, declarado en la clase main
	public char menu_p() throws IOException {
		char h;String t;
		
		//creo un bucle para evitar que introduzca valores nulos, en blanco o que sean mayores de 1 caracter	
		do {
		System.out.print("//--LOGIN--\\\\ "+"\n"+"*****************************************"+"\n"+"[+] A.Logueate como gestor"+"\n"+"[+] B.Logueate como cliente"+"\n"+
		"[+] C.Registrate como cliente"+"\n"+"[+] D.SALIR"+"\n"+"*****************************************"+"\n"+"Introduce una opcion: ");
		//
		t=Read.readLine().toUpperCase();
		if(t.length()!=1) {System.out.println("Tienes que introducir un caracter");}
		}while(t.isEmpty() || t==null || t.length()!=1);
		//convierto el string en carácter
		h=t.charAt(0);
		
		//devuelvo el carácter
		return h;
	}
	
	
	//este metodo será el menú principal del gestor
	public int gestor() throws IOException {
		String t; int x=0; boolean f;
		//uso este bucle para evitar que me meta valores nulos,alfabéticos o en blanco
		do {f=false;
		do {
			System.out.print("---OPCIONES---"+"\n"+"********************************************************"+"\n"+
		"1.Añadir cliente"+"\n"+"2.Eliminar cliente"+"\n"+"3.Añadir Actividad"+"\n"+"4.Eliminar Actividad"+"\n"+"5.Añadir pabellón"+"\n"+
					"6.Eliminar pabellón"+"\n"+"7.Listar todos los clientes"+"\n"+"8.Listar todas las actividades"+"\n"+"9.Listar todos los pabellones"+"\n"+
		"10.Listar las actividades que tengan actualmente sitios libres"+"\n"+"11.Salir de la aplicación"+"\n"+"********************************************************"+"\n"+
					"Introduce una opción: ");
			t=Read.readLine();
		}while(t.isEmpty() || t==null);
		//uso este trycatch para convertir el string en un int, si salta la excepcion NumeberFormatException la variable booleana pasa a ser true y se repite el proceso
		try {
			x=Integer.parseInt(t);
		} catch(NumberFormatException e) {f=true;}
		}while(f==true);
		return x;
	}
	
	
	//este metodo se utilizará como menú principal de los clientes que hayan iniciado sesion correctamente
	public int cliente() throws IOException {
		String t; int x=0;boolean f;
		//uso este bucle para evitar que me meta valores nulos,alfabéticos o en blanco
		do {
			f=false;
			do {
				System.out.println("---OPCIONES---"+"\n"+"********************************************************"+"\n"+
						"1.Listar todas las actividades"+"\n"+"2.Listar todos los pabellones"+"\n"+"3.Listar las actividades con plazas libres"+"\n"+"4.Listar las actividades con plazas libres que cuesten menos de una cantidad."+"\n"
						+"5.Listar las actividades con plazas libres que se desarrollen en un pabellon determinado"+"\n"+
						"6.Mostrar las informacion de una actividad."+"\n"+"7.Listar las actividades a las que estoy suscrito"+"\n"+"8.Suscribirme a una actividad"+"\n"+"9.Desuscribirme de una actividad"+"\n"+
			"10.Salir de la aplicacion"+"\n"+"********************************************************"+"\n"+
						"Introduce una opción: ");
				t=Read.readLine();
			} while(t.isEmpty() || t==null);
			
			//uso este trycatch para convertir el string en un int, si salta la excepcion NumberFormatException la variable booleana pasa a ser true y se repite el proceso
			try {
				x=Integer.parseInt(t);
			}catch(NumberFormatException e) {f=true;}
			
		}while(f==true);
		return x;
	}
	
	//metodo utilizado para preguntarle al usuario una pregunta que tengo que responder con una 'S' o una 'N',
	public char next(String cad) throws IOException {
		char h; String t;
		do {
		do {
		System.out.println(cad);
		t=Read.readLine().toUpperCase();	
		if(t.length()!=1) {System.out.println("Tienes que introducir un caracter");}
		}while(t.isEmpty() || t==null || t.length()!=1);
		h=t.charAt(0);
		}while(h!='S' && h!='N');
		return h;
			}
	
	
	/*public char pc(String cad) throws IOException {
		char h;
		System.out.println(cad);
		h=Read.readLine().toUpperCase().charAt(0);
		
		return h;
	}*/
	
	
	//este metodo se usa cuando el usuario tienes que introducir un dato numerico entero
	//sigue la misma dinamica que los menus de gestor y clientes pero con el añadido de si el numero introducido es inferior al valor "limit" se repite el bucle
	public int pn(String cad,int limit) throws IOException {
		String t;int x=0;boolean f;
		do {
			f=false;
			do {
				System.out.println(cad);
				t=Read.readLine();
			}while(t.isEmpty() || t==null);
			try {
				x=Integer.parseInt(t);
				if(x<limit) {System.out.println("el numero introducido tiene que ser mayor que "+limit);}
			}catch(NumberFormatException e) {f=true;}
			
		} while(f==true || x<limit);
		return x;
	}
	
	//este método se usa cuando el usuario tiene que introducir un dato numérico decimal
	//sigue la misma dinámica que los menús de gestor y clientes pero con el añadido de si el número introducido es inferior al valor "limit" se repite el bucle
	public float pd(String cad,float limit) throws IOException {
		String t;float x=0;boolean f;
		do {
			f=false;
			do {
				System.out.println(cad);
				t=Read.readLine();
			}while(t.isEmpty() || t==null);
			try {
				x=Float.parseFloat(t);
				if(x<limit) {System.out.println("el numero introducido tiene que ser mayor que "+limit);}
			}catch(NumberFormatException e) {f=true;}
			
		} while(f==true || x<limit);
		return x;
	}
	
	
	//Este método servirá para introducir cadenas de string sin valores nulos o cadenas en blanco
	public String ps(String cad) throws IOException {
		String t;
		do {
			System.out.println(cad);
			t=Read.readLine();
		}while(t.isEmpty() || t==null);
		return t;
	}
	
	//pide cadenas de string pero te permitirá introducir valores nulos o cadenas en blanco
	public String psw(String cad) throws IOException {
		System.out.println(cad);
		String t=Read.readLine();
		return t;
	}
	
	
	// comparte las características del método ps() pero además comprueba que la cadena sea inferior igual a 16
	public String psl(String cad) throws IOException {
		String t;
		do {
			System.out.println(cad);
			t=Read.readLine();
			if(t.length()>16) {System.out.println("la cadena tiene que ser menor igual a 16 caracteres");}
		}while(t.isEmpty() || t==null || t.length()>16);
		return t;
	}
	
	// comparte las características del método ps() pero además comprueba que la cadena sea inferior igual a 32
	public String psl3(String cad) throws IOException {
		String t;
		do {
			System.out.println(cad);
			t=Read.readLine();
			if(t.length()>16) {System.out.println("la cadena tiene que ser menor igual a 32 caracteres");}
		}while(t.isEmpty() || t==null || t.length()>32);
		return t;
	}


}
