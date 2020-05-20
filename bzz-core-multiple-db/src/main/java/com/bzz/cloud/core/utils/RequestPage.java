package com.bzz.cloud.core.utils;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.Utils.Page;
import lombok.Getter;
import lombok.Setter;


/**
 * @description：获取request分页参数
 * @author     ：Iscrdr
 * @email      ：624003618@qq.com
 * @date       ：2020-04-23 03:39
 * @modified By：
 * @version: 1.0.0
 */

@Getter
@Setter
public class RequestPage<T extends BaseEntity> {

    private T baseEntity;
    private Page page;
}
