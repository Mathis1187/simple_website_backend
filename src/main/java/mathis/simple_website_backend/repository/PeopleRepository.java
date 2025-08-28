package mathis.simple_website_backend.repository;

import mathis.simple_website_backend.models.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Integer> {
    public People findPeopleByPrenom(String prenom);
}
