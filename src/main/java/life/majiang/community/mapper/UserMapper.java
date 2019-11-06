package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user (accoutId,name,token,gmtCreate,gmtModified) values (#{accoutId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);
}
