package com.yx.dao;

import com.yx.db.DBUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class PersonDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    ThreadLocal<JdbcTemplate> threadLocal = new ThreadLocal<JdbcTemplate>() {
        @Override
        protected JdbcTemplate initialValue() {
            return jdbcTemplate;
        }
    };

    public List<Map<String, Object>> queryPersons(String serverName) throws SQLException {
        DataSource dataSource = DBUtils.getDataSource(serverName);
        if (dataSource == null) {
            throw new RuntimeException("can't get dataSource!");
        }
        threadLocal.get().setDataSource(dataSource);//
        return threadLocal.get().queryForList("select * from person");
       /* Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from person");
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", resultSet.getInt("id"));
            map.put("name", resultSet.getString("name"));
            maps.add(map);
        }
        return maps;*/
    }
}
