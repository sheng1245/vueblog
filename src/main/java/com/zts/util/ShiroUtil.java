package com.zts.util;

import com.zts.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @date 2020/10/7
 */
public class ShiroUtil {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
