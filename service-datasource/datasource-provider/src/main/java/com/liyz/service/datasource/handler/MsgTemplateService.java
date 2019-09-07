package com.liyz.service.datasource.handler;

import com.alibaba.fastjson.JSON;
import com.liyz.common.dao.service.AbstractService;
import com.liyz.service.datasource.constant.DatasourceConstant;
import com.liyz.service.datasource.dao.MsgTemplateMapper;
import com.liyz.service.datasource.model.MsgTemplateDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/6 11:29
 */
@Service
public class MsgTemplateService extends AbstractService<MsgTemplateDO> {

    @Autowired
    MsgTemplateMapper msgTemplateMapper;
    @Autowired
    RedissonService redissonService;

    public MsgTemplateDO getByCodeType(@NotNull Integer code, @NotNull Integer type) {
        String key = DatasourceConstant.REDIS_KEY_MSG_TEMPLATE + code + "_" + type;
        String value = redissonService.getValue(key);
        if (StringUtils.isBlank(value)) {
            MsgTemplateDO msgTemplateDO = new MsgTemplateDO();
            msgTemplateDO.setCode(code);
            msgTemplateDO.setType(type);
            msgTemplateDO = msgTemplateMapper.selectOne(msgTemplateDO);
            redissonService.setValue(key, JSON.toJSONString(msgTemplateDO));
            return msgTemplateDO;
        } else {
            return JSON.parseObject(value, MsgTemplateDO.class);
        }
    }
}
