package com.mdubravac.fonts.services;

import com.mdubravac.fonts.dto.CredentialsDto;
import com.mdubravac.fonts.dto.SignUpDto;
import com.mdubravac.fonts.dto.UserDto;
import com.mdubravac.fonts.entities.User;
import com.mdubravac.fonts.enums.Role;
import com.mdubravac.fonts.exceptions.ApplicationException;
import com.mdubravac.fonts.mappers.UserMapper;
import com.mdubravac.fonts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new ApplicationException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin()).orElseThrow(() -> new ApplicationException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new ApplicationException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new ApplicationException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        System.out.println("This is user - ");
        System.out.println(user);
        System.out.println("But this, this is dto - ");
        System.out.println(userMapper.toUserDto(user));

        userRepository.save(user);
        return userMapper.toUserDto(user);
    }
}
