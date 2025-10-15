package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.UserController;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersControllersTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUser() {
        User u1 = new User();
        User u2 = new User();
        when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        List<User> result = userController.getAllUser();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testCreateUser() {
        User u = new User();
        u.setPrenom("Test");
        when(userService.createUser(u)).thenReturn(u);

        User result = userController.createUser(u);

        assertEquals("Test", result.getPrenom());
        verify(userService, times(1)).createUser(u);
    }

    @Test
    void testUpdateUserFound() {
        User u = new User();
        u.setPrenom("Updated");
        when(userService.updateUser(1L, u)).thenReturn(Optional.of(u));

        ResponseEntity<User> response = userController.updateUser(1L, u);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getPrenom());
    }

    @Test
    void testUpdateUserNotFound() {
        User u = new User();
        when(userService.updateUser(1L, u)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUser(1L, u);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<User> response = userController.deleteUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testGetHistoryFound() {
        User u = new User();
        u.setPrenom("History");
        when(userService.getHistory(1)).thenReturn(Optional.of(u));

        ResponseEntity<User> response = userController.getHistory(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("History", response.getBody().getPrenom());
    }

    @Test
    void testGetHistoryNotFound() {
        when(userService.getHistory(1)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getHistory(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAddSeriesVueHistoryFound() {
        User u = new User();
        Series s = new Series();
        u.getSeries().add(s);
        when(userService.addSeriesVueHistory(1, 10)).thenReturn(Optional.of(u));

        ResponseEntity<User> response = userController.addSeriesVueHistory(1, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getSeries().contains(s));
    }

    @Test
    void testAddSeriesVueHistoryNotFound() {
        when(userService.addSeriesVueHistory(1, 10)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.addSeriesVueHistory(1, 10);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetRecommendationsFound() {
        Map<String, List<Series>> recs = new HashMap<>();
        recs.put("Action", List.of(new Series()));
        when(userService.getRecommendations(1)).thenReturn(Optional.of(recs));

        ResponseEntity<Map<String, List<Series>>> response = userController.getRecommendations(1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("Action"));
    }

    @Test
    void testGetRecommendationsNotFound() {
        when(userService.getRecommendations(1)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, List<Series>>> response = userController.getRecommendations(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAuthenticatedUser() {
        User u = new User();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(u);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<User> response = userController.authenticatedUser();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(u, response.getBody());
    }

    @Test
    void testAllUsers() {
        User u1 = new User();
        User u2 = new User();
        when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        ResponseEntity<List<User>> response = userController.allUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }
}
