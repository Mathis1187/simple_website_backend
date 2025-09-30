package mathis.simple_website_backend.services;

import mathis.simple_website_backend.models.Series;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.repository.SeriesRepository;
import mathis.simple_website_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }

        if (user.getPassword().length() < 3) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(long id, User user) {
        return userRepository.findById(id).map(p -> {
            p.setPrenom(user.getPrenom());
            p.setNom(user.getNom());
            p.setEmail(user.getEmail());
            p.setGender(user.getGender());
            return userRepository.save(p);
        });
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getHistory(int id) {
        return userRepository.findByIdWithSeries(id);
    }

    public Optional<User> addSeriesVueHistory(int id, long seriesId) {
        Optional<User> optionalUser = userRepository.findByIdWithSeries(id);
        if (optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.get();
        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (optionalSeries.isEmpty())
            return Optional.empty();

        Series series = optionalSeries.get();
        if (!user.getSeries().contains(series)) {
            user.getSeries().add(series);
        }

        return Optional.of(userRepository.save(user));
    }

    public Optional<Map<String, List<Series>>> getRecommendations(int id) {
        Optional<User> optionalUser = userRepository.findByIdWithSeries(id);
        if (optionalUser.isEmpty())
            return Optional.empty();

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

        return Optional.of(recommendations);
    }

    public boolean login(String email, String frontEndPWD) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        return passwordEncoder.matches(frontEndPWD, user.getPassword());
    }

}
