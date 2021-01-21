package blog.bean;

import lombok.Data;

/**
 * @author if
 */
@Data
public class Reply {
    private int replyId;
    private String identity1;
    private String email;
    private String body;
    private int commentId;
    private String time1;
}
