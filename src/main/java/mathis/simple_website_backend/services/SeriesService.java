package mathis.simple_website_backend.services;

import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Series createSeries(Series series) {
        return seriesRepository.save(series);
    }

    public Optional<Series> updateSeries(long id, Series series) {
        return seriesRepository.findById(id).map(s -> {
            s.setTitre(series.getTitre());
            s.setGenre(series.getGenre());
            s.setNote(series.getNote());
            s.setNbEpisodes(series.getNbEpisodes());
            return seriesRepository.save(s);
        });
    }

    public void deleteSeries(long id) {
        seriesRepository.deleteById(id);
    }

    public List<Series> searchByGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            return seriesRepository.findAll();
        } else {
            return seriesRepository.findSeriesByGenreIgnoreCase(genre);
        }
    }

    public List<Series> findByNbEpisodesGreaterThanEqual(int episodes) {
        List<Series> tout = seriesRepository.findAll();
        List<Series> resultat = new ArrayList<>();
        for (Series j : tout) {
            if (j.getNbEpisodes() >= episodes) {
                resultat.add(j);
            }
        }
        return resultat;
    }

    public Series getSerieByTitre(String titre) {
        return seriesRepository.findSeriesByTitre(titre);
    }
}
