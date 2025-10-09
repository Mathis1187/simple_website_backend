package mathis.simple_website_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Utilisateur", description = "Endpoints pour gérer les utilisateurs")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Récupérer tous les utilisateurs")
    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Créer un nouvel utilisateur")
    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Mettre à jour un utilisateur par ID")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Supprimer un utilisateur par ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Récupérer l'historique d'un utilisateur")
    @GetMapping("/{id}/history")
    public ResponseEntity<User> getHistory(@PathVariable int id) {
        return userService.getHistory(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter une série vue à l'historique")
    @PostMapping("/{id}/history/{seriesId}")
    public ResponseEntity<User> addSeriesVueHistory(@PathVariable int id, @PathVariable long seriesId) {
        return userService.addSeriesVueHistory(id, seriesId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtenir des recommandations pour un utilisateur")
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Series>>> getRecommendations(@PathVariable int id) {
        return userService.getRecommendations(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupérer l'utilisateur actuellement connecté")
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Récupérer tous les utilisateurs (endpoint alternatif)")
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
