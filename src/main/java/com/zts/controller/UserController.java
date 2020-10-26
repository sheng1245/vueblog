package com.zts.controller;


import com.zts.comon.lang.Result;
import com.zts.entity.User;
import com.zts.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2020-10-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //@RequiresAuthentication    //需要进行登陆才能进行访问的接口
    @GetMapping("/{id}")
    public Result test(@PathVariable("id") Long id){
        User user =  userService.getById(id);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){

        System.out.println(1);
        return Result.succ(user);
    }
}
