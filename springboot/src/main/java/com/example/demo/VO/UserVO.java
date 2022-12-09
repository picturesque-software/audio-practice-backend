package com.example.demo.VO;

import com.example.demo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer uid;

    private String username;

    private Integer step;

    public UserVO(User user){
        BeanUtils.copyProperties(user, this);
    }
}
