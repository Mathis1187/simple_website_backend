package mathis.simple_website_backend.controller;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.PeopleRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import mathis.simple_website_backend.models.People;


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

    @PostMapping
    public Series createSeries(@RequestBody Series series) {
        return seriesRepository.save(series);
    }

}
