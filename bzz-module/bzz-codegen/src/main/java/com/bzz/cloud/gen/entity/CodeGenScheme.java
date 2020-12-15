package com.bzz.cloud.gen.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-三月-01 09-17
 * @Modified by:
 * @Description:
 */
@Getter
@Setter
@NoArgsConstructor
public class CodeGenScheme extends BaseEntity<CodeGenTable,Long> implements Serializable {

    private static final long serialVersionUID = -334058517046612867L;
    /**
     * 名称
     */
    private String name;
    /**
     * 生成包路径
     */
    private String packageName;
    /**
     * 生成模块名
     */
    private String moduleName;
    /**
     * 生成子模块名
     */
    private String subModuleName;
    /**
     * 模块描述
     */
    private String moduleDesc;
    /**
     * 模块作者
     */
    private String moduleAuthor;
    /**
     * 作者电子邮件
     */
    private String email;

    /**
     * 业务表名
     */
    private CodeGenTable codeGenTable;
    /**
     * 是否替换现有文件    0：不替换；1：替换文件
     */
    private Boolean replaceFile;

}
