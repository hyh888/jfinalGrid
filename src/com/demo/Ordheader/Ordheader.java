package com.demo.ordheader;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Ordheader extends Model<Ordheader> {
	public static final Ordheader me = new Ordheader();
	
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */

	public Page<Ordheader> paginate (int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, 
			"SELECT o.id, o.Active,o.SalesRepID, o.CustID, c.CustName, o.OrdDate, o.OrdTypeID, o.Comments, s.RepName",
			"FROM ordheader AS o INNER JOIN customer AS c ON o.CustID = c.id "
			+"INNER JOIN salesrep AS s ON o.SalesRepID = s.id ORDER BY o.id ASC");
	}
	
}
