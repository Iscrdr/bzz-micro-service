/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.dao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.entity.${codeGenTable.className};

/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@BzzMyBatisDao("${codeGenTable.classNameLower}Dao")
public interface ${codeGenTable.className}Dao extends BzzBaseDao<${codeGenTable.className},Long> {

}
