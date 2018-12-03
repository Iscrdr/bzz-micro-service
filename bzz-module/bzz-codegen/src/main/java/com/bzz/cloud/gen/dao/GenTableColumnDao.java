/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.gen.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bzz.cloud.gen.entity.GenTableColumn;
import org.springframework.stereotype.Repository;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@Repository
public interface GenTableColumnDao extends BaseMapper<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
