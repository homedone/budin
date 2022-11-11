package indiv.budin.usercenter.service.impl;

import indiv.budin.common.constants.StorageSize;
import indiv.budin.common.constants.StorageType;
import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.po.BudinUserStorageInfo;
import indiv.budin.mapper.BudinUserMapper;
import indiv.budin.mapper.BudinUserStorageInfoMapper;
import indiv.budin.usercenter.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2022/10/25 16 14
 * discription
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BudinUserMapper budinUserMapper;
    @Autowired
    private BudinUserStorageInfoMapper budinUserStorageInfoMapper;

    @Override
    public BudinUser getUserInfoByUserId(Integer id) {
        return budinUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public BudinUser getUserByAccount(String account) {
        return budinUserMapper.getUserByAccount(account);
    }

    @Override
    public BudinUser getUserByEmail(String email) {
        return budinUserMapper.getUserByEmail(email);
    }

    @Override
    public List<BudinUserStorageInfo> getStorageInfoByUserId(Integer user_id) {
        return budinUserStorageInfoMapper.getUserStorageInfoByUserId(user_id);
    }

    @Override
    public boolean saveUser(BudinUser budinUser) {
        int insert = budinUserMapper.insert(budinUser);
        if (insert == 0) return false;
        BudinUser userByEmail = budinUserMapper.getUserByEmail(budinUser.getEmail());
        BudinUserStorageInfo budinUserStorageInfo = new BudinUserStorageInfo();
        budinUserStorageInfo.setUserId(userByEmail.getId());
        budinUserStorageInfo.setStorageSize(StorageSize.ORDINARY_STORAGE * StorageSize.GB);
        budinUserStorageInfo.setStorageType(StorageType.STORAGE_ORDINARY);
        budinUserStorageInfo.setUseStorageSize(0L);
        budinUserStorageInfo.setBuildTime(new Date());
        int storage = budinUserStorageInfoMapper.insert(budinUserStorageInfo);
        return storage > 0;
    }

    @Override
    public boolean isAccountUsed(String account) {
        return false;
    }
}
