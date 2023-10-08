package com.bzz.cloud;

import com.bzz.cloud.rbac.entity.SysArea;
import com.bzz.cloud.rbac.service.SysAreaService;
import com.bzz.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-10 11-07
 * @Modified by:
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysAreaTest {

    @Autowired
    private SysAreaService sysAreaService;


    @Test
    public void testallChildArea(){
        SysArea sysArea = new SysArea();
        sysArea.setId(110000L);
        List<SysArea> allChildArea = sysAreaService.findAllChildArea(sysArea);
        String s = JsonUtils.object2Json(allChildArea,true);
        System.out.println(s);
    }
    @Test
    public void testMap(){
        Map<String,String> province = new HashMap<>();
        province.put("key","330000");
        province.put("label","浙江省");

        Map<String,String> city = new HashMap<>();
        city.put("key","330100");
        city.put("label","杭州市");

        Map<String,Map<String,String>> geographic = new HashMap<>();
        geographic.put("province",province);
        geographic.put("city",province);

        String s = JsonUtils.object2Json(geographic,true);
        System.out.println(s);
    }

}
