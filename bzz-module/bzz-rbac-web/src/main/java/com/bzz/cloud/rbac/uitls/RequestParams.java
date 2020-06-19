package com.bzz.cloud.rbac.uitls;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ：Iscrdr
 * @description：请求参数封装
 * @email ：624003618@qq.com
 * @date ：2020-06-13 04:55
 * @modified By：
 * @version: 1.0.0
 */
@Getter
@Setter
public class RequestParams {
    private List<Long> ids;
}
