package com.bzz.cloud.services;

import com.bzz.cloud.entity.Auth2User;
import com.bzz.cloud.rbac.dao.SysUserDao;
import com.bzz.cloud.rbac.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class Auth2DetailsService implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 验证用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        SysUser sysUser = sysUserDao.selectUser(loginName);
        String password = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        System.out.println(sysUser.getPassword());
        Auth2User auth2User = new Auth2User(sysUser.getLoginName(),sysUser.getPassword(),true,true,true,true,Collections.emptySet());
        return auth2User;
    }

    public List<Map<String, Object>>  getAllClientDetails() throws UsernameNotFoundException {
        List<Map<String, Object>> allClientDetails = sysUserDao.getAllClientDetails();
        return allClientDetails;
    }


}
