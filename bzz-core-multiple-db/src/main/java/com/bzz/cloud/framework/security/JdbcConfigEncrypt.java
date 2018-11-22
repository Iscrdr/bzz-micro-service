package com.bzz.cloud.framework.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @PACKAGE_NAME: com.bzz.cloud.framework.security
 * @CLASS_NAME: JdbcConfigEncrypt
 * @Description:
 * @Author : yang qianli
 * @Date: 2017-11-24 1:38
 * @Modified by:
 * @Date:
 */
public class JdbcConfigEncrypt extends PropertyPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        String password = props.getProperty("jdbc.password");
        if (password != null) {
            //**Decryption assignment
            props.setProperty("jdbc.password", DeAndEncrpt.deCode(password));
        }
        super.processProperties(beanFactory, props);
    }
}
