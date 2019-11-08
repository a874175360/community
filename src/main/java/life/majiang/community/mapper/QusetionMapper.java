package life.majiang.community.mapper;

        import life.majiang.community.model.Qusetion;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Select;
        import org.springframework.stereotype.Component;

        import java.util.List;

@Mapper
@Component
public interface QusetionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Qusetion question);

    @Select("select * from question")
    List<Qusetion> list();
}
