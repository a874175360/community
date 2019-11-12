package life.majiang.community.mapper;

import life.majiang.community.model.Qusetion;
import life.majiang.community.model.QusetionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QusetionExtMapper {
    int incView(Qusetion record);
        }