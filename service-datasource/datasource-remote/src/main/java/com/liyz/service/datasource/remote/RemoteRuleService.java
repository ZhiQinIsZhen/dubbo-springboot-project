package com.liyz.service.datasource.remote;

import com.github.pagehelper.PageInfo;
import com.liyz.service.datasource.bo.RuleBO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 15:36
 */
public interface RemoteRuleService {

    Boolean add(@NotNull RuleBO ruleBO);

    List<RuleBO> list();

    PageInfo<RuleBO> listByPage(Integer page, Integer size);

    Boolean updateById(@NotNull RuleBO ruleBO);

    Boolean deleteById(@NotNull Integer id);
}
