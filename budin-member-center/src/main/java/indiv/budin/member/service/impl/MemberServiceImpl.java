package indiv.budin.member.service.impl;

import indiv.budin.entity.po.BudinUser;
import indiv.budin.member.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {



    @Override
    public BudinUser getUserByLoginAccount(String account) {

        return null;
    }

    @Override
    public BudinUser getUserByEmail(String email) {
        return null;
    }

    @Override
    public void saveBudinUser(BudinUser budinUser) {

    }

    @Override
    public boolean isAccountUsed(String account) {
        return false;
    }
}
