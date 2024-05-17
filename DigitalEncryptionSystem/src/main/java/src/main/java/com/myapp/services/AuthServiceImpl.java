package src.main.java.com.myapp.services;

import src.main.java.com.myapp.models.LoginCredentials;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private Map<String, String> userCredentials;

    public AuthServiceImpl() {
        this.userCredentials = new HashMap<>();
        userCredentials.put("user1", "password");
        userCredentials.put("user2", "password");
        userCredentials.put("user3", "password");
        userCredentials.put("user4", "password");
    }

    @Override
    public boolean authenticate(LoginCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        System.out.println(username);
        System.out.println(password);
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    @Override
    public boolean addUser(LoginCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (!userCredentials.containsKey(username)) {
            userCredentials.put(username, password);
            return true; // User added successfully
        }
        return false; // User already exists
    }

    @Override
    public List<String> getAllUsers() {
        return new ArrayList<>(userCredentials.keySet());
    }
}

