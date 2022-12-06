package com.example.demo.service;

import com.example.demo.VO.Step1ResultVO;
import com.example.demo.common.Response;

public interface StepService {

    Response<?> getAudioForStep1();
    Response<?> getAudioForStep2(Integer uid);
    Response<?> submitStep1(Step1ResultVO step1ResultVO);
}
