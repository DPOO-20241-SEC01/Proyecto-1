package model.usuarios;

import java.util.ArrayList;

import model.inventario.Pieza;
//import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;
import view.ViewComprador;

public class Comprador extends Usuario {

	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Arreglo con las piezas que actualmente son propiedad del usuario.
	 */
	private ArrayList<Pieza> piezasActuales = new ArrayList<Pieza>();
	
	
	/**
	 * Arreglo con todas las piezas que han sido propiedad del usuario.
	 */
	private ArrayList<Pieza> piezasPasadas = new ArrayList<Pieza>();
	
//	private HashMap<String, String> facturas = new HashMap<String, String>();
	
	/**
	 * Valor máximo de compras establecido para el usuario.
	 */
	private int valorMaximoCompras;
	
	
	/**
	 * Valor acumulado de las compras que ha realizado el usuario.
	 */
	private int totalComprasRealizadas;
	
	
	/**
	 * Valor del saldo disponible que el usuario tiene para realizar compras u ofertas
	 * dentro del sistema
	 */
	private int saldoDisponible;
	
	
	/**
	 * Representación de una subasta que se realiza en ese instante
	 * y de la que el usuario hace parte.
	 */
	private Subasta subastaEnCurso;
	
	
	/**
	 * La pieza de la subasta en curso que se está ofreciendo en
	 * ese instante.
	 */
	private Pieza piezaSubastaEnCurso;
	
//	private ArrayList<Consignacion> consignaciones = new  ArrayList<Consignacion>();
	
	/**
	 * Arreglo con las ofertas que el usuario a realizado pero que aún 
	 * no se han procesado como una compra. Puede que esas ofertas sean rechazadas. 
	 */
	private ArrayList<Oferta> ofertasPendientes = new  ArrayList<Oferta>();
	
	
	/**
	 * El view del usuario de tipo "Comprador"
	 */
	private transient ViewComprador viewComprador;
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param apellido
	 * @param cedula
	 * @param login
	 * @param password
	 * @param tipoUsuario
	 */
	public Comprador(String nombre, String apellido,  String cedula ,String login, String password, String tipoUsuario) {
		super(nombre, apellido, cedula, login, password, tipoUsuario);
		this.valorMaximoCompras = 200000;
		setSaldoDisponible();
	}

	
	public ArrayList<Pieza> getPiezasActuales() {
		return piezasActuales;
	}
	
	
	public ArrayList<Pieza> getPiezasPasadas() {
		return piezasPasadas;
	}
	
	
//	public HashMap<String, String> getFacturas() {
//		return facturas;
//	}
	
	public int getValorMaximoCompras() {
		return valorMaximoCompras;
	}

	
	public void setValorMaximoCompras(int valorMaximoCompras) {
		this.valorMaximoCompras = valorMaximoCompras;
		setSaldoDisponible();
	}
	
	public int getTotalComprasRealizadas() {
		return totalComprasRealizadas;
	}
	
	
	public void setgetTotalComprasRealizadas(int valorCompra) {
		this.totalComprasRealizadas += valorCompra;
	}
	
	
	public int getSaldoDisponible() {
		return saldoDisponible;
	}
	
	public void setSaldoDisponible() {
		int valorMaximoCompras = getValorMaximoCompras();
		int totalComprasRealizadas = getTotalComprasRealizadas();
		this.saldoDisponible = valorMaximoCompras - totalComprasRealizadas;
	}
	
	public void setPiezaSubastaEnCurso(Pieza pieza) {
		this.piezaSubastaEnCurso = pieza;
	}
	
	
	public Pieza getPiezaSubastaEnCurso() {
		return piezaSubastaEnCurso;
	}
	
	
	
	
	public Subasta getSubastaEnCurso() {
		return subastaEnCurso;
	}

	public void setSubastaEnCurso(Subasta subastaEnCurso) {
		this.subastaEnCurso = subastaEnCurso;
	}
	
	

	public ViewComprador getViewComprador() {
		return viewComprador;
	}

	public void setViewComprador(ViewComprador viewComprador) {
		this.viewComprador = viewComprador;
	}
	
	
	public ArrayList<Oferta> getOfertasPendientes() {
		return ofertasPendientes;
	}

	/*
	 * Métodos
	 */

	public void hacerOfertaSubasta(int valorOferta, String metodoPago) {
		Pieza pieza = getPiezaSubastaEnCurso();
		String peticion = null;
		Oferta oferta = new Oferta(pieza, this, valorOferta, peticion, metodoPago);
		getSubastaEnCurso().addOferta(oferta);
		ofertasPendientes.add(oferta);
	}
	
	public void comprarPieza(String tipoPieza, String idPieza, int valorOferta, String peticion, String metodoPago) {
		Pieza pieza = galeria.getPiezaPorID(tipoPieza, idPieza);
		Oferta oferta = new Oferta(pieza, this, valorOferta, peticion, metodoPago);
		if (peticion != null) {
			galeria.getAdminstrador().getOfertasARevisar().put(oferta.getIdOferta(), oferta);
			oferta.getPieza().setPropietario(this);
		} else {
			galeria.getCajero().getOfertasAceptadas().add(oferta);
			oferta.getPieza().setPropietario(this);
		
		
		}
	}
}

