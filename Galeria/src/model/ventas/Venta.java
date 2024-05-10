package model.ventas;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.inventario.Pieza;
import model.usuarios.Comprador;

public class Venta implements Serializable{
    
    private static final long serialVersionUID = 1L;
	private String idVenta;
    private Pieza pieza;
    private Comprador comprador;
    private String fecha;

    
    public Venta(Oferta oferta) {
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = now.format(formatter);
    	
        idVenta = oferta.getIdOferta();
        this.fecha = fecha;
        this.pieza = oferta.getPieza();
        this.comprador = oferta.getComprador();
    }


	public String getIdVenta() {
		return idVenta;
	}


	public Pieza getPieza() {
		return pieza;
	}


	public Comprador getComprador() {
		return comprador;
	}


	public String getFecha() {
		return fecha;
	}
    
    
    
    
}
    
   
    