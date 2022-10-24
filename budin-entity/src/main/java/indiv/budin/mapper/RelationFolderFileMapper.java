package indiv.budin.mapper;

import indiv.budin.entity.po.RelationFolderFile;
import indiv.budin.entity.po.RelationFolderFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RelationFolderFileMapper {
    long countByExample(RelationFolderFileExample example);

    int deleteByExample(RelationFolderFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RelationFolderFile record);

    int insertSelective(RelationFolderFile record);

    List<RelationFolderFile> selectByExample(RelationFolderFileExample example);

    RelationFolderFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RelationFolderFile record, @Param("example") RelationFolderFileExample example);

    int updateByExample(@Param("record") RelationFolderFile record, @Param("example") RelationFolderFileExample example);

    int updateByPrimaryKeySelective(RelationFolderFile record);

    int updateByPrimaryKey(RelationFolderFile record);
}