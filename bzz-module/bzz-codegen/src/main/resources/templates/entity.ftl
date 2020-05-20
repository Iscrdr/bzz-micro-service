/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ${genTable.className} extends BaseEntity<${genTable.className},Long> implements Serializable  {
	

	<#-- 生成字段属性 -->
	<#list genTable.columnList as c>
		private ${c.javaType} ${c.javaField} ; <#if c.comments??> // ${c.comments} </#if>
	</#list>



}
