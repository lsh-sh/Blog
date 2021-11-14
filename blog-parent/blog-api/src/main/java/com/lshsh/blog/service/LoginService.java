package com.lshsh.blog.service;

import com.lshsh.blog.dao.pojo.SysUser;
import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.params.LoginParam;

public interface LoginService {

    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 检查token的合法性
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
