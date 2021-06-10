package project;

import java.io.IOException;
import java.sql.*;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
//Esta clase se utilizará para administrar las funciones del usuario cliente, para pedir los datos de un cliente en caso de que un usuario gestor quiera añadir uno y resgistro de un nuevo usuario
public class cliente {
	//Declaracióhn de atributos
	private String login;
	private String pass;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	
	
	
	//se piden los datos del cliente al usuario
	public void intro_cliente(BSDATOS b) throws IOException, SQLException {
		menus m=new menus();
		//el bucle se repetirá mientras en las tabla clientes se encuentre un login idéntico al que introduce el usuario
		do {
		login=m.psl("Introduce el usuario del cliente");
		if(b.comp("login", "clientes", login)) {System.out.println("El usuario introducido ya se encuentra en la base de datos");}
		}while(b.comp("login", "clientes", login));
		pass=m.psl("Introduce la contraseña del cliente");
		nombre=m.psl("Introduce el nombre del cliente");
		apellido=m.psl("Introduce el apellido del cliente");
		direccion=m.psl("Introduce la direccion del cliente");
		telefono=p_tlfne();
	}
	
	//regitro de un nuevo usuario
	public void n_client(BSDATOS b) throws ClassNotFoundException, SQLException, IOException {
		//trycatch para validar que el servidor no esté caído
		try {
		//conexión a la base de datos
		b.intro_cliente();
		//introducción de datos del nuevo cliente
		intro_cliente(b);
		//se insertará como una nueva tupla en la tabla clientes
		b.p_u("insert into clientes values ('"+login+"','"+pass+"','"+nombre+"','"+apellido+"','"+direccion+"','"+telefono+"')");
		//se cierra conexión con la base de datos
		b.close();
		} catch(CommunicationsException e) {System.out.println("No se pudo conectar a la base de  datos.");}
		}
	
	//menú con las diferentes funciones del usuario cliente
	private void opc(BSDATOS b,menus m) throws IOException, SQLException {
		int x;
		do {
			x=m.cliente();
			switch(x) {
			case 1:ej1(b);break;
			case 2:ej2(b);break;
			case 3:ej3(b);break;
			case 4:ej4(b,m);break;
			case 5:ej5(b,m);break;
			case 6:ej6(b,m);break;
			case 7:ej7(b);break;
			case 8:ej8(b,m);break;
			case 9:ej9(b,m);break;
			case 10:break;
			default:System.out.println("Opcion no diponible");break;
			}
			
		}while(x!=10);
	}
	
	//inicio de sesión del usuario cliente
	public void inicio(BSDATOS b,menus m) throws IOException, SQLException, ClassNotFoundException {
		int cont=0; boolean f=false; char h;
		//try {
		//trycatch para validar que la base de datos no esté caída
		//try {
		//conexión con la base de datos
		b.intro_cliente();
		//bucle que se repetirá mientras el login y la contraseña no coincidan con ninguna de las tuplas de la tabla clientes o al fallar 3 veces y se le pregunte al usuario si quiere seguir intentandolo e introduzaca una "s"
		do {
		//si el contador es igual a 3 se le preguntará si quieres seguir intentandolo, si introduce una "s" el contador se reinicia y si introduce una "n" la variable booleana f pasa a ser true, finalizando el bucle y volviendo al menu principal
		if(cont==3) {
			h=m.next("Has fallado 3 veces seguidas el inicio de sesion, ¿quieres seguir intentandolo? S | N");
			if(h=='S') {cont=0;} else {f=true;}
		} else {
	
		login=m.psl("Introduce el usuario del cliente");
		pass=m.psl("Introduce la contraseña del cliente");
		if(!comp(b,login,pass)) {System.out.println("Datos incorrectos :(");}
		cont++;
		}
		}while(!comp(b,login,pass) && f==false);
		//si la variable boolean "f" es false se muestra el menu con las funcionalidades del cliente
		if(f==false) {
		opc(b,m);
		}
		//se cierra conexion con el servidor
		b.close();
		//} catch(CommunicationsException e) {System.out.println("No se pudo conectar a la base de datos");}
		//} catch(SQLException e) {System.out.println("problemas con la autentificacion con el servidor");}
	}
	
	//comprueba que el login y la contraseña pasados se encuentran en la tabla clientes
	private boolean comp(BSDATOS b, String usuario, String pass) throws SQLException {
		ResultSet st=b.p_r("select count(login) as tot from clientes where login like '"+usuario+"' and passw like '"+pass+"'");
		st.next();
		if(st.getInt("tot")==0) {return false;} else {return true;}
		
	}
	
	//pedir al usuario el número de tlfno, solo puede introducir 9 caracteres numéricos
	private String p_tlfne() throws IOException {
		menus m=new menus();String t;
		do {
		t=m.ps("Introduce el numero de telefono del cliente");
		}while(!t.matches("[0-9]{9}"));
		return t;
	}
	
	//métodos utilizados para pasar los atributos de la clase cliente a otras clases
	//*****************************************************
	public String getlogin() { return login;}
	public String getpass() { return pass;}
	public String getnombre() { return nombre;}
	public String getapellido() { return apellido;}
	public String getdireccion() { return direccion;}
	public String gettelefono() { return telefono;}
	//*****************************************************
	
	
	//como estas opciones también las desarrollan con el gestor, se llama a sus metodos correspondientes en la clase BSDATOS
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void ej1(BSDATOS b) throws SQLException {
		b.act();
	}
	private void ej2(BSDATOS b) throws SQLException {
		b.pab();
	}
	private void ej3(BSDATOS b) throws SQLException {
		b.act_p();
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//muestra todas las actividades con hueco libres que no pasen de un precio máximo
	private void ej4(BSDATOS b,menus m) throws SQLException, IOException {
		//se pide el valor
		float x=m.pd("Intro el precio máximo de la actividad", 0);
		//se ejecuta la sentencia con aquellas actividades que tengan menos plazas ocupadas que totales y el precio se menor o igual al introducido por el usuario
		ResultSet s=b.p_r("select * from actividades where plazas_ocupadas<plazas_totales and precio<="+x);
		
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		//se muestra el resultado
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}

	}
	
	
	//muestra todas las actividades con hueco libres que se desarrollen en un pabellón concreto
	private void ej5(BSDATOS b,menus m) throws SQLException, IOException {
		//muestra todos los pabellones
		ej2(b);
		System.out.println();
		String p;
		//bucle que se repetirá mientras no se encuentre en la base de datos un pabellón igual al introducido por el usuario
		do {
		p=m.psl("Introduce el pabellon.");
		if(!b.comp("pabellon", "pabellones", p)) {System.out.println("ESE PABELLON NO SE ENCUENTRA EN LA BASE DE DATOS");}
		}while(!b.comp("pabellon", "pabellones", p));
		//se ejecuta la sentencia con aquellas actividades que tengan menos plazas ocupada que totales y que el nombre del pabellon se el introducido por el usuario
		ResultSet s=b.p_r("select * from actividades where plazas_ocupadas<plazas_totales and nombre_pabellon like '"+p+"'");
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		//se muestra el resultado
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}

		
	}
	
	//mostrar actividad con un id específico
	private void ej6(BSDATOS b,menus m) throws SQLException, IOException {
		int x;
		//bucle que se repetirá mientras no se encuentre en la base de datos un id como el introducido por el usuario
		do {
			x=m.pn("Introduce el id de la actividad que quieres mostrar",0);
		}while(!b.compn("id", "actividades", x));
		//se ejecuta la consulta
		ResultSet s=b.p_r("select * from actividades where id="+x);
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		s.next();
		//se muestra el resultado
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		

	}
	
	
	
	//muestra todas las actividades a las que estas suscrito
	private void ej7(BSDATOS b) throws SQLException {
		//si no se encuentra en la tabla inscripciones el login del usuario clientes vuelve al menú cliente
		if(!b.comp("login_cliente", "inscripciones", login)) {System.out.println("No estas suscrito a ninguna actividad.");} else {
		// de lo contrario se ejecuta una consulta donde se unen las tablas actividades e inscripciones, se pide todos los datos de la tupla donde el id_login sea el del usuario cliente
			ResultSet s=b.p_r("select * from actividades as a inner join inscripciones as i on i.id_actividad=a.id where i.login_cliente like '"+login+"'");
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		//se muestra el resultado
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}
		
		}

		
		
	}
	
	
	//incripción en una actividad
	private void ejs8(BSDATOS b,menus m) throws SQLException, IOException {
		//muestra todas las actividades
		ej3(b); int x;
		System.out.println();
		//bucle que se repetirá mientras el id de la actividad no se encuentre en las tabla actividades
		do {
			x=m.pn("Introduce el id de la actividad a la que te quieres suscribir",0);
		} while(!b.compsus("id", "actividades", x));
		
		//trycatch para comprobar si ya estas suscrito a esa activida
		try {
		//insertar nueva inscripciones en la tabla inscipciones
		b.p_u("insert into inscripciones values ('"+login+"',"+x+")");
		//actualizar las plazas ocupadas de la actividad
		b.p_u("update actividades set plazas_ocupadas=(select plazas_ocupadas+1 from actividades where id="+x+") where id="+x);
	} catch(SQLIntegrityConstraintViolationException e) {System.out.println("Ya estas suscrito a esa actividad");}
	
	}
	
	
	//inscripciones en actividades (principal)
	private void ej8(BSDATOS b,menus m) throws SQLException, IOException {
		//se comprueba si la tabla está vacia
		ResultSet sr=b.p_r("select max(id_actividad) as total from inscripciones");
		sr.next();
		//si está vacia se ejecuta la funcion ejs8()
		if(sr.getInt("total")==0) {
			ejs8(b,m);
		} else {
		//si no está vacia se comprueba el numero de actividades a las que estas suscrito
		//se pide el numero de inscripciones con el login del usuario cliente
		sr=b.p_r("select count(id_actividad) as total from inscripciones where login_cliente like'"+login+"' group by login_cliente");
		sr.next();Integer s; 
		//se le asigna el valor a la variable s
		s=sr.getInt("total");
		//se pide el numero de actividades
		sr=b.p_r("select count(*) as total from actividades");
		sr.next();
		//se comprueba que el numero de actividades totales y el numero de actividades a las que está suscrito el clientes sean el mismo
		if(sr.getInt("total")!=s) {
			//si no lo son se le pide que introduzca una nueva suscripcion
			ejs8(b,m);
		} else {
			//pero si lo son se salta por consola el siguiente mensaje y vuelve al menu cliente
			System.out.println("ESTAS SUSCRITO A TODAS LAS ACTIVIDADES.");}
		}
		}
	
	
	//eliminación de suscipciones
	private void ej9(BSDATOS b,menus m) throws SQLException, IOException {
		//se comprueba si el login del usuario está en la tabla inscripciones
		if(!b.comp("login_cliente", "inscripciones", login)) {
			//si no está, salta el siguiente mensaje y vuelve al menú cliente
			System.out.println("No estas inscrito a ninguna actividad :(");} else {	
			int x;
			//se muestran las actividades a las que está suscrito el cliente
			ej7(b); 
			System.out.println();
			//bucle que se repetirá mientras que en las tabla incripciones no se encuentre una tupla con ese login y ese id de actividad
			do {
				x=m.pn("Introduce el id de la actividad de la que quieres desincribirte.", 0);
			}while(!b.compes("id_actividad","inscripciones","login_cliente",login,x));
			
			Statement st=b.p_s();
			//se eliminar la tupla
			st.executeUpdate("delete from inscripciones where login_cliente like '"+login+"' and id_actividad="+x);
			//se actualizá el campo plazas ocupadas de las tabla actividades
			ResultSet rt=b.p_r("select plazas_ocupadas-1 as total from actividades where id="+x);
			rt.next();
			System.out.println("asdasdasdasdas "+rt.getInt("total"));
			st.executeUpdate("update actividades set plazas_ocupadas="+rt.getInt("total")+" where id="+x);
			//se comprueba el número de tuplas actualizadas
			if(st.getUpdateCount()==1) {System.out.println("Se ha eliminado la inscripcion con éxito");} else {System.out.println("No se ha podido eliminar la inscripcion");}
			
		}
	}
	

}
