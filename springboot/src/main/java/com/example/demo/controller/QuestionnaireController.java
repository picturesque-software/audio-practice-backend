package com.example.demo.controller;

import com.example.demo.common.Response;
import com.example.demo.entity.Questionnaire;
import com.example.demo.mapper.QuestionnaireMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Resource
    QuestionnaireMapper audioRelationMapper;

    @GetMapping
    public Response<?> findById(){
        return Response.success("success");
    }

    @PostMapping("/submit")
    public Response<?> save(@RequestBody Questionnaire audioRelation){
        audioRelationMapper.insert(audioRelation);
        return Response.success();
    }

}
