package com.liyz.service.datasource.provider;

import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.datasource.bo.DataTableBO;
import com.liyz.service.datasource.bo.SystemBO;
import com.liyz.service.datasource.bo.TableColumnBO;
import com.liyz.service.datasource.dao.SystemMapper;
import com.liyz.service.datasource.exception.RemoteDatasourceServiceException;
import com.liyz.service.datasource.handler.mapper.SystemMapperImpl;
import com.liyz.service.datasource.model.TableColumn;
import com.liyz.service.datasource.remote.RemoteSystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 11:25
 */
@Slf4j
@Service(timeout = 2000, version = "1.0.0", actives = 1000)
public class RemoteSystemServiceImpl implements RemoteSystemService {

    private static volatile Map<Integer, List<String>> TABLE_MAP = new WeakHashMap<>();

    @Override
    public List<String> selectTables(@NotNull SystemBO systemBO, Boolean refresh) {
        if (Objects.nonNull(refresh) && !refresh) {
            if (!TABLE_MAP.containsKey(systemBO.getTableId())) {
                synchronized (RemoteSystemServiceImpl.class) {
                    if (!TABLE_MAP.containsKey(systemBO.getTableId())) {
                        SystemMapper systemMapper = new SystemMapperImpl(systemBO.getTableId());
                        TABLE_MAP.put(systemBO.getTableId(), systemMapper.selectTables());
                    }
                }
            }
        } else {
            SystemMapper systemMapper = new SystemMapperImpl(systemBO.getTableId());
            TABLE_MAP.put(systemBO.getTableId(), systemMapper.selectTables());
        }
        return TABLE_MAP.get(systemBO.getTableId());
    }

    @Override
    public List<TableColumnBO> selectColumns(@NotNull SystemBO systemBO) {
        List<String> tables = selectTables(systemBO, Boolean.FALSE);
        if (CollectionUtils.isEmpty(tables) || !tables.contains(systemBO.getTableName())) {
            throw new RemoteDatasourceServiceException(CommonCodeEnum.NonTable);
        }
        SystemMapper systemMapper = new SystemMapperImpl(systemBO.getTableId());
        try {
            List<TableColumn> list = systemMapper.selectColumns(systemBO.getTableName());
            return CommonConverterUtil.ListConverter(list, TableColumnBO.class);
        } catch (BadSqlGrammarException e) {
            log.error("查询出错了，exception:{}", e);
            throw new RemoteDatasourceServiceException(CommonCodeEnum.NonTable);
        }
    }

    @Override
    public Integer test(@NotNull DataTableBO dataTableBO) {
        try {
            SystemMapper systemMapper;
            if (Objects.nonNull(dataTableBO.getTableId())) {
                systemMapper = new SystemMapperImpl(dataTableBO.getTableId());
            } else {
                Resource[] resources = new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/*.xml");
                DataSource dataSource = DataSourceBuilder.create()
                        .driverClassName(dataTableBO.getDriver())
                        .url(dataTableBO.getUrl())
                        .username(dataTableBO.getUsername())
                        .password(dataTableBO.getPassword())
                        .build();
                SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
                bean.setMapperLocations(resources);
                bean.setDataSource(dataSource);
                SqlSessionTemplate template = new SqlSessionTemplate(bean.getObject());
                systemMapper = new SystemMapperImpl(template);
            }
            return systemMapper.test();
        } catch (Exception e) {
            log.error("测试连接fail", e);
        }
        return Integer.valueOf(0);
    }
}
