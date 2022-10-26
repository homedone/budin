package indiv.budin.mapper;

import indiv.budin.entity.po.BudinUserStorageInfo;
import indiv.budin.entity.po.BudinUserStorageInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BudinUserStorageInfoMapper {
    long countByExample(BudinUserStorageInfoExample example);

    int deleteByExample(BudinUserStorageInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BudinUserStorageInfo record);

    int insertSelective(BudinUserStorageInfo record);

    List<BudinUserStorageInfo> selectByExample(BudinUserStorageInfoExample example);

    BudinUserStorageInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BudinUserStorageInfo record, @Param("example") BudinUserStorageInfoExample example);

    int updateByExample(@Param("record") BudinUserStorageInfo record, @Param("example") BudinUserStorageInfoExample example);

    int updateByPrimaryKeySelective(BudinUserStorageInfo record);

    int updateByPrimaryKey(BudinUserStorageInfo record);

    List<BudinUserStorageInfo> getUserStorageInfoByUserId(Integer user_id);
}