package com.bzz.cloud.gen.web;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.gen.entity.GenTable;
import com.bzz.cloud.gen.service.GenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenTableController {

    @Autowired
    private GenTableService genTableService;

    @PostMapping("/getGenTable")
    @ResponseBody
    public GenTable getGenTable(Long id){

        //BaseEntity baseEntity = genTableService.get(id);
        return null;
    }


}
