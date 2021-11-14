package com.lshsh.blog.service;

import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.params.LoginParam;

public interface LoginService {

    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);
}
