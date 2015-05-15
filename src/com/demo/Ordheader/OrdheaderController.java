package com.demo.ordheader;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.util.ExcelHelper;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(OrdheaderInterceptor.class)
public class OrdheaderController extends Controller {
	
	public void index() {
		setAttr("blogPage", Ordheader.me.paginate(getParaToInt(0, 1), 100));
		//render("blog.jsp");
		renderJson();
	}
//	List<Ordheader>  list= Ordheader.me.findAll();      
	//setAttr("quoList", list);
	public void add() {
	}
	
	@Before(OrdheaderValidator.class)
	public void save() {
		getModel(Ordheader.class).save();
		redirect("/item");
	}
	
	public void edit() {
		setAttr("item", Ordheader.me.findById(getParaToInt()));
	}
	
	@Before(OrdheaderValidator.class)
	public void update() {
		getModel(Ordheader.class).update();
		redirect("/item");
	}
	
	public void delete() {
		Ordheader.me.deleteById(getParaToInt());
		renderText("OK");
	}

	public void batchCrud() {
		String myRs = "{'params':" + getPara("params") + "}";
		// 将字符串转为json对象，使用fastjson
		JSONObject obj = JSON.parseObject(myRs);
		// Object[] items=null;
		JSONArray items;
		try {
			items = obj.getJSONArray("params");
			for (int idx = 0; idx < items.size(); idx++) {
				Integer myId = items.getJSONObject(idx).getInteger("id");
				Integer myActive;
				if (items.getJSONObject(idx).getBoolean("Active")){
					myActive=1;
				}
				else{
					myActive=0;
				}
				String myOrdDate =  items.getJSONObject(idx).getString("OrdDate");
				String myComments = items.getJSONObject(idx).getString("Comments");
				Integer myCustID = items.getJSONObject(idx).getInteger("CustID");				
				String myStatus = items.getJSONObject(idx).getString("myStatus");
				Integer myOrdTypeID = items.getJSONObject(idx).getInteger("OrdTypeID");
				Integer mySalesRepID = items.getJSONObject(idx).getInteger("SalesRepID");
				System.out.println("id:" + myId);
				System.out.println("OrdDate:" + myOrdDate);
				System.out.println("Comments:" + myComments);
				System.out.println("CustID:" + myCustID);
				System.out.println("myStatus:" + myStatus);
				System.out.println("Record " + idx + "----");
/*				if (myStatus.equals("D")) {
					Db.deleteById("item",
							items.getJSONObject(idx).getInteger("id"));
				}*/
				if (myStatus.equals("U")) {
					if (myId != null) {
						System.out.println("进入update");
						Ordheader.me.findById(myId).set("Comments", myComments)
													.set("OrdDate", myOrdDate)
													.set("CustID", myCustID)
													.set("SalesRepID", mySalesRepID)								
													.set("OrdTypeID", myOrdTypeID)
													.set("Active", myActive).update();
					} else {
						System.out.println("进入Add Record.");
						Record item = new Record().set("Comments", myComments)
													.set("OrdDate", myOrdDate)
													.set("CustID", myCustID)
													.set("SalesRepID", mySalesRepID)								
													.set("OrdTypeID", myOrdTypeID)
													.set("Active", myActive);
						Db.save("Ordheader", item);
					}
				}
			}
		} catch (NullPointerException ne) {
			items = null;
			// items=new Object[]{};
		}
		// redirect("/");
		setAttr("blogPage", Ordheader.me.paginate(getParaToInt(0, 1), 100));
		renderJson();
	}

	public void createExcel() {
		ExcelHelper im = new ExcelHelper();
		im.CreateSimpleExcelToDisk();
		renderText("OK");
	}

	
}


