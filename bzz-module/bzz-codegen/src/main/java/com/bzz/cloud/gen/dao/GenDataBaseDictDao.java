/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bzz.cloud.gen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bzz.cloud.gen.entity.GenTable;
import com.bzz.cloud.gen.entity.GenTableColumn;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@Repository
public interface GenDataBaseDictDao extends BaseMapper<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
