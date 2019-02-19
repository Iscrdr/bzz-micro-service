package com.bzz.cloud;

import com.bzz.cloud.gen.entity.GenTable;
import com.bzz.cloud.gen.service.GenTableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={GenApp.class})
public class GenTableTest {
    @Autowired
    private GenTableService genTableService;


    @Test
    public void testGetTable(){
        GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());


    }
}
