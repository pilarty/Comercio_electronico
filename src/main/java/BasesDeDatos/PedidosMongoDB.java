package BasesDeDatos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import org.bson.Document;

public class PedidosMongoDB {
	private MongoClient mc;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public PedidosMongoDB() {
		this.mc = MongoClients.create("mongodb://localhost:27017");
		this.db = mc.getDatabase("TPO");
		this.collection = db.getCollection("Pedidos");
	}
	
	public void añadirPedido(Document cliente) {
		String dni = cliente.getString("DNI");
		CarritoMongoDB carritoDB = new CarritoMongoDB(dni);
		Document doc = new Document("Cliente", cliente)
							.append("Productos", carritoDB.allDocumentos())
							.append("Importe total", carritoDB.getSumaTotal());
		collection.insertOne(doc);
		carritoDB.borrarColection();
		carritoDB.close();
		System.out.println(cliente.getString("nombre") + " tu pedido fue realizado con exito.");
	}
	
	public Document getDocCliente(String email) {
		Document query = new Document("Cliente.email", email);
        Document result = collection.find(query).first();
        Document cliente = (Document) result.get("Cliente");
        return cliente;
	}
	
	public ArrayList<Document> getDocProductos(String email) {
		Document query = new Document("Cliente.email", email);
        Document result = collection.find(query).sort(Sorts.descending("_id")).first();
        ArrayList<Document> productos = (ArrayList<Document>) result.get("Productos");
        return productos;
	}
	
	public double getImporteTotal(String email) {
		Document query = new Document("Cliente.email", email);
        Document result = collection.find(query).sort(Sorts.descending("_id")).first();
        return (result.getDouble("Importe total"));
	}
	
	public void close() {
		 mc.close();
	 }
}
