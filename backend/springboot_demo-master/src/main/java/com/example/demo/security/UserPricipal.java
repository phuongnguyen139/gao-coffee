package com.example.demo.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.AuthorityEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;

public class UserPricipal implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    UserEntity userEntity;

    public UserPricipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
       Collection<AuthorityEntity> authorityEntities = new HashSet<>();;
       
       //Get user roles;
       Collection<RoleEntity> roles = this.userEntity.getRoles();
       
       if(roles == null) return grantedAuthorities;
       
       roles.forEach((role)->{
           grantedAuthorities.add( new SimpleGrantedAuthority(role.getName()));
           authorityEntities.addAll(role.getAuthorities());
       });
       
       authorityEntities.forEach((authority)->{
           grantedAuthorities.add( new SimpleGrantedAuthority(authority.getName()));
       });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
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
        return this.userEntity.getEmailVertificationStatus();
    }

}
