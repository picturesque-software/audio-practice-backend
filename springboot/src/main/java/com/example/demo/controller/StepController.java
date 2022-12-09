package com.example.demo.controller;


import com.example.demo.VO.AudioPairVO;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.VO.Step1ResultVO;
import com.example.demo.common.Response;
import com.example.demo.service.StepService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/getAudioForStep3")
    public Response<?> getAudioForStep3(Integer uid){
        return stepService.getAudioForStep3(uid);
    }

    @GetMapping("/getAudioForStep4")
    public Response<?> getAudioForStep4(Integer uid){
        return stepService.getAudioForStep4(uid);
    }

    @GetMapping("/getAudioForStep5")
    public Response<?> getAudioForStep5(Integer uid){
        return stepService.getAudioForStep5(uid);
    }

    @PostMapping("/submitStep1")
    public Response<?> submitStep1(@RequestBody List<SingleResultVO> singleResultVOList){
        return stepService.submitStep1(singleResultVOList);
    }

    @PostMapping("/submitStep2")
    public Response<?> submitStep2(@RequestBody List<SingleResultVO> singleResultVOList){
        return stepService.submitStep2(singleResultVOList);
    }

    @PostMapping("/submitStep3")
    public Response<?> submitStep3(@RequestBody List<SingleResultVO> singleResultVOList){
        return stepService.submitStep3(singleResultVOList);
    }

    @PostMapping("/submitStep4")
    public Response<?> submitStep4(Integer uid, @RequestBody List<AudioPairVO> audioPairVOList){
        return stepService.submitStep4(audioPairVOList, uid);
    }

    @PostMapping("/submitStep5")
    public Response<?> submitStep5(Integer uid, @RequestBody List<AudioPairVO> audioPairVOList){
        return stepService.submitStep5(audioPairVOList, uid);
    }
}
