package indiv.budin.mapper;

import indiv.budin.entity.po.BudinFile;
import indiv.budin.entity.po.BudinFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BudinFileMapper {
    long countByExample(BudinFileExample example);

    int deleteByExample(BudinFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BudinFile record);

    int insertSelective(BudinFile record);

    List<BudinFile> selectByExample(BudinFileExample example);

    BudinFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BudinFile record, @Param("example") BudinFileExample example);

    int updateByExample(@Param("record") BudinFile record, @Param("example") BudinFileExample example);

    int updateByPrimaryKeySelective(BudinFile record);

    int updateByPrimaryKey(BudinFile record);
}