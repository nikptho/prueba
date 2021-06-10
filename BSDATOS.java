package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//Esta clase será utilizada para cualquier interacción que ocurra con la base de datos
public class BSDATOS {
	//se declara el atributo Connection h con el cual se gestionará el inicio y cierre de sesion, creación de Statements y ResultSet
	private Connection h;
	//nombre de la base de datos
	private final String bs="actividades";

	//Acceder a la base de datos con un usuario y contraseña introducidos por el usuario, este método se utilizará en la clase gestor
	public void intro_root(String u,String p) throws SQLException, ClassNotFoundException {
		String t="jdbc:mysql://localhost:3306/"+bs;
		Class.forName("com.mysql.cj.jdbc.Driver");
		h=DriverManager.getConnection(t,u,p);
	}
	
	//Llamar al método privado inicio(), este método se utilizará en la clase cliente
	public void intro_cliente() throws SQLException, ClassNotFoundException {
		inicio();
		
	}
	
	
	//Iniciar una conexión con la base de datos con un usuario y una contraseña predefinido
	private void inicio() throws SQLException, ClassNotFoundException {
		String t="jdbc:mysql://remotemysql.com:3306/VyTx1Fx0wk?autoReconnect=true";
		Class.forName("com.mysql.cj.jdbc.Driver");
		h=DriverManager.getConnection(t,"VyTx1Fx0wk","iPk2U6E85g");
		
	}
	
	//Devolver un Statement con el cual hacer actualizaciones en las tablas
	public Statement p_s() throws SQLException {
		Statement st=h.createStatement();
		return st;
	}
	
	//devuelve el atributo Connection
	public Connection p_h() {return h;}
	//declara un nuevo Connection
	public void in_h(Connection h) {this.h=h;}
	
	//devuelve un ResultSet 
	public ResultSet p_r(String cad) throws SQLException {
		//utiliza el metodo p_s() para que le devuelva un Statement con el cuál crear el ResultSet
		Statement st=p_s();
		ResultSet rs=st.executeQuery(cad);
		return rs;

	}
	
	//ejecuta una actualización en la base de datos pasada como un string
	public void p_u(String cad) throws SQLException {
		Statement st=p_s();
		st.executeUpdate(cad);
		
	}
	
	
	//pasándole la columna de la tabla, la tabla y el dato a comparar, devulve un true si se ha encontrado ese dato en la tabla o un false en caso contrario
	public boolean comp(String c,String t,String cad) throws SQLException {
		Statement s=p_s(); boolean f=false;
		//haces la consulta con la columna y tabla pasadas como string
		ResultSet rs=s.executeQuery("select "+c+" from "+t);
		while(rs.next() && f==false) {
			//comparas los resultados de la consulta con el dato que quieres comprobar
			if(cad.equalsIgnoreCase(rs.getString(c))) {
				f=true;
			}
		}
		
		return f;
	}
	
	
	//tiene el mismo funcionamiento pero aqui el dato que se quiere comparar es un entero y no un string
	public boolean compn(String c,String t,int cad) throws SQLException {
		Statement s=p_s(); boolean f=false;
		ResultSet rs=s.executeQuery("select "+c+" from "+t);
		while(rs.next() && f==false) {
			if(cad==rs.getInt(c)) {
				f=true;
			}
		}
		
		return f;
	}
	
	//mismo funcionamiento que compn() pero filtrando las actividades con huecos libres
	public boolean compsus(String c,String t,int cad) throws SQLException {
		Statement s=p_s(); boolean f=false;
		ResultSet rs=s.executeQuery("select "+c+" from "+t+" where plazas_ocupadas<plazas_totales");
		while(rs.next() && f==false) {
			if(cad==rs.getInt(c)) {
				f=true;
			}
		}
		
		return f;
	}
	
	
	//Se pasa la columna, la tabla, dos variables que servirán como condición y el dato a comparar
	public boolean compes(String c,String t,String tt,String tr,int cad) throws SQLException {
		Statement s=p_s(); boolean f=false;
		ResultSet rs=s.executeQuery("select "+c+" from "+t+" where "+tt+" like '"+tr+"'");
		while(rs.next() && f==false) {
			if(cad==rs.getInt(c)) {
				f=true;
			}
		}
		
		return f;
	}
	
	//muestra los datos de todas las actividades
	public void act() throws SQLException {
		ResultSet s=p_r("select * from actividades");
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}
	}
	//muestra la información de todos los pabellones
	public void pab() throws SQLException {
		ResultSet s=p_r("select * from pabellones");
		System.out.println("pabellon"+"\t"+"localidad");
		while(s.next()){
			System.out.println(s.getString("pabellon")+"\t"+s.getString("localidad"));
		}
	}
	//muestra la información de todas las actividades que tengan plazas libres
	public void act_p() throws SQLException {
		ResultSet s=p_r("select * from actividades where plazas_ocupadas<plazas_totales");
		System.out.println("id"+"\t"+"nombre"+"\t"+"\t"+"nombre_pabellon"+"\t"+"\t"+"inicio"+"\t"+"\t"+"\t"+"precio"+"\t"+"plazas_totales"+"\t"+"plazas_ocupadas"+"\t"+"\t"+"descripcion");
		while(s.next()) {
			System.out.println(s.getInt("id")+"\t"+s.getString("nombre")+"\t"+s.getString("nombre_pabellon")+"\t"+"\t"+s.getString("inicio")+
					"\t"+s.getFloat("precio")+"\t"+"\t"+s.getInt("plazas_totales")+"\t"+"\t"+s.getInt("plazas_ocupadas")+"\t"+"\t"+s.getString("descripcion"));
		}

	}
	
	
	//cierra la conexión con la base de datos
	public void close() throws SQLException {h.close();}

}
