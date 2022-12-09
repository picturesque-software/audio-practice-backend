package com.example.demo.service;

import com.example.demo.VO.AudioPairVO;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.VO.Step1ResultVO;
import com.example.demo.common.Response;

import java.util.List;

public interface StepService {

    Response<?> getAudioForStep1();
    Response<?> getAudioForStep2(Integer uid);
    Response<?> getAudioForStep3(Integer uid);
    Response<?> getAudioForStep4(Integer uid);
    Response<?> getAudioForStep5(Integer uid);
    Response<?> submitStep1(List<SingleResultVO> singleResultVOList);
    Response<?> submitStep2(List<SingleResultVO> singleResultVOList);
    Response<?> submitStep3(List<SingleResultVO> singleResultVOList);
    Response<?> submitStep4(List<AudioPairVO> audioPairVOList, Integer uid);
    Response<?> submitStep5(List<AudioPairVO> audioPairVOList, Integer uid);
}
