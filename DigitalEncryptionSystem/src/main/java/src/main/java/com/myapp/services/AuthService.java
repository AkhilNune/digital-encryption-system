package src.main.java.com.myapp.services;

import src.main.java.com.myapp.models.LoginCredentials;

import java.util.List;

public interface AuthService {
    boolean authenticate(LoginCredentials credentials);
    boolean addUser(LoginCredentials credentials);

    List<String> getAllUsers();
}
