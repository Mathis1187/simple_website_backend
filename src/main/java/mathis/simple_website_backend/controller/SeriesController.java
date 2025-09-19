package mathis.simple_website_backend.controller;

import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.PeopleRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import mathis.simple_website_backend.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mathis.simple_website_backend.models.People;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = "http://localhost:5173")
public class SeriesController {
    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("/all")
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    @PostMapping("/createSeries")
    public Series createSeries(@RequestBody Series series) {
        return seriesRepository.save(series);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable long id, @RequestBody Series series) {
        return seriesRepository.findById(id)
                .map(s -> {
                    s.setTitre(series.getTitre());
                    s.setGenre(series.getGenre());
                    s.setNote(series.getNote());
                    s.setNbEpisodes(series.getNbEpisodes());

                    Series updated = seriesRepository.save(s);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Series> deleteSeries(@PathVariable long id) {
        seriesRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Series> search(@RequestParam(required = false) String genre) {
        if (genre == null || genre.isEmpty()) {
            return seriesRepository.findAll();
        } else {
            return seriesRepository.findSeriesByGenreIgnoreCase(genre);
        }
    }


    @GetMapping("/episodePlusGrand")
    public List<Series> findByNbEpisodesGreaterThanEqual(@RequestParam int EpisodesActuelle) {
        List<Series> tout = seriesRepository.findAll();
        List<Series> resultat = new ArrayList<>();
        for (Series j : tout) {
            if (j.getNbEpisodes() >= EpisodesActuelle) {
                resultat.add(j);
            }
        }
        return resultat;
    }

    @GetMapping("/titre/{titre}")
    public Series getSerieByTitre(@RequestParam String titre) {
        return seriesRepository.findSeriesByTitre(titre);
    }
}
