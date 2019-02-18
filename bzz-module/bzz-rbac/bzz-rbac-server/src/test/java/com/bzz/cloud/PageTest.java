package com.bzz.cloud;

/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: TODO
 * @email 624003618@qq.com
 * @date 2018-12-31 23:01:44
 */

import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.cloud.rbac.service.SysUserService;
import com.bzz.common.Utils.IdUtils;
import com.bzz.common.Utils.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BzzRbacServer.class})
public class PageTest {
    @Resource
    private SysUserService sysUserService;

    @Test
    public void testUser(){
        SysUser sysUser = new SysUser();

        sysUser.setLoginName("admin");
        SysUser userByLoginName = sysUserService.getUserByLoginName(sysUser);
        System.out.println(userByLoginName);
    }

    @Test
    public void testPage(){
        Page<SysUser> page = new Page<>();
        page.setPageNum(1);
        page.setPageSize(3);

        SysUser sysUser = new SysUser();
        sysUser.setId(IdUtils.getLongId());
        //sysUser.setPage(page);
        Page<SysUser> sysUserPage = sysUserService.selectPageMysql(sysUser);

        System.out.println(sysUserPage.getList().size());
    }

    @Test
    public void testService(){
        SysUser sysUser = new SysUser();
        sysUser.setId(IdUtils.getLongId());
        sysUser.setLoginName("qianli8811");
        sysUser.setName("刘亦菲");
        sysUser.setEmail("624003618@qq.com");
        sysUser.setMobile("15501236689");
        sysUser.setPhone("15501236689");
        sysUser.setWorkNum("BZZ-1001");
        sysUser.setPassword("admin");
        sysUser.setUserType(0);
        sysUser.setAccountNonExpired(false);
        sysUser.setAccountNonLocked(false);
        sysUser.setEnabled(true);
        sysUser.setCredentialsNonExpired(true);


        //sysUserService.insert(sysUser);
        Long id = sysUserService.insertOracle(sysUser);
        System.out.println(id);

    }

}
