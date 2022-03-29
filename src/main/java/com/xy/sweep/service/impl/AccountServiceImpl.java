package com.xy.sweep.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.exception.RRException;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.constant.ErrorCode;
import com.xy.sweep.dao.AccountDao;
import com.xy.sweep.email.service.EmailCodeService;
import com.xy.sweep.entity.AccountEntity;
import com.xy.sweep.entity.UserEntity;
import com.xy.sweep.entity.dto.RegisterDTO;
import com.xy.sweep.entity.dto.ResetPwdDTO;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.AccountService;
import com.xy.sweep.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {
    @Resource
    private UserService userService;
    @Resource
    private EmailCodeService emailCodeService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    TokenUtil tokenUtil;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccountEntity> page = this.page(new Query<AccountEntity>().getPage(params), new QueryWrapper<AccountEntity>());

        return new PageUtils(page);
    }

    private Map<String, Object> setToken(AccountEntity accountEntity, HttpServletRequest request) {
        UserEntity user = userService.getById(accountEntity.getUserId());
        UserDTO userDTO = new UserDTO(accountEntity, user);
        String token = tokenUtil.takeToken();
        boolean flag;
        if (flag = !StringUtils.isBlank(token)) {
            Object str = redisTemplate.opsForValue().get(token);
            if (flag = str != null) {
                UserDTO dto = JSON.parseObject(str.toString(), UserDTO.class);
                if (flag = dto != null) {
                    if (flag = accountEntity.getId().equals(dto.getAccount().getId())) {
                        redisTemplate.expire(token, Duration.ofDays(3L));//重复登录,将token有效期延长
                    }
                }
            }
        }
        if (!flag) {
            token = tokenUtil.generateValue();
            Object t = redisTemplate.opsForValue().get(accountEntity.getId() + "");
            if (t != null) {
                redisTemplate.delete(t.toString());
            }
            redisTemplate.opsForValue().set(token, userDTO, Duration.ofDays(3L));
            redisTemplate.opsForValue().set(accountEntity.getId() + "", token);
        }
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    @Override
    public Map<String, Object> login(AccountEntity account, HttpServletRequest request) {
        AccountEntity accountEntity = getOne(new QueryWrapper<AccountEntity>().eq("account", account.getAccount()).eq("password", account.getPassword()));
        if (accountEntity == null) {
            throw new RRException("用户名或密码错误");
        }
        return setToken(accountEntity, request);
    }

    private AccountEntity register(String email, String password) {
        AccountEntity account = new AccountEntity();
        account.setAccount(email);
        account.setPassword(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        userEntity.setEmail(email);
        userService.save(userEntity);
        account.setUserId(userEntity.getId());
        save(account);
        userEntity.setAccountId(account.getId());
        userService.updateById(userEntity);
        return account;
    }

    @Override
    public AccountEntity register(RegisterDTO registerDTO) {
        checkCode(registerDTO.getEmail(), registerDTO.getCode(), EmailCodeService.REGISTER);
        if (userService.getOne(new QueryWrapper<UserEntity>().eq("email", registerDTO.getEmail())) != null) {
            throw new RRException(ErrorCode.EMAIL_EXIST.msg, ErrorCode.EMAIL_EXIST.code);
        }
        return register(registerDTO.getEmail(), registerDTO.getPassword());
    }


    @Override
    public AccountEntity resetPassword(ResetPwdDTO dto) {
        AccountEntity login = getOne(new QueryWrapper<AccountEntity>().eq("account", dto.getAccount()).eq("password", dto.getOldPwd()));
        if (login != null) {
            login.setPassword(dto.getNewPwd());
            updateById(login);
            return login;
        }
        throw new RRException("账号或原密码错误");
    }

    @Override
    public AccountEntity forgetPassword(RegisterDTO registerDTO) {
        checkCode(registerDTO.getEmail(), registerDTO.getCode(), EmailCodeService.FORGET);
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("email", registerDTO.getEmail()));
        if (user == null) {
            throw new RRException("邮箱不存在");
        }
        AccountEntity account = getById(user.getAccountId());
        ResetPwdDTO resetPwdDTO = new ResetPwdDTO();
        resetPwdDTO.setAccount(account.getAccount());
        resetPwdDTO.setNewPwd(registerDTO.getPassword());
        resetPwdDTO.setOldPwd(account.getPassword());
        return resetPassword(resetPwdDTO);
    }

    @Override
    public Map<String, Object> loginForEmail(String email, String code, HttpServletRequest request) {
        checkCode(email, code, EmailCodeService.LOGIN);
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("email", email));
        AccountEntity account = null;
        if (user == null) {
            String randomPassword = UUID.randomUUID().toString().substring(26);
            account = register(email, randomPassword);
            emailCodeService.sendMsg("您好,欢迎使用easy-sweep云扫墓平台,您的账号初始密码为: " + randomPassword + ",请勿泄漏给他人!", email);
        } else {
            account = getOne(new QueryWrapper<AccountEntity>().eq("account", email));
        }
        return setToken(account, request);
    }

    @Override
    public void logout() {
        String token = tokenUtil.takeToken();
        Boolean result = null;
        if (token != null) {
            result = redisTemplate.delete(token);
        }
        if (result == null || !result) {
            throw new RRException("您还没有登录");
        }
    }

    private void checkCode(String email, String code, String type) {
        if (!emailCodeService.checkCode(email, code, type)) {
            throw new RRException("验证码过期或错误!");
        }
    }
}