package com.lshsh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lshsh.blog.dao.mapper.SysUserMapper;
import com.lshsh.blog.dao.pojo.SysUser;
import com.lshsh.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("匿名");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String pwd) {

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, pwd)
                .select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname);
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }
}
