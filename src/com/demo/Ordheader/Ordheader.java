package com.demo.Ordheader;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Ordheader extends Model<Ordheader> {
	public static final Ordheader me = new Ordheader();
	
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<Ordheader> paginate0(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, 
			"SELECT	o.id,o.CustID,c.CustName,o.SalesRepID,s.RepName,DATE_FORMAT(o.OrdDate,'%m-%d-%Y') OrdDate1,o.OrdType,"
			+ "o.Comments,o.CreatedTime,c.CustAddress,c.BankAccount", 
			"from ordheader AS o INNER JOIN customer AS c ON o.CustID = c.id "
			+ "INNER JOIN salesrep AS s ON o.SalesRepID = s.id");
	}
	public Page<Ordheader> paginate (int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, 
			"SELECT	o.id,o.CustID,c.CustName,o.SalesRepID,s.RepName,o.OrdDate,o.OrdType,"
			+ "o.Comments,o.CreatedTime,c.CustAddress,c.BankAccount", 
			"from ordheader AS o INNER JOIN customer AS c ON o.CustID = c.id "
			+ "INNER JOIN salesrep AS s ON o.SalesRepID = s.id");
	}
	
}
