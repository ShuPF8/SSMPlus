package com.spf.service;

import com.spf.common.ResultJson;
import com.spf.common.redis.RedisCacheStorage;
import com.spf.common.result.BizResult;
import com.spf.entity.UserEntity;
import com.spf.common.enums.CommonResultCode;
import com.spf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	private static final String  cacheKey  ="userPo";
	@Autowired UserMapper userMapper;
	@Autowired private RedisCacheStorage redisCacheStorage;
	public ResultJson save(UserEntity uPo){
		ResultJson r = new ResultJson();
		int n = userMapper.insert(uPo);
		if (n > 0){
			boolean flag = redisCacheStorage.hset(cacheKey, uPo.getId().toString(), uPo);
			r.setCode(200);
			r.setMaseege("成功");
		} else {
			r.setCode(400);
			r.setMaseege("失败");
		}
		return r;
	}
	
	public ResultJson findById(Integer id){
		ResultJson r = new ResultJson();
		Map<String, Object> map = null;
		map = (Map<String, Object>) redisCacheStorage.hget(cacheKey, id.toString());
		if (map == null) {
			map = userMapper.findById(id);
		}
		if (map == null || map.isEmpty()) {
			r.setCode(400);
			r.setMaseege("失败");
		} else {
			r.setCode(200);
			r.setMaseege("成功");
			r.setData(map);
		}
		return r;
	}
	
	public BizResult<List<Map<String, Object>>> findAll(){
		BizResult<List<Map<String, Object>>> r = new BizResult<List<Map<String, Object>>>();
		List<Map<String, Object>> list = userMapper.findAll();
		//redisCacheStorage.set("list", list);
		if (list == null || list.isEmpty()) {
			r.setFail(CommonResultCode.NULL_ARGUMENT_EXCEPTION, "没有查询到数据");
		} else {
			r.setSuccess();
			r.setData(list);
		}
		return r;
	}
}
