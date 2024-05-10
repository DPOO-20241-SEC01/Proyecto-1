package model.inventario;

public class Pintura extends Pieza {
	private static final long serialVersionUID = 1L;
	private int largo;
	private int ancho;
	
	public Pintura(String idPieza, String ubicacion, String tituloPieza, String anioCreacion, String lugarCreacion,
			String nombreArtista, int costoFijo, String tipoPieza, int largo, int ancho) {
		super(idPieza, ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza);
		this.largo = largo;
		this.ancho = ancho;
	}

	public int getLargo() {
		return largo;
	}

	public int getAncho() {
		return ancho;
	}
	
}