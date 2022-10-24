package indiv.budin.mapper;

import indiv.budin.entity.po.RelationBetweenFolder;
import indiv.budin.entity.po.RelationBetweenFolderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RelationBetweenFolderMapper {
    long countByExample(RelationBetweenFolderExample example);

    int deleteByExample(RelationBetweenFolderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RelationBetweenFolder record);

    int insertSelective(RelationBetweenFolder record);

    List<RelationBetweenFolder> selectByExample(RelationBetweenFolderExample example);

    RelationBetweenFolder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RelationBetweenFolder record, @Param("example") RelationBetweenFolderExample example);

    int updateByExample(@Param("record") RelationBetweenFolder record, @Param("example") RelationBetweenFolderExample example);

    int updateByPrimaryKeySelective(RelationBetweenFolder record);

    int updateByPrimaryKey(RelationBetweenFolder record);
}