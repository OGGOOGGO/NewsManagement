package com.zzhujing.news.management.controller.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReq {


    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
