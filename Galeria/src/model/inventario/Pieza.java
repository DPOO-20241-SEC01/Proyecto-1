package model.inventario;
import java.io.Serializable;
import java.util.ArrayList;

import model.usuarios.Comprador;
import model.ventas.Consignacion;

public abstract class Pieza implements Serializable {

    private static final long serialVersionUID = 1L;

	protected String idPieza;
    
    protected String ubicacion;
    
    protected String tituloPieza;
    
    protected String anioCreacion;
    
    protected String lugarCreacion;
    
    protected String nombreArtista;
    
    protected String tipoPieza;
    
    protected int costoFijo;
    
    protected boolean isDisponible;
    
    protected int valorMinimo;
    
    protected int valorInicial;
    
    protected int valorActualSubasta;
    
    protected ArrayList<Artista> artistas = new ArrayList<Artista>();
    
    protected Comprador propietario;
    
    protected Consignacion consignacion;
    
    /*
     * Constructor
     */

    public Pieza(String idPieza, String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion, String nombreArtista, int costoFijo, String tipoPieza) {
    
    	this.idPieza = idPieza;
    	this.tipoPieza = tipoPieza;
    	this.ubicacion = ubicacion;
    	this.tituloPieza = tituloPieza;
    	this.anioCreacion = anioCreacion;
    	this.lugarCreacion = lugarCreacion;
    	this.nombreArtista = nombreArtista;
    	this.isDisponible = true;
    	this.costoFijo = costoFijo;
    	
    		
    }
    /*
     * Getters + Setters
     */

	public String getIdPieza() {
		return idPieza;
	}

	public String getTituloPieza() {
		return tituloPieza;
	}

	public String getAnioCreacion() {
		return anioCreacion;
	}

	public String getLugarCreacion() {
		return lugarCreacion;
	}

	public String getNombreArtista() {
		return nombreArtista;
	}

	public String getTipoPieza() {
		return tipoPieza;
	}

	public void setTipoPieza(String tipoPieza) {
		this.tipoPieza = tipoPieza;
	}

	public int getCostoFijo() {
		return costoFijo;
	}

	public void setCostoFijo(int costoFijo) {
		this.costoFijo = costoFijo;
	}

	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}

	public int getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(int valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(int valorInicial) {
		this.valorInicial = valorInicial;
	}

	public ArrayList<Artista> getArtistas() {
		return artistas;
	}

	public void addArtista(Artista artista) {
		this.artistas.add(artista);
	}

	public Comprador getPropietario() {
		return propietario;
	}

	public void setPropietario(Comprador propietario) {
		this.propietario = propietario;
	}

	public Consignacion getConsignacion() {
		return consignacion;
	}

	public void setConsignacion(Consignacion consignacion) {
		this.consignacion = consignacion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public int getValorActualSubasta() {
		return valorActualSubasta;
	}
	
	public void setValorActualSubasta(int valorOferta) {
		this.valorActualSubasta = valorOferta;
	}
	
}

    
	
    
    
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

