package life.majiang.community.mapper;

import java.util.List;
import life.majiang.community.model.Qusetion;
import life.majiang.community.model.QusetionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QusetionMapper {
    long countByExample(QusetionExample example);

    int deleteByExample(QusetionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Qusetion record);

    int insertSelective(Qusetion record);

    List<Qusetion> selectByExampleWithBLOBsWithRowbounds(QusetionExample example, RowBounds rowBounds);

    List<Qusetion> selectByExampleWithBLOBs(QusetionExample example);

    List<Qusetion> selectByExampleWithRowbounds(QusetionExample example, RowBounds rowBounds);

    List<Qusetion> selectByExample(QusetionExample example);

    Qusetion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Qusetion record, @Param("example") QusetionExample example);

    int updateByExampleWithBLOBs(@Param("record") Qusetion record, @Param("example") QusetionExample example);

    int updateByExample(@Param("record") Qusetion record, @Param("example") QusetionExample example);

    int updateByPrimaryKeySelective(Qusetion record);

    int updateByPrimaryKeyWithBLOBs(Qusetion record);

    int updateByPrimaryKey(Qusetion record);
}