package com.bzz.cloud.rbac.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.stereotype.Repository;


/**
 * 用户DAO接口
 * @author yang-ql
 * @version 2014-05-16
 */
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

	SysUser selectUser(String loginName);

	Page<SysUser> selectPage(Page<SysUser> page, QueryWrapper<SysUser> queryWrapper);

}
