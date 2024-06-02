package com.example.demo.service;

import com.example.demo.dto.request.UserLoginDto;
import com.example.demo.dto.request.UserRegisterDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceAlreadyExistsExceptionHandler;
import com.example.demo.exception.ResourceNotFoundExceptionHandler;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserRegisterDto register(UserRegisterDto userRegisterDto) {

        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            throw new ResourceAlreadyExistsExceptionHandler("Nguời dùng", "username", userRegisterDto.getUsername());
        }

        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new ResourceAlreadyExistsExceptionHandler("Nguời dùng", "email", userRegisterDto.getEmail());
        }

        User user = new User();

        user.setName(userRegisterDto.getName());
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());

        // Bcryct password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        // Add roles for the user
        Role role = roleRepository.findByName(userRegisterDto.getRole());

        if(role == null) {
            throw new ResourceNotFoundExceptionHandler("role", userRegisterDto.getRole());
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Save user to database
        User savedUser = userRepository.save(user);

        // Convert the saved user to UserRegisterDto and return
        UserRegisterDto registeredUserDto = new UserRegisterDto();
        registeredUserDto.setName(savedUser.getName());
        registeredUserDto.setUsername(savedUser.getUsername());

        // Do not return password for security reasons
        registeredUserDto.setPassword(savedUser.getPassword());

        return registeredUserDto;
    }

    @Override
    public String login(UserLoginDto userLoginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginDto.getUsername(), userLoginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            return token;

        } catch (RuntimeException e) {
            throw new RuntimeException("Tên tài khoản hoặc mật khẩu không hợp lệ.");
        }
    }
}
