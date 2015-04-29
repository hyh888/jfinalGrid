package com.demo.Ordheader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(OrdheaderInterceptor.class)
public class OrdheaderController extends Controller {
	
	public void index() {
		setAttr("blogPage", Ordheader.me.paginate(getParaToInt(0, 1), 10));
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
		redirect("/item");
	}
	
	public void frModeltoProg()
	{	
	 	WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
		Configuration cfg;
		try {
			cfg = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		//	GroupTemplate gt = GroupTemplateHelper.instance();
			Template t = gt.getTemplate("WEB-INF/tpl/tl.txt");//获取模板文件  hello Richard.$(total)。
			Page<Ordheader> page = Ordheader.me.paginate(1, 10);
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("total", page.getTotalRow());
	        map.put("rows", page.getList());
			System.out.println(map);	
	       // gt.setSharedVars(map);
			System.out.println(page.getTotalRow());	
			t.binding("map",map);
	 		String str = t.render();
 			System.out.println(str);	
			OutputStream out = new FileOutputStream("C://hyh.java");//输出java文件

			t.renderTo(out) ;
			out.flush();
			out.close();
			renderText(str);
		} catch (IOException e) {
			//renderText("Something wrong in beetl making.");
			e.printStackTrace();
		}
	}
}


