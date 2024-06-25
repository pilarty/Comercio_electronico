package BasesDeDatos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class HistorialMongoDB {
	private MongoClient mc;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public HistorialMongoDB() {
		this.mc = MongoClients.create("mongodb://localhost:27017");
		this.db = mc.getDatabase("TPO");
		this.collection = db.getCollection("historial");
	}
	
	public void agregarHistorial(Document viejo, Document nuevo, String email) {
		Document doc = new Document("producto anterior", viejo)
							.append("producto modificado", nuevo)
							.append("admin", email);
		collection.insertOne(doc);
	}
	
	public void mostrarHistorial() {
        
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> i = documents.iterator();
        try {
        	System.out.println("Historial de productos:");
            while (i.hasNext()) {
            	Document doc = i.next();
            	Document anterior = (Document) doc.get("producto anterior");
                System.out.println("Codigo: " + anterior.getInteger("codigo"));
                System.out.println("Descripcion anterior: " + anterior.getString("descripcion"));
                System.out.println("Precio unitario anterior: " + anterior.getDouble("precio unitario"));
                System.out.println("Cantidad stock anterior: " + anterior.getInteger("cantidad stock"));
                System.out.println();
            	Document nuevo = (Document) doc.get("producto modificado");
                System.out.println("Descripcion nueva: " + nuevo.getString("descripcion"));
                System.out.println("Precio unitario nuevo: " + nuevo.getDouble("precio unitario"));
                System.out.println("Cantidad stock nueva: " + nuevo.getInteger("cantidad stock"));
                System.out.println();
                System.out.println("Modificado por: " + doc.getString("admin"));
            }
        } finally {
            i.close();
        }
	}
	
	
	public void close() {
		 mc.close();
	 }
}
