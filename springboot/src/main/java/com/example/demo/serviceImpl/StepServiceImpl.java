package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.VO.AudioPairVO;
import com.example.demo.VO.AudioVO;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.common.Response;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.service.AudioService;
import com.example.demo.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("stepService")
public class StepServiceImpl implements StepService {

    private final AudioService audioService;
    private final UserMapper userMapper;

    private final ResultMapper resultMapper;
    private final ResultStep1Mapper resultStep1Mapper;
    private final ResultStep2Mapper resultStep2Mapper;
    private final ResultStep3Mapper resultStep3Mapper;
    private final ResultStep4Mapper resultStep4Mapper;
    private final ResultStep5Mapper resultStep5Mapper;

    @Autowired
    public StepServiceImpl(AudioService audioService, UserMapper userMapper, ResultMapper resultMapper, ResultStep1Mapper resultStep1Mapper, ResultStep2Mapper resultStep2Mapper, ResultStep3Mapper resultStep3Mapper, ResultStep4Mapper resultStep4Mapper, ResultStep5Mapper resultStep5Mapper) {
        this.audioService = audioService;
        this.userMapper = userMapper;
        this.resultMapper = resultMapper;
        this.resultStep1Mapper = resultStep1Mapper;
        this.resultStep2Mapper = resultStep2Mapper;
        this.resultStep3Mapper = resultStep3Mapper;
        this.resultStep4Mapper = resultStep4Mapper;
        this.resultStep5Mapper = resultStep5Mapper;
    }

    @Override
    public Response<?> getAudioForStep1() {
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for (Material material : materialList) {
            Integer id = material.getId();
            // 获取该素材的参考音频
            AudioVO referAudio = new AudioVO(audioService.getReferAudio(id));
            // 获取该素材所有前向
            List<Audio> audioList = audioService.list(new QueryWrapper<Audio>().eq("material", id).eq("process_mode", 1));
            int offset = audioList.size() / 2;
            for (int i = 0; i < audioList.size() / 2; i++) {
                AudioVO audioVO1 = new AudioVO(audioList.get(i));
                AudioVO audioVO2 = new AudioVO(audioList.get(i + offset));
                AudioPairVO audioPairVO = new AudioPairVO(audioVO1, audioVO2, referAudio);
                audioPairVOList.add(audioPairVO);
            }
            // TODO 获取素材所有后向
        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> getAudioForStep2(Integer uid) {
        // 返回值
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 第一轮所有结果
        List<ResultStep1> resultStep1List = resultStep1Mapper.selectList(new QueryWrapper<ResultStep1>().eq("uid", uid));
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for (Material material : materialList) {
            Integer mid = material.getId();
            // 获取该素材所有前向
            List<ResultStep1> frontResultStep1List = resultStep1List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), 1)).collect(Collectors.toList());
            // list1: 1-0
            List<ResultStep1> frontResultStep1List1 = frontResultStep1List.stream().filter(obj -> Objects.equals(obj.getScore(), "1-0")).collect(Collectors.toList());
            // list2: 0-1
            List<ResultStep1> frontResultStep1List2 = frontResultStep1List.stream().filter(obj -> Objects.equals(obj.getScore(), "0-1")).collect(Collectors.toList());

            audioPairVOList.addAll(getPairFromResultList(frontResultStep1List1));
            audioPairVOList.addAll(getPairFromResultList(frontResultStep1List2));
            // TODO 获取该素材所有后向

        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> getAudioForStep3(Integer uid) {
        // 返回值
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 第二轮所有结果
        List<ResultStep2> resultStep2List = resultStep2Mapper.selectList(new QueryWrapper<ResultStep2>().eq("uid", uid));
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for (Material material : materialList) {
            Integer mid = material.getId();
            // 获取该素材所有前向
            List<ResultStep2> frontResultStep2List = resultStep2List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), 1)).collect(Collectors.toList());
            // list1: 2-0
            List<ResultStep2> frontResultStep2List1 = frontResultStep2List.stream().filter(obj -> Objects.equals(obj.getScore(), "2-0")).collect(Collectors.toList());
            // list2: 1-1
            List<ResultStep2> frontResultStep2List2 = frontResultStep2List.stream().filter(obj -> Objects.equals(obj.getScore(), "1-1")).collect(Collectors.toList());

            audioPairVOList.addAll(getPairFromResultList(frontResultStep2List1));
            audioPairVOList.addAll(getPairFromResultList(frontResultStep2List2));
            // TODO 获取该素材所有后向

        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> getAudioForStep4(Integer uid) {
        // 返回值
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 第三轮所有结果
        List<ResultStep3> resultStep3List = resultStep3Mapper.selectList(new QueryWrapper<ResultStep3>().eq("uid", uid));
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for (Material material : materialList) {
            Integer mid = material.getId();
            // 获取该素材所有前向，6个2-1
            List<ResultStep3> frontResultStep3List = resultStep3List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), 1) && Objects.equals(obj.getScore(), "2-1")).collect(Collectors.toList());
            // 构造冒泡
            List<AudioVO> audioVOList1 = new ArrayList<>();
            for(int i=0;i<frontResultStep3List.size()/2;i++){
                AudioVO audioVO = new AudioVO(audioService.getById(frontResultStep3List.get(i).getAid()));
                audioVOList1.add(audioVO);
            }
            audioPairVOList.add(new AudioPairVO(audioVOList1));
            List<AudioVO> audioVOList2 = new ArrayList<>();
            for(int i=frontResultStep3List.size()/2;i<frontResultStep3List.size();i++){
                AudioVO audioVO = new AudioVO(audioService.getById(frontResultStep3List.get(i).getAid()));
                audioVOList2.add(audioVO);
            }
            audioPairVOList.add(new AudioPairVO(audioVOList2));

            // TODO 获取该素材所有后向
        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> getAudioForStep5(Integer uid) {
        // 返回值
        List<AudioPairVO> audioPairVOList = new ArrayList<>();
        // 第三轮所有结果
        List<ResultStep3> resultStep3List = resultStep3Mapper.selectList(new QueryWrapper<ResultStep3>().eq("uid", uid));
        // 第4轮所有结果
        List<ResultStep4> resultStep4List = resultStep4Mapper.selectList(new QueryWrapper<ResultStep4>().eq("uid", uid));
        // 先获取所有material
        List<Material> materialList = audioService.getAllMaterial();
        for (Material material : materialList) {
            Integer mid = material.getId();
            // 获取该素材前向，2个3-0 and 1-0
            List<ResultStep3> frontResultStep3List = resultStep3List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), 1) && Objects.equals(obj.getScore(), "3-0")).collect(Collectors.toList());
            List<ResultStep4> frontResultStep4List = resultStep4List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), 1) && Objects.equals(obj.getScore(), "1-0")).collect(Collectors.toList());
            audioPairVOList.addAll(getPairFromResultList(frontResultStep3List));
            audioPairVOList.addAll(getPairFromResultList(frontResultStep4List));

            // TODO 获取该素材所有后向
        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> submitStep1(List<SingleResultVO> singleResultVOList) {
        for (SingleResultVO singleResultVO : singleResultVOList) {
            List<Integer> order = new ArrayList<>(singleResultVO.getOrder());
            order.remove(singleResultVO.getReferAid());
            if (singleResultVO.getProcessMode() == 1) {
                // 前向
                if (order.get(0) == singleResultVO.getAid()) {
                    singleResultVO.setScore("1-0");
                } else {
                    singleResultVO.setScore("0-1");
                }
            } else {
                // 后向
                if (order.get(0) == singleResultVO.getAid()) {
                    singleResultVO.setScore("0-1");
                } else {
                    singleResultVO.setScore("1-0");
                }
            }

            // step1 result
            ResultStep1 resultStep1 = new ResultStep1(singleResultVO);
            resultStep1.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
            resultStep1Mapper.insert(resultStep1);

            // 存入总结果表
            Result result = new Result(singleResultVO);
            resultMapper.insert(result);
        }

        updateUserStep(singleResultVOList.get(0).getUid(), 1);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep2(List<SingleResultVO> singleResultVOList) {
        for (SingleResultVO singleResultVO : singleResultVOList) {
            List<Integer> order = singleResultVO.getOrder();
            // 获取上一轮分数
            ResultStep1 resultStep1 = resultStep1Mapper.selectOne(new QueryWrapper<ResultStep1>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
            String[] splitScore = resultStep1.getScore().split("-");
            if ((singleResultVO.getProcessMode() == 1 && order.get(0) == singleResultVO.getAid()) || (singleResultVO.getProcessMode() == 2 && order.get(1) == singleResultVO.getAid())) {
                // 前向后向win!
                singleResultVO.setScore(Integer.parseInt(splitScore[0]) + 1 + "-" + splitScore[1]);
            } else {
                // lose
                singleResultVO.setScore(splitScore[0] + "-" + (Integer.parseInt(splitScore[1]) + 1));
            }
            // step2 result
            ResultStep2 resultStep2 = new ResultStep2(singleResultVO);
            resultStep2.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
            resultStep2Mapper.insert(resultStep2);
            // 存入总结果表
            if(Objects.equals(singleResultVO.getScore(), "0-2")) singleResultVO.setScore("0.1");
            resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid",singleResultVO.getAid()).eq("uid",singleResultVO.getUid()));
        }
        updateUserStep(singleResultVOList.get(0).getUid(), 2);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep3(List<SingleResultVO> singleResultVOList) {
        for (SingleResultVO singleResultVO : singleResultVOList) {
            List<Integer> order = singleResultVO.getOrder();
            // 获取上一轮分数
            ResultStep2 resultStep2 = resultStep2Mapper.selectOne(new QueryWrapper<ResultStep2>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
            setScoreFromLastStep(resultStep2.getScore(), singleResultVO);
            // step3 result
            ResultStep3 resultStep3 = new ResultStep3(singleResultVO);
            resultStep3.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
            resultStep3Mapper.insert(resultStep3);
            // 存入总结果表
            if(Objects.equals(singleResultVO.getScore(), "1-2")) singleResultVO.setScore("0.3");
            resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid",singleResultVO.getAid()).eq("uid",singleResultVO.getUid()));
        }
        updateUserStep(singleResultVOList.get(0).getUid(), 3);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep4(List<AudioPairVO> audioPairVOList, Integer uid) {
        for(AudioPairVO audioPairVO : audioPairVOList){
            // get order[]
            List<Integer> order = new ArrayList<>();
            for(AudioVO audioVO: audioPairVO.getAudioList()){
                order.add(audioVO.getAid());
            }
            for(int i=0;i<audioPairVO.getAudioList().size();i++){
                SingleResultVO singleResultVO = new SingleResultVO(audioPairVO.getAudioList().get(i));
                singleResultVO.setUid(uid);
                if(i==2){
                    // last is best
                    singleResultVO.setScore("1-0");
                    singleResultVO.setOrder(order);
                }else{
                    singleResultVO.setScore("0-1");
                    singleResultVO.setOrder(order);
                }
                ResultStep4 resultStep4 = new ResultStep4(singleResultVO);
                resultStep4.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
                resultStep4Mapper.insert(resultStep4);
                if(Objects.equals(singleResultVO.getScore(), "0-1")){
                    singleResultVO.setScore("0.5");
                    resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid",singleResultVO.getAid()).eq("uid",singleResultVO.getUid()));
                }
            }
        }
        updateUserStep(uid, 4);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep5(List<AudioPairVO> audioPairVOList, Integer uid) {
        for(AudioPairVO audioPairVO : audioPairVOList){

            for(int i=0;i<audioPairVO.getAudioList().size();i++){
                SingleResultVO singleResultVO = new SingleResultVO(audioPairVO.getAudioList().get(i));
                singleResultVO.setUid(uid);
                ResultStep5 resultStep5 = new ResultStep5(singleResultVO);
                resultStep5Mapper.insert(resultStep5);
                if(Objects.equals(singleResultVO.getScore(), "2-0")){
                    singleResultVO.setScore("1.0");
                }else if(Objects.equals(singleResultVO.getScore(), "1-1")){
                    singleResultVO.setScore("0.8");
                }else{
                    singleResultVO.setScore("0.7");
                }
                resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid",singleResultVO.getAid()).eq("uid",singleResultVO.getUid()));
            }
        }
        updateUserStep(uid, 5);
        return Response.success("恭喜你，提交成功！");
    }


    // 将database中待评测音频配对
    List<AudioPairVO> getPairFromResultList(List<?> resultStepList) {
        // 返回值
        List<AudioPairVO> resList = new ArrayList<>();
        int offset = resultStepList.size() / 2;
        for (int i = 0; i < resultStepList.size() / 2; i++) {
            Integer aid1,aid2;
            if(resultStepList.get(i) instanceof ResultStep1){
                aid1 = ((ResultStep1)resultStepList.get(i)).getAid();
                aid2 = ((ResultStep1)resultStepList.get(i + offset)).getAid();
            }else if(resultStepList.get(i) instanceof ResultStep2){
                aid1 = ((ResultStep2)resultStepList.get(i)).getAid();
                aid2 = ((ResultStep2)resultStepList.get(i + offset)).getAid();
            }
            else if(resultStepList.get(i) instanceof ResultStep3){
                aid1 = ((ResultStep3)resultStepList.get(i)).getAid();
                aid2 = ((ResultStep3)resultStepList.get(i + offset)).getAid();
            }else {
                aid1 = ((ResultStep4)resultStepList.get(i)).getAid();
                aid2 = ((ResultStep4)resultStepList.get(i + offset)).getAid();
            }
            AudioVO audioVO1 = new AudioVO(audioService.getById(aid1));
            AudioVO audioVO2 = new AudioVO(audioService.getById(aid2));
            AudioPairVO audioPairVO = new AudioPairVO(audioVO1, audioVO2);
            resList.add(audioPairVO);
        }
        return resList;
    }

    void updateUserStep(Integer uid, Integer step) {
        User user = userMapper.selectById(uid);
        user.setStep(step);
        userMapper.updateById(user);
        Response.success();
    }

    void setScoreFromLastStep(String scoreLastStep, SingleResultVO singleResultVO){
        String[] splitScore = scoreLastStep.split("-");
        if ((singleResultVO.getProcessMode() == 1 && singleResultVO.getOrder().get(0) == singleResultVO.getAid()) || (singleResultVO.getProcessMode() == 2 && singleResultVO.getOrder().get(1) == singleResultVO.getAid())) {
            // 前向后向win!
            singleResultVO.setScore(Integer.parseInt(splitScore[0]) + 1 + "-" + splitScore[1]);
        } else {
            // lose
            singleResultVO.setScore(splitScore[0] + "-" + (Integer.parseInt(splitScore[1]) + 1));
        }
    }
}
