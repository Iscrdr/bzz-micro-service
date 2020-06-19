package com.bzz.common.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: TODO 公共分页对象
 * @email 624003618@qq.com
 * @date 2018-12-31 19:54:07
 */
@Setter
@Getter
@NoArgsConstructor
public class Page<T>{

    /**
     * 数据库类型,如果为null,默认为mysql,目前支持oracle,mysql,mssql,postgresql
     */
    private String dbType;

    /**
     *每页的数量,默认10
     */
    private int pageSize = 10;
    /**
     总记录数
     */
    private int total ;
    /**
     总的页数
     */
    private int totalPageCount;
    /**
     当前页码,默认1
     */
    private int current = 1;
    /**
     首页索引,默认为1
     */
    private int first = 1;
    /**
     * 最后一页索引
     */
    private int last ;
    /**
     *上一页索引
     */
    private int pre ;
    /**
     *下一页索引
     */
    private int next ;

    private String orderBy = "" ;

    private List<T> data = new ArrayList<>();

    private boolean success;



    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNum, int pageSize,String orderBy) {
        this(pageNum, pageSize,0,orderBy);
    }
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     * @param total 数据条数
     */
    public Page(int pageNum, int pageSize, int total,String orderBy) {
        this(pageNum, pageSize, total, new ArrayList(),"");
    }

    /**
     * 构造方法
     * @param current 当前页码
     * @param pageSize 分页大小
     * @param total 数据条数
     * @param data 本页数据对象列表
     */
    public Page(int current, int pageSize, int total, List data,String orderBy) {

        current = current<1?1:current;
        pageSize = pageSize<1?10:pageSize;

        this.total = total;
        this.pageSize = pageSize;
        this.current = current;
        this.data = data;

        /*
         * 其它参数初始化
         */
        //总页面数
        int totalPage = total / pageSize;
        this.totalPageCount = (total % pageSize == 0) ? totalPage : totalPage + 1;
        //最后一页索引
        this.last = this.totalPageCount;

        this.pre = current <= first ? first : current - 1 ;
        this.next = (current < this.last - 1) ? this.current + 1 : this.last;
        this.orderBy = orderBy;


    }

    public String getDbType() {
        /**
         * 默认数据源为:mysql
         */
        if(StringUtils.isBlank(dbType)){
            this.setDbType("mysql");
        }
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * 设置本页数据对象列表
     * @param data
     */
    public Page setList(List data) {
        this.data = data;
        init();
        return this;
    }

    /**
     * 初始化参数
     */
    public void init(){


        //首页
        this.first = 1;

        //如果最后一页小于第一页,则只有一页
        this.last  = this.last < this.first ? this.first : this.last;

        this.current = current < 1 ? 1 : this.current;
        this.pageSize = pageSize < 1 ? 10 : pageSize;
        int totalPage = total / pageSize;
        this.totalPageCount = (total % pageSize == 0) ? totalPage : totalPage + 1;

        //最后一页索引
        this.last = this.totalPageCount;

        this.pre = current <= first ? first : current - 1 ;
        this.next = (current < this.last - 1) ? this.current + 1 : this.last;


        //当前页码设置
        this.current = this.current >= this.last ? this.last : this.current;

        //下一页设置
        this.next = (this.current < this.last - 1) ? this.current + 1 : this.last;
        //上一页设置
        this.pre = this.current <= 1 ? this.first : this.current - 1;

    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", total=" + total +
                ", totalPageCount=" + totalPageCount +
                ", current=" + current +
                ", first=" + first +
                ", last=" + last +
                ", pre=" + pre +
                ", next=" + next +
                ", orderBy='" + orderBy + '\'' +
                ", data=" + data +
                '}';
    }



}
