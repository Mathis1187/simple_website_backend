package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByPrenom(String prenom);
    public User findUserById(int id);
    @Query("SELECT p FROM User p LEFT JOIN FETCH p.series WHERE p.id = :id")
    Optional<User> findByIdWithSeries(@Param("id") int id);
}
