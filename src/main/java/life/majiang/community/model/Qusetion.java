package life.majiang.community.model;

import lombok.Data;

@Data
public class Qusetion {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewConut;
    private Integer commentCount;
    private Integer likeCount;

}
