package indiv.budin.usercenter.service.api;

import indiv.budin.entity.po.BudinUser;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2022/10/25 16 13
 * discription
 */
@Service
public interface UserService {
    public BudinUser getUserByAccount(String account);

    public BudinUser getUserByEmail(String email);

    void saveUser(BudinUser memberpo);

    public boolean isAccountUsed(String account);
}
