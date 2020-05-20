package com.bzz.cloud.core.web;/**
 * @description：Controller抽象公共类，封装公共CRUD，分页等功能
 * @author ：Iscrdr
 * @email ：624003618@qq.com
 * @date ：2020-04-23 01:10
 * @modified By：
 * @version: 1.0.0
 */

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.cloud.core.service.BaseService;
import com.bzz.cloud.core.service.BzzBaseService;
import com.bzz.cloud.core.utils.RequestPage;
import com.bzz.common.Utils.MsgData;
import com.bzz.common.Utils.Page;
import com.bzz.common.Utils.ResponseData;
import com.bzz.common.Utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description：Controller抽象公共类，封装公共CRUD，分页等功能
 * @author     ：Iscrdr
 * @email      ：624003618@qq.com
 * @date       ：2020-04-23 01:10
 * @modified By：
 * @version: 1.0.0
 */
public abstract class BaseController<B extends BaseService,D extends BaseDao, T extends BaseEntity> {
    protected Logger LOGGER= LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected BzzBaseService<D,T> bzzBaseService;

    public Page list(HttpServletRequest request, HttpServletResponse response,@RequestBody RequestPage<T> requestPage){
        Page page = requestPage.getPage();
        BaseEntity baseEntity = requestPage.getBaseEntity();
        page = bzzBaseService.findPage(page, baseEntity);
        return page;
    }

    public ResponseResult add(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false)T t){
        try{
            bzzBaseService.insert(t);
            return success("add");
        }catch (Exception e){
            return error("add");
        }


    }
    public ResponseResult update(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false)T t){
        try{
            bzzBaseService.update(t);
            return success("update");
        }catch (Exception e){
            return error("update");
        }

    }
    public ResponseResult detele(HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false)T t){
        bzzBaseService.delete(t);
        try{
            bzzBaseService.delete(t);
            return success("delete");
        }catch (Exception e){
            return error("delete");
        }
    }




    public ResponseResult success(String method){
        ResponseResult rr = new ResponseResult();
        rr.setSuccess(true);
        Map<String,String> map = new HashMap<String,String>(1);
        switch (method){
            case "add" :
                map.put(ResponseData.RESPONE_CODE,"添加成功");
                break;
            case "update" :
                map.put(ResponseData.RESPONE_CODE,"修改成功");
                break;
            case "delete" :
                map.put(ResponseData.RESPONE_CODE,"成功删除");
                break;
            case "addList" :
                map.put(ResponseData.RESPONE_CODE,"批量添加");
                break;
            case "deleteList" :
                map.put(ResponseData.RESPONE_CODE,"批量删除");
                break;
            case "import" :
                map.put(ResponseData.RESPONE_CODE,"导入成功");
                break;
            case "export" :
                map.put(ResponseData.RESPONE_CODE,"导出成功");
                break;
            default:
                map.put(ResponseData.RESPONE_CODE,"成功");
        }
        rr.setMsgMap(map);
        return rr;
    }
    public ResponseResult error(String method){
        ResponseResult rr = new ResponseResult();
        rr.setSuccess(true);
        Map<String,String> map = new HashMap<String,String>(1);
        switch (method){
            case "add" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"添加失败");
                break;
            case "update" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"修改失败");
                break;
            case "delete" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"删除失败");
                break;
            case "addList" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"批量添加失败");
                break;
            case "deleteList" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"批量删除失败");
                break;
            case "import" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"导入失败");
                break;
            case "export" :
                map.put(ResponseData.RESPONE_CODE_ERROR,"导出失败");
                break;
            default:
                map.put(ResponseData.RESPONE_CODE_ERROR,"失败");
        }
        rr.setMsgMap(map);
        return rr;
    }
}
