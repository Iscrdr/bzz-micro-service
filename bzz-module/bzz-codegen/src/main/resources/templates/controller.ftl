/**
* Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
*/
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.web;


import ${packageName}.${moduleName}.entity<#if subModuleName != "">.${subModuleName}</#if>.${genTable.className};
import ${packageName}.${moduleName}.service<#if subModuleName != "">.${subModuleName}</#if>.${genTable.className}Service;

/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@Controller
@RequestMapping(value = "/${moduleName}")
public class ${genTable.className}Controller  {

	@Autowired
	private ${genTable.className}Service ${genTable.classNameLower}Service;
	

	
}