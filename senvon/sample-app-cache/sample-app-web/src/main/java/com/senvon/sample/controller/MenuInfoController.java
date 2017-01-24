package com.senvon.sample.controller;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.istock.base.ibatis.model.Page;
import com.senvon.sample.model.MenuInfo;
import com.senvon.sample.service.MenuInfoService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MenuInfoController {

	@Autowired
	private MenuInfoService menuInfoService;
	
	@Value("${menu.title}")
	private String menuTitle;
	
	@RequestMapping("showMenuList")
	public String showMenuList(ModelMap model , String name , Page page){
		if(page == null){
			page = new Page();
		}
		page.setPageSize(3);
		menuInfoService.findByName(name, page);
		model.put("name", name);
		model.put("menuTitle", menuTitle);
		model.put("page", page);
		return "menuInfo/menuList";
	}
	
	@RequestMapping("editPre")
	public String editPre(ModelMap model , Integer id){
		if(id != null && id>0){
			//update
			MenuInfo menuInfo = menuInfoService.findMenuInfoById(id);
			model.put("menuInfo", menuInfo);
		}
		return "menuInfo/menuEdit";
	}
	
	@RequestMapping("edit")
	public String edit(ModelMap model , MenuInfo menuInfo){
		Integer result = menuInfoService.saveMenuInfo(menuInfo);
		if(result >0){
			model.put("message", "保存成功");
			
		}else{
			model.put("message", "保存失败");
			model.put("menuInfo", menuInfo);
			return "menuInfo/menuEdit";
		}
		return showMenuList(model ,null, new Page());
	}
	
	@RequestMapping("delete")
	public String delete(ModelMap model , Integer id){
		Integer result = menuInfoService.deleteMenuInfo(id);
		if(result >0){
			model.put("message", "保存成功");
		}else{
			model.put("message", "保存失败");
		}
		return showMenuList(model ,null, new Page());
	}
	
	
	@RequestMapping("generate")
	public @ResponseBody Map<String , Object> generate(ModelMap model , HttpServletRequest request, Integer id){
		Map<String , Object> result = new HashMap<String , Object>();
		try{
			VelocityEngine ve = new VelocityEngine();
//			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, request.getSession().getServletContext().getRealPath("view"));
//			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.setProperty("input.encoding","UTF-8");
			ve.setProperty("output.encoding","UTF-8");
			ve.init();
			
			VelocityContext ctx = new VelocityContext();
			MenuInfo menuInfo = menuInfoService.findMenuInfoById(id);
			ctx.put("menuInfo", menuInfo);
			ctx.put("rc", request);
			doRender(ctx , ve , "menuInfo/menuEdit.vm" ,menuInfo.getId()+"");
			
			/*Template t = ve.getTemplate("look.vm","UTF-8");
			VelocityContext ctx = new VelocityContext();
			MenuInfo menuInfo = menuInfoService.findMenuInfoById(id);
			ctx.put("menu", menuInfo);
			
			FileWriter writer = new FileWriter("D:/Program/nginx-1.10.1/sample/"+id+".html");
			t.merge(ctx, writer);
			writer.flush();
			writer.close();*/
			result.put("msg", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("msg", "失败");
		}
		return result;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private void renderScreenContent(Context velocityContext ,VelocityEngine ve , String fileName ) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Rendering screen content template [" + fileName + "]");
		}

		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate(ve,fileName);
		screenContentTemplate.merge(velocityContext, sw);
		// Put rendered content into Velocity context.
		velocityContext.put("screen_content", sw.toString());
	}
	
	protected Template getTemplate(VelocityEngine ve ,String name) throws Exception {
		return ve.getTemplate(name);
	}
	
	protected void doRender(Context context , VelocityEngine ve , String fileName , String targetFileName) throws Exception {
		//优先渲染controller.vm
		renderScreenContent(context , ve , fileName);

		String layoutUrlToUse = (String) context.get("layout");
		if (layoutUrlToUse != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
			}
		}
		else {
			layoutUrlToUse = "layout/blank.vm";
		}

		Template template = getTemplate(ve,layoutUrlToUse);
		FileWriter writer = new FileWriter("D:/Program/nginx-1.10.1/sample/"+targetFileName+".html");
		
		//最后渲染layout.vm
		List<String> marcoList = new ArrayList<String>();
		marcoList.add("macro/macros.vm");
		template.merge(context, writer , marcoList);
		writer.flush();
		writer.close();
	}
	
}
