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
import com.bzz.common.Utils.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BzzRbacServer.class})
public class PageTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void testPage(){
        Page<SysUser> page = new Page();
        page.setPageNum(1);
        page.setPageSize(3);

        SysUser sysUser = new SysUser();
        sysUser.setPage(page);
        Page<SysUser> sysUserPage = sysUserService.selectPageMysql(sysUser);

        System.out.println(sysUserPage.getList().size());
    }

}
