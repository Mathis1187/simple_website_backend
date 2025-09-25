package mathis.simple_website_backend.models;

public class AuthResponse {
    private String token;
    private String email;
    private Integer userId;
    private boolean success;

    public AuthResponse(String token, String email, int userId, boolean success) {
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.success = success;
    }

    public String getToken() {
        return token;
    }
    public String getEmail() {
        return email;
    }
    public Integer getUserId() {
        return userId;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
