package life.majiang.community.model;

import lombok.Data;

import java.util.Locale;
@Data
public class User {
    private int id;
    private String name;
    private String accoutId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
