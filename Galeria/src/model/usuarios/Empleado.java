package model.usuarios;

import java.util.HashMap;
import java.util.Map;

import model.inventario.Pieza;
import model.ventas.Oferta;
import model.ventas.Subasta;
import view.ViewEmpleado;

public class Empleado extends Usuario {

	
	/*
	 * Atributos
	 */
	
	private static final long serialVersionUID = 1L;

	private Subasta subastaEnCurso;
	
	private Pieza piezaSubastaEnCurso;
	
	/*
	 * Views
	 */
	
	private transient ViewEmpleado viewEmpleado;
	
	/*
	 * Constructor
	 */
	
	public Empleado(String nombre, String apellido,  String cedula, String login, String password, String tipoUsuario) {
		super(nombre, apellido, cedula, login, password, tipoUsuario);
	}
	
	/*
	 * Getters + Setters
	 */

	public Subasta getSubastaEnCurso() {
		return subastaEnCurso;
	}

	public void setSubastaEnCurso(Subasta subastaEnCurso) {
		this.subastaEnCurso = subastaEnCurso;
	}

	public Pieza getPiezaSubastaEnCurso() {
		return piezaSubastaEnCurso;
	}

	public void setPiezaSubastaEnCurso(Pieza piezaSubastaEnCurso) {
		this.piezaSubastaEnCurso = piezaSubastaEnCurso;
	}

	public ViewEmpleado getViewEmpleado() {
		return viewEmpleado;
	}

	public void setViewEmpleado(ViewEmpleado viewEmpleado) {
		this.viewEmpleado = viewEmpleado;
	}
	
	/*
	 * Métodos
	 */

	public void registrarOfertasSubasta() {
		Map<Pieza, Oferta> ofertasMasAltas = new HashMap<>();
		for (Oferta oferta : subastaEnCurso.getOfertas()) {
		Pieza pieza = oferta.getPieza();
			if (!(ofertasMasAltas.containsKey(pieza)) || ofertasMasAltas.get(pieza).getValorOferta() < oferta.getValorOferta()) {
				ofertasMasAltas.put(pieza, oferta);
			}
		}
      
        for (Oferta ofertaGanadora : ofertasMasAltas.values()) {
            galeria.getAdminstrador().getOfertasARevisar().put(ofertaGanadora.getIdOferta(), ofertaGanadora);
        }
	}
		
	
	
	

}
