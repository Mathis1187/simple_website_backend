package mathis.simple_website_backend.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mathis.simple_website_backend.models.AuthResponse;
import mathis.simple_website_backend.models.LoginRequest;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.JwtUtil;
import mathis.simple_website_backend.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        try {
            String email = loginData.getEmail();
            String password = loginData.getPassword();

            // Vérifier les identifiants
            boolean isValidLogin = userService.login(email, password);

            if (!isValidLogin) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }

            // Générer le token JWT
            String token = jwtUtil.generateToken(email);

            // Récupérer les informations utilisateur
            User user = userService.findByEmail(email);

            // Retourner la réponse sous forme de Map
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getId(), true));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getEmailFromToken(token);
                    User user = userService.findByEmail(email);

                    return ResponseEntity.ok(Map.of(
                            "valid", true,
                            "email", email,
                            "userId", user.getId()));
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "error", "Invalid token"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "error", "Token validation failed"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Vérifier si l'email existe déjà
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Email déjà utilisé"));
            }

            User createdUser = userService.createUser(user);

            return ResponseEntity.ok(Map.of(
                    "message", "Utilisateur créé avec succès",
                    "userId", createdUser.getId(),
                    "email", createdUser.getEmail()
            ));

        } catch (Exception e) {
            // Ici on renvoie l'erreur complète dans la réponse pour debug
            e.printStackTrace(); // pour la console aussi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erreur lors de l'inscription",
                            "exception", e.getClass().getSimpleName(),
                            "message", e.getMessage(),
                            "stackTrace", Arrays.toString(e.getStackTrace())
                    ));
        }
    }

}