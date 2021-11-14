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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional //添加事务注解，失败进行回滚
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

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOEKN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        //检查参数的合法性
        String account = loginParam.getAccount();
        String nickname = loginParam.getNickname();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(nickname) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        //查询长号是否已存在
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        //保存到数据库
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUserService.save(sysUser);
        //生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //将token存入redis
        redisTemplate.opsForValue().set("TOEKN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
