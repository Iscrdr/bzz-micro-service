/**
* Copyright &copy; 2012-2019 <a href="https://github.com/qianli8811/bzz-cloud">bzz-cloud</a> All rights reserved.
*/
package ${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.service;

import com.bzz.cloud.${moduleName}.entity.${genTable.className};
import com.bzz.cloud.core.service.BzzBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bzz.cloud.${moduleName}.dao.${genTable.className}Dao;
/**
 * @desc: ${moduleDesc}
 *
 * @Auther: ${moduleAuthor}
 * @Email: ${email}
 * @createDate: ${(createTime?string("yyyy-MM-dd HH:mm:ss"))!}
 * @updateDate: ${(updateTime?string("yyyy-MM-dd HH:mm:ss"))!}
 */
@Service
@Transactional
public class ${genTable.className}Service extends BzzBaseService<${genTable.className}Dao, ${genTable.className},Long>  {


	
}