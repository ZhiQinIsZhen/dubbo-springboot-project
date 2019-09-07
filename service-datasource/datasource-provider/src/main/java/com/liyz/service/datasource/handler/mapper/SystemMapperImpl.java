package com.liyz.service.datasource.handler.mapper;

import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.service.datasource.dao.SystemMapper;
import com.liyz.service.datasource.exception.RemoteDatasourceServiceException;
import com.liyz.service.datasource.handler.SqlTemplateService;
import com.liyz.service.datasource.model.TableColumn;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 10:29
 */
@Slf4j
public class SystemMapperImpl implements SystemMapper {

    private SqlSessionTemplate sqlSessionTemplate;
    private String mapper;

    public SystemMapperImpl(SqlSessionTemplate sqlSessionTemplate) {
        if (Objects.isNull(sqlSessionTemplate)) {
            throw new RemoteDatasourceServiceException(CommonCodeEnum.NonDatasource);
        }
        this.sqlSessionTemplate = sqlSessionTemplate;
        mapper = SystemMapper.class.getName();
    }

    public SystemMapperImpl(Integer tableId) {
        this(SqlTemplateService.getByTableId(tableId));
    }

    @Override
    public List<String> selectTables() {
        return sqlSessionTemplate.selectList(mapper + ".selectTables");
    }

    @Override
    public List<TableColumn> selectColumns(String table) {
        return sqlSessionTemplate.selectList(mapper + ".selectColumns", table);
    }

    @Override
    public int test() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String method = element.getMethodName();
        return sqlSessionTemplate.selectOne(mapper + ".test");
    }
}
