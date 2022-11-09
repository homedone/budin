package indiv.budin.usercenter.controller;

import com.fasterxml.jackson.databind.BeanProperty;
import indiv.budin.common.constants.CommonCode;
import indiv.budin.common.constants.EmailTemplate;
import indiv.budin.common.constants.UserCenterCode;
import indiv.budin.common.utils.ResultUtil;
import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.po.BudinUserStorageInfo;
import indiv.budin.entity.vo.BudinUserInfoVO;
import indiv.budin.entity.vo.BudinUserRegisterVO;
import indiv.budin.entity.vo.BudinUserVO;
import indiv.budin.usercenter.service.api.UserService;
import indiv.budin.usercenter.utils.EmailUtil;
import indiv.budin.usercenter.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2022/10/25 16 15
 * discription
 */
@RestController
public class UserCenterController {
    public Logger logger = LoggerFactory.getLogger(UserCenterController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/center/user/login")
    @ResponseBody
    public ResultUtil<BudinUserVO> loginByAccount(@RequestParam("account") String account, @RequestParam("password") String password) {
        try {
            BudinUser user = userService.getUserByAccount(account);
            //此处密码要加密，并核对，后续再补充,这里可以用手机号登陆
            String pass = new String(password);
            if (user == null || !user.getPassword().equals(pass))
                return ResultUtil.failWithExMessage(UserCenterCode.ACCOUNT_OR_PASSWORD_ERROR);
            List<BudinUserStorageInfo> budinUserStorageInfoList = userService.getStorageInfoByUserId(user.getId());
            if (budinUserStorageInfoList.isEmpty())
                return ResultUtil.failWithExMessage(UserCenterCode.BUDIN_STORAGE_ERROR);
            BudinUserInfoVO budinUserInfoVO = new BudinUserInfoVO();
            BeanUtils.copyProperties(user, budinUserInfoVO);
            long storageSize = 0, usedStorageSize = 0;
            for (BudinUserStorageInfo budinUserStorageInfo : budinUserStorageInfoList) {
                storageSize += budinUserStorageInfo.getStorageSize();
                usedStorageSize += budinUserStorageInfo.getUseStorageSize();
            }
            budinUserInfoVO.setStorageSize(storageSize);
            budinUserInfoVO.setUsedStorageSize(usedStorageSize);
            budinUserInfoVO.setDescription(budinUserStorageInfoList.get(0).getDescription());
            String subject = account + user.getId().toString();
            String token = JWTUtil.createToken(subject);
            BudinUserVO budinUserVO = new BudinUserVO();
            budinUserVO.setBudinUserInfoVO(budinUserInfoVO);
            budinUserVO.setToken(token);
            return ResultUtil.successWithData(budinUserVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failWithExMessage(UserCenterCode.SYSTEM_ERROR);
        }
    }

    /**
     * 发送验证码注册
     *
     * @param email
     * @return
     * @throws MessagingException
     */
    @RequestMapping("/center/user/send/code")
    public ResultUtil<String> sendCode(@RequestParam("email") String email) throws MessagingException {
        //生成验证码，并发送
        String code = EmailUtil.getEmailCode(8);
        String content = EmailTemplate.getEmailMessage(code, EmailTemplate.VALIDATE_TIME);
        boolean sendRes = EmailUtil.send(email, EmailTemplate.EMAIL_SUBJECT, content, EmailTemplate.CONTENT_TYPE);
        if (!sendRes) return ResultUtil.failWithExMessage(UserCenterCode.FAIL_SEND_CODE);
        //发送成功后，存入redis...
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set(email, code, EmailTemplate.VALIDATE_TIME, TimeUnit.MINUTES);
        return ResultUtil.successWithoutData();
    }

    @RequestMapping("/center/user/register")
    @ResponseBody
    public ResultUtil<String> register(@RequestBody BudinUserRegisterVO budinUserRegisterVO) {
        return ResultUtil.successWithoutData();
    }

    @RequestMapping("/center/user/get/info")
    public ResultUtil<BudinUserInfoVO> getUserInfoByUserId(Integer userId) {
        userService.getUserInfoByUserId(userId);
        return ResultUtil.failWithExMessage(UserCenterCode.SYSTEM_ERROR);
    }

    @RequestMapping("/center/logout")
    @ResponseBody
    public ResultUtil<String> logout(HttpServletRequest request) {
        String token = request.getHeader(JWTUtil.header);
        String check = JWTUtil.validateToken(token);
        if (check == null) return ResultUtil.fail();
        if (!JWTUtil.isExpiration(token)) {
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(token, "black", JWTUtil.expireTime, TimeUnit.MILLISECONDS);
        }
        JWTUtil.getExpirationDateFromToken(token);
        return ResultUtil.successWithoutData();
    }

    @RequestMapping("/center/token/check")
    @ResponseBody
    public ResultUtil<String> tokenCheck(HttpServletRequest request) {
        String token = request.getHeader(JWTUtil.header);
        if (token == null || token.equals("")) return ResultUtil.failWithExMessage(CommonCode.UNAUTHORIZED);
        //验证token是否合法 验证token是否过期
        if (JWTUtil.isExpiration(token) || JWTUtil.validateToken(token) == null)
            return ResultUtil.failWithExMessage(CommonCode.TOKEN_EXPIRED);
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String value = stringStringValueOperations.get(token);
        //验证token是否在黑名单
        if (value == null) return ResultUtil.successWithoutData();
        return ResultUtil.failWithExMessage(UserCenterCode.WITHOUT_LOGIN);
    }

    @RequestMapping("/center/test")
    public void test() {
        logger.info("test");
    }
}
