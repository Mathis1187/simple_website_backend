package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
    public People findPeopleByPrenom(String prenom);
    public People findPeopleById(int id);
    @Query("SELECT p FROM People p LEFT JOIN FETCH p.series WHERE p.id = :id")
    Optional<People> findByIdWithSeries(@Param("id") int id);
}
