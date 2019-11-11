package life.majiang.community.mapper;

        import life.majiang.community.model.Qusetion;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;
        import org.springframework.stereotype.Component;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Mapper
@Component
public interface QusetionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Qusetion question);

    @Select("select * from question limit #{offset},#{size}")
    List<Qusetion> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select COUNT(1) FROM question")
    Integer count();
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Qusetion> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select COUNT(1) FROM question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);
}
