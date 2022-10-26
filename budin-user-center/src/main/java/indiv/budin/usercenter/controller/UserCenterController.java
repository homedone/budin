package indiv.budin.usercenter.controller;

import indiv.budin.common.constants.UserCenterCode;
import indiv.budin.common.utils.ResultUtil;
import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.po.BudinUserStorageInfo;
import indiv.budin.entity.vo.BudinUserInfoVO;
import indiv.budin.entity.vo.BudinUserVO;
import indiv.budin.usercenter.service.api.UserService;
import indiv.budin.usercenter.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ResultUtil<BudinUserVO> loginByAccount(@RequestParam("account") String account, @RequestParam("password") String password) {
        try {
            BudinUser user = userService.getUserByAccount(account);
            String pass = new String(password);
            if (user == null || !user.getPassword().equals(pass))
                return ResultUtil.failWithExMessage(UserCenterCode.ACCOUNT_OR_PASSWORD_ERROR);
            List<BudinUserStorageInfo> budinUserStorageInfoList = userService.getStorageInfoByUserId(user.getId());
            if (budinUserStorageInfoList.isEmpty())
                return ResultUtil.failWithExMessage(UserCenterCode.BUDIN_STORAGE_ERROR);

            BudinUserInfoVO budinUserInfoVO = new BudinUserInfoVO(user.getUserAccount(), user.getUserNickname(), user.getEmail(), user.getTelephone());
            Integer storageSize = 0;
            for (BudinUserStorageInfo budinUserStorageInfo : budinUserStorageInfoList) {
                storageSize += budinUserStorageInfo.getStorageSize();
            }
            budinUserInfoVO.setStorageSize(storageSize);
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
}
