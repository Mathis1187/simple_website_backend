package mathis.simple_website_backend.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Données nécessaires pour l'inscription d'un utilisateur")
public class RegisterUserDto {

    @Schema(description = "Email de l'utilisateur", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "password123", required = true)
    private String password;

    @Schema(description = "Prénom de l'utilisateur", example = "Jean")
    private String prenom;

    @Schema(description = "Nom de famille de l'utilisateur", example = "Dupont")
    private String name;

    @Schema(description = "Genre de l'utilisateur", example = "MALE")
    private Gender gender;

    @Schema(description = "Liste des séries favorites de l'utilisateur")
    private Series[] series;

    // Getters et setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Series[] getSeries() { return series; }
    public void setSeries(Series[] series) { this.series = series; }
}
