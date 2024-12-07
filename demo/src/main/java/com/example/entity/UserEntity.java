package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "role_user")
    @Enumerated(value = EnumType.STRING)
    UserRole userRole;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "credentials_expiry_date")
    LocalDateTime credentialsExpiryDate;

    @Column(name = "is_account_non_expired")
    Boolean isAccountExpired;

    @Column(name = "is_account_non_locked")
    Boolean isAccountLocked;

    @Column(name = "is_active", nullable = false)
    Boolean isActive;

    @Column(name = "is_enabled")
    Boolean isEnabled;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EntryEntity> entries;


    public UserEntity(String firstName, String lastName, String username, String photoUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.photoUrl = photoUrl;
        this.isEnabled = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(this.userRole.name()));

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
