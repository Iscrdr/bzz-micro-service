package com.bzz.cloud.gen.web;

import com.bzz.cloud.core.utils.RequestPage;
import com.bzz.cloud.gen.entity.CodeGenTable;
import com.bzz.cloud.gen.service.CodeGenTableService;
import com.bzz.cloud.gen.vo.ICodeGenTable;
import com.bzz.common.utils.ResponseResult;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ：Iscrdr
 * @description：代码生成
 * @email ：624003618@qq.com
 * @date ：2020-11-29 00:59
 * @modified By：
 * @version: 1.0.0
 */
@Api(tags="CodeGenTableController",value="代码生成")
@RestController
@RequestMapping("/code")
public class CodeGenTableController {

    @Autowired
    private CodeGenTableService codeGenTableService;



    /**
     * @description：分页查询
     * @author     ：Iscrdr
     * @date       ：2020-06-08 19:00
     * @email      ：624003618@qq.com
     * @modified By：
     * @version:     1.0.0
     */
    @ApiImplicitParam(name = "tableName", value = "表明", defaultValue = "")
    @ApiOperation(value = "数据库列表", notes = "分页查询", code = 200, produces = "application/json")
    @ResponseBody

    @GetMapping("/table")
    public ResponseResult list(@ApiIgnore(value = "request") HttpServletRequest request, @ApiIgnore(value = "response") HttpServletResponse response,
                                    @RequestParam("tableName")String tableName){
        CodeGenTable codeGenTable = new CodeGenTable();
        if(StringUtils.isNotBlank(tableName)){
            codeGenTable.setTableName(tableName);
        }
        List<CodeGenTable> list = codeGenTableService.findList(codeGenTable);
        ResponseResult rr = new ResponseResult();
        rr.setData(list);
        rr.setSuccess(true);
        return rr;
    }



}
