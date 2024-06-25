package BasesDeDatos;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import org.bson.Document;

public class PagosCassandra {
    private CqlSession session;
    private String node;
    private Integer port;
    private String dataCenter;
    private String keyspace;

    public PagosCassandra() {
        this.node = "127.0.0.1";
        this.port = 9042;
        this.dataCenter = "datacenter1";
        this.keyspace = "tpos"; 
        }

    public void connect() {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress(node, port));
        builder.withLocalDatacenter(dataCenter);
        session = builder.build();
    }

    public void createKeyspace() {
        String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH replication = "
                     + "{'class':'SimpleStrategy', 'replication_factor':1}";
        session.execute(query);
    }
    
    public Map<String, String> Mongo2CassandraCliente(String email) {
        PedidosMongoDB pedidosDB = new PedidosMongoDB();
        Document cliente = pedidosDB.getDocCliente(email);
        Map<String, String> clienteMap = new HashMap<>();
        clienteMap.put("nombre", cliente.getString("nombre"));
        clienteMap.put("email", email);
        clienteMap.put("DNI", cliente.getString("DNI"));
        clienteMap.put("direccion", cliente.getString("direccion"));
        pedidosDB.close();
        return clienteMap;
    }

    public List<Map<String, Integer>> Mongo2CassandraProductos(String email) {
        PedidosMongoDB pedidosDB = new PedidosMongoDB();
        ArrayList<Document> productos = pedidosDB.getDocProductos(email);
        List<Map<String, Integer>> productosLista = new ArrayList<>();
        for (Document doc : productos) {
            Map<String, Integer> productosMap = new HashMap<>();
            productosMap.put(doc.getString("descripcion"), doc.getInteger("cantidad"));
            productosLista.add(productosMap);
        }
        pedidosDB.close();
        return productosLista;
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + keyspace + ".pagos ("
                     + "id UUID PRIMARY KEY, "
                     + "cliente MAP<TEXT, TEXT>,"
                     + "productos LIST<FROZEN<MAP<TEXT, INT>>>,"
                     + "fecha DATE,"
                     + "hora TIME,"
                     + "descuento text,"
                     + "iva double,"
                     + "importe_total DOUBLE,"
                     + "medio_pago text)";
        session.execute(query);
    }
    
    public void insertarDatos(String email, String medio_pago) {
    	UUID id = Uuids.timeBased();
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        double iva = 0.21;
        
        UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
        int cantMin = usuariosDB.getActividad(email);
    	int dias = usuariosDB.getDias(email);
		
    	double des = 1;
    	String descuento = "0%";
		if ((cantMin/dias) > 120 && (cantMin/dias) < 240) {
			des = 0.85;
			descuento = "15%";
		} else if ((cantMin/dias) > 240){
			des = 0.75;
			descuento = "25%";
		}
    	usuariosDB.close();
    	
    	PedidosMongoDB pedidosDB = new PedidosMongoDB();
    	//1.iva 2. descuento
    	double agregadoIva = (pedidosDB.getImporteTotal(email)*iva);
    	
        double importeTotal = pedidosDB.getImporteTotal(email) + agregadoIva;
    	importeTotal = importeTotal*des;
    	pedidosDB.close();
    	
    	Map<String, String> cliente = Mongo2CassandraCliente(email);
        List<Map<String, Integer>> productos = Mongo2CassandraProductos(email);
        
        String insertQuery = "INSERT INTO " + keyspace + ".pagos (id, cliente, productos, fecha, hora, descuento, iva, importe_total, medio_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        session.execute(SimpleStatement.newInstance(insertQuery, id, cliente, productos, fecha, hora, descuento, iva, importeTotal, medio_pago));
        
    }
    
    public double obtenerImpTotal(String email) {
    	double importeTotal = 0;
    	try (CqlSession session = CqlSession.builder().build()) {
    		ResultSet rs = session.execute("SELECT importe_total FROM tpos.pagos where cliente['email'] = ? ALLOW FILTERING", email);
    		for (Row row : rs) {
                importeTotal = row.getDouble("importe_total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return importeTotal;
    }
    
    public void verPagos() {
    	try (CqlSession session = new CqlSessionBuilder().build()) {
            ResultSet rs = session.execute("SELECT * FROM tpos.pagos ALLOW FILTERING");

            for (Row row : rs) {
                String clienteNombre = row.getMap("cliente", String.class, String.class).get("nombre");
                String email = row.getMap("cliente", String.class, String.class).get("email");
                List<Map<String, Integer>> productos = Mongo2CassandraProductos(email); 
                String fecha = row.getLocalDate("fecha").toString();
                String hora = row.getLocalTime("hora").toString();
                double importeTotal = row.getDouble("importe_total");
                String medioPago = row.getString("medio_pago");
                String descuento = row.getString("descuento");

                System.out.println("+--------------------------------------------+");
                System.out.println("|                  Pago                      |");
                System.out.println("+--------------------------------------------+");
                System.out.println("| Cliente: " + clienteNombre);
                System.out.println("| Email: " + email);
                System.out.println("|--------------------------------------------|");
                System.out.println("| Productos:");
                for (Map<String, Integer> producto : productos) {
                    for (Map.Entry<String, Integer> entry : producto.entrySet()) {
                        String nombreProducto = entry.getKey();
                        int cantidad = entry.getValue();
                        System.out.println("| - " + nombreProducto + ": " + cantidad);
                    }
                }
                System.out.println("|--------------------------------------------|");
                System.out.println("| Fecha: " + fecha);
                System.out.println("|--------------------------------------------|");
                System.out.println("| Hora: " + hora);
                System.out.println("|--------------------------------------------|");
                System.out.println("| Descuento: " + descuento);
                System.out.println("|--------------------------------------------|");
                System.out.println("| Importe Total: $" + importeTotal);
                System.out.println("|--------------------------------------------|");
                System.out.println("| Medio de Pago: " + medioPago);
                System.out.println("+--------------------------------------------+");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void close() {
        if (session != null) {
            session.close();
        }
    }
	
	
}
