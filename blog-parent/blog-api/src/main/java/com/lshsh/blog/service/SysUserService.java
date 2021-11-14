package com.lshsh.blog.service;

import com.lshsh.blog.dao.pojo.SysUser;
import com.lshsh.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long userId);

    /**
     * 根据账号和密码查找用户
     * @param account
     * @param pwd
     * @return
     */
    SysUser findUser(String account, String pwd);

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    Result getUserInfoByToken(String token);
}
