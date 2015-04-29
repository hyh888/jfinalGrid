package com.demo.myView;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class MyView extends Model<MyView> {
	public static final MyView me = new MyView();
	
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */

	public Page<MyView> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "SELECT *", "from myView");
	}
}
