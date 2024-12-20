package com.location.voiture.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {

    CLIENT,

    MANAGER;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}


