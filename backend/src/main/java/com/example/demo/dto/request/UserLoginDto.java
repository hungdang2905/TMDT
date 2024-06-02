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
public class UserLoginDto {

    @NotNull(message = "Tên tài khoản không được null!")
    @NotBlank(message = "Tên tài khoản là bắt buộc!")
    @Size(min = 5, message = "Tên tài khoản tối thiểu 5 ký tự!")
    @Size(max = 50, message = "Tên tài khoản tối đa 50 ký tự!")
    private String username;
    private String password;

}
