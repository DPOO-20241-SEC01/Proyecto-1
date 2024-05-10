package Galeria;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import model.Galeria;
import model.inventario.Artista;
import model.inventario.Pieza;
import model.inventario.Pintura;
import model.usuarios.Administrador;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;
import model.ventas.Venta;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNuevosRequerimientos
{
	private Galeria galeria;
	private Administrador admin;
	private Pintura pintura;
	private Comprador comprador;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
	
	@BeforeEach
	void setUp( ) throws Exception
    {	galeria=new Galeria();
        admin=new Administrador("Juan", "Perez", "1123765872", "m.florezg2", "amarrillo123", "Administrador");
        galeria.setAdministrador(admin);
        comprador = new Comprador("Mateo", "salinas", "1923765802", "m.salinas", "verde123", "Comprador");
        galeria.addUsuario(comprador);
        pintura=new Pintura("879586938", "Exhibición", "Macondo", "1990", "Italia", "papa", 5000, "Pintura",50, 30);
        galeria.addPiezasDisponibles(pintura);
        String peticion =null;
        comprador.comprarPieza("879586938", "Exhibición", 5000, peticion, "Efectivo");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
	
	@Test
	public void historialDeUnComprador() {
		double valorcoleccion=5000;
		String titulo=pintura.getTituloPieza();
		ArrayList<Venta>ventas=new ArrayList<>(galeria.getVentas().values());
		String fecha=null;
		for (Venta venta:ventas) {
			if(venta.getComprador().equals(comprador)) {
				fecha=venta.getFecha();
			}
		}
		String mensaje1="Esta pieza: "+titulo+" fue comprada en "+fecha+" y actualmente es dueño";
		String mensaje2="El valor de la colección es "+ String.valueOf(valorcoleccion);
        galeria.getAdminstrador().historialDeUnComprador(comprador.getLogin());
        String expectedOutput = mensaje1 + System.lineSeparator() +
        						mensaje2 + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());

    }
}