package com.example.demo.controller;

import com.example.demo.dto.request.UserLoginDto;
import com.example.demo.dto.request.UserRegisterDto;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.JWTAuthResponse;
import com.example.demo.exception.ResourceAlreadyExistsExceptionHandler;
import com.example.demo.exception.ResourceNotFoundExceptionHandler;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisterDto>> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        try{

            UserRegisterDto registerUser = authService.register(userRegisterDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(HttpStatus.CREATED.value(), null, "Đăng ký thành công!", registerUser));

        } catch (ResourceAlreadyExistsExceptionHandler ex) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), null));

        } catch (ResourceNotFoundExceptionHandler ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), null));

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Đăng ký thất bại: " + ex.getMessage(), null));

        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JWTAuthResponse>> authResponseResponseEntity(@RequestBody UserLoginDto userLoginDto) {

        try {

            String token = authService.login(userLoginDto);

            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
            jwtAuthResponse.setAccessToken(token);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            HttpStatus.OK.value(),
                            null,
                            "Đăng nhập thành công!",
                            jwtAuthResponse
                    ));

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Đăng nhập thất bại: " + ex.getMessage(), null));

        }
    }

}
