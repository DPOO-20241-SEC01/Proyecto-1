package usuariosTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Galeria;
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

public class CompradorTest {
	private Galeria galeria;
	private Fotografia fotografia;
    private Comprador comprador;
    private Escultura escultura;
    private Subasta subasta;
    private Impresion impresion;

    @BeforeEach
    public void setUp() {
        galeria = new Galeria(false);
        comprador = new Comprador("Nombre", "Apellido", "1234567890", "login", "password", "Comprador");
        HashMap<String, Oferta> ofertasARevisar = new HashMap<String,Oferta>();
        ArrayList<Consignacion> consignacionesARevisar = new ArrayList<Consignacion>();
        comprador.setGaleria(galeria);
        Administrador admin = new Administrador("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Administrador",  ofertasARevisar, consignacionesARevisar, galeria, null);
        Empleado operador = new Empleado("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Empleado");

        subasta = new Subasta("FechaSubasta", operador , admin);
        admin.setGaleria(galeria);
        galeria.setAdministrador(admin);

        // Agregar piezas al inventario
        escultura = new Escultura("128", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Escultura", "Dimensiones", "Peso", "Materiales", true, null);
       galeria.addPiezasDisponibles(escultura);
        

        fotografia = new Fotografia("125", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Fotografía", 10, 20, "Fotografia", "Resolucion", null);
       

        impresion = new Impresion("123", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Impresion", "TipoHoja", 30, 40, null);
     // Agregar piezas al inventario
        HashMap<String, Pieza> piezasEscultura = galeria.getPiezasInventario().get("Escultura");
        piezasEscultura.put(escultura.getIdPieza(), escultura);

        HashMap<String, Pieza> piezasFotografia = galeria.getPiezasInventario().get("Fotografía");
        piezasFotografia.put(fotografia.getIdPieza(), fotografia);

        HashMap<String, Pieza> piezasImpresion = galeria.getPiezasInventario().get("Impresión");
        piezasImpresion.put(impresion.getIdPieza(), impresion);

    }

    @Test
    
    public void testHacerOfertaSubastaPorEscultura() {
        // Preparación de datos de prueba
        comprador.setPiezaSubastaEnCurso(escultura);
        comprador.setSubastaEnCurso(subasta);

        // Ejecución del método a probar
        comprador.hacerOfertaSubasta(150, "MetodoPago");

        // Verificación de resultados
        assertTrue(comprador.getSubastaEnCurso().getOfertas().size() > 0); // Verifica que la subasta tenga al menos una oferta
        assertTrue(comprador.getOfertasPendientes().size() > 0); // Verifica que el comprador tenga al menos una oferta pendiente
    }

    @Test
    public void testHacerOfertaSubastaPorFotografia() {
        // Preparación de datos de prueba
        comprador.setPiezaSubastaEnCurso(fotografia);
        comprador.setSubastaEnCurso(subasta);

        // Ejecución del método a probar
        comprador.hacerOfertaSubasta(150, "MetodoPago");

        // Verificación de resultados
        assertTrue(comprador.getSubastaEnCurso().getOfertas().size() > 0); // Verifica que la subasta tenga al menos una oferta
        assertTrue(comprador.getOfertasPendientes().size() > 0); // Verifica que el comprador tenga al menos una oferta pendiente
    }
    @Test
    public void testHacerOfertaSubastaPorImpresion() {
        // Preparación de datos de prueba
        comprador.setPiezaSubastaEnCurso(impresion);
        comprador.setSubastaEnCurso(subasta);

        // Ejecución del método a probar
        comprador.hacerOfertaSubasta(150, "MetodoPago");

        // Verificación de resultados
        assertTrue(comprador.getSubastaEnCurso().getOfertas().size() > 0); // Verifica que la subasta tenga al menos una oferta
        assertTrue(comprador.getOfertasPendientes().size() > 0); // Verifica que el comprador tenga al menos una oferta pendiente
    }


    @Test
    public void testComprarEscultura() {
        Oferta oferta = new Oferta (escultura, comprador, 1010, "peticion", "efectivo");
        oferta.setPieza(escultura);
        
        // Ejecución del método a probar
        comprador.comprarPieza("Escultura", "128", 150, "Peticion", "MetodoPago");

        // Verificación de resultados
        assertTrue(galeria.getAdminstrador().getOfertasARevisar().containsKey(escultura.getIdPieza()), "La pieza no se registró en ofertas a revisar");
        assertNotNull(escultura.getPropietario(),"La pieza es nula y no tiene propietario");
        assertEquals(comprador, escultura.getPropietario(), "El propietario no coincide con el comprador de la pieza");
    }


    @Test
    public void testComprarFotografia() {
    	
    	Oferta oferta = new Oferta (fotografia, comprador, 1010, "peticion", "efectivo");
        oferta.setPieza(fotografia);
        
        
        comprador.comprarPieza("Fotografía", "125", 200, "Peticion", "MetodoPago");

        // Verificación de resultados

        assertTrue(galeria.getAdminstrador().getOfertasARevisar().containsKey(fotografia.getIdPieza()), "La pieza no se registró en ofertas a revisar");
        assertNotNull(fotografia.getPropietario(),"La pieza es nula y no tiene propietario");
        assertEquals(comprador, fotografia.getPropietario(), "El propietario no coincide con el comprador de la pieza");
    }

    @Test
    public void testComprarImpresion() {
    	
    	
    	
        // Preparación de datos de prueba
        
        
    	Oferta oferta = new Oferta (impresion, comprador, 1010, "Peticion", "efectivo");
    
        oferta.setPieza(impresion);
       
        
        
        
        comprador.comprarPieza("Impresión", "123", 1010, "Peticion", "efectivo");
        

        // Verificación de resultados
        assertTrue(galeria.getAdminstrador().getOfertasARevisar().containsKey(impresion.getIdPieza()), "La pieza no se registró en ofertas a revisar");
        assertNotNull(impresion.getPropietario(), "La pieza es nula y no tiene propietario");
        assertEquals(comprador, impresion.getPropietario(), "El propietario no coincide con el comprador de la pieza");
    }
}
