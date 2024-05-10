package usuariosTests;
import model.inventario.Escultura;
import model.inventario.Fotografia;
import model.inventario.Impresion;
import model.usuarios.Administrador;
import model.inventario.Pieza;
import model.inventario.Artista;
import model.Galeria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class usuarioTest {
    private Galeria galeria;
    private Administrador admin;
    
    @BeforeEach
    public void setUp() {
        galeria = new Galeria(false);
        admin = new Administrador("Nombre1", "Apellido1", "1234567890", "login1", "contraseña", "Administrador", new HashMap<>(), new ArrayList<>(), galeria, null);
        galeria.setAdministrador(admin);
        admin.setGaleria(galeria);
       

        // Create artists
        Artista artista1 = new Artista("Artista1");
        galeria.addArtista(artista1);

        // Create artworks
        Escultura escultura = new Escultura("128", "Ubicacion1", "Escultura1", "2020", "Lugar1", "Artista1", 100, "Escultura", "1x2x3", "10kg", "Bronze", true, null);
        Fotografia fotografia = new Fotografia("125", "Ubicacion2", "Fotografia1", "2021", "Lugar2", "Artista1", 100, "Fotografía", 10, 20, "Tipo","High-res",  null);
        Impresion impresion = new Impresion("123", "Ubicacion3", "Impresion1", "2019", "Lugar3", "Artista1", 100, "Impresion", "Glossy", 30, 40, null);

        escultura.addArtista(artista1);
        fotografia.addArtista(artista1);
        impresion.addArtista(artista1);
        
        galeria.addPiezasPasadas(escultura);
        galeria.addPiezasPasadas(fotografia);
        galeria.addPiezasPasadas(impresion);
    }

    @Test
    public void historiaArtistaTest() {
        HashMap<String, ArrayList<Pieza>> historia = admin.procesarHistoriaArtista("Artista1");
        System.out.println(historia);
        assertNotNull(historia, "La historia es nula");
        assertTrue(historia.containsKey("Artista1"), "La historia debe contener Artista1.");
        assertEquals(3, historia.get("Artista1").size(), "Artista1 debe tener tres piezas en su historia.");
    }
}


