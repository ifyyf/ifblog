package blog.bean;

import lombok.Data;

/**
 * @author if
 */
@Data
public class Blog {
    private int blogId;
    private String title;
    private String body;
    private String identity1;
    private String time1;
    private String category;
    private int top;
}
