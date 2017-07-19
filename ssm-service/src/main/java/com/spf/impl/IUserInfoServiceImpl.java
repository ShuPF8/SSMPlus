package com.spf.impl;

import com.spf.entity.UserInfo;
import com.spf.mapper.UserInfoMapper;
import com.spf.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ShuPF
 * @since 2017-07-10
 */
@Service
public class IUserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
	
}
