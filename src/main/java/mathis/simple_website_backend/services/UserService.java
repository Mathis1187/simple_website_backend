package mathis.simple_website_backend.services;

import jakarta.transaction.Transactional;
import mathis.simple_website_backend.models.User;
import mathis.simple_website_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> getAllUser(int id){
        return userRepository.findAll();
    }

}
