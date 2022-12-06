package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Response;
import com.example.demo.entity.News;
import com.example.demo.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    NewsMapper newsMapper;


    @PostMapping
    public Response<?> save(@RequestBody News news){
        news.setTime(new Date());
        newsMapper.insert(news);
        return Response.success();
    }


    @PutMapping("/update")
    public Response<?> update(@RequestBody News news){
        newsMapper.updateById(news);
        return Response.success();
    }


    @DeleteMapping("/delete/{id}")
    public Response<?> delete(@PathVariable Long id){
        newsMapper.deleteById(id);
        return Response.success();
    }


    @GetMapping("/get")
    public Response<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search ){
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Response.success(newsPage);
    }


    @GetMapping("/get/{id}")
    public Response<?> findById(@PathVariable Long id){
        return Response.success(newsMapper.selectById(id));
    }
}
