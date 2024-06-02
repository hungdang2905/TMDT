package com.example.demo.service;

import com.example.demo.dto.request.UserGetAllDto;
import com.example.demo.dto.request.UserGetByIdDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserGetAllDto> getAllUsers() {
        // Lấy danh sách tất cả người dùng từ UserRepository
        List<User> users = userRepository.findAll();

        // Khởi tạo danh sách để lưu trữ kết quả
        List<UserGetAllDto> userDtos = new ArrayList<>();

        // Duyệt qua từng User và ánh xạ dữ liệu vào UserRegisterDto
        for (User user : users) {
            UserGetAllDto userDto = new UserGetAllDto();

            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());


            userDtos.add(userDto);
        }

        return userDtos;
    }

    @Override
    public UserGetByIdDto getUserById(Long userId, String currentUsername) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getUsername().equals(currentUsername)) {
                Set<String> userRoleNames = user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

                return new UserGetByIdDto(user.getId(), user.getName(), user.getEmail(), user.getUsername(), userRoleNames);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}

