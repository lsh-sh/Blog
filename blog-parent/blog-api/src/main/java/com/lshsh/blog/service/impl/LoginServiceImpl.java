package com.lshsh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.lshsh.blog.dao.pojo.SysUser;
import com.lshsh.blog.service.LoginService;
import com.lshsh.blog.service.SysUserService;
import com.lshsh.blog.utils.JWTUtils;
import com.lshsh.blog.vo.ErrorCode;
import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String slat = "mszlu!@#";


    @Override
    public Result login(LoginParam loginParam) {

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        String pwd = DigestUtils.md5Hex(password + slat);
        System.out.println("pwd" + pwd);
        SysUser sysUser = sysUserService.findUser(account, pwd);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        //将token放入redis中，1天后过期
        redisTemplate.opsForValue().set("TOEKN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }

        String jsonString = redisTemplate.opsForValue().get("TOEKN_" + token);
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }

        SysUser sysUser = JSON.parseObject(jsonString, SysUser.class);

        return sysUser;
    }
}
