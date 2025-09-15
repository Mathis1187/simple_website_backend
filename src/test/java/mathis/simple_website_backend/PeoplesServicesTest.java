package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.PeopleController;
import mathis.simple_website_backend.models.Gender;
import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.repository.PeopleRepository;
import mathis.simple_website_backend.services.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PeoplesServicesTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleController peopleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPeople() {
        People p = new People();
        p.setPrenom("Bobby");
        p.setLast_name("King");
        p.setEmail("bobbytheking@king.org");
        p.setGender(Gender.valueOf("Male"));
        peopleController.createPeople(p);
        assert(p.getPrenom().equals("Bobby"));
    }

    @Test
    void testGetAllPeople() {
        People p1 = new People();
        p1.setId(1);
        p1.setPrenom("Bobby");
        p1.setLast_name("King");
        p1.setEmail("bobbytheking@king.org");
        p1.setGender(Gender.valueOf("Male"));
        People p2 = new People();
        p2.setId(2);
        p2.setPrenom("Marcus");
        p2.setLast_name("Batman");
        p2.setEmail("batman@marcus.com");
        p2.setGender(Gender.valueOf("Male"));
        People p3 = new People();
        p3.setId(3);
        p3.setPrenom("Jocker");
        p3.setLast_name("Joe");
        p3.setEmail("jocker@joe.com");
        p3.setGender(Gender.valueOf("Male"));

        List<People> peopleList = List.of(p1, p2, p3);

        when(peopleRepository.findAll()).thenReturn(peopleList);

        List<People> result = peopleController.getAllPeople();

        assertEquals(3, result.size());
        assertEquals("Bobby", result.get(0).getPrenom());
        assertEquals("Marcus", result.get(1).getPrenom());
        assertEquals("Jocker", result.get(2).getPrenom());
    }
}
