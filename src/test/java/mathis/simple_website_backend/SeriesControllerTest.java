package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.SeriesController;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.services.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<Series> response = seriesController.updateSeries(1, s1);
        Series result = response.getBody();

        assertEquals("Titre 1 Modifié", result.getTitre());
        assertEquals(9.0f, result.getNote());
        assertEquals(35, result.getNbEpisodes());

        verify(seriesService, times(1)).updateSeries(1L, s1);
    }

    @Test
    void testDeleteSerie() {
        doNothing().when(seriesService).deleteSeries(1L);

        ResponseEntity<Series> response = seriesController.deleteSeries(1L);

        assertEquals(200, response.getStatusCodeValue());
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

    @Test
    void testGetAllSeries() {
        Series s1 = new Series();
        Series s2 = new Series();
        when(seriesService.getAllSeries()).thenReturn(List.of(s1, s2));

        List<Series> result = seriesController.getAllSeries();

        assertEquals(2, result.size());
        verify(seriesService, times(1)).getAllSeries();
    }

    @Test
    void testFindByNbEpisodesGreaterThanEqual() {
        Series s1 = new Series();
        s1.setNbEpisodes(5);
        Series s2 = new Series();
        s2.setNbEpisodes(10);
        when(seriesService.findByNbEpisodesGreaterThanEqual(6)).thenReturn(List.of(s2));

        List<Series> result = seriesController.findByNbEpisodesGreaterThanEqual(6);

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getNbEpisodes());
        verify(seriesService, times(1)).findByNbEpisodesGreaterThanEqual(6);
    }

    @Test
    void testGetSerieByTitre() {
        Series s = new Series();
        s.setTitre("TitreTest");
        when(seriesService.getSerieByTitre("TitreTest")).thenReturn(s);

        Series result = seriesController.getSerieByTitre("TitreTest");

        assertEquals("TitreTest", result.getTitre());
        verify(seriesService, times(1)).getSerieByTitre("TitreTest");
    }

    @Test
    void testUpdateSeriesRatings() {
        Series s = new Series();
        s.setNote(9.5f);
        when(seriesService.updateSerieRatingById(1L, s)).thenReturn(java.util.Optional.of(s));

        ResponseEntity<Series> response = seriesController.updateSeriesRatings(1, s);
        Series result = response.getBody();

        assertEquals(9.5f, result.getNote());
        verify(seriesService, times(1)).updateSerieRatingById(1L, s);
    }

    @Test
    void testGetTrending() {
        Series s1 = new Series();
        Series s2 = new Series();
        when(seriesService.getAllSeries()).thenReturn(List.of(s1, s2));

        List<Series> result = seriesController.getTrending();

        assertEquals(2, result.size());
        verify(seriesService, times(1)).getAllSeries();
    }
}
