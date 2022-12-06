package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Response;
import com.example.demo.entity.Audio;
import com.example.demo.entity.Material;
import com.example.demo.mapper.AudioMapper;
import com.example.demo.mapper.MaterialMapper;
import com.example.demo.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("audioService")
public class AudioServiceImpl extends ServiceImpl<AudioMapper, Audio> implements AudioService {

    private final MaterialMapper materialMapper;

    @Autowired
    public AudioServiceImpl(MaterialMapper materialMapper){
        this.materialMapper = materialMapper;
    }

    @Override
    public Response<?> getAllAudiosByMaterialByProcessMode(Integer material, Integer processMode) {
        List<Audio> audioList = list(new QueryWrapper<Audio>().eq("material", material).eq("process_mode", processMode));
        return Response.success(audioList);
    }

    @Override
    public List<Material> getAllMaterial() {
        return materialMapper.selectList(new QueryWrapper<Material>());
    }

    @Override
    public Audio getReferAudio(Integer id) {
        return getOne(new QueryWrapper<Audio>().eq("material", id).eq("process_mode", 0));
    }
}
