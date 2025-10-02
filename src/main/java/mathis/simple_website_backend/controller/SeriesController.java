package mathis.simple_website_backend.controller;

import mathis.simple_website_backend.models.NoteDTO;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = "http://localhost:5173")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @GetMapping("/all")
    public List<Series> getAllSeries() {
        return seriesService.getAllSeries();
    }

    @PostMapping("/createSeries")
    public Series createSeries(@RequestBody Series series) {
        return seriesService.createSeries(series);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable long id, @RequestBody Series series) {
        return seriesService.updateSeries(id, series)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Series> deleteSeries(@PathVariable long id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Series> search(@RequestParam(required = false) String genre) {
        return seriesService.searchByGenre(genre);
    }

    @GetMapping("/episodePlusGrand")
    public List<Series> findByNbEpisodesGreaterThanEqual(@RequestParam int EpisodesActuelle) {
        return seriesService.findByNbEpisodesGreaterThanEqual(EpisodesActuelle);
    }

    @GetMapping("/titre/{titre}")
    public Series getSerieByTitre(@PathVariable String titre) {
        return seriesService.getSerieByTitre(titre);
    }


    @PatchMapping("/ratings/{id}")
    public ResponseEntity<Series> updateSeriesRatings(@PathVariable long id, @RequestBody Series series) {
        return seriesService.updateSerieRatingById(id, series)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
