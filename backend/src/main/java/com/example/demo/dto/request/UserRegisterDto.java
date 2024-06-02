package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterDto {

//    @NotNull(message = "Họ và tên không được null!")
//    @NotBlank(message = "Họ và tên là bắt buộc!")
    @Size(min = 5, message = "Họ và tên tối thiểu 5 ký tự!")
//    @Size(max = 50, message = "Họ và tên tối đa 50 ký tự!")
    private String name;
    private String email;
    private String username;
    private String password;
    private String role;

}
