package life.majiang.community.mapper;

        import life.majiang.community.model.Qusetion;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.springframework.stereotype.Component;

@Mapper
@Component
public interface QusetionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Qusetion question);

}
