package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Response;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper bookMapper;


    @PostMapping
    public Response<?> save(@RequestBody Book book){
        bookMapper.insert(book);
        return Response.success();
    }


    @PutMapping("/update")
    public Response<?> update(@RequestBody Book book){
        bookMapper.updateById(book);
        return Response.success();
    }


    @DeleteMapping("/delete/{id}")
    public Response<?> delete(@PathVariable Long id){
        bookMapper.deleteById(id);
        return Response.success();
    }


    @GetMapping("/get")
    public Response<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search ){
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getName, search);
        }
        Page<Book> bookPage = bookMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Response.success(bookPage);
    }


    @GetMapping("/get/{id}")
    public Response<?> findById(@PathVariable Long id){
        return Response.success(bookMapper.selectById(id));
    }
}
