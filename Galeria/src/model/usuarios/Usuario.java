package model.usuarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import model.Galeria;
import model.inventario.Artista;
import model.inventario.Pieza;


public abstract class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Atributos
	 */
	
	protected Galeria galeria;
	
	protected String nombre;
	
	protected String apellido;
	
	protected String cedula;
	
	protected String login;

	protected String password;

	protected String tipoUsuario;
	
	
	/*
	 * Constructor
	 */

	public Usuario(String nombre, String apellido, String cedula, String login, String password, String tipoUsuario) {
		
		setNombre(nombre);
		setApellido(apellido);
		setCedula(cedula);
		setLogin(login);
		setPassword(password);
		setTipoUsuario(tipoUsuario);
	}
	
	/*
	 * Getters + Setters
	 */

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Galeria getGaleria() {
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}	
	
	public HashMap<String, ArrayList<Pieza>> procesarHistoriaArtista(String nombreArtista){
		HashMap<String, ArrayList<Pieza>> historiaArtista = new HashMap<String, ArrayList<Pieza>>();

		ArrayList<Pieza> piezasPasadas = galeria.getPiezasPasadas();
		for(Pieza p : piezasPasadas) {
			ArrayList<Artista> artistasSub =   p.getArtistas();
			for (Artista a : artistasSub ) {
				String nombreArtista1 = a.getNombre();
				if(historiaArtista.containsKey(a.getNombre())){
					ArrayList<Pieza> arreglo = historiaArtista.get(a.getNombre());
					arreglo.add(p);
				}
				else {
					ArrayList<Pieza> arrayPiezas = new ArrayList<Pieza>();
					historiaArtista.put(nombreArtista1, arrayPiezas);
					ArrayList<Pieza> arreglo = historiaArtista.get(a.getNombre());
					arrayPiezas.add(p);
					
				}
			}
		}
		return historiaArtista;
	}
	

}
