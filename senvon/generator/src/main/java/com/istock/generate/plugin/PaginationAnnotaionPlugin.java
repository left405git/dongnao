package com.istock.generate.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class PaginationAnnotaionPlugin extends PluginAdapter {

	
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		String superClazz = getProperties().getProperty("extends");
		
		if(superClazz != null && superClazz.trim().length()>0){
			FullyQualifiedJavaType superType = new FullyQualifiedJavaType(superClazz);
			topLevelClass.setSuperClass(superType);
			topLevelClass.addImportedType(superType);
			topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
			topLevelClass.addAnnotation("@Repository(\""+interfaze.getType().getShortName()+"\")");
			
			String pageClazz = getProperties().getProperty("pageClass");
			topLevelClass.addImportedType(new FullyQualifiedJavaType(pageClazz));
			Method m = new Method();
			m.setName("selectByPage");
			m.setVisibility(JavaVisibility.PUBLIC);
			m.addAnnotation("@SuppressWarnings(\"unchecked\")");
			m.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()) , "example"));
			m.addParameter(new Parameter(new FullyQualifiedJavaType(pageClazz) , "page"));
			FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
			returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
			m.setReturnType(returnType);
			
			String prifix = "";
//			System.out.println("schema============="+introspectedTable.getFullyQualifiedTable().getIntrospectedSchema());
			if(introspectedTable.getFullyQualifiedTable().getIntrospectedSchema() != null && introspectedTable.getFullyQualifiedTable().getIntrospectedSchema().length()>0){
				prifix = introspectedTable.getFullyQualifiedTable().getIntrospectedSchema()+"_";
			}
			m.addBodyLine(m.getReturnType().getShortName() + " list = this.searchListPageMyObject(\""+prifix+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+".selectByExample\", example, page);");
			m.addBodyLine("return list;");
			topLevelClass.addMethod(m);
			
			interfaze.addImportedType(new FullyQualifiedJavaType(pageClazz));
			Method m1 = new Method();
			m1.setName("selectByPage");
			m1.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()) , "example"));
			m1.addParameter(new Parameter(new FullyQualifiedJavaType(pageClazz) , "page"));
			FullyQualifiedJavaType interfaceReturnType = FullyQualifiedJavaType.getNewListInstance();
			interfaceReturnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
			m1.setReturnType(interfaceReturnType);
			interfaze.addMethod(m1);
			
		}
		
		String template = getProperties().getProperty("sqlMapTemplate");
		if(template != null && template.trim().length()>0){
			FullyQualifiedJavaType superType = new FullyQualifiedJavaType("com.istock.base.ibatis.IbatisTemplate");
			topLevelClass.addImportedType(superType);
			FullyQualifiedJavaType resourceClass = new FullyQualifiedJavaType("javax.annotation.Resource");
			topLevelClass.addImportedType(resourceClass);

			Field templateField = new Field();
			templateField.addAnnotation("@Resource(name=\""+template+"\")");
			templateField.setName("template");
			templateField.setVisibility(JavaVisibility.PRIVATE);
			templateField.setType(new FullyQualifiedJavaType("com.istock.base.ibatis.IbatisTemplate"));
			topLevelClass.addField(templateField);
			
			Method getTemplate = new Method();
			getTemplate.setName("getSqlMapClientTemplate");
			getTemplate.setReturnType(new FullyQualifiedJavaType("com.istock.base.ibatis.IbatisTemplate"));
			getTemplate.addBodyLine("return this.template;");
			getTemplate.setVisibility(JavaVisibility.PUBLIC);
			topLevelClass.addMethod(getTemplate);
		}
		
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

}
