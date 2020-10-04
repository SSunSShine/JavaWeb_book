package com.atguigu.test;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void registerUser() {
        userService.registerUser(new User(null, "sjw2", "123456", "222222@qq.com"));
        userService.registerUser(new User(null, "sjw3", "123456", "222222@qq.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null, "sjw2", "123456", "222222@qq.com")));
    }

    @Test
    public void existsUsername() {
        if(userService.existsUsername("sjw1234")){
            System.out.println("用户名已存在！");
        }else{
            System.out.println("用户名可用！");
        }
    }
}