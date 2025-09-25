package mathis.simple_website_backend.controller;
import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.repository.UserRepository;
import mathis.simple_website_backend.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.services.UserService;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository  seriesRepository;

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(p -> {
                    p.setPrenom(user.getPrenom());
                    p.setNom(user.getNom());
                    p.setEmail(user.getEmail());
                    p.setGender(user.getGender());

                    User updated = userRepository.save(p);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<User> getHistory(@PathVariable int id) {
        return userRepository.findByIdWithSeries(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/history/{seriesId}")
    public ResponseEntity<User> addSeriesVueHistory(@PathVariable int id, @PathVariable long seriesId) {
        Optional<User> optionalUser = userRepository.findByIdWithSeries(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (optionalSeries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Series series = optionalSeries.get();

        if (!user.getSeries().contains(series)) {
            user.getSeries().add(series);
        }

        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Series>>> getRecommendations(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findByIdWithSeries(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Set<Series> seenSeries = user.getSeries();

        Map<String, Long> genreCount = seenSeries.stream()
                .collect(Collectors.groupingBy(Series::getGenre, Collectors.counting()));

        List<String> topGenres = genreCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        List<Integer> seenIds = seenSeries.stream().map(Series::getId).toList();

        Map<String, List<Series>> recommendations = new HashMap<>();
        for (String genre : topGenres) {
            List<Series> recs = seriesRepository.findByGenreIgnoreCaseAndIdNotIn(genre, seenIds)
                    .stream()
                    .limit(3)
                    .toList();
            recommendations.put(genre, recs);
        }

        return ResponseEntity.ok(recommendations);
    }


}
