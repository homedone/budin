package indiv.budin.mapper;

import indiv.budin.entity.po.BudinFolder;
import indiv.budin.entity.po.BudinFolderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BudinFolderMapper {
    long countByExample(BudinFolderExample example);

    int deleteByExample(BudinFolderExample example);

    int deleteByPrimaryKey(Integer folderId);

    int insert(BudinFolder record);

    int insertSelective(BudinFolder record);

    List<BudinFolder> selectByExample(BudinFolderExample example);

    BudinFolder selectByPrimaryKey(Integer folderId);

    int updateByExampleSelective(@Param("record") BudinFolder record, @Param("example") BudinFolderExample example);

    int updateByExample(@Param("record") BudinFolder record, @Param("example") BudinFolderExample example);

    int updateByPrimaryKeySelective(BudinFolder record);

    int updateByPrimaryKey(BudinFolder record);
}