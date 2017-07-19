package com.spf.common.sql;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author SPF
 * @Date 2017/5/24
 */
@Component
public class SqlTemplate {
   @Autowired
    private DataSource dataSource;
    private QueryRunner queryRunner;

    private static final Logger  LOG = Logger.getLogger(SqlTemplate.class);

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 执行sql语句
     * @param sql sql语句
     * @return 受影响的行数
     */
    public int update(String sql) {
        return update(sql, null);
    }

    /**
     * 执行sql语句
     * <code>
     * executeUpdate("update user set username = 'kitty' where username = ?", "hello kitty");
     * </code>
     * @param sql sql语句
     * @param param 参数
     * @return 受影响的行数
     */
    public int update(String sql, Object param) {
        return update(sql, new Object[] { param });
    }

    /**
     * 执行sql语句
     * @param sql sql语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public int update(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource,true);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
            LOG.info("SQL--->" + sql);
            LOG.info("params--->" + Arrays.toString(params));
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to update data", e);
        }
        return affectedRows;
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @return 查询结果
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return queryForPageList(sql, null, null, null);
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public List<Map<String, Object>> queryForList(String sql, Object param) {
        return queryForPageList(sql, new Object[] {param}, null, null);
    }

    /**
     * 执行分页查询
     * @param sql sql语句
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return 结果
     */
    public List<Map<String, Object>> queryForPageList(String sql, Integer pageNo, Integer pageSize) {
        return queryForPageList(sql, null, pageNo, pageSize);
    }

    /**
     * 执行分页查询
     * @param sql sql语句
     * @param param 参数
     * @param pageNo 当前页
     * @param pageSize 每页查询条数
     * @return 结果
     */
    public List<Map<String, Object>> queryForPageList(String sql, Object param, Integer pageNo, Integer pageSize) {
        return queryForPageList(sql, new Object[] {param}, pageNo, pageSize);
    }

    public List<Map<String, Object>> queryForList(String sql, Object[] params) {
        return queryForPageList(sql, params, null, null);
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryForPageList(String sql, Object[] params, Integer pageNo, Integer pageSize) {
        queryRunner = new QueryRunner(dataSource,true);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        try {
            if (params == null) {
                if (pageNo !=null && pageSize != null) {
                    Integer index = (pageNo - 1) * pageSize;
                    Object[] param = {index, pageSize};
                    list = (List<Map<String, Object>>) queryRunner.query(sql + " LIMIT ?,?", new MapListHandler(), param);
                    LOG.info("SQL--->" + sql + " LIMIT ?,?");
                    LOG.info("params--->" + Arrays.toString(param));
                }  else {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
                    LOG.info("SQL--->" + sql);
                }
            } else {
                if (pageNo !=null && pageSize != null) {
                    Integer index = (pageNo - 1) * pageSize;
                    List<Object> arr = new ArrayList<Object>();
                    for (Object obj : params) {
                        arr.add(obj);
                    }
                    arr.add(index);
                    arr.add(pageSize);
                    Object[] param = arr.toArray(); //将集合转化为数组
                    list = (List<Map<String, Object>>) queryRunner.query(sql + " LIMIT ?,?", new MapListHandler(), param);
                    LOG.info("SQL--->" + sql + " LIMIT ?,?");
                    LOG.info("params--->" + Arrays.toString(param));
                } else {
                    list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params);
                    LOG.info("SQL--->" + sql);
                    LOG.info("params--->" + Arrays.toString(params));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @return 查询结果
     */
    public <T> List<T> queryForList(Class<T> entityClass, String sql) {
        return queryForPageList(entityClass, sql, null, null, null);
    }

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public <T> List<T> queryForList(Class<T> entityClass, String sql, Object param) {
        return queryForPageList(entityClass, sql, new Object[] { param }, null, null);
    }

    /**
     * 执行分页查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @return 查询结果
     */
    public <T> List<T> queryForPageList(Class<T> entityClass, String sql, Integer index, Integer pageSize) {
        return queryForPageList(entityClass, sql, null, index, pageSize);
    }

    /**
     * 执行分页查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @param param 参数
     * @return 查询结果
     */
    public <T> List<T> queryForPageList(Class<T> entityClass, String sql, Object param, Integer pageNo, Integer pageSize) {
        return queryForPageList(entityClass, sql, new Object[] { param }, pageNo, pageSize);
    }

    public <T> List<T> queryForList(Class<T> entityClass, String sql, Object[] params) {
        return queryForPageList(entityClass, sql, params, null, null);
    }


    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForPageList(Class<T> entityClass, String sql, Object[] params, Integer pageNo, Integer pageSize) {
        queryRunner = new QueryRunner(dataSource,true);
        List<T> list = new ArrayList<T>();
        try {
            if (params == null) {
                if (pageNo !=null && pageSize != null) {
                    Integer index = (pageNo - 1) * pageSize;
                    Object[] param = {index, pageSize};
                    list = (List<T>) queryRunner.query(sql + " LIMIT ?,?", new BeanListHandler(entityClass), param);
                    LOG.info("SQL--->" + sql + " LIMIT ?,?");
                    LOG.info("params--->" + Arrays.toString(param));
                } else {
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler(entityClass));
                    LOG.info("SQL--->" + sql);
                }
            } else {
                if (pageNo !=null && pageSize != null) {
                    Integer index = (pageNo - 1) * pageSize;
                    List<Object> arr = new ArrayList<Object>();
                    for (Object obj : params) {
                        arr.add(obj);
                    }
                    arr.add(index);
                    arr.add(pageSize);
                    Object [] param = arr.toArray(); //将集合转化为数组
                    list = (List<T>) queryRunner.query(sql + " LIMIT ?,?", new BeanListHandler(entityClass), param);
                    LOG.info("SQL--->" + sql + " LIMIT ?,?");
                    LOG.info("params--->" + Arrays.toString(param));
                } else{
                    list = (List<T>) queryRunner.query(sql, new BeanListHandler(entityClass), params);
                    LOG.info("SQL--->" + sql);
                    LOG.info("params--->" + Arrays.toString(params));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @return 对象
     */
    public <T> T query(Class<T> entityClass, String sql) {
        return queryFirst(entityClass, sql, null);
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @param param 参数
     * @return 对象
     */
    public <T> T queryFirst(Class<T> entityClass, String sql, Object param) {
        return queryFirst(entityClass, sql, new Object[] { param });
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public <T> T queryFirst(Class<T> entityClass, String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource,true);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new BeanHandler(entityClass));
            } else {
                object = queryRunner.query(sql, new BeanHandler(entityClass), params);
            }
            LOG.info("SQL--->" + sql);
            LOG.info("params--->" + Arrays.toString(params));
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return (T) object;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @return 封装为Map的对象
     */
    public Map<String, Object> queryFirst(String sql) {
        return queryFirst(sql, null);
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @param param 参数
     * @return 封装为Map的对象
     */
    public Map<String, Object> queryFirst(String sql, Object param) {
        return queryFirst(sql, new Object[] { param });
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * @param sql sql语句
     * @param params 参数数组
     * @return 封装为Map的对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> queryFirst(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource,true);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
            } else {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params);
            }
            LOG.info("SQL--->" + sql);
            LOG.info("params--->" + Arrays.toString(params));
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return map;
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @return 结果对象
     */
    public Object queryForObject(String sql, String columnName) {
        return queryForOjbect(sql, columnName, null);
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @param param 参数
     * @return 结果对象
     */
    public Object queryForObject(String sql, String columnName, Object param) {
        return queryForOjbect(sql, columnName, new Object[] { param });
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * @param sql sql语句
     * @param columnName 列名
     * @param params 参数数组
     * @return 结果对象
     */
    public Object queryForOjbect(String sql, String columnName, Object[] params) {
        queryRunner = new QueryRunner(dataSource,true);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnName));
            } else {
                object = queryRunner.query(sql, new ScalarHandler(columnName), params);
            }
            LOG.info("SQL--->" + sql);
            LOG.info("params--->" + Arrays.toString(params));
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return object;
    }
}
