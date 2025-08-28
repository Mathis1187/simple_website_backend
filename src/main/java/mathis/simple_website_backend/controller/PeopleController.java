package mathis.simple_website_backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.services.PeopleService;


import java.util.List;
@RestController
@RequestMapping("/people")

public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    @GetMapping("/PeopleService")
    public List<People> getAllPeople() {
        return peopleService.getAllPeople();
    }

}
