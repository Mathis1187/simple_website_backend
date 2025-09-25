package mathis.simple_website_backend;


import mathis.simple_website_backend.controller.UserController;
import mathis.simple_website_backend.controller.SeriesController;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.UserRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SeriesControllerTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SeriesController seriesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSerie() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setTitre("Titre 1");
        s1.setGenre("Genre 1");
        s1.setNote(8.1f);
        s1.setNbEpisodes(32);

        seriesController.createSeries(s1);
        assert(s1.getTitre().equals("Titre 1"));
    }

    @Test
    void testUpdateSerie() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setTitre("Titre 1");
        s1.setGenre("Genre 1");
        s1.setNote(8.1f);
        s1.setNbEpisodes(32);

        when(seriesRepository.findById(1L)).thenReturn(java.util.Optional.of(s1));
        when(seriesRepository.save(s1)).thenReturn(s1);

        Series updatedSerie = new Series();
        updatedSerie.setTitre("Titre 1 Modifié");
        updatedSerie.setGenre("Genre 1");
        updatedSerie.setNote(9.0f);
        updatedSerie.setNbEpisodes(35);

        seriesController.updateSeries(1, updatedSerie);

        assertEquals("Titre 1 Modifié", s1.getTitre());
        assertEquals(9.0f, s1.getNote());
        assertEquals(35, s1.getNbEpisodes());
    }

    @Test
    void testDeleteSerie() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setTitre("Titre 1");
        s1.setGenre("Genre 1");
        s1.setNote(8.1f);
        s1.setNbEpisodes(32);

        when(seriesRepository.findById(1L)).thenReturn(java.util.Optional.of(s1));

        seriesController.deleteSeries(1L);

        Mockito.verify(seriesRepository).deleteById(1L);
    }

    @Test
    void testSearchSerieByGenre() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setTitre("Titre 1");
        s1.setGenre("Genre 1");
        s1.setNote(8.1f);
        s1.setNbEpisodes(32);

        Series s2 = new Series();
        s2.setId(2);
        s2.setTitre("Titre 2");
        s2.setGenre("Genre 2");
        s2.setNote(9.0f);
        s2.setNbEpisodes(32);

        Series s3 = new Series();
        s3.setId(3);
        s3.setTitre("Titre 3");
        s3.setGenre("Genre 1");
        s3.setNote(8.5f);
        s3.setNbEpisodes(28);

        // Mock du repository
        when(seriesRepository.findSeriesByGenreIgnoreCase("Genre 1")).thenReturn(List.of(s1, s3));

        // Appel du controller
        List<Series> result = seriesController.search("Genre 1");

        // Vérifications
        assertEquals(2, result.size());
        assertEquals("Titre 1", result.get(0).getTitre());
        assertEquals("Titre 3", result.get(1).getTitre());
    }




}
