package com.gzucm.yiyuankeshi.View;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gzucm.yiyuankeshi.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {

	private EditText et;
	private Button bt;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
//	private ViewMiddle viewMiddle;
//	private ViewRight viewRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initView();
		initVaule();
	    initListener();

		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						final String etstr= et.getText().toString();
						final List<String> str = queryBName(etstr);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								viewLeft.setItemData(str);

							}
						});

						System.out.println(str+"wwwwwwwwww");

					}
				}).start();
				Toast.makeText(MyActivity.this,"数据查询完成，请点击下拉列表选择查看",Toast.LENGTH_LONG).show();
			}
		});


		
	}



	private void initView() {
		
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewLeft = new ViewLeft(this);
//		viewMiddle = new ViewMiddle(this);
		et = (EditText)findViewById(R.id.et04);
		bt = (Button)findViewById(R.id.btn04);
//		viewRight = new ViewRight(this);
		
	}

	private void initVaule() {
		
		mViewArray.add(viewLeft);
//		mViewArray.add(viewMiddle);
//		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("为您呈现广中医一附院官网的科室分类,请您点击按钮选择");
		mTextArray.add("为您呈现广中医一附院官网的科室分类,请您点击按钮选择");
		mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewLeft.getShowText(), 2);
//		expandTabView.setTitle(viewMiddle.getShowText(), 2);
//		expandTabView.setTitle(viewRight.getShowText(), 2);
		
	}

	private void initListener() {
		
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});
		
//		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
//
//			@Override
//			public void getValue(String showText) {
//
//				onRefresh(viewMiddle,showText);
//
//			}
//		});
		
//		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
//
//			@Override
//			public void getValue(String distance, String showText) {
//				onRefresh(viewRight, showText);
//			}
//		});
		
	}
	
	private void onRefresh(View view, String showText) {
		
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(MyActivity.this, showText, Toast.LENGTH_SHORT).show();

	}
	
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void onBackPressed() {
		
		if (!expandTabView.onPressBack()) {
			finish();
		}
		
	}


	public static List<String> queryBName(String name) {
		ResultSet rs2 = null;
		List<String>  myList = null;
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
			String s = "select * from BigDepartment where DBigDepart =" + "'" + name
					+ "'";
			rs2 = stmt2.executeQuery(s);
			ResultSet rs = null;

			while (rs2.next()) {

				System.out.print("你所查的大科为：：" + rs2.getString(2));
				System.out.print("    ");
				System.out.print("所对应的大科ID " + rs2.getString(1));
				System.out.print("    ");
				System.out.println();
				String bstr = rs2.getString(1);
				String ss = "select * from Department where DBDID =" + "'"
						+ bstr + "'";
				rs = stmt.executeQuery(ss);

			}
			System.out.println();
			System.out.println("您所选择的大科包括的下拉列表即科室为：");
			System.out.println();

			List<String> list = new ArrayList<>();
			while (rs.next()) {
//				System.out.println("您所选择的大科包括的下拉列表即科室为：" + rs.getString(2));

				System.out.println(rs.getString(2));
				list.add(rs.getString(2));
			}
			System.out.println(list + "22222");
			myList = list;

		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
		return myList;
	}



}
