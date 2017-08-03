package com.spf.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.spf.common.ResultJson;
import com.spf.common.enums.CommonResultCode;
import com.spf.common.result.BizResult;
import com.spf.entity.SfUser;
import com.spf.mapper.SfUserMapper;
import com.spf.service.ISfUserService;
import com.spf.service.Single;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-07-06
 */
@Service
public class ISfUserServiceImpl extends ServiceImpl<SfUserMapper, SfUser> implements ISfUserService {
    public ResultJson save(SfUser s) {
        ResultJson result = new ResultJson();
        int n = baseMapper.insert(s);
        if (n > 0) {
            result.success();
        }
        return result;
    }

    public BizResult< List<SfUser>> findAll() {
        Single s = Single.getInstance();
        s.getTO();
        BizResult< List<SfUser>> result = new BizResult< List<SfUser>>();
        List<SfUser> list = baseMapper.selectList(new EntityWrapper<SfUser>());
        if (list == null || list.isEmpty()) {
            result.setFail(CommonResultCode.NULL_ARGUMENT_EXCEPTION,"没有查询到数据");
            return result;
        }
        result.setSuccess();
        result.setData(list);
        return result;
    }

    public BizResult<SfUser> findByID(String id) {
        BizResult<SfUser> result= new BizResult<SfUser>();
        SfUser sfUser = baseMapper.selectById(id);
        result.setData(sfUser);
        result.setSuccess();
        return result;
    }
}
