package com.example.demo.controller;


import com.example.demo.VO.Step1ResultVO;
import com.example.demo.common.Response;
import com.example.demo.service.StepService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/step")
public class StepController {

    @Resource
    StepService stepService;

    @GetMapping("/getAudioForStep1")
    public Response<?> getAudioForStep1(){
        return stepService.getAudioForStep1();
    }

    @GetMapping("/getAudioForStep2")
    public Response<?> getAudioForStep2(Integer uid){
        return stepService.getAudioForStep2(uid);
    }

    @PostMapping("/submitStep1")
    public Response<?> submitStep1(@RequestBody Step1ResultVO step1ResultVO){
        return stepService.submitStep1(step1ResultVO);
    }
}
