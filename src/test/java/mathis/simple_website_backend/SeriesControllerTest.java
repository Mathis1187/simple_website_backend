package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.SeriesController;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.services.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SeriesControllerTest {

    @Mock
    private SeriesService seriesService;

    @InjectMocks
    private SeriesController seriesController;

    @BeforeEach
    void setUp() {
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

        when(seriesService.createSeries(s1)).thenReturn(s1);

        Series result = seriesController.createSeries(s1);
        assertEquals("Titre 1", result.getTitre());
        assertEquals(8.1f, result.getNote());
        assertEquals(32, result.getNbEpisodes());

        verify(seriesService, times(1)).createSeries(s1);
    }

    @Test
    void testUpdateSerie() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setTitre("Titre 1 Modifié");
        s1.setGenre("Genre 1");
        s1.setNote(9.0f);
        s1.setNbEpisodes(35);

        when(seriesService.updateSeries(1L, s1)).thenReturn(java.util.Optional.of(s1));

        Series result = seriesController.updateSeries(1, s1).getBody();

        assertEquals("Titre 1 Modifié", result.getTitre());
        assertEquals(9.0f, result.getNote());
        assertEquals(35, result.getNbEpisodes());

        verify(seriesService, times(1)).updateSeries(1L, s1);
    }

    @Test
    void testDeleteSerie() {
        doNothing().when(seriesService).deleteSeries(1L);

        seriesController.deleteSeries(1L);

        verify(seriesService, times(1)).deleteSeries(1L);
    }

    @Test
    void testSearchSerieByGenre() {
        Series s1 = new Series();
        s1.setTitre("Titre 1");
        Series s2 = new Series();
        s2.setTitre("Titre 3");

        when(seriesService.searchByGenre("Genre 1")).thenReturn(List.of(s1, s2));

        List<Series> result = seriesController.search("Genre 1");

        assertEquals(2, result.size());
        assertEquals("Titre 1", result.get(0).getTitre());
        assertEquals("Titre 3", result.get(1).getTitre());

        verify(seriesService, times(1)).searchByGenre("Genre 1");
    }
}
