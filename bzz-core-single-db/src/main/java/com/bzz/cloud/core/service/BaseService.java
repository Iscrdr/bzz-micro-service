
package com.bzz.cloud.core.service;


import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Service基类
 * @author yang-ql
 * @version 2014-05-16
 */
public interface BaseService<D extends BzzBaseDao,T extends BaseEntity ,PK>  {


}
