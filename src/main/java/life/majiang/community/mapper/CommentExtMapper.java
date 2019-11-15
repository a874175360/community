package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import life.majiang.community.model.Qusetion;
import org.springframework.stereotype.Component;

@Component
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}