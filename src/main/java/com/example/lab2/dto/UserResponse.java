package com.example.lab2.dto;

import com.example.lab2.entity.User;
import com.example.lab2.security.Role;
import java.util.Set;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private boolean enabled;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.enabled = user.isEnabled();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Set<Role> getRoles() { return roles; }
    public boolean isEnabled() { return enabled; }
}
