package com.yx.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读写分离、分库分表
 * User: LiWenC
 * Date: 16-9-1
 */
public class DBUtils {
    private static final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();

    /**
     * 初始化数据源
     */
    public static void initDataSource() {
        Context ctx = null;
        try {
            String[] servers = new String[]{"DBPool_one", "DBPool_local"};
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
