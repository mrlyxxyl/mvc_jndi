package com.yx.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读写分离、分库分表
 * User: LiWenC
 * Date: 16-9-1
 */
public class DBUtils {
    private static final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();

    static String[] servers = new String[]{"m_test_0", "m_test_1", "m_test_2", "s_test_0", "s_test_1", "s_test_2"};

    private static final ConcurrentHashMap<String, LinkedList<Connection>> connectionMap = new ConcurrentHashMap<String, LinkedList<Connection>>();

    /**
     * 初始化数据源
     */
    public static void initDataSource() {
        Context ctx = null;
        try {
            ctx = new InitialContext();
            for (int i = 0; i < servers.length; i++) {
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + servers[i]);
                dataSourceMap.putIfAbsent(servers[i], ds);
            }

        } catch (Exception e) {
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                }
            }
        }
    }

    /**
     * 初始化连接池
     */
    public static void initConnections() {
        try {
            for (int i = 0; i < servers.length; i++) {
                DataSource dataSource = getDataSource(servers[i]);
                LinkedList<Connection> list = new LinkedList<Connection>();
                for (int j = 0; j < 1; j++) {
                    long start = System.currentTimeMillis();
                    list.add(dataSource.getConnection());
                    System.out.println(servers[i] + "-------------" + (System.currentTimeMillis() - start));
                }
                connectionMap.put(servers[i], list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @param serverName
     * @return
     */
    public static Connection getConnection(String serverName) throws SQLException {
        LinkedList<Connection> list = connectionMap.get(serverName);
        if (list.size() > 0) {
            return list.removeFirst();
        } else {
            DataSource dataSource = getDataSource(serverName);
            return dataSource.getConnection();
        }
    }

    /**
     * 释放连接
     *
     * @param serverName 服务器名称
     * @param connection 连接
     */
    public static void releaseConnection(String serverName, Connection connection) {
        connectionMap.get(serverName).add(connection);
    }

    /**
     * 根据服务名称获取数据源
     *
     * @param serverName context.xml文件中Resource元素的name属性值
     * @return
     */
    public static DataSource getDataSource(String serverName) {
        DataSource ds = dataSourceMap.get(serverName);
        if (ds == null) {
            Context ctx = null;
            try {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/" + serverName);
                DataSource tmpDs = dataSourceMap.putIfAbsent(serverName, ds);
                if (null != tmpDs) {
                    ds = tmpDs;
                }
            } catch (NamingException e) {
                return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (NamingException e) {
                    }
                }
            }
        }
        return ds;
    }
}
