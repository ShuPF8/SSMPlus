package com.spf.service;

import com.spf.entity.UserRecordEntity;
import com.spf.mapper.UserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author SPF
 * @Date 2017/5/2
 */
@Service
public class UserRecordService {

    @Autowired
    private UserRecordMapper userRecordMapper;

    public int insert(UserRecordEntity userRecord) {
        return userRecordMapper.insert(userRecord);
    }

}
