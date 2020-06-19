package com.bzz.cloud.framework.handler;

import com.bzz.cloud.oauth.entity.Auth2User;
import com.bzz.cloud.rbac.entity.SysMenu;
import com.bzz.cloud.rbac.entity.SysRole;
import com.bzz.cloud.rbac.entity.SysUser;
import com.bzz.common.utils.DateUtils;
import com.bzz.common.utils.JsonUtils;
import com.bzz.common.utils.ResponseData;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-27 01-51
 * @Modified by:
 * @Description:
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)  {

        try {

            String username = auth.getName();
            logger.info("账号：{}，在{}登录成功",username, DateUtils.getCurrentTime(new Date(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));

            Auth2User details = (Auth2User)auth.getPrincipal();

            String clientId = details.getSysUser().getClientId();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            List<String> roleList = new ArrayList<>();
            for (Iterator<? extends GrantedAuthority> iterator = authorities.iterator();iterator.hasNext();){
                GrantedAuthority next = iterator.next();
                roleList.add(next.getAuthority());
            }

            Map<String,Object> dataMap = new HashMap<>();

            dataMap.put("userName", username);
            dataMap.put("loginType", details.getLoginType());
            dataMap.put("status","ok");
            dataMap.put("resCode", ResponseData.LOGIN_CODE);
            SysUser sysUser = details.getSysUser();
            List<SysRole> sysRoleList = sysUser.getSysRoleList();
            List<SysMenu> menuData = new ArrayList<>();
            List<String> authoritys = new ArrayList<>();
            for(SysRole role:sysRoleList){
                String roleType = role.getRoleType().toUpperCase();
                //角色
                if(!authorities.contains(roleType)){
                    authoritys.add(role.getRoleType().toUpperCase());
                }
                List<SysMenu> sysMenuList = role.getSysMenuList();
                menuData.addAll(sysMenuList);
                //菜单下的所有操作权限：添加,修改,删除等.格式：ADMIN_QUERY
                for(SysMenu sysMenu: sysMenuList){
                    if(StringUtils.isNotBlank(sysMenu.getAuthorityStr())){
                        String str = role.getRoleType().toUpperCase()+"_"+sysMenu.getAuthorityStr();
                        if(!authorities.contains(str)){
                            authoritys.add(str);
                        }
                    }
                }
            }
            dataMap.put("currentAuthority",authoritys);

            //默认根节点
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(1L);
            List<SysMenu> menuList = treeSysMenuList(menuData, sysMenu);
            dataMap.put("menuData",menuList);

            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientDetails.getClientId(), clientDetails.getScope(),"read,write");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, auth);


            OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
            if(accessToken == null || accessToken.isExpired()){
                accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            }
            //token过期时间
            int expiresIn = accessToken.getExpiresIn();
            //token刷新
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            String refreshTokenValue = refreshToken.getValue();

            response.setHeader("refreshToken",refreshTokenValue);
            response.setHeader("expiresIn",expiresIn+"");
            response.setHeader("Authorization","Bearer "+accessToken.getValue());
            response.setContentType("application/json;charset=UTF-8");

            JsonUtils.filter(SysMenu.class,null,"createTime,updateTime,page,menu,component,redirect,sort,authorityStr");
            response.getWriter().write(JsonUtils.object2Json(dataMap));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回view
        //super.onAuthenticationSuccess(request,response,auth);
    }

    /**
     * 获取某个父节点下面的所有子节点
     * @param sysMenuList
     * @param rootSysMenu
     * @return
     */
    private   List<SysMenu> treeSysMenuList( List<SysMenu> sysMenuList, SysMenu rootSysMenu){
        List<SysMenu> childMenu = new ArrayList<>();
        for(SysMenu sysMenu: sysMenuList){
            if(sysMenu == null || sysMenu.getId() == null){
                continue;
            }
            SysMenu parentMenu = sysMenu.getParentMenu();

            //遍历出父id等于参数的id，add进子节点集合
            if(parentMenu!=null && parentMenu.getId()!=null && parentMenu.getId().equals(rootSysMenu.getId())){
                childMenu.add(sysMenu);
            }
        }
        // 递归子菜单
        for (SysMenu menu : childMenu) {
            menu.setChildren(treeSysMenuList(sysMenuList,menu));
        } // 递归退出条件
        if (childMenu.size() == 0) {
            return null;
        }
        return childMenu;
    }

}
