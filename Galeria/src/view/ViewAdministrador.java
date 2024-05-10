package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.Galeria;
import model.persistencia.CentralPersistencia;
import model.usuarios.Administrador;
import model.usuarios.Cajero;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.usuarios.Usuario;
import model.ventas.Oferta;
import model.ventas.Subasta;

public class ViewAdministrador extends View {
	
	/**
	 * Atributos
	 */
	private Administrador administrador;
	
	/**
	 * Método constructor del view.
	 * @param administrador
	 */
	public ViewAdministrador(Administrador administrador) {
		administrador.setViewAdministrador(this);
		this.administrador = administrador;
	}
	
	/**
	 * Muestra el menú de opciones del ViewAdministrador
	 */
	@Override
	public void mostrarMenu() {
		System.out.println("\n===========================================");
		System.out.println("Bienvenido, administrador!");
        System.out.println("===========================================\n");
        
        //menú
        System.out.println("1. Ingresar pieza a inventario"); //DONE
        System.out.println("2. Configurar subasta"); //DONE
        System.out.println("3. Iniciar subasta"); 
        System.out.println("4. Verificar comprador"); 
        System.out.println("5. Revisar ofertas pendientes"); 
        System.out.println("6. Revisar consignaciones pendientes");
        System.out.println("7. Registrar empleado"); //DONE
        System.out.println("8. Configurar cajero"); 
        System.out.println("9. Ver historial de un comprador."); 
        System.out.println("\n0. Cerrar sesión");
      
        String opcion = getInput("\nSelecciona una opción: ").trim();
        seleccionarOpcion(opcion);
	}
	
	/**
	 * Ejecuta una opción del menú según la opción seleccionada.
	 * @param opcion
	 */
	public void seleccionarOpcion(String opcion) {
		if (!(opcion.equals("0"))) {
			switch(opcion) {
			case "1":
				ingresarPiezaAInventario();
				break;
			case "2":
				crearSubasta();
				break;
			case "3":
				iniciarSubasta();
				break;
			case "4":
				verificarComprador();
				break;
			case "5":
				revisarOfertasPendientes();
				break;
			case "7":
				registrarEmpleado();
				break;
			case "8":
				configurarCajero();
				break;
			}
			mostrarMenu();
		} else {
			ViewLogin viewLogin = new ViewLogin(administrador.getGaleria());
			viewLogin.mostrarMenu();	
		}
	}
		
	/**
	 * Métood main del View del Administrador.
	 * Por medio de este, el administrador puede iniciar el sistema. 
	 */
	public static void main(String[] args) {
        CentralPersistencia centralPersistencia = new CentralPersistencia();
        Galeria galeria;

        Object data = centralPersistencia.cargar(); 
        if (data instanceof Galeria) {
            galeria = (Galeria) data;
            galeria.setCentralPersistencia(centralPersistencia);
            System.out.println("Galería cargada con éxito");
            ViewLogin viewLogin = new ViewLogin(galeria);
            viewLogin.mostrarMenu();
        } else {
            galeria = new Galeria();
            centralPersistencia.guardar(galeria);
        }
    }
	
	public void ingresarPiezaAInventario() {
		System.out.println("--> 1. Ingresar pieza a inventario");
		String salir = getInput("Ingresa cualquier tecla para continuar ó [0] para regresar:").trim();
		if (salir.equals("0")) {
			mostrarMenu();
		} else {
			System.out.println("\n¿Adónde ingresa la pieza?");
			System.out.println("1. Exhibición");
	        System.out.println("2. Bodega");
	        String ubicacion = getInput("\nEscoge una opción:").trim();
	        try {
	        	if (ubicacion.equals("1")) {
	        		ubicacion = "Exhibición";
	        	}
	        	else if (ubicacion.equals("2")) {
	        		ubicacion = "Bodega";
	        	} else {
	        		throw new IllegalArgumentException("Opción inválida.");
	        	}
	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }
	        System.out.println("Pieza a " + ubicacion);
			System.out.println("\nInformación del autor.");
			System.out.println("\n¿Es un artista individual o un colectivo de artistas?");
	        System.out.println("1. Artista individual");
	        System.out.println("2. Colectivo de artistas");
	        System.out.println("0. Artista anónimo");
	        String nombreArtista = getInput("\nEscoge una opción:").trim();
	        String nombreColectivo = null;
	        boolean perteneceAColectivo = false;
			try {
	        	if (nombreArtista.equals("1")) {
	        		nombreArtista = getInput("\nNombre del artista:").trim();
	        		boolean c = getInputY_N("\n¿El artista pertenece a algún colectivo de artista?");
	        		if (c) {
	        			nombreColectivo = getInput("\nNombre del colectivo:").trim();
	        			perteneceAColectivo = true;
	        		}
	        	}
	        	else if (nombreArtista.equals("2")) {
	        		nombreArtista = getInput("\nNombre del colectivo:").trim();
	        		nombreColectivo = nombreArtista;
	        		perteneceAColectivo = true;
	        	} else if (nombreArtista.equals("0")) {
					nombreArtista = "Anónimo";
					nombreColectivo = null;
			        perteneceAColectivo = false;
				}
	        	else {
	        		throw new IllegalArgumentException("Opción inválida.");
	        	}
	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }
			System.out.println("\nInformación de la pieza.");
			String tituloPieza = getInput("\nTítulo de la pieza: ").trim();
			String anioCreacion = getInputFecha("\nAño de creación: ").trim();
			String lugarCreacion = capitalize(getInput("\nLugar de creación: ")).trim();
			System.out.println("\nTipo de la pieza:");
			System.out.println("1. Escultura");
	        System.out.println("2. Pintura");
	        System.out.println("3. Impresión");
	        System.out.println("4. Fotografía");
	        System.out.println("5. Vídeos");
	        String tipoPieza = getInput("\nEscoge una opción:").trim();
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
	        System.out.println("\n¿La pieza tiene precio de venta? Ingresa [0] si no.");
	        int costoFijo = getInputInt("Precio de venta: ");
            ingresarTipoPieza(tituloPieza, ubicacion, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, nombreColectivo, perteneceAColectivo);
	    } 
	}
	
	public void ingresarTipoPieza(String tituloPieza, String ubicacion, String anioCreacion, String lugarCreacion, String nombreArtista, int costoFijo, String tipoPieza, String nombreColectivo, boolean perteneceAColectivo) {
		switch(tipoPieza) {
		case "Escultura":
			System.out.println("\nDimensiones (Centímetros):");
			int alto = getInputInt("Alto: ");
			int largo = getInputInt("Largo: ");
			int ancho = getInputInt("Ancho: ");
			String dimensiones = ( String.valueOf(alto) + "-" + String.valueOf(largo) + "-" + String.valueOf(ancho));
			String peso = String.valueOf(getInputInt("\nPeso (Kilogramos): "));
			String materialesConstruccion = String.valueOf(getInput("\nMateriales de construcción:  "));
			boolean requiereElectricidad = getInputY_N("\n¿La escultura requiere electricidad?");
			String idPieza = administrador.ingresarEscultura(ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, dimensiones, peso, materialesConstruccion, requiereElectricidad);
			administrador.agregarPiezaAArtista(idPieza, tipoPieza, nombreArtista, nombreColectivo, perteneceAColectivo);
		case "Pintura":
			int largoP = getInputInt("\nLargo(centimetros): ");
			int anchoP = getInputInt("\nAncho(centimetros): ");
			String idPintura= administrador.ingresarPintura(ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, largoP, anchoP);
			administrador.agregarPiezaAArtista(idPintura, tipoPieza, nombreArtista, nombreColectivo, perteneceAColectivo);
			break;

		case "Impresión":
			String tipohoja= String.valueOf(getInput("\nTipo de hoja: "));
			int largoI = getInputInt("\nLargo: ");
			int anchoI = getInputInt("\nAncho: ");
			String idImpresion=administrador.ingresarImpresion(ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, tipohoja, largoI, anchoI);
			administrador.agregarPiezaAArtista(idImpresion, tipoPieza, nombreArtista, nombreColectivo, perteneceAColectivo);
			break;

		case "Fotografía":
			String tipoFotografia= String.valueOf(getInput("\nTipo de fotografia"));
			int altoF = getInputInt("\nLargo: ");
			int anchoF = getInputInt("\nAncho: ");
			String resolucionImagen=String.valueOf(getInput("\nResolucion imagen(Píxeles por pulgada)"));
			String idFotografía= administrador.ingresarFotografia(ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, altoF, anchoF, tipoFotografia, resolucionImagen);
			administrador.agregarPiezaAArtista(idFotografía, tipoPieza, nombreArtista, nombreColectivo, perteneceAColectivo);
			break;
			
		case "Vídeos":
			int duracion =getInputInt("\nDuración(minutos): ");
			int pesoV =getInputInt("\nCuanto pesa el video(MB): ");
			boolean mayoresDeEdad = getInputY_N("\n¿El video es para mayores de 18?");
			String resolucionVideo=String.valueOf(getInput("\nResolucion imagen(Píxeles por pulgada)"));
			String idVideo= administrador.ingresarVideo(ubicacion, tituloPieza, anioCreacion, lugarCreacion, nombreArtista, costoFijo, tipoPieza, duracion, mayoresDeEdad, resolucionVideo, pesoV);
			administrador.agregarPiezaAArtista(idVideo, tipoPieza, nombreArtista, nombreColectivo, perteneceAColectivo);
			break;
		}
		System.out.println("\nPieza ingresada con éxito: " + nombreArtista + ". (" + anioCreacion + "). " + tituloPieza + " [" + tipoPieza + "].");
		mostrarMenu();
	}
	
	public void registrarEmpleado() {
		administrador.registrarEmpleado();
	}
	 
	public void revisarOfertasPendientes() {
	        HashMap<String, Oferta> ofertas = administrador.getOfertasARevisar();
	        System.out.println("\nOfertas Pendientes:");
	        for (Oferta oferta : ofertas.values()) {
	            System.out.println("ID: " + oferta.getIdOferta() + ". Monto: " + oferta.getValorOferta() + ". Fecha: " + oferta.getFecha());
	            // Imprimir otras propiedades de la oferta según sea necesario
	            boolean aceptar = getInputY_N("¿Desea aceptar esta oferta?");
	            if (aceptar) {
	            	administrador.aceptarOferta(oferta);
	                System.out.println("Oferta aceptada correctamente.");
	            } else {
	                System.out.println("Oferta rechazada.");
	            }
	        }
	    }

	public void iniciarSubasta() {
		HashMap<String, Subasta> subastas = administrador.getGaleria().getSubastas();
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
	
	public void crearSubasta() {
	    System.out.println("\n--> Seleccionar fecha");
	    String fecha = getInputFecha("Ingresa la fecha en la que se realizará la subasta (DD-MM-YYYY): ");
	    System.out.println("\n--> Seleccionar operador");
	    Empleado empleado = seleccionarEmpleado();

	    if (empleado != null) {
	        boolean subastaCreada = administrador.getGaleria().crearSubasta(fecha, empleado);
	        if (subastaCreada) {
	            System.out.println("\nSe creó una subasta para " + fecha + ". Operador: " + empleado.getNombre() + " " + empleado.getApellido() + ", CC: " + empleado.getCedula());
	        } else {
	            System.out.println("No hay suficientes piezas disponibles para crear una subasta con 5 piezas.");
	        }
	    } else {
	        System.out.println("Error: No se pudo asignar un operador para la subasta.");
	        mostrarMenu();
	    }
	}
	
	public Empleado seleccionarEmpleado() {
        ArrayList<Empleado> empleados = administrador.getGaleria().getEmpleadosDisponibles();
        if (empleados.isEmpty()) {
            System.out.println("No hay ningún empleado registrado. Primero, registra un empleado.\n");
            return null;
        } else {
            System.out.println("Empleados disponibles: \n");
            for (Empleado e : empleados) {
                System.out.println(e.getNombre() + " " + e.getApellido() + ", CC: " + e.getCedula());
            }
        }
        System.out.println("\nPara asignar el empleado, ingresa su número de cédula.");
        while (true) {
        	String numeroCedula = getInput("\nNúmero de cédula: ");
            try {
                Empleado empleado = administrador.getGaleria().getEmpleado(numeroCedula);
                if (empleado != null) {
                    return empleado;
                } else {
                    System.out.println("No se encontró el empleado con la cédula proporcionada. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
	
	public void verificarComprador() {
	    HashMap<String, Subasta> subastas = administrador.getGaleria().getSubastas();
	    String fecha = getInputFecha("Ingresa la fecha de la subasta a iniciar (DD-MM-YYYY): ");
	    if (subastas.containsKey(fecha)) {
	        Subasta subasta = subastas.get(fecha);
	        Comprador comprador = seleccionarComprador();
	        if (comprador != null) {
	            if (subasta.addCompradorVerificado(comprador)) {
	                // Save the state after a successful addition
	                administrador.getGaleria().getCentralPersistencia().guardar(administrador.getGaleria());
	                System.out.println(comprador.getNombre() + " " + comprador.getApellido() +
	                                   " (CC: " + comprador.getCedula() + ") fue agregado exitosamente a la subasta del " + fecha + ".");
	            } else {
	                System.out.println("\nError: El comprador ya está verificado en la subasta del " + fecha + ".");
	            }
	        } else {
	            System.out.println("Todavía no hay compradores registrados.");
	            mostrarMenu();
	        }
	    } else {
	        System.out.println("No se encontró una subasta para la fecha indicada.");
	        mostrarMenu();
	    }
	}
	
	public Comprador seleccionarComprador() {
	    Collection<Usuario> usuarios = administrador.getGaleria().getUsuarios().values();
	    if (usuarios.isEmpty()) {
	        System.out.println("No hay compradores. Espera a que se registren.\n");
	        return null;
	    } else {
	        System.out.println("Compradores disponibles: \n");
	        for (Usuario u : usuarios) {
	            if (u instanceof Comprador) {
	                System.out.println(u.getNombre() + " " + u.getApellido() + ", CC: " + u.getCedula());
	            }
	        }
	    }
	    System.out.println("\nPara agregar a un comprador, ingresa su número de cédula.");
	    while (true) {
	        String numeroCedula = getInput("\nNúmero de cédula: ").trim();
	        for (Usuario u : usuarios) {
	            if (u instanceof Comprador && u.getCedula().equals(numeroCedula)) {
	                return (Comprador) u;
	            }
	        }
	        System.out.println("\nNo se encontró un comprador con la cédula proporcionada. Intente nuevamente.");
	    }
	}
	
	public void configurarCajero() {
	    System.out.println("\nSelecciona un empleado para que sea el cajero");
	    Empleado empleado = seleccionarEmpleado();

	    if (empleado != null) {
	        Cajero cajero = new Cajero(empleado.getNombre(), empleado.getApellido(),empleado.getLogin(), empleado.getPassword(),  empleado.getCedula(), "Cajero");
	        administrador.getGaleria().setCajero(cajero);
	        administrador.getGaleria().removeEmpleado(empleado);
	        administrador.getGaleria().getCentralPersistencia().guardar(administrador.getGaleria());
	        System.out.println("\nEmpleado configurado como Cajero exitosamente.");
	    } else {
	        System.out.println("No se pudo configurar el empleado como Cajero.");
	    }
	}

}
	




