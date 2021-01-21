package blog.bean;

import lombok.Data;

/**
 * @author if
 */
@Data
public class Comment {
    private int commentId;
    private String identity1;
    private String email;
    private String body;
    private int blogId;
    private String time1;
    private int ifShow;
}
