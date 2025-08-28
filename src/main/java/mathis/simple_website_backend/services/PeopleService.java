package mathis.simple_website_backend.services;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

    @Autowired
    public PeopleRepository peopleRepository;

    public List<People> getAllPeople(int id){
        return peopleRepository.findAll();
    }

}
