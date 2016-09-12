package com.yx.listener; /**
 * User: LiWenC
 * Date: 16-9-12
 */

import com.yx.db.DBUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class AppListener implements ServletContextListener {

    public AppListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        DBUtils.initDataSource();
        DBUtils.initConnections();
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
