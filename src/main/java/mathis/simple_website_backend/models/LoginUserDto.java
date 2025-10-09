package mathis.simple_website_backend.models;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginUserDto {

    @Schema(description = "Email de l'utilisateur", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "password123", required = true)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
