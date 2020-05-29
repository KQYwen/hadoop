package com.bigdata.wen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * DDL:
 * 1.判断表是否存在
 * 2.创建表
 * 3.创建命名空间
 * 4.删除表
 *<>p</>
 * DML:
 * 5.插入数据
 * 6.查数据（get）
 * 7.查数据（scan）
 * 8.删除数据
 */

public class TestAPI {

    private static Connection connection=null;
    private static Admin admin=null;


    static {
        try {
        //1.配置文件信息
        Configuration configuration=new HBaseConfiguration();
        configuration.set("hbase.zookeeper.quorum","master");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.master","192.168.174.128:9001");

        //2.获取管理员对象
        //HBaseAdmin admin=new HBaseAdmin(configuration);
        Connection connection = ConnectionFactory.createConnection(configuration);


        //创建Admin对象
        admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1.判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        //3.判断表是否存在
        boolean exists = admin.tableExists(TableName.valueOf(tableName));

        //4.关闭链接
//        admin.close();

        //5.返回结果
        return exists;
    }


    //2创建表
    public static void createTable(String tableName,String... cfs) throws IOException {
        //1.判断是否存在列族信息
        if (cfs.length<=0){
            System.out.println("请设置列族信息");
            return;
        }

        //2.判断表是否存在
        if (isTableExist(tableName)){
            System.out.println(tableName+"表已存在");
            return;
        }

        //3.创建表描述器
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        //4.循环添加列族信息
        for (String cf :
                cfs) {
            //5.创建列族描述
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);

            //6.添加具体列族信息
            hTableDescriptor.addFamily(hColumnDescriptor);
        }

        //7.创建表
        admin.createTable(hTableDescriptor);
    }


    //关闭资源

    public static void close(){
        if (admin!=null){
            try {
                admin.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1.测试表是否存在
        System.out.println(isTableExist("teacher"));




        //关闭资源
        close();


    }



}
