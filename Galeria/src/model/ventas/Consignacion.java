package model.ventas;

import java.io.Serializable;
import java.time.LocalDate;

import model.inventario.Pieza;
import model.usuarios.Administrador;
import model.usuarios.Comprador;

public class Consignacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Pieza pieza;
	private Administrador administrador;
	private Comprador comprador;
	private String fechaVencimiento;
	private String modoConsignacion;


	public Consignacion(Pieza pieza, String fechaVencimiento, String modoConsignacion) {
		this.pieza = pieza;
		this.fechaVencimiento = fechaVencimiento;
		this.modoConsignacion = modoConsignacion;
	}


	public String getFechaVencimiento() {
		return fechaVencimiento;
	}


	public Pieza getPieza() {
		return pieza;
	}


	public Administrador getAdministrador() {
		return administrador;
	}


	public boolean verificarVencimiento(String fechaActual) {
		boolean resultado;
		String [] fechaA=fechaActual.split("/");
		String [] fechaV=this.getFechaVencimiento().split("/");
		int AnioA=Integer.parseInt(fechaA[2]);
		int AnioV=Integer.parseInt(fechaV[2]);
		int MesA=Integer.parseInt(fechaA[1]);
		int MesV=Integer.parseInt(fechaV[1]);
		int DiaA=Integer.parseInt(fechaA[0]);
		int DiaV=Integer.parseInt(fechaV[0]);

		LocalDate VENCIMIENTO=LocalDate.of(AnioV, MesV, DiaV);
		LocalDate ACTUAL=LocalDate.of(AnioA, MesA, DiaA);
		if (VENCIMIENTO.isEqual(ACTUAL)) {
		    resultado=false;
		} else if (VENCIMIENTO.isBefore(ACTUAL)) {
			Pieza pieza=this.getPieza();
			this.finalizarConsignacion(pieza);
		    resultado=true;

		} else {
		    resultado=false;
		}
		return resultado;
	}

	public void agregarConsignacionPendiante() {
		administrador.getConsignacionesARevisar().add(this);
	}

	public void finalizarConsignacion(Pieza pieza) {
		administrador.getConsignacionesARevisar().remove(this);
		administrador.devolverPieza(pieza);
	}
}
