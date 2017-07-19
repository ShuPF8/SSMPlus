package com.spf.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.spf.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<UserEntity>{

	Integer insert(@Param("user") UserEntity uPo);
	
	Map<String, Object> findById(@Param("id") int id);
	
	List<Map<String, Object>> findAll();
}
