package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.UserController;
import mathis.simple_website_backend.models.Gender;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsersServicesTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User p = new User();
        p.setPrenom("Bobby");
        p.setNom("King");
        p.setEmail("bobbytheking@king.org");
        p.setGender(Gender.Male);

        when(userService.createUser(p)).thenReturn(p);

        User result = userController.createUser(p);

        assertEquals("Bobby", result.getPrenom());
        verify(userService, times(1)).createUser(p);
    }

    @Test
    void testGetAllUser() {
        User p1 = new User();
        p1.setPrenom("Bobby");
        User p2 = new User();
        p2.setPrenom("Marcus");
        User p3 = new User();
        p3.setPrenom("Jocker");

        List<User> userList = List.of(p1, p2, p3);

        when(userService.getAllUsers()).thenReturn(userList);

        List<User> result = userController.getAllUser();

        assertEquals(3, result.size());
        assertEquals("Bobby", result.get(0).getPrenom());
        assertEquals("Marcus", result.get(1).getPrenom());
        assertEquals("Jocker", result.get(2).getPrenom());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetHistoryUser() {
        int userId = 1;
        User p = new User();
        p.setId(userId);
        p.setPrenom("Bobby");

        when(userService.getHistory(userId)).thenReturn(Optional.of(p));

        ResponseEntity<User> response = userController.getHistory(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bobby", response.getBody().getPrenom());

        verify(userService, times(1)).getHistory(userId);
    }
}
