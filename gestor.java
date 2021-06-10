package project;

import java.io.IOException;
import java.rmi.ConnectException;
import java.sql.*;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

//método utilizado para gestionar las funciones del usuario gestor
public class gestor {
	//declaracion de atributos 
	private String usuario;
	private String pass;
	
	//método principal de la clase donde se comprobará la validez del usuario y contraseña introducidos y donde se desplegará un menú con las diferentes posibilidades del gestor
	//*********************************************************************************
	public void inicio() throws IOException, ClassNotFoundException, SQLException {
		//declaración de la clase menús, un contador, una variable booleana y una variable carácter
		menus m=new menus(); int cont=0; boolean f=false;char h;
		//este método se repetira mientras que "f" sea false y el método ini() devuelva true
		do {
		//si el contador llega a 3, se le dará la opción al usuario de terminar con el ciclo y volver al menú principal
		if(cont==3) {			
			h=m.next("Has fallado tres veces seguidas el inicio de sesion, ¿quieres seguir intentandolo? S | N" );	
			if(h=='S') {cont=0;} else {f=true;}//si prefiere seguir, el contador vuelve a 0 y si quiere salir f se vuelve true matando el bucle
		} else {
		//introduce el usuario y la contraseña
		usuario=m.ps("Introduce el usuario");
		pass=m.psw("Introduce la contraseña");
		//se añade una unidad al contador
		cont++;}
		}while(ini() && f==false);
		//si el ciclo finaliza con "f" siendo false, se ejecuta el metodo opc(), el menú con las diferentes opciones
		if(f==false) {
		//el menú esta en un trycatch con la excepción CommunicationsException por si acaso hay algún problema con el servidor
		try {
		opc();
		} catch(CommunicationsException e) {System.out.println("No se pudo conectar a la base de datos, error: "+e);
		}
		}
		}
	//**********************************************************************************
	
	
	//metodo donde se intenta conectar a la base de datos con los datos introducidos por el usuario, si salta una sqlException devulve un true, repitiendo el ciclo
	private boolean ini() throws ClassNotFoundException {
		boolean f=false;
		try {
			try {
			BSDATOS b=new BSDATOS();
			b.intro_root(usuario, pass);
			b.close();
			} catch(CommunicationsException e) {f=false;}
		}catch(SQLException e) {f=true;}
		return f;
	}
	
	
	//método con el menú con todas las opciones del gestor
	public void opc() throws ClassNotFoundException, SQLException, IOException {
		//declaración del metodo BSDATOS,menús y variable x
		BSDATOS b=new BSDATOS(); menus m=new menus(); int x;
		//uso del método intro_root() con los datos introducidos previamente para conectarte a la base de datos
		b.intro_root(usuario, pass);
		//bucle que se reiniciará mientra el valor introducido sea diferente de 11
		do {
			x=m.gestor();
			switch(x) {
			case 1:ej1(b);break;
			case 2:ej2(b);break;
			case 3:ej3(b);break;
			case 4:ej4(b);break;
			case 5:ej5(b);break;
			case 6:ej6(b);break;
			case 7:ej7(b);break;
			case 8:ej8(b);break;
			case 9:ej9(b);break;
			case 10:ej10(b);break;
			case 11:System.out.println("Gracias por utilizar el programa"); b.close();;break;
			default:System.out.println("opcion no disponible.");break;
			}
		}while(x!=11);
	}

	
	//introducir un nuevo cliente en la base de datos
	private void ej1(BSDATOS b) throws SQLException, IOException {
		//se declara la clase cliente y se piden los datos con en metodo intro_cliente
		cliente c=new cliente();
		c.intro_cliente(b);
		//se declara un Statement para ejecutar la actualización y posteriormente con el getUpdateCount saber cuantas tuplas se han actualizado
		Statement h=b.p_s();
		h.executeUpdate("insert into clientes values('"+c.getlogin()+"','"+c.getpass()+"','"+c.getnombre()+"','"+c.getapellido()+"','"+c.getdireccion()+"','"+c.gettelefono()+"')");
		if(h.getUpdateCount()==1) {System.out.println("Cliente insertado correctamente");} else {System.out.println("no se pudo insertar el cliente");}
	}
	
	
	//eliminar clientes
	private void ej2(BSDATOS b) throws SQLException, IOException {
		menus m=new menus();String cad;
		//se declara un resultSet para mostrar todos los clientes de la base de datos
		ResultSet s;
		s=b.p_r("select count(login) as total from clientes");
		s.next();
		if(s.getInt("total")==0) {System.out.println("NO HAY NINGUN CLIENTE EN LA BASE DE DATOS.");} else {
		s=b.p_r("select * from clientes");
		System.out.println("LOGIN"+"\t"+"PSSW"+"\t"+"NOMBRE"+"\t"+"APELLIDOS"+"DIRECCION"+"\t"+"TELEFONO");
		while(s.next()) {
			System.out.println(s.getString("login")+"\t"+s.getString("passw")+"\t"+s.getString("nombre")+"\t"+s.getString("apellido")+"\t"+s.getString("direccion")+"\t"+s.getString("telefono"));
		}
		System.out.println();
		//bucle que se repitirá mientras que el id introducido no se encuentre en la tabla clientes
		do {
			cad=m.ps("Introduce el usuario del cliente que quieres eliminar");
			if(!b.comp("login", "clientes", cad)) {System.out.println("no se ha encontrado ese usario en la base de datos");}
		}while(!b.comp("login", "clientes", cad));
		//ejecución de la eliminación de la tupla con el id introducido y a continuación se comprueba cuantas tuplas se han actualizado
		Statement sq=b.p_s();
		sq.executeUpdate("delete from clientes where login like '"+cad+"'");
		if(sq.getUpdateCount()==1) {System.out.println("se ha podido eliminar el cliente correctamente");} else {System.out.println("No se ha podido eliminar el cliente");}
		}
	}
	
	
	//introducir una nueva actividad
	private void ej3(BSDATOS b) throws SQLException, IOException {
		actividades ac=new actividades();
		ac.pedir(b);
		Statement s=b.p_s();
		s.executeUpdate("insert into actividades values ("+ac.getid()+",'"+ac.getnombre()+"','"+ac.getpab()+"','"+ac.getdescrip()+"','"+ac.getfecha()+"',"+ac.getprecio()+","+ac.getotal()+","+ac.getocupadas()+")");
		if(s.getUpdateCount()==1) {System.out.println("Actividad insertada correctamente");} else {System.out.println("No se pudo insertar la actividad");}
	}
	
	
	//eliminar una actividad
	private void ej4(BSDATOS b) throws SQLException, IOException {
		menus m=new menus();int cad; 
		//declaración del resultSet 
		ResultSet s;
		//sacar la cantidad de "id" para saber si la tabla está vacía 
		s=b.p_r("select count(id) as total from actividades");
		s.next();
		//si el resultado es "0" saltará el mensaje "NO HAY NINGUNA ACTIVIDAD" y volverá al menú y si el resultado es diferente de "0" pasa al siguiente paso
		if(s.getInt("total")==0) {System.out.println("NO HAY NINGUNA ACTIVIDAD EN LA BASE DE DATOS.");} else {
		//muestra todas las actividades de de la tabla
		b.act();
		System.out.println();
		//bucle que se repetirá mientras no se encuentre en la tabla actividades el id que ha introducido el usuario
		do {
			cad=m.pn("Introduce el id de la actividad que quieres eliminar",0);
			if(!b.compn("id", "actividades", cad)) {System.out.println("no se ha encontrado ese usario en la base de datos");}
		//se utiliza el método compn() de la clase BSDATOS para este bucle
		}while(!b.compn("id", "actividades", cad));
		Statement sq=b.p_s();
		//Una vez comprobado que el id existe se procede a eliminar de la base de datos
		sq.executeUpdate("delete from activiadades where id="+cad+"");
		if(sq.getUpdateCount()==1) {System.out.println("se ha podido eliminar la actividad correctamente");} else {System.out.println("No se ha podido eliminar el cliente");}
		}
		
		}
	
	
	//método utilizado para añadir un nuevo pabellón a la base de datos
	private void ej5(BSDATOS b) throws SQLException, IOException {
		pabellon p=new pabellon();
		p.pedir(b);
		Statement s=b.p_s();
		s.executeUpdate("insert into pabellones values ('"+p.getpav()+"','"+p.getloc()+"')");
		if(s.getUpdateCount()==1) {System.out.println("pabellon creado correctamente");} else {System.out.println("no se ha podido crear el pabellon");}
	}
	
	
	
	//método utilizado para eliminar los pabellones
	private void ej6(BSDATOS b) throws SQLException, IOException {
		menus m=new menus();String cad;char h;
		ResultSet s;
		//se comprueba que la tabla esta vacía contando la cantidad de "pabellon" que hay en la base de datos pabellones
		s=b.p_r("select count(pabellon) as total from pabellones");
		s.next();
		
		//si el resultado es "0" se muestra un mensaje por pantalla diciendo que la base de datos está vacío y de lo contrario se sigue con la actividad
		if(s.getInt("total")==0) {System.out.println("NO HAY NINGUN PABELLON EN LA BASE DE DATOS.");} else {
		//se muestran los pabellones de la base de datos pabellones
			b.pab();
		System.out.println();
		//bucle que se repetirá mientras el pabellon que introduzca el usuario no se encuentre en la base de datos
		do {
			cad=m.psl("Introduce el pabellon que quieres eliminar");
			if(!b.comp("pabellon", "pabellones", cad)) {System.out.println("no se ha encontrado ese usario en la base de datos");}
		}while(!b.comp("pabellon", "pabellones", cad));
		//Como en la tabla actividades tiene una clave foranea con eliminaciones y actualizaciones en cascada, si elimino el pabellón tambien se eliminarán las tuplas de la tabla actividades con ese pabellón
		//le muestro al usuario las actividades con ese pabellon para que verifique si quiere eliminarlo de todas formas
		
		System.out.println("Si quieres eliminar el pabellon "+cad+" eliminarás las siguientes actividades:");
		s=b.p_r("select * from actividades where nombre_pabellon like '"+cad+"'");
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}
		System.out.println();
		h=m.next("¿Quieres eliminar este pabellon junto con estas actividades? \"s\" | \"n\"");
		if(h=='S') {
		Statement sq=b.p_s();
		//se procede a eliminar la tupla de la base de datos
		sq.executeUpdate("delete from pabellones where pabellon like '"+cad+"'");
		//si la cantidad de tuplas actualizadas es 1 la tupla se ha eliminado correctamente
		if(sq.getUpdateCount()==1) {System.out.println("se ha podido eliminar el pabellon correctamente");} else {System.out.println("No se ha podido eliminar el pabellon");}
		}
		}
	}
	
	//Mostrar todos los clientes de la tabla clientes
	private void ej7(BSDATOS b) throws SQLException {
		ResultSet s=b.p_r("select * from clientes");
		System.out.println("login"+"\t"+"\t"+"passw"+"\t"+"nombre"+"\t"+"apellido"+"   "+"direccion"+"\t"+"telefono");
		while(s.next()) {
			System.out.println(s.getString("login")+"\t"+s.getString("passw")+"\t"+s.getString("nombre")+"\t"+s.getString("apellido")+"\t"+" "+
					s.getString("direccion")+"\t"+s.getString("telefono"));
		}
	}
	
	//como estas opciones también las desarrolla el cliente, se llama a sus métodos correspondientes en la clase BSDATOS
	//****************************************************
	private void ej8(BSDATOS b) throws SQLException {
		b.act();
	}
	private void ej9(BSDATOS b) throws SQLException {
		b.pab();
	}
	private void ej10(BSDATOS b) throws SQLException {
		b.act_p();
	}
	//***************************************************
	
	
	
	

}
