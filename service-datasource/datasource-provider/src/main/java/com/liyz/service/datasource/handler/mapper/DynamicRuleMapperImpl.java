package com.liyz.service.datasource.handler.mapper;

import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.service.datasource.dao.DynamicRuleMapper;
import com.liyz.service.datasource.exception.RemoteDatasourceServiceException;
import com.liyz.service.datasource.handler.SqlTemplateService;
import com.liyz.service.datasource.model.DynamicRuleDO;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/4 19:15
 */
@Slf4j
public class DynamicRuleMapperImpl implements DynamicRuleMapper {

    private SqlSessionTemplate sqlSessionTemplate;
    private String mapper;

    public DynamicRuleMapperImpl(SqlSessionTemplate sqlSessionTemplate) {
        if (Objects.isNull(sqlSessionTemplate)) {
            throw new RemoteDatasourceServiceException(CommonCodeEnum.NonDatasource);
        }
        this.sqlSessionTemplate = sqlSessionTemplate;
        mapper = DynamicRuleMapper.class.getName();
    }

    public DynamicRuleMapperImpl(Integer tableId) {
        this(SqlTemplateService.getByTableId(tableId));
    }

    @Override
    public int dynamicRule(DynamicRuleDO dynamicRuleDO) {
        return sqlSessionTemplate.selectOne(mapper + ".dynamicRule", dynamicRuleDO);
    }
}
