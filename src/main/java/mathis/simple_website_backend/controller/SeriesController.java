package mathis.simple_website_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Séries", description = "Endpoints pour gérer les séries")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @Operation(summary = "Récupérer toutes les séries")
    @GetMapping("/all")
    public List<Series> getAllSeries() {
        return seriesService.getAllSeries();
    }

    @Operation(summary = "Créer une nouvelle série")
    @PostMapping("/createSeries")
    public Series createSeries(@RequestBody Series series) {
        return seriesService.createSeries(series);
    }

    @Operation(summary = "Mettre à jour une série par ID")
    @PutMapping("/{id}")
    public ResponseEntity<Series> updateSeries(@PathVariable long id, @RequestBody Series series) {
        return seriesService.updateSeries(id, series)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Supprimer une série par ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Series> deleteSeries(@PathVariable long id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Rechercher des séries par genre")
    @GetMapping("/search")
    public List<Series> search(@RequestParam(required = false) String genre) {
        return seriesService.searchByGenre(genre);
    }

    @Operation(summary = "Récupérer les séries avec un nombre d'épisodes minimum")
    @GetMapping("/episodePlusGrand")
    public List<Series> findByNbEpisodesGreaterThanEqual(@RequestParam int EpisodesActuelle) {
        return seriesService.findByNbEpisodesGreaterThanEqual(EpisodesActuelle);
    }

    @Operation(summary = "Récupérer une série par son titre")
    @GetMapping("/titre/{titre}")
    public Series getSerieByTitre(@PathVariable String titre) {
        return seriesService.getSerieByTitre(titre);
    }

    @Operation(summary = "Mettre à jour les notes d'une série")
    @PatchMapping("/ratings/{id}")
    public ResponseEntity<Series> updateSeriesRatings(@PathVariable long id, @RequestBody Series series) {
        return seriesService.updateSerieRatingById(id, series)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer les 10 séries les plus populaires")
    @GetMapping("/trending")
    public List<Series> getTrending() {
        return seriesService.getAllSeries().stream().limit(10).collect(Collectors.toList());
    }
}
