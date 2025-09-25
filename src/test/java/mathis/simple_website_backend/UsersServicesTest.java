package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.UserController;
import mathis.simple_website_backend.models.Gender;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.repository.UserRepository;
import mathis.simple_website_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsersServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User p = new User();
        p.setPrenom("Bobby");
        p.setNom("King");
        p.setEmail("bobbytheking@king.org");
        p.setGender(Gender.valueOf("Male"));
        userController.createUser(p);
        assert(p.getPrenom().equals("Bobby"));
    }

    @Test
    void testGetAllUser() {
        User p1 = new User();
        p1.setId(1);
        p1.setPrenom("Bobby");
        p1.setNom("King");
        p1.setEmail("bobbytheking@king.org");
        p1.setGender(Gender.valueOf("Male"));
        User p2 = new User();
        p2.setId(2);
        p2.setPrenom("Marcus");
        p2.setNom("Batman");
        p2.setEmail("batman@marcus.com");
        p2.setGender(Gender.valueOf("Male"));
        User p3 = new User();
        p3.setId(3);
        p3.setPrenom("Jocker");
        p3.setNom("Joe");
        p3.setEmail("jocker@joe.com");
        p3.setGender(Gender.valueOf("Male"));

        List<User> userList = List.of(p1, p2, p3);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userController.getAllUser();

        assertEquals(3, result.size());
        assertEquals("Bobby", result.get(0).getPrenom());
        assertEquals("Marcus", result.get(1).getPrenom());
        assertEquals("Jocker", result.get(2).getPrenom());
    }

    @Test
    void testGetHistoryUser() {
        int userId = 1;

        User p = new User();
        p.setId(userId);
        p.setPrenom("Bobby");
        p.setNom("King");
        p.setEmail("bobbytheking@king.org");
        p.setGender(Gender.Male);

        when(userRepository.findByIdWithSeries(userId)).thenReturn(Optional.of(p));

        ResponseEntity<User> response = userController.getHistory(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bobby", response.getBody().getPrenom());
    }


}
