package mathis.simple_website_backend;

import mathis.simple_website_backend.controller.AuthenticationController;
import mathis.simple_website_backend.models.LoginResponse;
import mathis.simple_website_backend.models.LoginUserDto;
import mathis.simple_website_backend.models.RegisterUserDto;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.AuthenticationService;
import mathis.simple_website_backend.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Test du endpoint /auth/signup ---
    @Test
    void register_ShouldReturnRegisteredUser() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        when(authenticationService.signup(registerUserDto)).thenReturn(user);

        // Act
        ResponseEntity<User> response = authenticationController.register(registerUserDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(authenticationService, times(1)).signup(registerUserDto);
    }

    // --- Test du endpoint /auth/login ---
    @Test
    void authenticate_ShouldReturnLoginResponseWithToken() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto();
        User user = new User();
        user.setEmail("test@example.com");

        String fakeToken = "jwt-token";
        long fakeExpiration = 3600L;

        when(authenticationService.authenticate(loginUserDto)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(fakeToken);
        when(jwtService.getExpirationTime()).thenReturn(fakeExpiration);

        // Act
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUserDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(fakeToken, response.getBody().getToken());
        assertEquals(fakeExpiration, response.getBody().getExpiresIn());

        verify(authenticationService, times(1)).authenticate(loginUserDto);
        verify(jwtService, times(1)).generateToken(user);
        verify(jwtService, times(1)).getExpirationTime();
    }
}
