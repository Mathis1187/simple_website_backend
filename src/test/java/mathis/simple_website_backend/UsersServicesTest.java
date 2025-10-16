package mathis.simple_website_backend;

import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.repository.SeriesRepository;
import mathis.simple_website_backend.repository.UserRepository;
import mathis.simple_website_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServicesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User p1 = new User();
        User p2 = new User();
        when(userRepository.findAll()).thenReturn(List.of(p1, p2));

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        User result = userService.findByEmail("test@test.com");

        assertEquals(user, result);
        verify(userRepository).findByEmail("test@test.com");
    }

    @Test
    void testCreateUserValidPassword() {
        User user = new User();
        user.setPassword("abc");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUserEmptyPassword() {
        User user = new User();
        user.setPassword("");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void testCreateUserShortPassword() {
        User user = new User();
        user.setPassword("ab");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void testUpdateUser() {
        int id = 1;
        User existing = new User();
        existing.setId(id);
        existing.setPrenom("Old");
        User updated = new User();
        updated.setPrenom("New");

        when(userRepository.findById((long) id)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        Optional<User> result = userService.updateUser(id, updated);

        assertEquals("New", result.get().getPrenom());
        verify(userRepository).save(existing);
    }

    @Test
    void testDeleteUser() {
        long id = 1;
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetHistory() {
        User user = new User();
        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getHistory(1);

        assertTrue(result.isPresent());
        verify(userRepository).findByIdWithSeries(anyInt());
    }

    @Test
    void testAddSeriesVueHistorySuccess() {
        User user = new User();
        user.setId(1);
        Series series = new Series();
        series.setId(10);

        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.of(user));
        when(seriesRepository.findById(anyLong())).thenReturn(Optional.of(series));
        when(userRepository.save(user)).thenReturn(user);

        Optional<User> result = userService.addSeriesVueHistory(1, 10);

        assertTrue(result.isPresent());
        assertTrue(result.get().getSeries().contains(series));
    }

    @Test
    void testAddSeriesVueHistoryUserNotFound() {
        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.empty());

        Optional<User> result = userService.addSeriesVueHistory(1, 10);

        assertTrue(result.isEmpty());
    }

    @Test
    void testAddSeriesVueHistorySeriesNotFound() {
        User user = new User();
        user.setId(1);
        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.of(user));
        when(seriesRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> result = userService.addSeriesVueHistory(1, 10);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRecommendations() {
        Series s1 = new Series();
        s1.setId(1);
        s1.setGenre("Action");
        Series s2 = new Series();
        s2.setId(2);
        s2.setGenre("Drama");

        User user = new User();
        user.getSeries().addAll(Set.of(s1, s2));

        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.of(user));
        when(seriesRepository.findByGenreIgnoreCaseAndIdNotIn(anyString(), anyList()))
                .thenReturn(List.of(new Series(), new Series()));

        Optional<Map<String, List<Series>>> result = userService.getRecommendations(1);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
    }

    @Test
    void testGetRecommendationsUserNotFound() {
        when(userRepository.findByIdWithSeries(anyInt())).thenReturn(Optional.empty());

        Optional<Map<String, List<Series>>> result = userService.getRecommendations(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        boolean result = userService.login("test@test.com", "password");

        assertTrue(result);
    }

    @Test
    void testLoginFailWrongPassword() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        boolean result = userService.login("test@test.com", "wrong");

        assertFalse(result);
    }

    @Test
    void testLoginFailUserNotFound() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(null);

        boolean result = userService.login("unknown@test.com", "password");

        assertFalse(result);
    }
}
