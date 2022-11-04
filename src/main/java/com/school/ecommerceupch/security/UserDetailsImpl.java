package com.school.ecommerceupch.security;

import com.school.ecommerceupch.entities.Address;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    private Date getDateOfBirth() {
        return user.getDateOfBirth();
    }

    private List<UserRole> getUserRoles() {
        return user.getUserRoles();
    }

    private List<Product> getProducts() {
        return user.getProducts();
    }

    private List<Address> getAddresses() {
        return user.getAddresses();
    }

}
