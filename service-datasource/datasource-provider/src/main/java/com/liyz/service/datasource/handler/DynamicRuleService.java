package com.liyz.service.datasource.handler;

import com.liyz.service.datasource.constant.DatasourceConstant;
import com.liyz.service.datasource.constant.DatasourceEnum;
import com.liyz.service.datasource.dao.DynamicRuleMapper;
import com.liyz.service.datasource.dao.RuleMapper;
import com.liyz.service.datasource.handler.mapper.DynamicRuleMapperImpl;
import com.liyz.service.datasource.model.DynamicRuleDO;
import com.liyz.service.datasource.model.MsgTemplateDO;
import com.liyz.service.datasource.model.RuleDO;
import com.liyz.service.datasource.util.EmailMessage;
import com.liyz.service.datasource.util.EmailUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/4 19:30
 */
@Slf4j
@Service
public class DynamicRuleService {

    @Getter
    private static final Map<Integer, ScheduledFuture> FUTURE_MAP = new ConcurrentHashMap<>();

    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    TaskScheduler taskScheduler;
    @Autowired
    MsgTemplateService msgTemplateService;

    @PostConstruct
    private void init() {
        log.info("start init dynamicRuleTask ......");
        try {
            RuleDO condition = new RuleDO();
            condition.setIsInactive(0);
            List<RuleDO> doList = ruleMapper.select(condition);
            if (!CollectionUtils.isEmpty(doList)) {
                log.info("一共需要初始化 {} 条数的校验规则", doList.size());
                for (RuleDO ruleDO : doList) {
                    operateTask(ruleDO, DatasourceEnum.OperationType.INSERT);
                }
            }
            log.info("init dynamicRuleTask success ......");
        } catch (Exception e) {
            log.error("init dynamicRuleTask fail ......", e);
        }

    }

    public void operateTask(RuleDO ruleDO, DatasourceEnum.OperationType operationType) {
        switch (operationType) {
            case INSERT:
                ScheduledFuture future =taskScheduler.schedule(getTask(ruleDO), new CronTrigger(ruleDO.getCorn()));
                FUTURE_MAP.putIfAbsent(ruleDO.getId(), future);
                break;
            case UPDATE:
                ScheduledFuture oldFuture = FUTURE_MAP.get(ruleDO.getId());
                if (Objects.nonNull(oldFuture)) {
                    oldFuture.cancel(true);
                }
                FUTURE_MAP.remove(ruleDO.getId());
                if (ruleDO.getIsInactive() == 0) {
                    future =taskScheduler.schedule(getTask(ruleDO), new CronTrigger(ruleDO.getCorn()));
                    FUTURE_MAP.putIfAbsent(ruleDO.getId(), future);
                }
                break;
            case DELETE:
                oldFuture = FUTURE_MAP.get(ruleDO.getId());
                if (Objects.nonNull(oldFuture)) {
                    oldFuture.cancel(true);
                }
                FUTURE_MAP.remove(ruleDO.getId());
                break;
            default:
                break;
        }
    }

    private Thread getTask(RuleDO ruleDO) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (DatasourceConstant.RULE_TOTAL_NUM_TYPE.equals(ruleDO.getType())) {
                    DynamicRuleDO dynamicRuleDO = new DynamicRuleDO();
                    dynamicRuleDO.setTable(ruleDO.getTableName());
                    dynamicRuleDO.setCondition(null);
                    DynamicRuleMapper dynamicRuleMapper = new DynamicRuleMapperImpl(ruleDO.getTableId());
                    int count = dynamicRuleMapper.dynamicRule(dynamicRuleDO);
                    if (count <= Integer.valueOf(ruleDO.getValue())) {
                        log.error("规则不成立");
                        MsgTemplateDO msgTemplateDO = msgTemplateService.getByCodeType(DatasourceConstant.RULE_WARN_TYPE, DatasourceConstant.EMAIL_TYPE);
                        List<String> params = new ArrayList<>();
                        params.add(ruleDO.getName());
                        EmailMessage emailMessage = EmailMessage.builder()
                                .address(ruleDO.getLeaderEmail())
                                .content(msgTemplateDO.getContent())
                                .subject("数据质量规则触发预警")
                                .params(params)
                                .build();
                        EmailUtil.sendSmtpEmail(emailMessage);
                    }
                }
            }
        });
        return thread;
    }
}
