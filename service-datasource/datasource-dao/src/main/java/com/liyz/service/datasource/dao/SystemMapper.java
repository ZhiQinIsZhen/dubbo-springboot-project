package com.liyz.service.datasource.dao;

import com.liyz.service.datasource.model.TableColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/30 10:11
 */
public interface SystemMapper {

    List<String> selectTables();

    List<TableColumn> selectColumns(@Param("table") String table);

    int test();
}
