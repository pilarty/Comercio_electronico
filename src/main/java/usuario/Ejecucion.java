package usuario;
import java.util.Scanner;
import BasesDeDatos.*;
import cliente.Temporizador;

import negocio.*; //--> importa todas las clases


public class Ejecucion {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	while (true) {
            System.out.println(ColoresConsola.AZUL + "╔═════════════════════ Supermercado ELGORDIS ═════════════════════╗" + ColoresConsola.RESET);
            System.out.println(ColoresConsola.AZUL + "║                          Menú principal                         ║" + ColoresConsola.RESET);
            System.out.println(ColoresConsola.AZUL + "║" + ColoresConsola.RESET + " 1. REGISTRARSE" + ColoresConsola.AZUL + "                                                  ║" + ColoresConsola.RESET);
            System.out.println(ColoresConsola.AZUL + "║" + ColoresConsola.RESET + " 2. INICIAR SESIÓN" + ColoresConsola.AZUL + "                                               ║" + ColoresConsola.RESET);     
            System.out.println(ColoresConsola.AZUL + "║" + ColoresConsola.RESET + " 3. SALIR" + ColoresConsola.AZUL + "                                                        ║" + ColoresConsola.RESET);                       
            System.out.println(ColoresConsola.AZUL + "╚═════════════════════════════════════════════════════════════════╝" + ColoresConsola.RESET);
            
            System.out.println(ColoresConsola.ROJO + "¿Qué desea hacer?" + ColoresConsola.RESET);
			int opcion_inicio = scanner.nextInt();
			System.out.println();
			
			if (opcion_inicio == 1) {
				System.out.println(ColoresConsola.ROJO + "Por favor, rellene con sus datos:" + ColoresConsola.RESET);
				UsuariosMongoDB db = new UsuariosMongoDB();
				System.out.println("Email: ");
				String email = scanner.next();
				System.out.println();
				System.out.println("Contraseña: ");
				String contraseña = scanner.next();
				System.out.println();
				System.out.println(ColoresConsola.ROJO + "¿Cúal es su rol?" + ColoresConsola.RESET);
				
				System.out.println("1. Admin");
				System.out.println("2. Cliente");
				int rol = scanner.nextInt();
				System.out.println();
				
				if (rol == 1) {
					System.out.print(ColoresConsola.ROJO + "Contraseña para admin: " + ColoresConsola.RESET);
		            String lo_que_ingreso = scanner.next();
		            if (lo_que_ingreso.equals("gordis")) {
		                db.registrarAdmin(email, contraseña);
		                System.out.println(ColoresConsola.VERDE + "!Bienvenido admin!" + ColoresConsola.RESET);
		                System.out.println();
		            } else {
		                System.out.println(ColoresConsola.ROJO +"Contraseña incorrecta."+ ColoresConsola.RESET);
		            }
					
				}
				else if (rol == 2) {
					System.out.println("Nombre:");
					String nombre = scanner.next();
					System.out.println("DNI: ");
					String dni = scanner.next();
					System.out.println("Domicilio: ");
					String domicilio = scanner.next();
					db.registrarCliente(email, contraseña, nombre, domicilio, dni, 0);
					System.out.print(ColoresConsola.VERDE + "!Bienvenido cliente!" + ColoresConsola.RESET);
					System.out.println();
				}
				else {
					System.out.println(ColoresConsola.ROJO + "Opcion invalida" + ColoresConsola.RESET);
				}
				db.close();
			}
			
			else if (opcion_inicio == 2) {
				System.out.println("Mail: ");
	    		String mail = scanner.next();
	    		System.out.println();
	    		System.out.println("Contraseña: ");
	    		String contrasenia = scanner.next();
	    		System.out.println();
	    		
	    		UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
	    		ProductosMongoDB dbProductos = new ProductosMongoDB();
	    		
	    		if (usuariosDB.iniciarUsuario(mail, contrasenia)) {
	    			if (usuariosDB.isAdmin(mail)) {
	    				while (true) {
	    					System.out.println(ColoresConsola.PURPURA + "╔═════════════════════ Supermercado ELGORDIS ═════════════════════╗" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║                  ¿Qué operación deseas realizar?                ║" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 1. AGREGAR PRODUCTO" + ColoresConsola.PURPURA + "                                             ║" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 2. LISTAR PRODUCTOS" + ColoresConsola.PURPURA + "                                             ║" + ColoresConsola.RESET);     
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 3. MODIFICAR PRODUCTO" + ColoresConsola.PURPURA + "                                           ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 4. ELIMINAR PRODUCTO" + ColoresConsola.PURPURA + "                                            ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 5. LISTAR USUARIOS" + ColoresConsola.PURPURA + "                                              ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 6. AGREGAR STOCK A UN PRODUCTO" + ColoresConsola.PURPURA + "                                  ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 7. MOSTRAR HISTORIAL DE PRODUCTOS" + ColoresConsola.PURPURA + "                               ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 8. MOSTRAR PAGOS" + ColoresConsola.PURPURA + "                                                ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 9. SALIR" + ColoresConsola.PURPURA + "                                                        ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "╚═════════════════════════════════════════════════════════════════╝" + ColoresConsola.RESET);
	    					
			        	    int opcion_admin = scanner.nextInt();
			        	    
			   
			        	    if (opcion_admin == 1) {
			        	        agregarProducto(scanner);
			        	    } 
			        	    else if (opcion_admin == 2) {
			        	        dbProductos.listarProductos();
			        	    }
			        	    else if (opcion_admin == 3) {
			        	    	modificarProducto(scanner, mail);
			        	    } 
			        	    else if (opcion_admin == 4) {
			        	    	eliminarProducto(scanner);
			        	    }
			        	    else if (opcion_admin == 5) {
			        	    	usuariosDB.printAllDocuments();
			        	    }
			        	    else if (opcion_admin == 6) {
			        	    	agregarStock(scanner);
			        	    }
			        	    else if (opcion_admin == 7) {
			        	    	HistorialMongoDB historialDB = new HistorialMongoDB();
			        	    	historialDB.mostrarHistorial();
			        	    	historialDB.close();
			        	    }
			        	    else if (opcion_admin == 8) {
			        	    	PagosCassandra pagosDB = new PagosCassandra();
			        	    	pagosDB.connect();
			        	    	pagosDB.createKeyspace();
			        	    	pagosDB.createTable();
			        	    	pagosDB.verPagos();
			        	    	pagosDB.close();
			        	    }
			        	    else if (opcion_admin == 9) {
			        	    	break;
			        	    }
			        	    else {
			        	        System.out.println(ColoresConsola.ROJO + "Error. Ingrese de nuevo." + ColoresConsola.RESET);
			        	    }
	    				}
	    				
	    			} else {
	    				usuariosDB.actualizarFecha(mail);
	    				Temporizador temp = new Temporizador();
	    				temp.iniciarTemporizador();
	    				while (true) {
	    					System.out.println(ColoresConsola.PURPURA + "╔═════════════════════ Supermercado ELGORDIS ═════════════════════╗" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║                  ¿Qué operación deseas realizar?                ║" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 1. VER PRODUCTOS" + ColoresConsola.PURPURA + "                                                ║" + ColoresConsola.RESET);
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 2. VER CARRITO" + ColoresConsola.PURPURA + "                                                  ║" + ColoresConsola.RESET);     
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 3. AGREGAR PRODUCTO AL CARRITO" + ColoresConsola.PURPURA + "                                  ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 4. ELIMINAR PRODUCTO DEL CARRITO" + ColoresConsola.PURPURA + "                                ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 5. RESTAR CANTIDAD DE UN PRODUCTO" + ColoresConsola.PURPURA + "                               ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 6. VER MI CATEGORIA" + ColoresConsola.PURPURA + "                                             ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 7. REALIZAR PAGO" + ColoresConsola.PURPURA + "                                                ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "║" + ColoresConsola.RESET +" 8. SALIR" + ColoresConsola.PURPURA + "                                                        ║" + ColoresConsola.RESET);                       
	    		            System.out.println(ColoresConsola.PURPURA + "╚═════════════════════════════════════════════════════════════════╝" + ColoresConsola.RESET);
	    					
			            	//pago->cliente, factura->admin
			        	    
			        	    int opcion_cliente = scanner.nextInt();
			        	    if (opcion_cliente == 1) {
			        	    	dbProductos.listarProductos();
			        	    }
			        	    else if(opcion_cliente == 2) {
			        	    	verCarrito(mail);
			        	    }
			        	    else if(opcion_cliente == 3) {
			            		agregarProdCarrito(scanner, mail);
			            	}
			            	else if (opcion_cliente == 4) {
			            		eliminarProdCarrito(scanner, mail);
			            	}
			            	else if (opcion_cliente == 5) {
			            		restarCant(scanner, mail);
			            	}
			            	else if (opcion_cliente == 6) {
			            		verCategoria(mail);
			            	}
			            	else if (opcion_cliente == 7) {
			            		registrarPedido(mail);
			            		realizarFacturaYPago(scanner, mail);
			        	    }
			            	else if (opcion_cliente == 8) {
			            		usuariosDB.actualizarActividad(temp.terminarTemporizador(), mail);
			            		
			            		break;
			                } 
			            	else {
			                    System.out.println("Error. Ingrese de nuevo.");
			                }
	    				}
	    			}
	    		} else {
	    			System.out.println("Usuario o contraseña invalidos.");
	    		}
	    		usuariosDB.close();
	    		dbProductos.close();
			}
	    		
			else if (opcion_inicio == 3) {
				scanner.close();
				System.exit(0);
			}
			
			else {
	        System.out.println("Error. Opción no válida.");
			}
    	}
    }
    

	//*****ADMIN*****
    private static void agregarProducto(Scanner scanner) {
        System.out.print("Código: ");
        int codigo = scanner.nextInt();

        System.out.print("Descripción: ");
        String descripcion = scanner.next();

        System.out.print("Precio unitario: ");
        double precioUnitario = scanner.nextDouble();

        System.out.print("Ingrese la cantidad en stock del producto: ");
        int cantidadStock = scanner.nextInt();
        Producto nuevoProducto = new Producto(codigo, descripcion, precioUnitario, cantidadStock);
        ProductosMongoDB productosDB = new ProductosMongoDB();
        productosDB.registrarProducto(nuevoProducto);
        productosDB.close();
        
    }

    private static void modificarProducto(Scanner scanner, String email) {
    	ProductosMongoDB productosDB = new ProductosMongoDB();
        if (productosDB.productosVacio()) {
            System.out.println("No hay productos.");
        }
        else {
	        System.out.println("Lista de productos:");
	        productosDB.listarProductos();
	        
	        System.out.print("Ingrese el código del producto que desea modificar: ");
	        int codigoModificar = scanner.nextInt();

	        if (productosDB.existeProducto(codigoModificar)) {
	            System.out.println("Producto encontrado.");
	
	            System.out.print("Nueva descripción: ");
	            String nuevaDescripcion = scanner.next();
	            
	            System.out.print("Nuevo precio unitario: ");
	            double nuevoPrecioUnitario = scanner.nextDouble();
	
	            System.out.print("Nueva cantidad en stock: ");
	            int nuevaCantidadStock = scanner.nextInt();
	            
	            productosDB.modificarProducto(codigoModificar, nuevaDescripcion, nuevoPrecioUnitario, nuevaCantidadStock, email);
	       }
	        else {
	        	System.out.println("Producto no encontrado. No se ha modificado ningún producto.");
	        }
        }
        productosDB.close();
    }
    
    public static void eliminarProducto(Scanner scanner) {
    	System.out.print("Código del producto a eliminar: ");
        int codigoEliminar = scanner.nextInt();
        ProductosMongoDB productosdDB = new ProductosMongoDB();
        if (productosdDB.existeProducto(codigoEliminar)) {
        	productosdDB.eliminarProducto(codigoEliminar);
        	System.out.println("Producto eliminado correctamente");
        }
        else {
        	System.out.println("No se encontro el producto");
        }
        productosdDB.close();
    }
    
    public static void agregarStock(Scanner scanner) {
    	ProductosMongoDB productosDB = new ProductosMongoDB();
    	System.out.print("Código del producto: ");
        int codigo = scanner.nextInt();
        System.out.print("Ingrese cantidad: ");
        int cantidad = scanner.nextInt();
        productosDB.agregarStock(codigo, cantidad);
        productosDB.close();
    }
 
    //*****CLIENTE*****
	private static void agregarProdCarrito(Scanner scanner, String email) {
		UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
		ProductosMongoDB productosDB = new ProductosMongoDB();		
		CarritoMongoDB carritoDB = new CarritoMongoDB(usuariosDB.getDNI(email));
    	while (true) {
    		System.out.print("Ingrese el código del producto (-1 para terminar): ");
            int codigoProducto = scanner.nextInt();	
            
            if (codigoProducto == -1) {
                break;
            }
            if (productosDB.existeProducto(codigoProducto)) {
	            int cantidad = 0;
	            while(cantidad < 1 || cantidad > productosDB.getCantidadStock(codigoProducto)) {
			        System.out.print("Ingrese cantidad: ");
			        cantidad = scanner.nextInt();
			        if (cantidad < 1 || cantidad > productosDB.getCantidadStock(codigoProducto)) {
			        	System.out.print("Cantidad no valida. ");
			        }
	            }
	            if (carritoDB.existeProductoCarrito(codigoProducto)){
	            	int cantAnterior = carritoDB.getCantidad(codigoProducto);
	            	carritoDB.eliminarProductoCarrito(codigoProducto);
	            	cantidad = cantidad + cantAnterior;
	            }
		        String descripcion = productosDB.getDescrpcion(codigoProducto);
		        Double precioUnitario = productosDB.getPrecioUnitario(codigoProducto);
		        carritoDB.agregarProductos(codigoProducto, cantidad, descripcion, precioUnitario);
	            productosDB.restarStock(codigoProducto, cantidad);
            }
            else {
            	System.out.println("Codigo no valido.");
            }
    	}
		usuariosDB.close();
		productosDB.close();
		carritoDB.close();
    }
    
    private static void eliminarProdCarrito(Scanner scanner, String email) {
    	UsuariosMongoDB usuariosDB = new UsuariosMongoDB();	
		CarritoMongoDB carritoDB = new CarritoMongoDB(usuariosDB.getDNI(email));
		ProductosMongoDB productosDB = new ProductosMongoDB();
        while (true) {
        	System.out.println("Código del producto a eliminar (-1 para terminar): ");
        	int codigo = scanner.nextInt();
        	
        	if (codigo == -1) {
                break;
            }
        	
        	if (carritoDB.existeProductoCarrito(codigo)) {
        		int cant = carritoDB.getCantidad(codigo);
        		if (productosDB.existeProducto(codigo)) {
        			productosDB.agregarStock(codigo, cant);
        		} else {
        			Producto p = new Producto(codigo, carritoDB.getDescripcion(codigo), carritoDB.getPrecioUnitario(codigo), carritoDB.getCantidad(codigo));
					productosDB.registrarProducto(p);
        		}
        		carritoDB.eliminarProductoCarrito(codigo);
	            System.out.println("Producto eliminado.");
	            }else {
	            	System.out.println("No se encontro el producto");
				}
        }
        productosDB.close();
		usuariosDB.close();
		carritoDB.close();
    }
    
    private static void verCarrito(String email) {
		UsuariosMongoDB usuariosDB = new UsuariosMongoDB();		
		CarritoMongoDB carritoDB = new CarritoMongoDB(usuariosDB.getDNI(email));
		carritoDB.verCarrito();
		usuariosDB.close();
		carritoDB.close();
	}
    
    public static void restarCant(Scanner scanner, String email) {
    	UsuariosMongoDB usuariosDB = new UsuariosMongoDB();		
		CarritoMongoDB carritoDB = new CarritoMongoDB(usuariosDB.getDNI(email));
		ProductosMongoDB productosDB = new ProductosMongoDB();
		carritoDB.verCarrito();
		System.out.println("Ingrese el codigo del producto que desea actualizar: ");
		int codigoProducto = scanner.nextInt();
		if (carritoDB.existeProductoCarrito(codigoProducto)) {
			System.out.println("Ingrese la cantidad deseada: ");
			int cantNueva = scanner.nextInt();
			if (cantNueva > 0) {
				if (productosDB.existeProducto(codigoProducto)) {
					productosDB.agregarStock(codigoProducto, carritoDB.getCantidad(codigoProducto));
					carritoDB.eliminarProductoCarrito(codigoProducto);
				}
				else {
					Producto p = new Producto(codigoProducto, carritoDB.getDescripcion(codigoProducto), carritoDB.getPrecioUnitario(codigoProducto), carritoDB.getCantidad(codigoProducto));
					productosDB.registrarProducto(p);
					carritoDB.eliminarProductoCarrito(codigoProducto);
				}
				String descripcion = productosDB.getDescrpcion(codigoProducto);
		        Double precioUnitario = productosDB.getPrecioUnitario(codigoProducto);
		        carritoDB.agregarProductos(codigoProducto, cantNueva, descripcion, precioUnitario);
		        productosDB.restarStock(codigoProducto, cantNueva);
			}
		}
		productosDB.close();
		usuariosDB.close();
		carritoDB.close();
	}
		
    public static void realizarFacturaYPago(Scanner scanner, String email) {
    	UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
    	PagosCassandra pagosDB = new PagosCassandra();
    	FacturasCassandra facturasDB = new FacturasCassandra();
    	
    	System.out.println("¿Qué medio de pago desea utilizar?");
    	System.out.println("1. Tarjeta de crédito");
    	System.out.println("2. Tarjeta de debito");
    	System.out.println("3. Transferencia");
    	int opcion = scanner.nextInt();
    	String medio = "Medio";
    	while (opcion < 1 || opcion > 3) {
		     System.out.println("Error. Opción no válida. Vuelva a intentarlo");
		     opcion = scanner.nextInt();
			}
    	if (opcion == 1){
    		medio = "Crédito";
    	}
    	else if(opcion == 2) {
    		medio = "Debito";
    	}
    	else if(opcion == 3) {
    		medio = "Transferencia";
    	}
    	
    	System.out.println("Procesando pago...");
    	
    	pagosDB.connect();
    	pagosDB.createKeyspace();
    	pagosDB.createTable();
    	pagosDB.insertarDatos(email, medio);
    	
    	facturasDB.connect();
    	facturasDB.createKeyspace();
    	facturasDB.createTable();
    	facturasDB.verFactura(facturasDB.insertarDatos(email, medio));
    	
    	facturasDB.close(); 
    	pagosDB.close();  	
    	usuariosDB.close();
    }
    
    
    public static void registrarPedido(String email) {
    	UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
    	PedidosMongoDB pedidosDB = new PedidosMongoDB();
    	pedidosDB.añadirPedido(usuariosDB.getDoc(email));
    	
    	usuariosDB.close();
    	pedidosDB.close();
    }
    
    public static void verCategoria(String email) {
    	UsuariosMongoDB usuariosDB = new UsuariosMongoDB();
    	int cantMin = usuariosDB.getActividad(email);
    	int dias = usuariosDB.getDias(email);
		if ((cantMin/dias) < 120) {
			System.out.println("Su categoría es Low");
		} else if ((cantMin/dias) < 240) {
			System.out.println("Su categoría es Medium");
		} else {
			System.out.println("Su categoría es Top");
		}
    	usuariosDB.close();
    }
    	
 }
    

