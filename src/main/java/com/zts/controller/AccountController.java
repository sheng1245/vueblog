package com.zts.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zts.comon.dto.LoginDto;
import com.zts.comon.lang.Result;
import com.zts.entity.User;
import com.zts.service.UserService;
import com.zts.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @date 2020/10/7
 */
@RestController
//@CrossOrigin(origins = "*",exposedHeaders = {"token"})
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //Access-Control-Expose-Headers : 'Key'
    @CrossOrigin(exposedHeaders = {"token","Access-control-Expose-Headers"})
    @PostMapping(value = "/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        System.out.println(loginDto.getUsername()+" "+loginDto.getPassword());
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //System.out.println(user.getId());
        Assert.notNull(user,"用户不存在");   //断言进行处理
        System.out.println(SecureUtil.md5(loginDto.getPassword()));
        if(!user.getPassword().equals( SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());


        response.setHeader("token",jwt);
        response.setHeader("Access-control-Expose-Headers","token");
        //这个必须要设置的，不然拿不到token的，
        //System.out.println(response.getHeader("Authorization"));
        System.out.println(jwt);
        return Result.succ(MapUtil.builder()
                                    .put("id",user.getId())
                                    .put("username",user.getUsername())
                                    .put("evatar",user.getEvatar())
                                    .put("email",user.getEmail()).map());
    }

    @CrossOrigin
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
