package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Series findSeriesByTitre(String titre);
    public List<Series> findSeriesByGenreIgnoreCase(String genre);
    public List<Series> findByGenreIgnoreCaseAndIdNotIn(String genre, List<Integer> excludeIds);
    public Series findByNbEpisodesGreaterThanEqual(int nbEpisodes);
    public Series findSeriesById(int id);
}
