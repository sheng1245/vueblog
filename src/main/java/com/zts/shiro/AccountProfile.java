package com.zts.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @date 2020/10/7
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String evatar;

    private String email;
}
