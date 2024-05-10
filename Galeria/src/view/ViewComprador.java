package view;

import java.util.ArrayList;

import model.Galeria;
import model.inventario.Pieza;
import model.persistencia.CentralPersistencia;
import model.usuarios.Comprador;
import model.ventas.Oferta; 

public class ViewComprador extends View {
   
	/**
	 * Atributos
	 */
	private Comprador comprador;
	
	
	/**
	 * Método constructor del view.
	 * @param administrador
	 */
    public ViewComprador(Comprador comprador) {
		this.comprador = comprador;
	}

    
    /**
	 * Muestra el menú de opciones del ViewComprador
	 */
    public void mostrarMenu() {
    	System.out.println("\n===========================================");
		System.out.println("Bienvenido, " + comprador.getNombre()+ "!");
        System.out.println("===========================================\n");
        
        // En caso de subasta
        if (comprador.getSubastaEnCurso() != null) {
        	System.out.println("\n----------------------------------------");
        	System.out.println("Subasta en curso\n");
        	System.out.println("Pieza actual: " + comprador.getPiezaSubastaEnCurso().getTituloPieza()+ "de" + comprador.getPiezaSubastaEnCurso().getArtistas().get(0));
        	System.out.println("[" + comprador.getPiezaSubastaEnCurso().getTipoPieza() + "]");
        	System.out.println("\nOferta actual: $" + String.valueOf(comprador.getPiezaSubastaEnCurso().getValorActualSubasta()));
        	System.out.println("----------------------------------------\n");
        	
        }
        
        // Info
        System.out.println("Valor máximo de compras: $" + String.valueOf(comprador.getValorMaximoCompras()));
        System.out.println("Total de compras realizadas: $" + String.valueOf(comprador.getTotalComprasRealizadas()));
        System.out.println("Saldo disponible: $" + String.valueOf(comprador.getSaldoDisponible()));
        
        //Menú
        System.out.println("\n"); //DONE
        System.out.println("1. Comprar pieza"); //DONE
        System.out.println("2. Consignar pieza"); 
        System.out.println("3. Ofertar por la pieza actual de la subasta"); //DONE
        System.out.println("4. Ver mis consignaciones actuales"); 
        System.out.println("5. Revisar consignaciones pendientes por aceptar"); 
        System.out.println("6. Revisar ofertas pendientes por aceptar"); //DONE
        System.out.println("0. Salir");
        
        String opcion = getInput("\nSelecciona una opción: ").trim();
        seleccionarOpcion(opcion);
    }
    
    
    /**
	 * Muestra el menú de opciones del ViewComprador
	 */
    public void seleccionarOpcion(String opcion) {
		if (!opcion.equals("0")) {
			switch(opcion) {
			case "1":
				comprarPieza();
				break;
			case "2":
//				consignarPieza(); //DONE
				break;
			case "3":
				hacerOfertaSubasta();
				break;
			case "4":
//				verConsignacionesComprador(); //DONE
				break;
			case "5":
//				verConsignacionesPendientesComprador(); //DONE 
				break;
			case "6":
				verOfertasPendientesComprador();
				break;
			}
			mostrarMenu();
		} else {
			ViewLogin viewLogin = new ViewLogin(comprador.getGaleria());
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
    

    /**
	 * Permite que le usuario compre una pieza de la galería
	 */
    public void comprarPieza() {
    	boolean verPiezas = verPiezasDisponibles();
    	if (verPiezas == false) {
    		System.out.println("No hay piezas disponibles para la compra.");
    		mostrarMenu();
    	} else {
    	System.out.println("\nPara comprar una pieza debes conocer su tipo y el id de la pieza en la galería.");
    	
    	System.out.println("1. Escultura");
        System.out.println("2. Pintura");
        System.out.println("3. Impresión");
        System.out.println("4. Fotografía");
        System.out.println("5. Vídeos");
        String tipoPieza = getInput("\nEscoge el tipo de pieza:").trim();
    	try {
        	if (tipoPieza.equals("1")) {
        		tipoPieza = "Escultura";
        	}
        	else if (tipoPieza.equals("2")) {
        		tipoPieza = "Pintura";
        	}
        	else if (tipoPieza.equals("3")) {
        		tipoPieza = "Impresión";
        	}
        	else if (tipoPieza.equals("4")) {
        		tipoPieza = "Fotografía";
        	}
        	else if (tipoPieza.equals("5")) {
        		tipoPieza = "Vídeos";
        	} else {
        		throw new IllegalArgumentException("Opción inválida.");
        	}
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        String idPieza = String.valueOf(getInputInt("\nID de la pieza: "));
        Pieza pieza = comprador.getGaleria().getPiezaPorID(tipoPieza, idPieza);
        try {
        	String metodoPago = escogerMetodoPago();
        	if (pieza != null) {
        		String peticion = null;
        		if (pieza.getCostoFijo() <= comprador.getSaldoDisponible()) {
        			comprador.comprarPieza(tipoPieza, idPieza, pieza.getCostoFijo(), peticion, metodoPago);
        		} else {
        			System.out.println("No tienes más saldo para comprar en la galería.");
        			boolean hacerPeticion = getInputY_N("¿Quieres solicitar un cupo mayor de compra?");
        			if (hacerPeticion) {
        				peticion = getInput("Petición: ");
        				
        				comprador.comprarPieza(tipoPieza, idPieza, pieza.getCostoFijo(), peticion, metodoPago);
        			} else {
        				mostrarMenu();
        			}
        		}
        	}
        	else {
        		throw new IllegalArgumentException("No se encontró una pieza para el ID: " + idPieza);
        	}
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    System.out.println("\nPieza comprada con éxito: " + mostrarPieza(pieza));
    	}
    }
        
    
    /**
     * Muestra en consola las piezas que se encuentran disponibles para
     * la compra
     * @return false si no hay nignuna pieza disponible. true si hay piezas.
     */
    public boolean verPiezasDisponibles() {
        System.out.println("\nPiezas disponibles para compra: ");
        ArrayList<Pieza> piezasDisponibles = comprador.getGaleria().getPiezasDisponibles();
        if (piezasDisponibles.isEmpty()) {
        	return false;
        } else {
        	for (Pieza p : piezasDisponibles) {
        		System.out.println(mostrarPieza(p));
        	}
        }
        return true;
    }
    
    
    public void hacerOfertaSubasta() {
    	if (comprador.getSubastaEnCurso() == null) {
    		System.out.println("\nNo puedes ofertar ahora mismo. \nNo estás participando en ninguna subasta en este momento");
    	} else {
    	System.out.println("Pieza actual: " + comprador.getPiezaSubastaEnCurso().getTituloPieza()+ "de" + comprador.getPiezaSubastaEnCurso().getArtistas().get(0));
    	System.out.println("[" + comprador.getPiezaSubastaEnCurso().getTipoPieza() + "]");
    	System.out.println("\nOferta actual: $" + String.valueOf(comprador.getPiezaSubastaEnCurso().getValorActualSubasta()));
    	int valorOferta = getInputInt("\nIngresa el valor de tu oferta: ");
    	try {
    		if (comprador.getPiezaSubastaEnCurso().getValorActualSubasta() > comprador.getSaldoDisponible()) {
    			throw new IllegalArgumentException("No tienes suficiente saldo para ofertar por esta pieza.");
    		}
    		if (valorOferta >= comprador.getPiezaSubastaEnCurso().getValorActualSubasta()) {
    			throw new IllegalArgumentException("El valor de tu oferta debe ser mayor al de la oferta actual.");
    		} else {
    			String metodoPago = escogerMetodoPago();
    			comprador.hacerOfertaSubasta(valorOferta, metodoPago);
    		}
    	} catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
    	}
    }
    }

    public void verOfertasPendientesComprador() {
    	if (comprador.getOfertasPendientes().isEmpty()) {
    		System.out.println("\nAhora mismo no tienes ofertas pendientes por ser aceptadas.");
    	} else {
    	System.out.println("\nOfertas realizadas:");
    	for (Oferta o : comprador.getOfertasPendientes()) {
    		System.out.println("ID: " + o.getIdOferta() +". " + o.getFecha() + ". Status: PENDIENTE");
    	}
    	}
    }
    
    
    public String escogerMetodoPago() {
    	 System.out.println("\nEscoge un método de pago");
    	 System.out.println("1. Efectivo");
    	 System.out.println("2. Tarjeta de crédito");
    	 System.out.println("3. Transferencia Bancaria");
    	 String metodoPago = getInput("\nEscoge una opción:").trim();
    	 try {
    		 if (metodoPago.equals("1")) {
    	   		metodoPago = "Efectivo";
    		 }
    		 else if (metodoPago.equals("2")) {
    	   		metodoPago = "Tarjeta de crédito";
    		 }
    		 else if (metodoPago.equals("3")) {
    			 metodoPago = "Transferencia Bancaria";
    		 }
    		 else {
    			 throw new IllegalArgumentException("Opción inválida.");
    	    }
    	  } catch (IllegalArgumentException e) {
    	        System.out.println(e.getMessage());
    	  }
    	 return metodoPago;
   }
}
////
////    public void verConsignaciones() {
////        System.out.println("Consignaciones del comprador:");
////        // Aquí deberías implementar la lógica para mostrar las consignaciones del comprador
////        ArrayList<Consignación> consignaciones = comprador.getConsignaciones();
////        for (Consignación consignacion : consignaciones) {
////            System.out.println("Pieza: " + consignacion.getPieza().getTitulo()  + ", Costo: " + consignacion.getPieza().getCosto());
////        }
////    }
////}
