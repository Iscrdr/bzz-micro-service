package com.bzz.cloud.framework.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReqLogger {
	/**
	 * 说明
	 * @return
	 */
	public String desc();
}
