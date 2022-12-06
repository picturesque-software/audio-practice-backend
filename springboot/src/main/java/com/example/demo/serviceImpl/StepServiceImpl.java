package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.VO.AudioPairVO;
import com.example.demo.VO.AudioVO;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.VO.Step1ResultVO;
import com.example.demo.common.Response;
import com.example.demo.entity.Audio;
import com.example.demo.entity.Material;
import com.example.demo.entity.Result;
import com.example.demo.entity.ResultStep1;
import com.example.demo.mapper.ResultMapper;
import com.example.demo.mapper.ResultStep1Mapper;
import com.example.demo.service.AudioService;
import com.example.demo.service.StepService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("stepService")
public class StepServiceImpl implements StepService {

    private final AudioService audioService;
    private final UserService userService;

    private final ResultMapper resultMapper;
    private final ResultStep1Mapper resultStep1Mapper;

    @Autowired
    public StepServiceImpl(AudioService audioService, UserService userService, ResultMapper resultMapper, ResultStep1Mapper resultStep1Mapper){
        this.audioService = audioService;
        this.userService = userService;
        this.resultMapper = resultMapper;
        this.resultStep1Mapper = resultStep1Mapper;
    }

    @Override
    public Response<?> getAudioForStep1() {
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for(Material material: materialList){
            Integer id = material.getId();
            // 获取该素材的参考音频
            AudioVO referAudio = new AudioVO(audioService.getReferAudio(id));
            // 获取该素材所有前向
            List<Audio> audioList = audioService.list(new QueryWrapper<Audio>().eq("material", id).eq("process_mode", 1));
            int offset = audioList.size()/2;
            for(int i=0;i<audioList.size()/2;i++){
                AudioVO audioVO1 =new AudioVO(audioList.get(i));
                AudioVO audioVO2 =new AudioVO(audioList.get(i+offset));
                AudioPairVO audioPairVO = new AudioPairVO(audioVO1, audioVO2, referAudio);
                audioPairVOList.add(audioPairVO);
            }
            // TODO 获取素材所有后向
        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> getAudioForStep2(Integer uid) {
        return Response.success();
    }

    @Override
    public Response<?> submitStep1(Step1ResultVO step1ResultVO) {
        for(SingleResultVO singleResultVO: step1ResultVO.getScoreList()){
            Result result = new Result(singleResultVO);
            resultMapper.insert(result);
            ResultStep1 resultStep1 = new ResultStep1(singleResultVO);
            resultStep1Mapper.insert(resultStep1);
        }
        return Response.success("恭喜你，提交成功！");
    }
}
