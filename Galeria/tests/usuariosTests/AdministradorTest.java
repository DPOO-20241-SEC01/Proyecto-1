package usuariosTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import model.Galeria;
import model.inventario.Artista;
import model.inventario.Escultura;
import model.inventario.Fotografia;
import model.inventario.Impresion;
import model.inventario.Pieza;
import model.usuarios.Administrador;
import model.usuarios.Comprador;
import model.usuarios.Empleado;
import model.ventas.Consignacion;
import model.ventas.Oferta;
import model.ventas.Subasta;

public class AdministradorTest {

    private Galeria galeria;
    private Fotografia fotografia;
    private Comprador comprador;
    private Escultura escultura;
    private Subasta subasta;
    private Impresion impresion;
    private Empleado operador;
    private Administrador admin;

    @BeforeEach
    public void setUp() {
        galeria = new Galeria(false);
        comprador = new Comprador("Nombre", "Apellido", "1234567890", "login", "password", "Comprador");
        
        comprador.setGaleria(galeria);
        
        HashMap<String, Oferta> ofertasARevisar = new HashMap<>();
        ArrayList<Consignacion> consignacionesARevisar = new ArrayList<>();
        admin = new Administrador("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Administrador",  ofertasARevisar, consignacionesARevisar, galeria, null);        
        operador = new Empleado("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Empleado");

        admin.setGaleria(galeria);
        galeria.setAdministrador(admin);
        
        // Crear artistas
        Artista artista1 = new Artista("Artista1");
        Artista artista2 = new Artista("Artista2");
        Artista colectivo1 = new Artista("Colectivo1");
        Artista colectivo2 = new Artista("Colectivo2");
        
        galeria.addArtista(artista1);
        galeria.addArtista(artista2);
        galeria.addArtista(colectivo1);
        galeria.addArtista(colectivo2);
        
        // Agregar piezas al inventario
        escultura = new Escultura("128", "Ubicacion", "Titulo", "Anio", "Lugar", null, 100, "Escultura", "Dimensiones", "Peso", "Materiales", true, null);
        galeria.addPiezasDisponibles(escultura);

        fotografia = new Fotografia("125", "Ubicacion", "Titulo", "Anio", "Lugar", null, 100, "Fotografía", 10, 20, "Fotografia", "Resolucion", null);
        impresion = new Impresion("123", "Ubicacion", "Titulo", "Anio", "Lugar", null, 100, "Impresión", "TipoHoja", 30, 40, null);
        
        // Agregar piezas al inventario
        HashMap<String, Pieza> piezasEscultura = galeria.getPiezasInventario().get("Escultura");
        piezasEscultura.put(escultura.getIdPieza(), escultura);

        HashMap<String, Pieza> piezasFotografia = galeria.getPiezasInventario().get("Fotografía");
        piezasFotografia.put(fotografia.getIdPieza(), fotografia);

        HashMap<String, Pieza> piezasImpresion = galeria.getPiezasInventario().get("Impresión");
        piezasImpresion.put(impresion.getIdPieza(), impresion);
    }

    @Test
    public void testAgregarPiezaAArtista_ColectivoYArtistaIndividual() {
        admin.agregarPiezaAArtista("128", "Escultura", "Artista1", null, false);
        assertEquals(1, galeria.getPiezaPorID("Escultura", "128").getArtistas().size());
        assertEquals("Artista1", galeria.getPiezaPorID("Escultura", "128").getArtistas().get(0).getNombre());
    }

    @Test
    public void testAgregarPiezaAArtista_DosColectivos() {
        admin.agregarPiezaAArtista("128", "Escultura", "Colectivo2", "Colectivo1",true);
        
        assertEquals(2, galeria.getPiezaPorID("Escultura", "128").getArtistas().size());
        assertEquals("Colectivo2", galeria.getPiezaPorID("Escultura", "128").getArtistas().get(0).getNombre());
        assertEquals("Colectivo2", galeria.getPiezaPorID("Escultura", "128").getArtistas().get(1).getNombre());
    }
}
