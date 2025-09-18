package mathis.simple_website_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String genre;
    private int nbEpisodes;
    private float note;

    @ManyToMany(mappedBy = "series")
    @JsonIgnore
    private Set<People> peoples = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(int nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Set<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(Set<People> peoples) {
        this.peoples = peoples;
    }
}
