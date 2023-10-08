package com.bzz.cloud.core.service;

import com.bzz.cloud.core.dao.BaseDao;
import com.bzz.cloud.core.dao.BzzBaseDao;
import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.IdUtils;
import com.bzz.common.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-16 14-58
 * @Modified by:
 * @Description:
 */

public abstract   class BzzBaseService<D extends BzzBaseDao,T extends BaseEntity,PK>  implements BaseService<D ,T,PK>  {

    @Autowired
    private D baseDao ;



    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据ID获取单条数据
     * @param  baseEntity
     * @return
     */

    public BaseEntity get(BaseEntity baseEntity) {

        return baseDao.get(baseEntity);
    }


    /**
     * 插入数据
     * @param entity
     * @return Object 主键类型
     */


    /**
     * 根据属性查询数据列表
     * @param entity
     * @return
     */
    public List<BaseEntity> findList(BaseEntity entity){
        return baseDao.findList(entity);
    }


    /**
     * 分页查询
     */
    public Page<BaseEntity> findPage(BaseEntity entity, int pageNo, int pageSzie){
        int count = findCount(entity);
        List<BaseEntity> list = baseDao.findPage(entity, pageNo, pageSzie);
        Page<BaseEntity> page = new Page<>(pageNo,pageSzie,count,list,"");
        return page;
    }
    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    public  List<BaseEntity> findAllList(BaseEntity entity){
        return  baseDao.findAllList(entity);
    }

    /**
     * 查询总条数
     * @param entity
     * @return
     */
    public  int findCount(BaseEntity entity){
        return  baseDao.findCount(entity);
    }



    /**
     * 批量插入数据
     * @param list
     * @return
     */
    @Transactional(readOnly = false)
    public  int insertBatch(List<BaseEntity> list){
        return  baseDao.insertBatch(list);
    }

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    @Transactional(readOnly = false)
    public  int updateBatch(List<BaseEntity> list){
        return  baseDao.updateBatch(list);
    }



    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(BaseEntity entity){
        return  baseDao.delete(entity);
    }

    /**
     * 批量删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param list
     * @return
     */
    @Transactional(readOnly = false)
    public  int deleteBacth(List<Long> list){
        return  baseDao.deleteBacth(list);
    }



    public long insert(BaseEntity entity) {
        setCommData(entity,0);
        return baseDao.insert(entity);
    }


    public int update(BaseEntity entity) {

        setCommData(entity,1);
        return baseDao.update(entity);
    }

    /**
     * 公共数据
     * @param entity
     * @param flag 0：插入，1更新
     */
    private void setCommData(BaseEntity entity,int flag){

        Date date = new Date();

        entity.setUpdateTime(date);//更新时间

        //获取当前登录用户
        BaseEntity currentUser = (BaseEntity)redisTemplate.opsForValue().get("currentUser");
        /*
         * 插入
         */
        if(flag == 0){
            if(entity.getId() == null ){
                entity.setId(IdUtils.getLongId()); //id
            }
            entity.setCreateTime(date); //创建时间
            //设置创建人
            if(entity.getCreateUserId() == null && currentUser != null){
                entity.setCreateUserId((Long)currentUser.getId());
                entity.setUpdateUserId((Long)currentUser.getId());
            }else if(entity.getCreateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置创建人的id: "+entity.toString());
            }else if(entity.getUpdateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置最后修改人的id: "+entity.toString());
            }

            entity.setDelFlag(0);
            entity.setVersion(1);
        }else {
            /**
             * 修改
             */
            entity.setVersion(entity.getVersion()+1);
            //设置最后修改人
            if(entity.getUpdateUserId() == null && currentUser != null){
                entity.setUpdateUserId((Long)currentUser.getId());
            }else if(entity.getUpdateUserId() == null && currentUser == null){
                throw new RuntimeException("保存到数据库中的数据必须设置最后修改人的id: "+entity.toString());
            }
            entity.setVersion(entity.getVersion()+1);

        }

    }


}
