package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Audio;
import com.example.demo.entity.AudioScore;
import com.example.demo.entity.ResultStep1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResultStep1Mapper extends BaseMapper<ResultStep1> {

//    @Select("SELECT t_question.*,t_student.`name` FROM t_question,t_student WHERE t_question.student_id=t_student.id")
//    List<AudioScore> getStep1ResultAudioByMP(Pagination page);
}
