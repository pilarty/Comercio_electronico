package BasesDeDatos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import java.util.Date;


public class UsuariosMongoDB {
	private MongoClient mc;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	//host= "mongodb://localhost:27017"
	public UsuariosMongoDB() {
		this.mc = MongoClients.create("mongodb://localhost:27017");
		this.db = mc.getDatabase("TPO");
		this.collection = db.getCollection("usuario");
	}
	
	public void registrarCliente(String email, String contraseña, String nombre, String direccion, String dni, int actividad) {
		Document doc = new Document("email", email)
								.append("contraseña", contraseña)
								.append("nombre", nombre)
								.append("direccion", direccion)
								.append("DNI", dni)
								.append("actividad", actividad)
								.append("ultima conexion", new Date())
								.append("dias", 1);
		collection.insertOne(doc);
	}
	
	public void registrarAdmin(String email, String contraseña) {
		Document doc = new Document("email", email)
								.append("contraseña", contraseña)
								.append("rango", "admin");
		collection.insertOne(doc);
	}
	
	public boolean iniciarUsuario(String email, String contraseña) {
		Document doc = new Document("email", email)
				.append("contraseña", contraseña);
		FindIterable<Document> documents = collection.find(doc);
		return documents.iterator().hasNext();
	}
	
	public boolean isAdmin(String email) {
		Document doc = new Document("email", email)
					.append("rango", "admin");
		FindIterable<Document> documents = collection.find(doc);
		return documents.iterator().hasNext();
	}
	
	public String getDNI(String email) {
		Document doc = new Document("email", email);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getString("DNI");
	}
	
	public void printAllDocuments() {
	 	FindIterable<Document> documents = collection.find();
        System.out.println("Lista de Usuarios:");
        System.out.println("+----------------------------------+----------------------------+----------------+----------------------+----------------------+--------------------------------+--------+-----------+-----------+");
        System.out.println("|          Nombre                  |          Correo            |    Contraseña  |      Dirección       |         DNI          |      Última Conexión           | Días   | Actividad | Categoria |");
        System.out.println("+----------------------------------+----------------------------+----------------+----------------------+----------------------+--------------------------------+--------+-----------+-----------+");
        try {
        for (Document doc : documents) {
        	if (doc.containsKey("_id") && doc.containsKey("email") && doc.containsKey("contraseña") && doc.containsKey("rango")) {
                continue;
            }
            String nombre = doc.getString("nombre");
            String correo = doc.getString("email");
            String contraseña = doc.getString("contraseña");
            String direccion = doc.getString("direccion");
            String dni = doc.getString("DNI");
            int actividad = doc.getInteger("actividad");
            Date ultimaConexion = doc.getDate("ultima conexion");
            int dias = doc.getInteger("dias");
            String categoria = "Low";
            if ((actividad/dias) > 120) {
    			categoria = "Medium";
    		} else if ((actividad/dias) > 240){
    			categoria = "Top";
    		}
            System.out.printf("| %-32s | %-26s | %-14s | %-20s | %-20s | %-30s | %-6s | %-9s | %-9s |%n", nombre, correo, contraseña, direccion, dni, ultimaConexion, dias, actividad, categoria);        }

        System.out.println("+----------------------------------+----------------------------+----------------+----------------------+----------------------+--------------------------------+--------+-----------+-----------+");
        }catch (Exception e) {
            e.printStackTrace();
        	}
	 }
	 
	 public void actualizarActividad(int minutos, String email) {
		Document doc = new Document("email", email);
		collection.updateOne(doc, Updates.inc("actividad", minutos));
	 }
	 
	 public Document getDoc(String email) {
		Document doc = new Document("email", email);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado;
	 }
	 
	 public int getActividad(String email){
		Document doc = new Document("email", email);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getInteger("actividad");
	 }
	 
	 public void actualizarFecha(String email) {
		Document doc = new Document("email", email);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		Date hoy =  new Date();
		if (!hoy.equals(resultado.getDate("ultima conexion"))){
            Document update = new Document("$set", new Document("ultima conexion", hoy));
            collection.updateOne(resultado, update);
            collection.updateOne(resultado, Updates.inc("dias", 1));
		}
	 }
	 
	 public int getDias(String email){
		Document doc = new Document("email", email);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getInteger("dias");
	 }
	 
	 
	 
	 public void close() {
		 mc.close();
	 }
}
