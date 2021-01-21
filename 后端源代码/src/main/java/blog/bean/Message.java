package blog.bean;

import lombok.Data;

/**
 * @author if
 */
@Data
public class Message {
    private int id;
    private String identity1;
    private String email;
    private String body;
    private String time1;
    private int ifShow;
}
