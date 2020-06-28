package com.wen;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SbMybatis02ApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {

        //看一下默认数据源
        System.out.println(dataSource.getClass());

        //获得链接
        Connection connection=dataSource.getConnection();

        System.out.println(connection);

        DruidDataSource druidDataSource= (DruidDataSource) dataSource;

        System.out.println("数据最大连接数："+druidDataSource.getMaxActive());
        System.out.println("数据源初始化连接数："+druidDataSource.getInitialSize());

        //关闭链接
        connection.close();

    }

}
