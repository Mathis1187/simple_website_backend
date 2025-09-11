package mathis.simple_website_backend.services;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.PeopleRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    @Autowired
    public SeriesRepository SeriesRepository;

    public List<Series> getAllSeries(int id){
        return SeriesRepository.findAll();
    }

}
