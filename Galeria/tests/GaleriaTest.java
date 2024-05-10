package Galeria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Galeria;
import model.inventario.Fotografia;
import model.inventario.Impresion;
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
import model.inventario.Pieza;

public class GaleriaTest {
	private Comprador comprador;
	private Subasta subasta;
	private Escultura escultura;
    private Galeria galeria;
    private Fotografia fotografia;
    private Impresion impresion;
    private Administrador admin;
    private Empleado operador;



    @BeforeEach
    public void setUp() {
        galeria = new Galeria(false);
        comprador = new Comprador("Nombre", "Apellido", "1234567890", "login", "password", "Comprador");

        comprador.setGaleria(galeria);
        HashMap<String, Oferta> ofertasARevisar = new HashMap<String,Oferta>();
        ArrayList<Consignacion> consignacionesARevisar = new ArrayList<Consignacion>();
        Administrador admin = new Administrador("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Administrador",  ofertasARevisar, consignacionesARevisar, galeria, null);        operador = new Empleado("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Empleado");

        subasta = new Subasta("FechaSubasta", operador , admin);
        admin.setGaleria(galeria);
        galeria.setAdministrador(admin);

        // Agregar piezas al inventario
        escultura = new Escultura("128", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Escultura", "Dimensiones", "Peso", "Materiales", true, null);
        fotografia = new Fotografia("125", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Fotografía", 10, 20, "Fotografia", "Resolucion", null);
        impresion = new Impresion("123", "Ubicacion", "Titulo", "Anio", "Lugar", "Artista", 100, "Impresión", "TipoHoja", 30, 40, null);

        // Agregar piezas al inventario de la galería
        HashMap<String, Pieza> piezasEscultura = galeria.getPiezasInventario().get("Escultura");
        piezasEscultura.put(escultura.getIdPieza(), escultura);

        HashMap<String, Pieza> piezasFotografia = galeria.getPiezasInventario().get("Fotografía");
        piezasFotografia.put(fotografia.getIdPieza(), fotografia);

        HashMap<String, Pieza> piezasImpresion = galeria.getPiezasInventario().get("Impresión");
        piezasImpresion.put(impresion.getIdPieza(), impresion);
    }


    @Test
    public void testRegistrarHistoriaPiezaEscultura() {
        // Datos de prueba
        String idPieza = "128"; // ID de la escultura
        String comprador = "Pedro";
        int precioVenta = 1500;
        String tipoPieza = "Escultura"; // Tipo de pieza

        // Obtener la escultura del inventario de la galería
        Escultura escultura = (Escultura) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la escultura no sea nula
        assertNotNull(escultura);

        // Obtener la historia actual de la escultura
        ArrayList<HashMap<String, Object>> historiaActual = escultura.getHistoria();

        // Si la historia actual es nula, inicializarla
        if (historiaActual == null) {
            historiaActual = new ArrayList<>();
        }

        // Obtener la cantidad de registros en la historia actual
        int cantidadRegistrosAntes = historiaActual.size();

        // Crear un nuevo registro para la historia de la escultura vendida
        HashMap<String, Object> nuevoRegistro = new HashMap<>();
        nuevoRegistro.put("comprador", comprador);
        nuevoRegistro.put("precioVenta", precioVenta);
        nuevoRegistro.put("fechaVenta", LocalDate.now().toString());

        // Agregar el nuevo registro a la historia de la escultura
        historiaActual.add(nuevoRegistro);

        // Establecer la nueva historia en la escultura
        escultura.setHistoria(historiaActual);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = escultura.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia de la escultura
        assertEquals(cantidadRegistrosAntes + 1, cantidadRegistrosDespues);

        // Verificar que el último registro añadido sea correcto
        HashMap<String, Object> ultimoRegistro = escultura.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, ultimoRegistro.get("comprador"));
        assertEquals(precioVenta, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), ultimoRegistro.get("fechaVenta"));
    }

    @Test
    public void testRegistrarHistoriaPiezaImpresion() {
        // Datos de prueba
        String idPieza = "123"; // ID de la impresión
        String comprador = "María";
        int precioVenta = 800;
        String tipoPieza = "Impresión"; // Tipo de pieza

        // Obtener la impresión del inventario de la galería
        Impresion impresion = (Impresion) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la impresión no sea nula
        assertNotNull(impresion);

        // Obtener la historia actual de la impresión
        ArrayList<HashMap<String, Object>> historiaActual = impresion.getHistoria();

        // Si la historia actual es nula, inicializarla
        if (historiaActual == null) {
            historiaActual = new ArrayList<>();
        }

        // Obtener la cantidad de registros en la historia actual
        int cantidadRegistrosAntes = historiaActual.size();

        // Crear un nuevo registro para la historia de la impresión vendida
        HashMap<String, Object> nuevoRegistro = new HashMap<>();
        nuevoRegistro.put("comprador", comprador);
        nuevoRegistro.put("precioVenta", precioVenta);
        nuevoRegistro.put("fechaVenta", LocalDate.now().toString());

        // Agregar el nuevo registro a la historia de la impresión
        historiaActual.add(nuevoRegistro);

        // Establecer la nueva historia en la impresión
        impresion.setHistoria(historiaActual);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = impresion.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia de la impresión
        assertEquals(cantidadRegistrosAntes + 1, cantidadRegistrosDespues);

        // Verificar que el último registro añadido sea correcto
        HashMap<String, Object> ultimoRegistro = impresion.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, ultimoRegistro.get("comprador"));
        assertEquals(precioVenta, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), ultimoRegistro.get("fechaVenta"));
    }

    @Test
    public void testRegistrarHistoriaPiezaFotografia() {
        // Datos de prueba
        String idPieza = "125"; // ID de la fotografía
        String comprador = "Laura";
        int precioVenta = 1200;
        String tipoPieza = "Fotografía"; // Tipo de pieza

        // Obtener la fotografía del inventario de la galería
        Fotografia fotografia = (Fotografia) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la fotografía no sea nula
        assertNotNull(fotografia);

        // Obtener la historia actual de la fotografía
        ArrayList<HashMap<String, Object>> historiaActual = fotografia.getHistoria();

        // Si la historia actual es nula, inicializarla
        if (historiaActual == null) {
            historiaActual = new ArrayList<>();
        }

        // Obtener la cantidad de registros en la historia actual
        int cantidadRegistrosAntes = historiaActual.size();

        // Crear un nuevo registro para la historia de la fotografía vendida
        HashMap<String, Object> nuevoRegistro = new HashMap<>();
        nuevoRegistro.put("comprador", comprador);
        nuevoRegistro.put("precioVenta", precioVenta);
        nuevoRegistro.put("fechaVenta", LocalDate.now().toString());

        // Agregar el nuevo registro a la historia de la fotografía
        historiaActual.add(nuevoRegistro);

        // Establecer la nueva historia en la fotografía
        fotografia.setHistoria(historiaActual);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = fotografia.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia de la fotografía
        assertEquals(cantidadRegistrosAntes + 1, cantidadRegistrosDespues);

        // Verificar que el último registro añadido sea correcto
        HashMap<String, Object> ultimoRegistro = fotografia.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, ultimoRegistro.get("comprador"));
        assertEquals(precioVenta, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), ultimoRegistro.get("fechaVenta"));
    }
    @Test
    public void testRegistrarHistoriaPiezaFotografiaConHistorial() {
        // Datos de prueba
        String idPieza = "125"; // ID de la fotografía
        String comprador = "Laura";
        int precioVenta = 1200;
        String tipoPieza = "Fotografía"; // Tipo de pieza

        // Obtener la fotografía del inventario de la galería
        Fotografia fotografia = (Fotografia) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la fotografía no sea nula
        assertNotNull(fotografia);

        // Agregar un historial de piezas
        for (int i = 0; i < 3; i++) {
            // Obtener la historia actual de la fotografía
            ArrayList<HashMap<String, Object>> historiaActual = fotografia.getHistoria();

            // Si la historia actual es nula, inicializarla
            if (historiaActual == null) {
                historiaActual = new ArrayList<>();
            }

            // Crear un nuevo registro para la historia de la fotografía vendida
            HashMap<String, Object> nuevoRegistro = new HashMap<>();
            nuevoRegistro.put("comprador", "Comprador " + i);
            nuevoRegistro.put("precioVenta", precioVenta);
            nuevoRegistro.put("fechaVenta", LocalDate.now().minusDays(i).toString());

            // Agregar el nuevo registro a la historia de la fotografía
            historiaActual.add(nuevoRegistro);

            // Establecer la nueva historia en la fotografía
            fotografia.setHistoria(historiaActual);
        }

        // Obtener la cantidad de registros en la historia
        int cantidadRegistros = fotografia.getHistoria().size();

        // Verificar que haya al menos dos registros en la historia
        assertTrue(cantidadRegistros >= 2);

        // Obtener el registro más reciente
        HashMap<String, Object> ultimoRegistro = fotografia.getHistoria().get(cantidadRegistros - 1);

        // Verificar que el último registro añadido sea correcto
        assertEquals("Comprador 2", ultimoRegistro.get("comprador"));
        assertEquals(1200, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().minusDays(2).toString(), ultimoRegistro.get("fechaVenta"));

        // Llamamos al método a testear
        galeria.registrarHistoriaPieza(idPieza, comprador, precioVenta, tipoPieza);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = fotografia.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia
        assertEquals(cantidadRegistros + 1, cantidadRegistrosDespues);

        // Verificar que el nuevo registro sea el último
        HashMap<String, Object> nuevoRegistro = fotografia.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, nuevoRegistro.get("comprador"));
        assertEquals(precioVenta, nuevoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), nuevoRegistro.get("fechaVenta"));
    }
    @Test
    public void testRegistrarHistoriaPiezaEsculturaConHistorial() {
        // Datos de prueba
        String idPieza = "128"; // ID de la escultura
        String comprador = "Ana";
        int precioVenta = 1500;
        String tipoPieza = "Escultura"; // Tipo de pieza

        // Obtener la escultura del inventario de la galería
        Escultura escultura = (Escultura) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la escultura no sea nula
        assertNotNull(escultura);

        // Agregar un historial de piezas
        for (int i = 0; i < 3; i++) {
            // Obtener la historia actual de la escultura
            ArrayList<HashMap<String, Object>> historiaActual = escultura.getHistoria();

            // Si la historia actual es nula, inicializarla
            if (historiaActual == null) {
                historiaActual = new ArrayList<>();
            }

            // Crear un nuevo registro para la historia de la escultura vendida
            HashMap<String, Object> nuevoRegistro = new HashMap<>();
            nuevoRegistro.put("comprador", "Comprador " + i);
            nuevoRegistro.put("precioVenta", precioVenta);
            nuevoRegistro.put("fechaVenta", LocalDate.now().minusDays(i).toString());

            // Agregar el nuevo registro a la historia de la escultura
            historiaActual.add(nuevoRegistro);

            // Establecer la nueva historia en la escultura
            escultura.setHistoria(historiaActual);
        }

        // Obtener la cantidad de registros en la historia
        int cantidadRegistros = escultura.getHistoria().size();

        // Verificar que haya al menos dos registros en la historia
        assertTrue(cantidadRegistros >= 2);

        // Obtener el registro más reciente
        HashMap<String, Object> ultimoRegistro = escultura.getHistoria().get(cantidadRegistros - 1);

        // Verificar que el último registro añadido sea correcto
        assertEquals("Comprador 2", ultimoRegistro.get("comprador"));
        assertEquals(1500, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().minusDays(2).toString(), ultimoRegistro.get("fechaVenta"));

        // Llamamos al método a testear
        galeria.registrarHistoriaPieza(idPieza, comprador, precioVenta, tipoPieza);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = escultura.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia
        assertEquals(cantidadRegistros + 1, cantidadRegistrosDespues);

        // Verificar que el nuevo registro sea el último
        HashMap<String, Object> nuevoRegistro = escultura.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, nuevoRegistro.get("comprador"));
        assertEquals(precioVenta, nuevoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), nuevoRegistro.get("fechaVenta"));
    }
    @Test
    public void testRegistrarHistoriaPiezaImpresionConHistorial() {
        // Datos de prueba
        String idPieza = "123"; // ID de la impresión
        String comprador = "Pedro";
        int precioVenta = 800;
        String tipoPieza = "Impresión"; // Tipo de pieza

        // Obtener la impresión del inventario de la galería
        Impresion impresion = (Impresion) galeria.getPiezasInventario().get(tipoPieza).get(idPieza);

        // Verificar que la impresión no sea nula
        assertNotNull(impresion);

        // Agregar un historial de piezas
        for (int i = 0; i < 3; i++) {
            // Obtener la historia actual de la impresión
            ArrayList<HashMap<String, Object>> historiaActual = impresion.getHistoria();

            // Si la historia actual es nula, inicializarla
            if (historiaActual == null) {
                historiaActual = new ArrayList<>();
            }

            // Crear un nuevo registro para la historia de la impresión vendida
            HashMap<String, Object> nuevoRegistro = new HashMap<>();
            nuevoRegistro.put("comprador", "Comprador " + i);
            nuevoRegistro.put("precioVenta", precioVenta);
            nuevoRegistro.put("fechaVenta", LocalDate.now().minusDays(i).toString());

            // Agregar el nuevo registro a la historia de la impresión
            historiaActual.add(nuevoRegistro);

            // Establecer la nueva historia en la impresión
            impresion.setHistoria(historiaActual);
        }

        // Obtener la cantidad de registros en la historia
        int cantidadRegistros = impresion.getHistoria().size();

        // Verificar que haya al menos dos registros en la historia
        assertTrue(cantidadRegistros >= 2);

        // Obtener el registro más reciente
        HashMap<String, Object> ultimoRegistro = impresion.getHistoria().get(cantidadRegistros - 1);

        // Verificar que el último registro añadido sea correcto
        assertEquals("Comprador 2", ultimoRegistro.get("comprador"));
        assertEquals(800, ultimoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().minusDays(2).toString(), ultimoRegistro.get("fechaVenta"));

        // Llamamos al método a testear
        galeria.registrarHistoriaPieza(idPieza, comprador, precioVenta, tipoPieza);

        // Obtener la cantidad de registros en la historia después de agregar el nuevo registro
        int cantidadRegistrosDespues = impresion.getHistoria().size();

        // Verificar que se haya agregado un nuevo registro a la historia
        assertEquals(cantidadRegistros + 1, cantidadRegistrosDespues);

        // Verificar que el nuevo registro sea el último
        HashMap<String, Object> nuevoRegistro = impresion.getHistoria().get(cantidadRegistrosDespues - 1);
        assertEquals(comprador, nuevoRegistro.get("comprador"));
        assertEquals(precioVenta, nuevoRegistro.get("precioVenta"));
        assertEquals(LocalDate.now().toString(), nuevoRegistro.get("fechaVenta"));
    }


}