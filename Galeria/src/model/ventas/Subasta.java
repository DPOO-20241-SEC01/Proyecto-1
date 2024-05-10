package model.ventas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.inventario.Pieza;
import model.usuarios.Comprador;
import model.usuarios.Empleado;

public class Subasta implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String fecha;
	
	private Empleado operador;
	
	private ArrayList<Comprador> compradoresVerificados = new ArrayList<Comprador>();
	
	private ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
	
	private ArrayList<Pieza> piezasSubasta = new ArrayList<Pieza>();
	
	/*
	 * Constructor
	 */
	
	public Subasta(String fecha, Empleado operador) {
		 this.fecha = fecha;
		 this.operador = operador;
	     this.scheduler = Executors.newScheduledThreadPool(1); 
	}

	public String getFecha() {
		return fecha;
	}

	public Empleado getOperador() {
		return operador;
	}

	public ArrayList<Comprador> getCompradoresVerificados() {
		return compradoresVerificados;
	}
	
	public boolean addCompradorVerificado(Comprador comprador) {
	    if (!compradoresVerificados.contains(comprador)) {
	        compradoresVerificados.add(comprador);
	        return true;
	    }
	    return false;
	}

	public ArrayList<Oferta> getOfertas() {
		return ofertas;
	}

	public void addOferta(Oferta oferta) {
		this.ofertas.add(oferta);
		mostrarPieza(oferta.getPieza(), oferta.getValorOferta());
	}

	public ArrayList<Pieza> getPiezasSubasta() {
		return piezasSubasta;
	}

	public void addPiezasSubasta(Pieza pieza) {
		this.piezasSubasta.add(pieza);
	}
	
	/*
	 * Métodos
	 */

	private ScheduledExecutorService scheduler;


    public void iniciarSubasta() {
        operador.setSubastaEnCurso(this);
        for (Comprador c : compradoresVerificados) {
            c.setSubastaEnCurso(this);
        }
        final int[] index = {0}; 
        final Runnable mostrarPieza = new Runnable() {
            public void run() {
                if (index[0] < piezasSubasta.size()) {
                    Pieza p = piezasSubasta.get(index[0]++);
                    mostrarPieza(p, p.getValorMinimo());
                } else {
                    scheduler.shutdown();
                    finalizarSubasta();
                }
            }
        };
        scheduler.scheduleAtFixedRate(mostrarPieza, 0, 1, TimeUnit.MINUTES);
    }

    
    public void mostrarPieza(Pieza pieza, int valorOfertaActual) {
        pieza.setValorActualSubasta(valorOfertaActual);
        for (Comprador c : compradoresVerificados) {
            c.setPiezaSubastaEnCurso(pieza);
        }
    }
    
    
    public void finalizarSubasta() {
        operador.registrarOfertasSubasta();
        operador.setSubastaEnCurso(null);
        for (Comprador c : compradoresVerificados) {
            c.setSubastaEnCurso(null);
        }
    }
}
	
	
	
	

	
	
	

