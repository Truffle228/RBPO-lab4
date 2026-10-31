package com.example.lab2.service;

import com.example.lab2.dto.UserDto;
import com.example.lab2.entity.User;
import com.example.lab2.repository.UserRepository;
import com.example.lab2.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private final UserRepository repo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository repo) { 
        this.repo = repo; 
    }

    public List<User> findAll() { return repo.findAll(); }
    public User findById(Long id) { return repo.findById(id).orElse(null); }

    public User create(UserDto dto) {
        User u = new User(dto.getUsername(), dto.getEmail());
        return repo.save(u);
    }

    public User update(Long id, Map<String, Object> patch) {
        User u = repo.findById(id).orElse(null);
        if (u == null) return null;
        if (patch.containsKey("username")) u.setUsername((String) patch.get("username"));
        if (patch.containsKey("email")) u.setEmail((String) patch.get("email"));
        return repo.save(u);
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public User register(String username, String password) {
        if (repo.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }
        validatePassword(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(username + "@example.com");
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(Role.ROLE_USER));
        user.setEnabled(true);

        return repo.save(user);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password must contain uppercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new RuntimeException("Password must contain a digit");
        }
    }
}
