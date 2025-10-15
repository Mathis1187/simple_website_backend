package mathis.simple_website_backend;

import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.SeriesRepository;
import mathis.simple_website_backend.services.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeriesServicesTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SeriesService seriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSeries() {
        Series s1 = new Series();
        Series s2 = new Series();
        when(seriesRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Series> result = seriesService.getAllSeries();

        assertEquals(2, result.size());
        verify(seriesRepository, times(1)).findAll();
    }

    @Test
    void testCreateSeries() {
        Series s = new Series();
        s.setTitre("Titre Test");
        when(seriesRepository.save(s)).thenReturn(s);

        Series result = seriesService.createSeries(s);

        assertEquals("Titre Test", result.getTitre());
        verify(seriesRepository, times(1)).save(s);
    }

    @Test
    void testUpdateSeriesFound() {
        Series existing = new Series();
        existing.setId(1);
        existing.setTitre("Old");

        Series updated = new Series();
        updated.setTitre("New");
        updated.setGenre("Genre");
        updated.setNote(9.0f);
        updated.setNbEpisodes(12);

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(seriesRepository.save(existing)).thenReturn(existing);

        Optional<Series> result = seriesService.updateSeries(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().getTitre());
        verify(seriesRepository, times(1)).save(existing);
    }

    @Test
    void testUpdateSeriesNotFound() {
        Series updated = new Series();
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Series> result = seriesService.updateSeries(1L, updated);

        assertTrue(result.isEmpty());
        verify(seriesRepository, never()).save(any());
    }

    @Test
    void testDeleteSeries() {
        doNothing().when(seriesRepository).deleteById(1L);

        seriesService.deleteSeries(1L);

        verify(seriesRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchByGenreNullOrEmpty() {
        Series s1 = new Series();
        Series s2 = new Series();
        when(seriesRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Series> result = seriesService.searchByGenre(null);
        assertEquals(2, result.size());

        result = seriesService.searchByGenre("");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchByGenreNonEmpty() {
        Series s1 = new Series();
        when(seriesRepository.findSeriesByGenreIgnoreCase("Action")).thenReturn(List.of(s1));

        List<Series> result = seriesService.searchByGenre("Action");
        assertEquals(1, result.size());
        verify(seriesRepository, times(1)).findSeriesByGenreIgnoreCase("Action");
    }

    @Test
    void testFindByNbEpisodesGreaterThanEqual() {
        Series s1 = new Series();
        s1.setNbEpisodes(10);
        Series s2 = new Series();
        s2.setNbEpisodes(5);
        when(seriesRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Series> result = seriesService.findByNbEpisodesGreaterThanEqual(6);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getNbEpisodes());
    }

    @Test
    void testGetSerieByTitre() {
        Series s = new Series();
        s.setTitre("Test");
        when(seriesRepository.findSeriesByTitre("Test")).thenReturn(s);

        Series result = seriesService.getSerieByTitre("Test");
        assertEquals("Test", result.getTitre());
        verify(seriesRepository, times(1)).findSeriesByTitre("Test");
    }

    @Test
    void testUpdateSerieRatingByIdFound() {
        Series existing = new Series();
        existing.setId(1);
        existing.setNote(5.0f);

        Series updated = new Series();
        updated.setNote(9.5f);

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(seriesRepository.save(existing)).thenReturn(existing);

        Optional<Series> result = seriesService.updateSerieRatingById(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(9.5f, result.get().getNote());
        verify(seriesRepository, times(1)).save(existing);
    }

    @Test
    void testUpdateSerieRatingByIdNotFound() {
        Series updated = new Series();
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Series> result = seriesService.updateSerieRatingById(1L, updated);

        assertTrue(result.isEmpty());
        verify(seriesRepository, never()).save(any());
    }
}
