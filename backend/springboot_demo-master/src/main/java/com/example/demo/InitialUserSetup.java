package com.example.demo;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AuthorityEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.shared.RolesEnum;
import com.example.demo.shared.Utils;

@Component
public class InitialUserSetup {
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application Ready Event ...");
        AuthorityEntity readAuthorityEntity = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthorityEntity = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthorityEntity = createAuthority("DELETE_AUTHORITY");

        createRole(RolesEnum.ROLE_USER.name(), Arrays.asList(readAuthorityEntity, writeAuthorityEntity));
        RoleEntity roleAdmin = createRole(RolesEnum.ROLE_ADMIN.name(),
                Arrays.asList(readAuthorityEntity, writeAuthorityEntity, deleteAuthorityEntity));

        if (roleAdmin == null)
            return;
        UserEntity adminUserEntity = new UserEntity();
        adminUserEntity.setFirstName("Phan");
        adminUserEntity.setLastName("Dinh Hiep");
        adminUserEntity.setEmail("phandinhhiep94@gmail.com");
        adminUserEntity.setEmailVertificationStatus(true);
        adminUserEntity.setUserId(utils.genarateUserId(30));
        adminUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode("20051994"));
        adminUserEntity.setRoles(Arrays.asList(roleAdmin));
        createAdmin(adminUserEntity);
    }

    @Transactional
    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authorityEntity = authorityRepository.findByName(name);
        if (authorityEntity == null) {
            authorityEntity = new AuthorityEntity(name);
            authorityRepository.save(authorityEntity);
        }
        return authorityEntity;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorityEntities) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            roleEntity.setAuthorities(authorityEntities);
            roleRepository.save(roleEntity);
        }

        return roleEntity;
    }
    
    @Transactional
    private UserEntity createAdmin(UserEntity userEntity) {
        UserEntity entity = userRepository.findByEmail(userEntity.getEmail());
        if (entity == null) {
            userRepository.save(entity);
        }

        return entity;
    }

}
