package com.zts.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zts.comon.lang.Result;
import com.zts.entity.Blog;
import com.zts.service.BlogService;
import com.zts.service.UserService;
import com.zts.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    //详情页，这里要用到分页的。
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));//按照这个created列来进行排序。
        System.out.println(1);
        return Result.succ(pageData);
    }

    //删除
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable("id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已经被删除");

        return Result.succ(blog);
    }

    //修改
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        if(blog.getId() != null){   //编辑状态
            System.out.println("kkk");
            temp = blogService.getById(blog.getId());
            System.out.println("kkk");
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"你没有权限编辑");
        }else{      //添加状态。

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);

        }

        BeanUtil.copyProperties(blog, temp, "id","userId","created","status");  //用这个进行复制，后面的参数是忽略属性。
        blogService.saveOrUpdate(temp);
        return Result.succ(temp);
    }

    //删除博客
    @GetMapping("/del/{blogid}")
    public Result del(@PathVariable("blogid") int id){
        blogService.removeById(id);
        //System.out.println("调用了"+id);
        return Result.succ(null);
    }

}
