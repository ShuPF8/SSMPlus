package com.spf.service;

import com.baomidou.mybatisplus.service.IService;
import com.spf.common.ResultJson;
import com.spf.common.result.BizResult;
import com.spf.entity.SfUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2017-07-06
 */
@Repository
public interface ISfUserService extends IService<SfUser> {

    ResultJson save(SfUser s);

    BizResult< List<SfUser>> findAll();

    BizResult<SfUser> findByID(String id);

}
