package com.liyz.service.datasource.handler;

import com.github.pagehelper.PageInterceptor;
import com.liyz.service.datasource.dao.DataTableMapper;
import com.liyz.service.datasource.model.DataTableDO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/4 19:28
 */
@Slf4j
@Service
public class SqlTemplateService {

    private static final Map<Integer, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();
    private static final Map<Integer, SqlSessionFactory> DATA_SOURCE_FACTORY = new ConcurrentHashMap<>();
    private static final Map<Integer, DataSourceTransactionManager> DATA_SOURCE_MANAGER = new ConcurrentHashMap<>();
    @Getter
    private static final Map<Integer, SqlSessionTemplate> DATA_SOURCE_TEMPLATE = new ConcurrentHashMap<>();

    @Autowired
    DataTableMapper dataTableMapper;
    @Autowired
    PageInterceptor pageHelper;

    @PostConstruct
    public void init() {
        //初始化SqlSessionTemplate
        try {
            List<DataTableDO> doList = dataTableMapper.selectAll();
            if (!CollectionUtils.isEmpty(doList)) {
                DataSource dataSource;
                SqlSessionFactoryBean bean;
                DataSourceTransactionManager manager;
                SqlSessionTemplate template;
                Resource[] resources = new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/*.xml");
                for (DataTableDO dataTableDO : doList) {
                    dataSource = DataSourceBuilder.create()
                            .driverClassName(dataTableDO.getDriver())
                            .url(dataTableDO.getUrl())
                            .username(dataTableDO.getUsername())
                            .password(dataTableDO.getPassword())
                            .build();
                    DATA_SOURCE_MAP.putIfAbsent(dataTableDO.getTableId(), dataSource);
                    bean = new SqlSessionFactoryBean();
                    bean.setMapperLocations(resources);
                    bean.setDataSource(dataSource);
                    bean.setPlugins(new Interceptor[]{pageHelper});
                    DATA_SOURCE_FACTORY.putIfAbsent(dataTableDO.getTableId(), bean.getObject());
                    manager = new DataSourceTransactionManager(dataSource);
                    DATA_SOURCE_MANAGER.putIfAbsent(dataTableDO.getTableId(), manager);
                    template = new SqlSessionTemplate(bean.getObject());
                    DATA_SOURCE_TEMPLATE.putIfAbsent(dataTableDO.getTableId(), template);
                }
            }
            log.info("SqlSessionTemplate init success ......");
        } catch (Exception e) {
            log.error("SqlSessionTemplate init fail ......", e);
        }
    }

    public static final SqlSessionTemplate getByTableId(Integer tableId) {
        return DATA_SOURCE_TEMPLATE.get(tableId);
    }
}
