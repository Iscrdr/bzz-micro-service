package com.bzz.cloud.core.dao;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.Utils.IdUtils;
import com.bzz.common.Utils.RedisUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: TODO
 * @email 624003618@qq.com
 * @date 2019-01-01 03:10:37
 */
public abstract class BzzBaseDao <T extends BaseEntity,PK extends Serializable> implements BaseDao {

}
