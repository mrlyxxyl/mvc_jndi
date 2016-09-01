package com.yx.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * User: NMY
 * Date: 16-8-28
 */
public class Configuration {

    @Value("${driverClassName}")
    public static String driverClassName;
    @Value("${url}")
    public static String url;
    @Value("${username}")
    public static String username;
    @Value("${password}")
    public static String password;
    @Value("${maxActive}")
    public static int maxActive;
    @Value("${maxIdle}")
    public static int maxIdle;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
}
