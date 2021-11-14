package com.lshsh.blog.service;

import com.lshsh.blog.dao.pojo.SysUser;

public interface SysUserService {

    SysUser findUserById(Long userId);
}
