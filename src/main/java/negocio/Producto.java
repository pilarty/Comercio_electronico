package negocio;

public class Producto {
    private int codigo;
    private String descripcion;
    private double precioUnitario;
    private int cantidadStock;

    public Producto(int codigo, String descripcion, double precioUnitario, int cantidadStock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadStock = cantidadStock;
    }
    
    public double getPrecioUnitario() {
    	return precioUnitario;
    }
    
    public int getCantidadStock() {
    	return cantidadStock;
    }
    
    public int getCodigo() {
		return codigo;
	}
    
    public String getDescripcion() {
		return descripcion;
	}
    
    public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	
	public void setCantStock(int stock) {
		cantidadStock = stock;
		
	}
    
}

