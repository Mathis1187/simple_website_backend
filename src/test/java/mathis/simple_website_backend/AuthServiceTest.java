package mathis.simple_website_backend;

import mathis.simple_website_backend.models.LoginUserDto;
import mathis.simple_website_backend.models.RegisterUserDto;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.repository.UserRepository;
import mathis.simple_website_backend.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static mathis.simple_website_backend.models.Gender.Male;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setPrenom("John");
        dto.setName("Doe");
        dto.setEmail("john.doe@test.com");
        dto.setPassword("password");
        dto.setGender(Male);

        User userToSave = new User();
        userToSave.setPrenom("John");
        userToSave.setNom("Doe");
        userToSave.setEmail("john.doe@test.com");
        userToSave.setGender(Male);
        userToSave.setPassword("encodedPassword");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        User result = authenticationService.signup(dto);

        assertNotNull(result);
        assertEquals("John", result.getPrenom());
        assertEquals("Doe", result.getNom());
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticateSuccess() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("john.doe@test.com");
        dto.setPassword("password");

        User user = new User();
        user.setEmail("john.doe@test.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByEmail("john.doe@test.com")).thenReturn(user);

        User result = authenticationService.authenticate(dto);

        assertNotNull(result);
        assertEquals("john.doe@test.com", result.getEmail());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail("john.doe@test.com");
    }

    @Test
    void testAuthenticateFailAuthenticationException() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("john.doe@test.com");
        dto.setPassword("wrong");

        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(dto));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(0)).findByEmail(anyString());
    }
}
