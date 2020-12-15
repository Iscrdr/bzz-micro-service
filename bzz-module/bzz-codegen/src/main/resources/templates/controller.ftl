/**
* Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
*/
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.web;


import ${packageName}.${moduleName}.entity<#if subModuleName != "">.${subModuleName}</#if>.${codeGenTable.className};
import ${packageName}.${moduleName}.service<#if subModuleName != "">.${subModuleName}</#if>.${codeGenTable.className}Service;

/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@Api(tags="${codeGenTable.className}Controller",value="${codeGenTable.className}Controller")
@RestController
@RequestMapping(value = "/${moduleName}")
public class ${codeGenTable.className}Controller  {

	@Autowired
	private ${codeGenTable.className}Service ${codeGenTable.classNameLower}Service;



}
