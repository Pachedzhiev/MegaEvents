package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.example.megaevents.constants.ConstantError.*;

@Getter
@Setter
@Entity
@Table(name="users")
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    @Length(min=4, max=20, message = ERROR_USERNAME)
    private String username;

    @Column(nullable = false)
    @Length(min=8,message = ERROR_PASSWORD)
    private String password;

    @Column(nullable = false,unique = true)
    @Length(min=5,message = ERROR_EMAIL)
    private String email;

    @OneToOne(cascade=CascadeType.ALL )
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> roles;


    public User() {
        this.roles = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
