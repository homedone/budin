package indiv.budin.usercenter.controller;

import indiv.budin.common.utils.ResultUtil;
import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.vo.BudinUserVO;
import indiv.budin.usercenter.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    @RequestMapping("/center/user/login")
    @ResponseBody
    public ResultUtil<BudinUserVO> loginByAccount(@RequestParam("account") String account, @RequestParam("password") String password, HttpSession session){
        logger.info(account);
        logger.info(password);

        return ResultUtil.successWithoutData();
    }
}
