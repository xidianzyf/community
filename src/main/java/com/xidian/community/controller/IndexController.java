package com.xidian.community.controller;

import com.xidian.community.mapper.UserMapper;
import com.xidian.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Value("${com.env.name}")
    private String name;
    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        System.out.println("name = "+ name);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
