package com.istock.generate.plugin;

import java.io.File;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class SqlMapUnMergePlugin extends PluginAdapter {

	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        File directory;
        try {
            directory = shellCallback.getDirectory(context.getSqlMapGeneratorConfiguration().getTargetProject(), context
                    .getSqlMapGeneratorConfiguration().getTargetPackage());
            File targetFile = new File(directory, introspectedTable.getIbatis2SqlMapFileName());
            if (targetFile.exists()) {
                targetFile.delete();
            }
        } catch (ShellException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return true;
    }
	
	public boolean validate(List<String> warnings) {
		return true;
	}

}
