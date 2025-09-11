package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.People;
import mathis.simple_website_backend.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    public Series findSeriesByTitre(String titre);
}
