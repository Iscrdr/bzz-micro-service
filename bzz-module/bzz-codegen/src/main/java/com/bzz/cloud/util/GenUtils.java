/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.util;


import com.bzz.cloud.gen.entity.GenScheme;
import com.bzz.cloud.gen.entity.GenTable;
import com.bzz.cloud.gen.entity.GenTableColumn;
import com.bzz.common.utils.BzzStringUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 代码生成工具类
 * @author ThinkGem
 * @version 2013-11-16
 */
public class GenUtils {

	private static Logger logger = LoggerFactory.getLogger(GenUtils.class);

	/**
	 * 初始化列属性字段
	 * @param genTable
	 */
	public static void initColumnField(GenTable genTable){
		for (GenTableColumn column : genTable.getColumnList()){
			column.setGenTable(genTable);
            System.out.println(column.getColumnName()+","+column.getJdbcType());
			// 设置字段说明
			if (StringUtils.isBlank(column.getComments())){
				column.setComments(column.getComments());
			}
			// 设置java类型
			if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR")){
				column.setJavaType("String");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP")){
				column.setJavaType("java.util.Date");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "INT")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DOUBLE")
            ){
				// 如果是浮点型
				String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
				if (ss != null && ss.length == 2 && Integer.parseInt(ss[1])>0){
					column.setJavaType("Double");
				}
				// 如果是整形
				else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0])<=15){
					column.setJavaType("Integer");
				}
				// 长整形
				else{
					column.setJavaType("Long");
				}
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TINYINT"))  {
                String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");

                System.out.println(column.getColumnName()+":"+column.getJdbcType()+":"+ss.toString());
                if (ss != null && ss.length == 1 && Integer.parseInt(ss[0])>1){
                    column.setJavaType("Integer");
                }else {
                    column.setJavaType("boolean");
                }
			}

			// 设置java字段名
			column.setJavaField(BzzStringUtils.toCamelCase(column.getColumnName()));
            System.out.println(column.getJavaField()+","+column.getJavaType());
		}
	}
	
	/**
	 * 获取模板路径
	 * @return
	 */
	public static String getOutPath(){
	    String path = null;
        try{
            path = GenUtils.class.getClassLoader().getResource("").getPath();
            path = "D:/test/";
		}catch(Exception e){
			logger.error("{}", e);
		}
		return path;
	}


    public static void genEntity(GenScheme genScheme){
        String suffix = ".java";
        String templateName = "entity.ftl";
        String outPath = getOutPath() +genScheme.getPackageName()+"."+genScheme.getModuleName();
        if(StringUtils.isNotBlank(genScheme.getSubModuleName())){
            outPath = outPath+"."+genScheme.getSubModuleName();
        }
        outPath = outPath+".entity";
        outPath = StringUtils.replace(outPath,".","/");
        String fileName = genScheme.getGenTable().getClassName() + suffix;
        generateFileByTemplate(templateName,outPath,fileName,genScheme);

    }
    public static void genDao(GenScheme genScheme){
        String templateName = "dao.ftl";
        String suffix = "Dao.java";
        String outPath = getOutPath() + genScheme.getPackageName()+"."+genScheme.getModuleName();
        if(StringUtils.isNotBlank(genScheme.getSubModuleName())){
            outPath = outPath+"."+genScheme.getSubModuleName();
        }
        outPath = outPath+".dao";
        outPath = StringUtils.replace(outPath,".","/");
        String fileName = genScheme.getGenTable().getClassName() + suffix;
        generateFileByTemplate(templateName,outPath,fileName,genScheme);
    }
    public static void genService(GenScheme genScheme){
        String templateName = "service.ftl";
        String suffix = "Service.java";
        String outPath = getOutPath() + genScheme.getPackageName()+"."+genScheme.getModuleName();
        if(StringUtils.isNotBlank(genScheme.getSubModuleName())){
            outPath = outPath+"."+genScheme.getSubModuleName();
        }
        outPath = outPath+".service";
        outPath = StringUtils.replace(outPath,".","/");
        String fileName = genScheme.getGenTable().getClassName() + suffix;
        generateFileByTemplate(templateName,outPath,fileName,genScheme);
    }
    public static void genController(GenScheme genScheme){
        String templateName = "controller.ftl";
        String suffix = "Controller.java";
        String outPath = getOutPath() + genScheme.getPackageName()+"."+genScheme.getModuleName();
        if(StringUtils.isNotBlank(genScheme.getSubModuleName())){
            outPath = outPath+"."+genScheme.getSubModuleName();
        }
        outPath = outPath+".controller";

        outPath = StringUtils.replace(outPath,".","/");
        String fileName = genScheme.getGenTable().getClassName() + suffix;
        generateFileByTemplate(templateName,outPath,fileName,genScheme);
    }
    public static void genMapper(GenScheme genScheme){
        final String templateName = "mapper.ftl";
        String suffix = "Dao.xml";
        String outPath = getOutPath() + genScheme.getPackageName()+"."+genScheme.getModuleName();
        if(StringUtils.isNotBlank(genScheme.getSubModuleName())){
            outPath = outPath+"."+genScheme.getSubModuleName();
        }
        outPath = outPath+".mapper";

        outPath = StringUtils.replace(outPath,".","/");
        String fileName = genScheme.getGenTable().getClassName() + suffix;
        generateFileByTemplate(templateName,outPath,fileName,genScheme);
    }
    public static void generateFileByTemplate(String templateName,String outDir,String outFileName,GenScheme genScheme) {
        try {
            File fileOutDir = new File(outDir);
            if(!fileOutDir.exists()){
                fileOutDir.mkdirs();
            }
            File outFile = new File(outDir+"/"+outFileName);
            if(!outFile.exists()){
                outFile.createNewFile();
            }

            Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
            FileOutputStream fos = new FileOutputStream(outFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
            template.process(genScheme,out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
		GenScheme genScheme = new GenScheme();
        genEntity(genScheme);
    }
}
