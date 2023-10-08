package com.bzz.cloud.framework.annotations;



import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Yang qianli
 * @version 1.0.0
 * @ProjectName bzz-cloud
 * @Description: 标识DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * @email 624003618@qq.com
 * @date 2019-01-07 01:20:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented

public @interface BzzMyBatisDao {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";
}
