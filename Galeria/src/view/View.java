package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Galeria;
import model.inventario.Pieza;

public abstract class View {

	/**
	 * Atributos
	 */
	protected Scanner scanner; 
	
	protected Galeria galeria;
	
	/**
	 * Constructor
	 */
	public View() {
		this.scanner = new Scanner(System.in);
	}
	

	/**
	 * Mostrar el menú específico del view
	 */
	public void mostrarMenu() {
	}
	
	
	/**
	 * Obtener input desde la consola.
	 * @param prompt El mensaje que solicita la entrada.
	 * @return La entrada del usuario como una cadena de caracteres.
	 */
	public String getInput(String prompt) {
		System.out.println(prompt);
        String input = scanner.nextLine();
        return input;
    }
    
	
	/**
	 * Convertir la primera letra de cualquier entrada en mayúscula.
	 * @param input La cadena de caracteres a capitalizar.
	 * @return La cadena de caracteres con la primera letra en mayúscula.
	 */
	public String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
	
	
	/**
	 * Mostrar la información de una pieza.
	 * @param pieza La pieza de inventario de la cual mostrar la información.
	 * @return Una cadena de caracteres con la información de la pieza.
	 */
	public String mostrarPieza(Pieza pieza) {
		String infoPieza = "ID: " + pieza.getIdPieza() + " --> " + pieza.getTituloPieza() + ". " + pieza.getNombreArtista() + ". [" + pieza.getTipoPieza() + "]. Precio: $" + pieza.getCostoFijo();
		return infoPieza;
	}
    
	
	/**
	 * Obtener entrada de tipo fecha.
	 * @param mensaje El mensaje que solicita la entrada.
	 * @return La entrada del usuario como una cadena de caracteres representando la fecha.
	 * @exception IllegalArgumentException Si la fecha ingresada es inválida.
	 * @exception DateTimeParseException Si el formato de la fecha ingresada es inválido.
	 */
	public String getInputFecha(String mensaje) {
		while (true) {
            System.out.println(mensaje);
            String input = scanner.nextLine().trim();
            try {
                LocalDate date;
                if (input.matches("\\d{4}")) {
                    int year = Integer.parseInt(input);
                    if (year > 2024) {
                        throw new IllegalArgumentException("\nEl año no puede ser mayor a 2024.");
                    }
                    date = LocalDate.of(year, 1, 1); 
                } else {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    if (date.isBefore(LocalDate.now())) {
                        throw new IllegalArgumentException("\nLa fecha no puede ser anterior a la fecha actual.");
                    }
                }
                return input; 
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
	
	
	/**
	 * Obtener entrada de tipo entero.
	 * @param mensaje El mensaje que solicita la entrada.
	 * @return El entero ingresado por el usuario.
	 * @exception InputMismatchException Si el valor ingresado no es un número.
	 */
	public int getInputInt(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero; // Return the successfully parsed integer
            } catch (InputMismatchException e) {
            	System.out.println("La información ingresada no es un número.");
                scanner.nextLine();
            }
        }
    }
	
	
	/**
	 * Manejar entrada de sí o no.
	 * @param mensaje El mensaje que solicita la entrada.
	 * @return Verdadero si la respuesta es "Y" (sí), falso si es "N" (no).
	 */
	public boolean getInputY_N(String mensaje) {
        System.out.println(mensaje + " [Y/N]");
        while (true) {
            String action = getInput("Ingresa una opción:").trim();
            if (action.equalsIgnoreCase("Y")) {
                return true;
            } else if (action.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Opción inválida. Ingresa 'Y' para sí o 'N' para no.");
            }
        }
    }
	
	/**
	 * Cerrar el Scanner.
	 */
	public void close() {
        scanner.close();
    }

	
}
