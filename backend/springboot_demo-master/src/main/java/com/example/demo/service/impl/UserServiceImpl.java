package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.response.ErrorMessage;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPricipal;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new UsernameNotFoundException("Email address already exists!");

//        UserEntity entity = new UserEntity();
//        BeanUtils.copyProperties(userDto, entity);

        for (int i = 0; i < user.getAddresses().size(); i++) {
            AddressDTO address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.genarateAddressId(30));
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity entity = modelMapper.map(user, UserEntity.class);

        entity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entity.setUserId(utils.genarateUserId(30));
//        entity.setEmailVertificationToken(utils.g);
        
        
        //set Roles
        
        Collection<RoleEntity> roleEntities = new HashSet<RoleEntity>();
        for (String role : user.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if(roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        entity.setRoles(roleEntities);

        UserEntity storedUserDetail = userRepository.save(entity);

//        UserDto returnUserDto = new UserDto();

//        BeanUtils.copyProperties(storedUserDetail, returnUserDto);
        UserDto returnUserDto = modelMapper.map(storedUserDetail, UserDto.class);
//		try {
//			sendMail("gaocoffee230422@gmail.com", "test", "ahihih");
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return returnUserDto;
    }

    @Value("${spring.mail.username}")
    private String from;

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendMail(String to, String subject, String body) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper;
//        helper = new MimeMessageHelper(message, true);// true indicates multipart message
//
//        helper.setFrom(from); // <--- THIS IS IMPORTANT
//
//        helper.setSubject(subject);
//        helper.setTo(to);
//        helper.setText(body, true);// true indicates body is html
//        javaMailSender.send(message);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null)
            throw new UsernameNotFoundException(username);
        
        return new UserPricipal(userEntity);
//        return new User(username, userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto returnUserDto = new UserDto();
        BeanUtils.copyProperties(userEntity, returnUserDto);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null)
            throw new UsernameNotFoundException(id);

        UserDto returnUserDto = new UserDto();
//        BeanUtils.copyProperties(userEntity, returnUserDto);
//        ModelMapper modelMapper = new ModelMapper();
//        returnUserDto = modelMapper.map(userEntity, UserDto.class);
        returnUserDto = convertToDto(userEntity);

        return returnUserDto;
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        if (userRepository.findByUserId(id) == null)
            throw new UsernameNotFoundException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(userDto, entity);
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());

        UserEntity storedUserDetail = userRepository.save(entity);

        UserDto returnUserDto = new UserDto();
        BeanUtils.copyProperties(storedUserDetail, returnUserDto);
        return returnUserDto;
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null)
            throw new UsernameNotFoundException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());
        userRepository.delete(userEntity);
    }
    
    
    private UserDto convertToDto(UserEntity userEntity) {
     // Tạo TypeMap để map List<RoleEntity> thành List<String>
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<UserEntity, UserDto> typeMap = modelMapper.createTypeMap(UserEntity.class, UserDto.class);

        // Cấu hình mapping cho roles -> roleNames
        typeMap.addMappings(mapper -> mapper.using(ctx -> 
            ((List<RoleEntity>) ctx.getSource())
                .stream()
                .map(RoleEntity::getName)  // Lấy roleName từ RoleEntity
                .collect(Collectors.toList())
        ).map(UserEntity::getRoles, UserDto::setRoles));

        return typeMap.map(userEntity);
    }
}
