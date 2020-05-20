/**
 * Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
 */
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.dao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.entity.${genTable.className};
import com.bzz.cloud.core.dao.BaseDao;
/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@BzzMyBatisDao("${genTable.classNameLower}Dao")
public interface ${genTable.className}Dao<S extends BaseEntity<${genTable.className}, Long>> extends BaseDao<${genTable.className},Long> {
	
}