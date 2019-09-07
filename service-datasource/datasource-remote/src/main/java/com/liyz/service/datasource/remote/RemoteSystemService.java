package com.liyz.service.datasource.remote;

import com.liyz.service.datasource.bo.DataTableBO;
import com.liyz.service.datasource.bo.SystemBO;
import com.liyz.service.datasource.bo.TableColumnBO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 11:21
 */
public interface RemoteSystemService {

    List<String> selectTables(@NotNull SystemBO systemBO, Boolean refresh);

    List<TableColumnBO> selectColumns(@NotNull SystemBO systemBO);

    Integer test(@NotNull DataTableBO dataTableBO);
}
