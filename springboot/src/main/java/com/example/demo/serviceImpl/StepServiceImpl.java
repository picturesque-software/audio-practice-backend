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
import javafx.scene.transform.MatrixType;
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
            referAudio.setUrl(getUrl(referAudio.getMaterial(),referAudio.getProcessMode(), referAudio.getAlgorithm(), 0 ));
            referAudio.setReverb1(getUrl(referAudio.getMaterial(),referAudio.getProcessMode(), referAudio.getAlgorithm(), 1 ));
            referAudio.setReverb2(getUrl(referAudio.getMaterial(),referAudio.getProcessMode(), referAudio.getAlgorithm(), 2 ));
            // 获取该素材所有前向 后向
            for (int j = 1; j <= 2; j++) {
                // 遍历前后向
                List<Audio> audioList = audioService.list(new QueryWrapper<Audio>().eq("material", id).eq("process_mode", j));
                for(Audio audio : audioList){
                    audio.setUrl(getUrl(audio.getMaterial(),audio.getProcessMode(), audio.getAlgorithm(), 0 ));
                    audio.setReverb1(getUrl(audio.getMaterial(),audio.getProcessMode(), audio.getAlgorithm(), 1 ));
                    audio.setReverb2(getUrl(audio.getMaterial(),audio.getProcessMode(), audio.getAlgorithm(), 2 ));
                }
                int offset = audioList.size() / 2;
                for (int i = 0; i < audioList.size() / 2; i++) {
                    AudioVO audioVO1 = new AudioVO(audioList.get(i));
                    AudioVO audioVO2 = new AudioVO(audioList.get(i + offset));
                    AudioPairVO audioPairVO = new AudioPairVO(audioVO1, audioVO2, referAudio);
                    audioPairVOList.add(audioPairVO);
                }
            }
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
            for (int j = 1; j <= 2; j++) {
                // 获取该素材所有前向
                int finalJ = j;
                List<ResultStep1> frontResultStep1List = resultStep1List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), finalJ)).collect(Collectors.toList());
                // list1: 1-0
                List<ResultStep1> frontResultStep1List1 = frontResultStep1List.stream().filter(obj -> Objects.equals(obj.getScore(), "1-0")).collect(Collectors.toList());
                // list2: 0-1
                List<ResultStep1> frontResultStep1List2 = frontResultStep1List.stream().filter(obj -> Objects.equals(obj.getScore(), "0-1")).collect(Collectors.toList());

                audioPairVOList.addAll(getPairFromResultList(frontResultStep1List1));
                audioPairVOList.addAll(getPairFromResultList(frontResultStep1List2));
            }
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
            for (int j = 1; j <= 2; j++) {
                // 获取该素材所有前向
                int finalJ = j;
                List<ResultStep2> frontResultStep2List = resultStep2List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), finalJ)).collect(Collectors.toList());
                // list1: 2-0
                List<ResultStep2> frontResultStep2List1 = frontResultStep2List.stream().filter(obj -> Objects.equals(obj.getScore(), "2-0")).collect(Collectors.toList());
                // list2: 1-1
                List<ResultStep2> frontResultStep2List2 = frontResultStep2List.stream().filter(obj -> Objects.equals(obj.getScore(), "1-1")).collect(Collectors.toList());

                audioPairVOList.addAll(getPairFromResultList(frontResultStep2List1));
                audioPairVOList.addAll(getPairFromResultList(frontResultStep2List2));
                // TODO 获取该素材所有后向
            }
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
            for (int j = 1; j <= 2; j++) {
                // 获取该素材所有前向，6个2-1
                int finalJ = j;
                List<ResultStep3> frontResultStep3List = resultStep3List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), finalJ) && Objects.equals(obj.getScore(), "2-1")).collect(Collectors.toList());
                // 构造冒泡，每组三个返回给前端
                List<AudioVO> audioVOList1 = new ArrayList<>();
                for (int i = 0; i < frontResultStep3List.size() / 2; i++) {
                    AudioVO audioVO = new AudioVO(audioService.getById(frontResultStep3List.get(i).getAid()));
                    audioVOList1.add(audioVO);
                }
                audioPairVOList.add(new AudioPairVO(audioVOList1));
                List<AudioVO> audioVOList2 = new ArrayList<>();
                for (int i = frontResultStep3List.size() / 2; i < frontResultStep3List.size(); i++) {
                    AudioVO audioVO = new AudioVO(audioService.getById(frontResultStep3List.get(i).getAid()));
                    audioVOList2.add(audioVO);
                }
                audioPairVOList.add(new AudioPairVO(audioVOList2));
            }
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
            for (int j = 1; j <= 2; j++) {
                // 获取该素材前向，2个3-0 and 1-0
                int finalJ = j;
                List<ResultStep3> frontResultStep3List = resultStep3List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), finalJ) && Objects.equals(obj.getScore(), "3-0")).collect(Collectors.toList());
                int finalJ1 = j;
                List<ResultStep4> frontResultStep4List = resultStep4List.stream().filter(obj -> Objects.equals(obj.getMaterial(), mid) && Objects.equals(obj.getProcessMode(), finalJ1) && Objects.equals(obj.getScore(), "1-0")).collect(Collectors.toList());
                audioPairVOList.addAll(getPairFromResultList(frontResultStep3List));
                audioPairVOList.addAll(getPairFromResultList(frontResultStep4List));
            }
        }
        return Response.success(audioPairVOList);
    }

    @Override
    public Response<?> submitStep1(List<SingleResultVO> singleResultVOList) {
        SingleResultVO singleResultVO0 = singleResultVOList.get(0);
        List<ResultStep1> resultStep1Temp = resultStep1Mapper.selectList(new QueryWrapper<ResultStep1>().eq("uid", singleResultVO0.getUid()));
        if (!resultStep1Temp.isEmpty()) {
            return Response.error("-1", "请勿重复提交！");
        }
        for (SingleResultVO singleResultVO : singleResultVOList) {
            List<Integer> order = new ArrayList<>(singleResultVO.getOrder());
            order.remove(singleResultVO.getReferAid());
            if (singleResultVO.getProcessMode() == 1) {
                // 前向
                if (order.get(0).equals(singleResultVO.getAid())) {
                    singleResultVO.setScore("1-0");
                } else {
                    singleResultVO.setScore("0-1");
                }
            } else {
                // 后向
                if (order.get(0).equals(singleResultVO.getAid())) {
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
        SingleResultVO singleResultVO0 = singleResultVOList.get(0);
        List<ResultStep2> resultStep2Temp = resultStep2Mapper.selectList(new QueryWrapper<ResultStep2>().eq("uid", singleResultVO0.getUid()));
        if (!resultStep2Temp.isEmpty()) {
            return Response.error("-1", "请勿重复提交！");
        }
        for (SingleResultVO singleResultVO : singleResultVOList) {
            List<Integer> order = singleResultVO.getOrder();
            // 获取上一轮分数
            ResultStep1 resultStep1 = resultStep1Mapper.selectOne(new QueryWrapper<ResultStep1>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
            setScoreFromLastStep(resultStep1.getScore(), singleResultVO);
            // step2 result
            ResultStep2 resultStep2 = new ResultStep2(singleResultVO);
            resultStep2.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
            resultStep2Mapper.insert(resultStep2);
            // 存入总结果表
            if (Objects.equals(singleResultVO.getScore(), "0-2")) singleResultVO.setScore("0.1");
            resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
        }
        updateUserStep(singleResultVOList.get(0).getUid(), 2);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep3(List<SingleResultVO> singleResultVOList) {
        SingleResultVO singleResultVO0 = singleResultVOList.get(0);
        List<ResultStep3> resultStep3Temp = resultStep3Mapper.selectList(new QueryWrapper<ResultStep3>().eq("uid", singleResultVO0.getUid()));
        if (!resultStep3Temp.isEmpty()) {
            return Response.error("-1", "请勿重复提交！");
        }
        for (SingleResultVO singleResultVO : singleResultVOList) {
            // 获取上一轮分数
            ResultStep2 resultStep2 = resultStep2Mapper.selectOne(new QueryWrapper<ResultStep2>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
            setScoreFromLastStep(resultStep2.getScore(), singleResultVO);
            // step3 result
            ResultStep3 resultStep3 = new ResultStep3(singleResultVO);
            resultStep3.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
            resultStep3Mapper.insert(resultStep3);
            // 存入总结果表
            if (Objects.equals(singleResultVO.getScore(), "1-2")) singleResultVO.setScore("0.3");
            resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
        }
        updateUserStep(singleResultVOList.get(0).getUid(), 3);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep4(List<AudioPairVO> audioPairVOList, Integer uid) {
        List<ResultStep4> resultStep4Temp = resultStep4Mapper.selectList(new QueryWrapper<ResultStep4>().eq("uid", uid));
        if (!resultStep4Temp.isEmpty()) {
            return Response.error("-1", "请勿重复提交！");
        }
        for (AudioPairVO audioPairVO : audioPairVOList) {
            // get order[]
            List<Integer> order = new ArrayList<>();
            for (AudioVO audioVO : audioPairVO.getAudioList()) {
                order.add(audioVO.getAid());
            }
            for (int i = 0; i < audioPairVO.getAudioList().size(); i++) {
                SingleResultVO singleResultVO = new SingleResultVO(audioPairVO.getAudioList().get(i));
                singleResultVO.setUid(uid);
                if (i == 2) {
                    // last is best
                    singleResultVO.setScore("1-0");
                    singleResultVO.setOrder(order);
                } else {
                    singleResultVO.setScore("0-1");
                    singleResultVO.setOrder(order);
                }
                ResultStep4 resultStep4 = new ResultStep4(singleResultVO);
                resultStep4.setAudioOrder(singleResultVO.getOrder().stream().map(String::valueOf).collect(Collectors.toList()));
                resultStep4Mapper.insert(resultStep4);
                if (Objects.equals(singleResultVO.getScore(), "0-1")) {
                    singleResultVO.setScore("0.5");
                    resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
                }
            }
        }
        updateUserStep(uid, 4);
        return Response.success("恭喜你，提交成功！");
    }

    @Override
    public Response<?> submitStep5(List<AudioPairVO> audioPairVOList, Integer uid) {
        List<ResultStep5> resultStep5Temp = resultStep5Mapper.selectList(new QueryWrapper<ResultStep5>().eq("uid", uid));
        if (!resultStep5Temp.isEmpty()) {
            return Response.error("-1", "请勿重复提交！");
        }
        for (AudioPairVO audioPairVO : audioPairVOList) {

            for (int i = 0; i < audioPairVO.getAudioList().size(); i++) {
                SingleResultVO singleResultVO = new SingleResultVO(audioPairVO.getAudioList().get(i));
                singleResultVO.setUid(uid);
                ResultStep5 resultStep5 = new ResultStep5(singleResultVO);
                resultStep5Mapper.insert(resultStep5);
                if (Objects.equals(singleResultVO.getScore(), "2-0")) {
                    singleResultVO.setScore("1.0");
                } else if (Objects.equals(singleResultVO.getScore(), "1-1")) {
                    singleResultVO.setScore("0.8");
                } else {
                    singleResultVO.setScore("0.7");
                }
                resultMapper.update(new Result(singleResultVO), new UpdateWrapper<Result>().eq("aid", singleResultVO.getAid()).eq("uid", singleResultVO.getUid()));
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
            Integer aid1, aid2;
            if (resultStepList.get(i) instanceof ResultStep1) {
                aid1 = ((ResultStep1) resultStepList.get(i)).getAid();
                aid2 = ((ResultStep1) resultStepList.get(i + offset)).getAid();
            } else if (resultStepList.get(i) instanceof ResultStep2) {
                aid1 = ((ResultStep2) resultStepList.get(i)).getAid();
                aid2 = ((ResultStep2) resultStepList.get(i + offset)).getAid();
            } else if (resultStepList.get(i) instanceof ResultStep3) {
                aid1 = ((ResultStep3) resultStepList.get(i)).getAid();
                aid2 = ((ResultStep3) resultStepList.get(i + offset)).getAid();
            } else {
                aid1 = ((ResultStep4) resultStepList.get(i)).getAid();
                aid2 = ((ResultStep4) resultStepList.get(i + offset)).getAid();
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

    void setScoreFromLastStep(String scoreLastStep, SingleResultVO singleResultVO) {
        String[] splitScore = scoreLastStep.split("-");
        if ((singleResultVO.getProcessMode() == 1 && singleResultVO.getOrder().get(0).equals(singleResultVO.getAid())) || (singleResultVO.getProcessMode() == 2 && singleResultVO.getOrder().get(1).equals(singleResultVO.getAid()))) {
            // 前向后向win!
            singleResultVO.setScore(Integer.parseInt(splitScore[0]) + 1 + "-" + splitScore[1]);
        } else {
            // lose
            singleResultVO.setScore(splitScore[0] + "-" + (Integer.parseInt(splitScore[1]) + 1));
        }
    }

    String getUrl(Integer material, Integer processMode, Integer algorithm, Integer reverb) {
        String baseUrl = "https://audio-practice.oss-cn-hangzhou.aliyuncs.com/audios/";
        if (material == 1) {
            baseUrl += "%E6%9E%AA%E5%A3%B0";
        } else if (material == 2) {
            baseUrl += "%E8%84%9A%E6%AD%A5";
        } else if (material == 3) {
            baseUrl += "%E7%94%B7%E5%A3%B0";
        } else {
            baseUrl += "%E5%A5%B3%E5%A3%B0";
        }
        if (processMode == 0) {
            return baseUrl + "_reverb_" + reverb.toString() + ".wav";
        } else {
            return baseUrl + "_HRTF" + (processMode == 1 ? "_front" : "_back") + algorithm + "_reverb_" + reverb.toString() + ".wav";
        }
    }
}
