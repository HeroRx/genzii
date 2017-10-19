package com.gzucm.yiyuankeshi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.gzucm.yiyuankeshi.View.MyActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends Activity{

    private TextView textView;
    private TextView textView02;
    private EditText editText;
    private EditText editText02;
    private Button button;
    private Button button02;
    private Button button03;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv);
        textView02 = (TextView)findViewById(R.id.tv02);
        editText = (EditText)findViewById(R.id.et);
        editText02 = (EditText)findViewById(R.id.et02);
        button = (Button)findViewById(R.id.btn);
        button02 = (Button)findViewById(R.id.btn02);
        button03 = (Button)findViewById(R.id.btn03);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String str= editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(str+"hhhhhhhh");
                        final String idstr = queryId(str);
                        System.out.println(idstr+"hhhhhhhh");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(idstr);
                            }
                        });
                    }
                }).start();
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String str02= editText02.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(str02+"hhhhhhhh");
                        final String namestr = queryName(str02);
                        System.out.println(namestr+"hhhhhhhh");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView02.setText(namestr);
                            }
                        });
                    }
                }).start();
            }
        });

        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }

    public  String queryId(String id) {
        ResultSet rs2 = null;
        String IDstr=null;
        String IDstr02=null;
        // TODO Auto-generated method stub
        try {
            Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
            // Class.forName("org.gjt.mm.mysql.Driver");
            System.out.println("Success loadingMysql Driver!");
        } catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
        try {
            // Connection connect = DriverManager.getConnection(
            // "jdbc:mysql://localhost:3306/test","root"," yourpassword");

            Connection connect = DriverManager
                    .getConnection(
                            "jdbc:mysql://192.168.43.8:3306/Department_DataBase",
                            "root", "123456");
            // 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是你数据库的登录用户名和密码
            // test为你之前建的数据库的名字，root替换成你的数据库的登录用户名（默认是root），yourpassword换成你的密码
            System.out.println("Success connect Mysql server!");
            Statement stmt = connect.createStatement();
            Statement stmt2 = connect.createStatement();
            // ResultSet rs = stmt.executeQuery("select * from BigDepartment");
            String s = "select * from Department where DID =" + "'" + id + "'";
            rs2 = stmt2.executeQuery(s);
            ResultSet rs = null;

            while (rs2.next()) {
                System.out.print("你所查的科室ID " + rs2.getString(1));
                System.out.print("    ");
                System.out.print("所对应的科室为：" + rs2.getString(2));
                System.out.print("    ");
                System.out.println("所在的大科编号为：" + rs2.getString(3));
                String str = "你所查的科室ID " + rs2.getString(1) + "所对应的科室为：" + rs2.getString(2) + "所在的大科编号为：" + rs2.getString(3);
                IDstr = str;
                String bstr = rs2.getString(3);
                String ss = "select * from BigDepartment where DBID =" + "'"
                        + bstr + "'";
                rs = stmt.executeQuery(ss);

            }

            while (rs.next()) {
                System.out.println("所对应的大科为：" + rs.getString(2));
                String str02 = "所对应的大科为：" + rs.getString(2);
                IDstr02 = str02;

            }

        } catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        }

        return IDstr + IDstr02;
    }



    public String queryName(String name) {
        ResultSet rs2 = null;
        String Namestr = null;
        String Namestr02 = null;
        // TODO Auto-generated method stub
        try {
            Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
            // Class.forName("org.gjt.mm.mysql.Driver");
            System.out.println("Success loadingMysql Driver!");
        } catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
        try {
            // Connection connect = DriverManager.getConnection(
            // "jdbc:mysql://localhost:3306/test","root"," yourpassword");
            Connection connect = DriverManager
                    .getConnection(
                            "jdbc:mysql://192.168.43.8:3306/Department_DataBase?useUnicode=true&characterEncoding=UTF-8",
                            "root", "123456");
            // 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是你数据库的登录用户名和密码
            // test为你之前建的数据库的名字，root替换成你的数据库的登录用户名（默认是root），yourpassword换成你的密码
            System.out.println("Success connect Mysql server!");
            Statement stmt = connect.createStatement();
            Statement stmt2 = connect.createStatement();
            // ResultSet rs = stmt.executeQuery("select * from BigDepartment");
            String s = "select * from Department where Dname =" + "'" + name
                    + "'";
            rs2 = stmt2.executeQuery(s);
            ResultSet rs = null;

            while (rs2.next()) {

                System.out.print("你所查的科室为：：" + rs2.getString(2));
                System.out.print("    ");
                System.out.print("所对应的科室ID " + rs2.getString(1));
                System.out.print("    ");
                System.out.println("所在的大科编号为：" + rs2.getString(3));
                String bstr = rs2.getString(3);
                String Namestring = "你所查的科室为：：" + rs2.getString(2) + "所对应的科室ID " + rs2.getString(1)
                        + "所在的大科编号为：" + rs2.getString(3);
                Namestr = Namestring;
                String ss = "select * from BigDepartment where DBID =" + "'"
                        + bstr + "'";
                rs = stmt.executeQuery(ss);

            }

            while (rs.next()) {
                System.out.println("所对应的大科为：" + rs.getString(2));
                String Namestring02 = "所对应的大科为：" + rs.getString(2);
                Namestr02 = Namestring02;
            }

        } catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        }
        return Namestr + Namestr02;
    }




}
