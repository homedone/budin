package indiv.budin.usercenter.service.impl;

import indiv.budin.entity.po.BudinUser;
import indiv.budin.mapper.BudinUserMapper;
import indiv.budin.usercenter.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2022/10/25 16 14
 * discription
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BudinUserMapper budinUserMapper;
    @Override
    public BudinUser getUserByAccount(String account) {
        return budinUserMapper.getUserByAccount(account);
    }

    @Override
    public BudinUser getUserByEmail(String email) {
        return null;
    }

    @Override
    public void saveUser(BudinUser memberpo) {

    }

    @Override
    public boolean isAccountUsed(String account) {
        return false;
    }
}
