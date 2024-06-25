package BasesDeDatos;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import negocio.Producto;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class ProductosMongoDB {
	private MongoClient mc;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public ProductosMongoDB() {
		this.mc = MongoClients.create("mongodb://localhost:27017");
		this.db = mc.getDatabase("TPO");
		this.collection = db.getCollection("productos");
	}
	
	public void registrarProducto(Producto p) {
		Document doc = new Document("codigo", p.getCodigo())
							.append("descripcion", p.getDescripcion())
							.append("precio unitario", p.getPrecioUnitario())
							.append("cantidad stock", p.getCantidadStock());
		collection.insertOne(doc);
	}
	
	public void modificarProducto(int codigo, String descripcion, double precioUnitario, int cantidadStock, String email) {
		Document doc = new Document("codigo", codigo);
		Document nuevosValores = new Document("$set", new Document("descripcion", descripcion)
                						.append("precio unitario", precioUnitario)
                						.append("cantidad stock", cantidadStock));
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		HistorialMongoDB historialDB = new HistorialMongoDB();
		Document nuevosValoresGuardar = new Document("descripcion", descripcion)
										.append("precio unitario", precioUnitario)
										.append("cantidad stock", cantidadStock);
		historialDB.agregarHistorial(resultado, nuevosValoresGuardar, email);
		historialDB.close();
		collection.updateOne(doc, nuevosValores);
	}
	
	public boolean existeProducto(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		return documents.iterator().hasNext();
	}
	
	public boolean productosVacio() {
		return !collection.find().iterator().hasNext();
	}
	
	public void eliminarProducto(int codigo) {
		Document doc = new Document("codigo", codigo);
		collection.deleteOne(doc);
	}
	
	 public void listarProductos() {
	        FindIterable<Document> documents = collection.find();
	        MongoCursor<Document> i = documents.iterator();
	        try {
	            while (i.hasNext()) {
	            	Document doc = i.next();
	                System.out.println("Codigo: " + doc.getInteger("codigo"));
	                System.out.println("Descripcion: " + doc.getString("descripcion"));
	                System.out.println("Precio unitario: " + doc.getDouble("precio unitario"));
	                System.out.println("Cantidad stock: " + doc.getInteger("cantidad stock"));
	                System.out.println();
	            }
	        } finally {
	            i.close();
	        }
	    }
	 
	public String getDescrpcion(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getString("descripcion");
	}
	
	public Double getPrecioUnitario(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getDouble("precio unitario");
	}
	
	public int getCantidadStock(int codigo) {
		Document doc = new Document("codigo", codigo);
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		return resultado.getInteger("cantidad stock");
	}
	 
	public void agregarStock(int codigo, int nuevoStock) {
		Document doc = new Document("codigo", codigo);
		collection.updateOne(doc, Updates.inc("cantidad stock", nuevoStock));
	}
	
	public void restarStock(int codigo, int cant) {
		Document doc = new Document("codigo", codigo);
		cant = cant*-1;
		collection.updateOne(doc, Updates.inc("cantidad stock", cant));
		FindIterable<Document> documents = collection.find(doc);
		Document resultado = documents.first();
		if (resultado.getInteger("cantidad stock") <= 0) {
			eliminarProducto(codigo);
		}
	}
	 
	 public void close() {
		 mc.close();
	 }
}
