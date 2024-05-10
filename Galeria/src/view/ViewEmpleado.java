package view;

import java.util.HashMap;

import model.Galeria;
import model.persistencia.CentralPersistencia;
import model.usuarios.Empleado;
import model.ventas.Subasta; 

public class ViewEmpleado extends View {
   
	private Empleado empleado;
	
    public ViewEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

    public void mostrarMenu() {
    	System.out.println("\n===========================================");
		System.out.println("Bienvenido, " + empleado.getNombre()+ "!");
        System.out.println("===========================================\n");
        
        if (empleado.getSubastaEnCurso() != null) {
        	System.out.println("\n----------------------------------------");
        	System.out.println("Subasta en curso\n");
        	System.out.println("Pieza actual: " + empleado.getPiezaSubastaEnCurso().getTituloPieza()+ "de" + empleado.getPiezaSubastaEnCurso().getArtistas().get(0));
        	System.out.println("[" + empleado.getPiezaSubastaEnCurso().getTipoPieza() + "]");
        	System.out.println("\nOferta actual: $" + String.valueOf(empleado.getPiezaSubastaEnCurso().getValorInicial()));
        	System.out.println("----------------------------------------\n");
        	
        }
        System.out.println("Disfruta tu tiempo libre. Por ahora no hay mucho que hacer...\n");
        System.out.println("1. Iniciar subasta");
        System.out.println("0. Cerrar sesión");
        
        String opcion = getInput("\nSelecciona una opción: ").trim();
        seleccionarOpcion(opcion);
    }
    
    public void seleccionarOpcion(String opcion) {
    	if (!opcion.equals("0")) {
    		switch(opcion) {
			case "1":
			iniciarSubasta();
			break;
			}
    		mostrarMenu();
    	} else {
    		ViewLogin viewLogin = new ViewLogin(empleado.getGaleria());
			viewLogin.mostrarMenu();
    	}
	}
    
    
    public static void main(String[] args) {
        CentralPersistencia centralPersistencia = new CentralPersistencia();
        Galeria galeria = (Galeria) centralPersistencia.cargar();

        if (galeria == null) {
            System.out.println("Debes esperar a que el administrador realice la configuración inicial de la galería.");
            return; 
        }

        ViewLogin viewLogin = new ViewLogin(galeria); 
        viewLogin.mostrarMenu();
    }
    
    
    public void iniciarSubasta() {
		HashMap<String, Subasta> subastas = empleado.getGaleria().getSubastas();
		String fecha = getInputFecha("Ingresa la fecha de la subasta a inciar (DD-MM-YYYY): ");
		
		if (subastas.containsKey(fecha)) {
			Subasta subasta = subastas.get(fecha);
			if (!(subasta.getCompradoresVerificados().isEmpty()) && !(subasta.getPiezasSubasta().isEmpty())) {
				System.out.println("Se ha iniciado la subasta :).");
				subasta.iniciarSubasta();
			} else {
					System.out.println("\nNo es posible iniciar la subasta porque no hay suficientes piezas o compradores verificados.");
			}
		} else {
			System.out.println("No se encontró una subasta para la fecha.");
			mostrarMenu();
		}
	}
}

