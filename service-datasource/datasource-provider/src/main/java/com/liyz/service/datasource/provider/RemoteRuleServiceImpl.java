package com.liyz.service.datasource.provider;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.datasource.bo.RuleBO;
import com.liyz.service.datasource.constant.DatasourceEnum;
import com.liyz.service.datasource.dao.RuleMapper;
import com.liyz.service.datasource.handler.DynamicRuleService;
import com.liyz.service.datasource.model.RuleDO;
import com.liyz.service.datasource.remote.RemoteRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 15:37
 */
@Slf4j
@Service(timeout = 2000, version = "1.0.0", actives = 1000)
public class RemoteRuleServiceImpl implements RemoteRuleService {

    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    DynamicRuleService dynamicRuleService;

    @Override
    public Boolean add(@NotNull RuleBO ruleBO) {
        ruleBO.setCreateTime(new Date());
        ruleBO.setUpdateTime(new Date());
        RuleDO ruleDO = CommonConverterUtil.beanConverter(ruleBO, RuleDO.class);
        int count = ruleMapper.insert(ruleDO);
        if (ruleDO.getIsInactive() == 0) {
            dynamicRuleService.operateTask(ruleDO, DatasourceEnum.OperationType.INSERT);
        }
        return count == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public List<RuleBO> list() {
        return CommonConverterUtil.ListConverter(ruleMapper.selectAll(), RuleBO.class);
    }

    @Override
    public PageInfo<RuleBO> listByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<RuleDO> doList = ruleMapper.selectAll();
        List<RuleBO> boList = CommonConverterUtil.ListConverter(doList, RuleBO.class);
        return new PageInfo<>(boList);
    }

    @Override
    public Boolean updateById(@NotNull RuleBO ruleBO) {
        ruleBO.setUpdateTime(new Date());
        RuleDO ruleDO = CommonConverterUtil.beanConverter(ruleBO, RuleDO.class);
        int count = ruleMapper.updateByPrimaryKeySelective(ruleDO);
        ruleDO = ruleMapper.selectByPrimaryKey(ruleDO.getId());
        dynamicRuleService.operateTask(ruleDO, DatasourceEnum.OperationType.UPDATE);
        return count == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean deleteById(@NotNull Integer id) {
        int count = ruleMapper.deleteByPrimaryKey(id);
        RuleDO ruleDO = new RuleDO();
        ruleDO.setId(id);
        dynamicRuleService.operateTask(ruleDO, DatasourceEnum.OperationType.DELETE);
        return count == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
}
