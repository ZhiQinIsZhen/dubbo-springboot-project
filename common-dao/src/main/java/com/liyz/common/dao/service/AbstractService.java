package com.liyz.common.dao.service;

import com.liyz.common.dao.exception.ServiceException;
import com.liyz.common.dao.mapper.Mapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 注释:基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/28 18:04
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    /**
     * 构造方法
     */
    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public int save(T model) {
        return mapper.insertSelective(model);
    }

    @Override
    public int save(List<T> models) {
        return mapper.insertList(models);
    }

    @Override
    public int removeById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T getBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public T getOne(T model) {
        return mapper.selectOne(model);
    }

    @Override
    public List<T> listByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> listByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> listAll() {
        return mapper.selectAll();
    }
}
