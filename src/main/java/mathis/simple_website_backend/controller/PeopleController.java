package mathis.simple_website_backend.controller;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.PeopleRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.services.PeopleService;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/people")
@CrossOrigin(origins = "http://localhost:5173")
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private SeriesRepository  seriesRepository;

    @GetMapping("/all")
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    @PostMapping("/createPeople")
    public People createPeople(@RequestBody People people) {
        return peopleRepository.save(people);
    }

    @PutMapping("/{id}")
    public ResponseEntity<People> updatePeople(@PathVariable long id, @RequestBody People people) {
        return peopleRepository.findById(id)
                .map(p -> {
                    p.setPrenom(people.getPrenom());
                    p.setLast_name(people.getLast_name());
                    p.setEmail(people.getEmail());
                    p.setGender(people.getGender());

                    People updated = peopleRepository.save(p);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<People> deletePeople(@PathVariable long id) {
        peopleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<People> getHistory(@PathVariable int id) {
        return peopleRepository.findByIdWithSeries(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/history/{seriesId}")
    public ResponseEntity<People> addSeriesVueHistory(@PathVariable int id, @PathVariable long seriesId) {
        Optional<People> optionalPeople = peopleRepository.findByIdWithSeries(id);
        if (optionalPeople.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        People people = optionalPeople.get();

        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (optionalSeries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Series series = optionalSeries.get();

        if (!people.getSeries().contains(series)) {
            people.getSeries().add(series);
        }

        People updatedPeople = peopleRepository.save(people);

        return ResponseEntity.ok(updatedPeople);
    }

}
