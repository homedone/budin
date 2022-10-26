package indiv.budin.usercenter.service.api;

import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.po.BudinUserStorageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date 2022/10/25 16 13
 * discription
 */
@Service
public interface UserService {
    public BudinUser getUserByAccount(String account);

    public BudinUser getUserByEmail(String email);

    public List<BudinUserStorageInfo> getStorageInfoByUserId(Integer user_id);

    void saveUser(BudinUser memberpo);

    public boolean isAccountUsed(String account);
}
