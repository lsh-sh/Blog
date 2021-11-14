package com.lshsh.blog.service;

import com.lshsh.blog.dao.pojo.SysUser;

public interface SysUserService {

    SysUser findUserById(Long userId);

    /**
     * 根据账号和密码查找用户
     * @param account
     * @param pwd
     * @return
     */
    SysUser findUser(String account, String pwd);
}
