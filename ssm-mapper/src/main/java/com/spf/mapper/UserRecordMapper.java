package com.spf.mapper;


import com.spf.entity.UserRecordEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordMapper {
    int deleteById(Integer id);

    int insert(UserRecordEntity record);

    int insertSelective(UserRecordEntity record);

    UserRecordEntity selectFindById(Integer id);

    int updateAllById(UserRecordEntity record);

    int updateById(UserRecordEntity record);
}