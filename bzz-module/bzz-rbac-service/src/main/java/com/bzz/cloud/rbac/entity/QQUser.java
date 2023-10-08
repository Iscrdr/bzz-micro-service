package com.bzz.cloud.rbac.entity;

import com.bzz.cloud.core.entity.BaseEntity;
import com.bzz.common.utils.JsonUtils;
import lombok.*;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-22 21-34
 * @Modified by:
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QQUser extends BaseEntity<Long> {


    private static final long serialVersionUID = 2334972510411069311L;
    /**
     * 返回码
     */
    private String ret;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     *
     */
    private String openId;
    /**
     * 不知道什么东西，文档上没写，但是实际api返回里有。
     */
    private String is_lost;
    /**
     * 省(直辖市)
     */
    private String province;
    /**
     * 市(直辖市区)
     */
    private String city;
    /**
     * 出生年月
     */
    private String year;
    /**
     * 用户在QQ空间的昵称。
     */
    private String nickname;

    private String constellation;
    /**
     * 大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;
    /**
     * 大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl_1;
    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;
    /**
     * 大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;
    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
     */
    private String figureurl_qq_2;


    private String figureurl_qq;

    private String figureurl_type;


    /**
     * 性别。 如果获取不到则默认返回”男”
     */
    private String gender;
    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）。
     */
    private String is_yellow_vip;
    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String vip;
    /**
     * 黄钻等级
     */
    private String yellow_vip_level;
    /**
     * 黄钻等级
     */
    private String level;
    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private String is_yellow_year_vip;


    public static void main(String[] args) {
        String userString = "{\n" +
                "\"ret\": 0,\n" +
                "\"msg\": \"\",\n" +
                "\"is_lost\": 0,\n" +
                "\"nickname\": \"冰舞\",\n" +
                "\"gender\": \"男\",\n" +
                "\"province\": \"北京\",\n" +
                "\"city\": \"海淀\",\n" +
                "\"year\": \"1988\",\n" +
                "\"constellation\": \"\",\n" +
                "\"figureurl\": \"http://qzapp.qlogo.cn/qzapp/100687189/89708EB6F3BC89FA8ED8E62E6C8848B2/30\",\n" +
                "\"figureurl_1\": \"http://qzapp.qlogo.cn/qzapp/100687189/89708EB6F3BC89FA8ED8E62E6C8848B2/50\",\n" +
                "\"figureurl_2\": \"http://qzapp.qlogo.cn/qzapp/100687189/89708EB6F3BC89FA8ED8E62E6C8848B2/100\",\n" +
                "\"figureurl_qq_1\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=ricap3ygI9cFOI6avoQe7Og&s=40\",\n" +
                "\"figureurl_qq_2\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=ricap3ygI9cFOI6avoQe7Og&s=100\",\n" +
                "\"figureurl_qq\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=ricap3ygI9cFOI6avoQe7Og&s=140\",\n" +
                "\"figureurl_type\": \"1\",\n" +
                "\"is_yellow_vip\": \"0\",\n" +
                "\"vip\": \"0\",\n" +
                "\"yellow_vip_level\": \"0\",\n" +
                "\"level\": \"0\",\n" +
                "\"is_yellow_year_vip\": \"0\"\n" +
                "}";
        QQUser user = (QQUser)JsonUtils.json2Object(userString, QQUser.class);
        System.out.println(user);
    }

}
