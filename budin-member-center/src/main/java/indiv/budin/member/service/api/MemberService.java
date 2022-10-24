package indiv.budin.member.service.api;

import indiv.budin.entity.po.BudinUser;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    public BudinUser getUserByLoginAccount(String account);
    public BudinUser getUserByEmail(String email);
    void saveBudinUser(BudinUser budinUser);
    public boolean isAccountUsed(String account);

}
