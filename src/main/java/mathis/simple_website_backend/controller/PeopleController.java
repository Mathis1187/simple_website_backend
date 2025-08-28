package mathis.simple_website_backend.controller;
import mathis.simple_website_backend.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.services.PeopleService;


import java.util.List;
@RestController
@RequestMapping("/people")
@CrossOrigin(origins = "http://localhost:5173")
public class PeopleController {
    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping("/all")
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

}
