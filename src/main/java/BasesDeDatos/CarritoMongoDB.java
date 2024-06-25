package BasesDeDatos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class CarritoMongoDB {
	private MongoClient mc;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public CarritoMongoDB(String dni) {
		this.mc = MongoClients.create("mongodb://localhost:27017");
		this.db = mc.getDatabase("Carritos");
		this.collection = db.getCollection("carrito" + dni);
	}
	
	public void agregarProductos(int codigo, int cant, String descripcion, double precioUnitario) {
		Document doc = new Document("codigo", codigo)
							.append("cantidad", cant)
							.append("descripcion", descripcion)
							.append("precio unitario", precioUnitario)
							.append("precio total", (cant*precioUnitario));
		collection.insertOne(doc);
	}
	
	public void verCarrito() {
	    FindIterable<Document> documents = collection.find();
	    MongoCursor<Document> i = documents.iterator();
	    try {
	        if (!i.hasNext()) {
	            System.out.println("Tu carrito está vacío.");
	        } else {
	            System.out.println("+----------+----------------------+------------+------------+------------+");
	            System.out.println("| Codigo   | Producto             | Cantidad   | Precio     | Total      |");
	            System.out.println("+----------+----------------------+------------+------------+------------+");
	            double precioTotalGlobal = 0.0; 

	            while (i.hasNext()) {
	                Document doc = i.next();

	                int codigo = doc.getInteger("codigo");
	                String nombreProducto = doc.getString("descripcion");
	                int cantidad = doc.getInteger("cantidad");
	                double precioUnitario = doc.getDouble("precio unitario");
	                double precioTotal = doc.getDouble("precio total");

	                System.out.printf("| %-8s | %-20s | %-10s | $%-9.2f | $%-9.2f |%n", codigo, nombreProducto, cantidad, precioUnitario, precioTotal);

	                precioTotalGlobal += precioTotal;
	            }

	            System.out.println("+----------+----------------------+------------+------------+------------+");
	            System.out.printf("Precio total global: $%.2f%n", precioTotalGlobal); // Imprimir precio total global
	            System.out.println();
	        }
	    } finally {
	        i.close();
	    }
	}

	
	public void eliminarProductoCarrito(int codigo) {
		Document doc = new Document("codigo", codigo);
		collection.deleteOne(doc);
	}
	
	public boolean existeProductoCarrito(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		return documents.iterator().hasNext();
	}
	
	public double getSumaTotal() {
        MongoCursor<Document> i = collection.find().iterator();
        double precioTotal = 0;
        try {
            while (i.hasNext()) {
                double precio = i.next().getDouble("precio total");
                precioTotal += precio;
            }
        } finally {
            i.close();
        }
        return precioTotal;
	}
	
	public void restarCantCarrito(int codigo, int cantNueva) {
		Document doc = new Document("codigo", codigo);
		Document nuevosValores = new Document("$set", new Document("cantidad", cantNueva));
		collection.updateOne(doc, nuevosValores);
		System.out.println("Carrito actualizado");
	}
	
	public void borrarColection() {
		collection.drop();
	}
	
	 public ArrayList<Document> allDocumentos() {
		ArrayList<Document> docs = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> i = documents.iterator();
        try {
            while (i.hasNext()) {
                docs.add(i.next());
            }
        } finally {
            i.close();
        }
        return docs;
	  }
	 
	public int getCantidad(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getInteger("cantidad");
	}
	
	public String getDescripcion(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getString("descripcion");
	}
	
	public double getPrecioUnitario(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getDouble("precio unitario");
	}
	
	 public void close() {
		 mc.close();
	 }
}
