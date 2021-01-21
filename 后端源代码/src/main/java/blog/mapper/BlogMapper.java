package blog.mapper;

import blog.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author if
 */
@Mapper
public interface BlogMapper {
    /**
     * 分页查询返回博客
     * @param pageNum
     * @return
     */
    List<Blog> getBlogs(int pageNum);
    /**
     * 查看博客
     * @param blogId
     * @return
     */
    Blog readBlog(int blogId);
    /**
     *添加博客
     * @param title
     * @param body
     */
    void addBlog(@Param("title")String title,@Param("body")String body);
    /**
     * 添加置顶博客
     * @param title
     * @param body
     */
    void addTopBlog(@Param("title")String title,@Param("body")String body);
    /**
     * 查询所有置顶博客
     * @return
     */
    List<Blog> getTopBlogs();
    /**
     * 查询关于我
     * @return
     */
    About getAbout();
    /**
     * 修改关于我
     * @param about
     */
    void changeAbout(String about);
    /**
     * 查询网站声明
     * @return
     */
    Statement getStatement();
    /**
     * 修改网站声明
     * @param statement
     */
    void changeStatement(String statement);
    /**
     * 分页查询留言
     * @param pageNum
     * @return
     */
    List<Message> getMessage(int pageNum);
    /**
     * 分页查询未激活的留言
     * @param pageNum
     * @return
     */
    List<Message> getActiveMessage(int pageNum);
    /**
     * 添加留言
     * @param identity1
     * @param body
     * @param email
     */
    void addMessage(@Param("identity1")String identity1,@Param("body")String body,@Param("email")String email);
    /**
     * 激活留言
     * @param id
     */
    void acMessage(int id);
    /**
     * 删除留言
     * @param id
     */
    void deleteMessage(int id);
    /**
     * 删除博客
     * @param blogId
     */
    void deleteBlog(int blogId);
    /**
     * 修改博客
     * @param blogId
     * @param title
     * @param body
     * @param top
     */
    void changeBlog(@Param("blogId")int blogId,@Param("title")String title,@Param("body")String body,@Param("top")int top);
    /**
     * 查询博客
     * @param title
     * @return
     */
    List<Blog> searchBlog(String title);
    /**
     * 添加评论
     * @param identity1
     * @param email
     * @param body
     * @param blogId
     */
    void addComment(@Param("identity1")String identity1,@Param("email")String email,@Param("body")String body,@Param("blogId")int blogId);
    /**
     * 查单个博客中的评论
     * @param blogId
     * @param pageNum
     * @return
     */
    List<Comment> getComment(@Param("blogId") int blogId,@Param("pageNum") int pageNum);

    /**
     * 查询所有激活的评论
     * @param pageNum
     * @return
     */
    List<Comment> getAllComments(int pageNum);
    /**
     * 激活评论
     * @param commentId
     */
    void acComment(int commentId);
    /**
     * 查询所有未激活评论
     * @return
     */
    List<Comment> getComments();
    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(@Param("commentId")int commentId);
    /**
     * 添加回复
     * @param body
     * @param commentId
     */
    void addReply(@Param("body")String body,@Param("commentId")int commentId);
    /**
     * 删除回复
     * @param replyId
     */
    void deleteReply(int replyId);
    /**
     * 查询评论的回复
     * @param commentId
     * @return
     */
    List<Reply> getReply(@Param("commentId")int commentId);
    /**
     * 获取自动保存的博客
     * @return
     */
    String getAutoBlog();
    /**
     * 自动保存博客
     * @param body
     */
    void changeAutoBlog(String body);
}
