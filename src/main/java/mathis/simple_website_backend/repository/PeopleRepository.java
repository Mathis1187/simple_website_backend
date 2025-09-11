package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {
    public People findPeopleByPrenom(String prenom);
    public People findPeopleById(int id);
}
