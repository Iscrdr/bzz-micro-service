package com.bzz.cloud.oauth.dao;

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.framework.annotations.BzzMyBatisDao;
import com.bzz.cloud.oauth.entity.OauthClientDetails;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-04-05 11-47
 * @Modified by:
 * @Description:
 */
@BzzMyBatisDao("oauthClientDetailsDao")
public interface OauthClientDetailsDao extends BaseDao<OauthClientDetails,Long> {

}
