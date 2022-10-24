package indiv.budin.mapper;

import indiv.budin.entity.po.BudinUser;
import indiv.budin.entity.po.BudinUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BudinUserMapper {
    long countByExample(BudinUserExample example);

    int deleteByExample(BudinUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BudinUser record);

    int insertSelective(BudinUser record);

    List<BudinUser> selectByExample(BudinUserExample example);

    BudinUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BudinUser record, @Param("example") BudinUserExample example);

    int updateByExample(@Param("record") BudinUser record, @Param("example") BudinUserExample example);

    int updateByPrimaryKeySelective(BudinUser record);

    int updateByPrimaryKey(BudinUser record);
}