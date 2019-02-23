package com.bzz.common.Utils;

import lombok.Getter;
import lombok.Setter;

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
public class Page<T>  {

    private int pageSize = 10;//每页的数量,默认10
    private int totalCount ; //总记录数
    private int totalPageCount; // 总的页数
    private int pageNum = 1; //当前页码,默认1
    private int first = 1;//首页索引,默认为1
    private int last ;//最后一页索引
    private int pre ;//上一页索引
    private int next ;//下一页索引

    private String orderBy = "" ;

    private List<T> list = new ArrayList<T>();

    public Page() {
    }
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize,0);
    }
    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     * @param totalCount 数据条数
     */
    public Page(int pageNum, int pageSize, int totalCount) {
        this(pageNum, pageSize, totalCount, new ArrayList<T>());
    }

    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 分页大小
     * @param totalCount 数据条数
     * @param list 本页数据对象列表
     */
    public Page(int pageNum, int pageSize, int totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.list = list;

        /*
         * 其它参数初始化
         */
        //总页面数
        int totalPage = totalCount / pageSize;
        this.totalPageCount = (totalCount % pageSize == 0) ? totalPage : totalPage + 1;

        this.pre = pageNum <= first ? first : pageNum - 1 ;
        this.next = pageNum >= last ? last : pageNum + 1;
        this.last = this.totalPageCount;//最后一页索引

    }


    /**
     * 设置本页数据对象列表
     * @param list
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        init();
        return this;
    }

    /**
     * 初始化参数
     */
    private void init(){

        //首页
        this.first = 1;
        //最后一页
        this.last = totalCount / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1;

        if (this.totalCount % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }
        //如果最后一页小于第一页,则只有一页
        this.last  = this.last < this.first ? this.first : this.last;

        //当前页码设置
        this.pageNum = this.pageNum <=1 ? this.first : this.pageNum;
        this.pageNum = this.pageNum >= this.last ? this.last : this.pageNum;
        //下一页设置
        this.next = (this.pageNum < this.last - 1) ? this.pageNum + 1 : this.last;
        //上一页设置
        this.pre = this.pageNum <= 1 ? this.first : this.pageNum - 1;

    }

}
