package com.wen.controller;

import com.wen.mapper.UserMapper;
import com.wen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    @ResponseBody
    public List<User> queryUserList(){
        List<User> userList=userMapper.queryUserList();

        for (User user:
             userList) {
            System.out.println(user);
        }

        return userList;
    }
}
