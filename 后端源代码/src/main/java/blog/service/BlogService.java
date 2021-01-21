package blog.service;

import blog.bean.*;
import blog.mapper.BlogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author if
 */
@Service
public class BlogService implements BlogMapper {
    @Resource
    private BlogMapper blogMapper;
    @Override
    public List<Blog> getBlogs(int pageNum) {
        pageNum=(pageNum-1)*5;
        return blogMapper.getBlogs(pageNum);
    }
    @Override
    public List<Comment> getComments() {
        return blogMapper.getComments();
    }
    @Override
    public void addComment(String identity1, String email, String body, int blogId) {
        blogMapper.addComment(identity1,email,body,blogId);
    }
    @Override
    public void deleteComment(int commentId) {
        blogMapper.deleteComment(commentId);
    }
    @Override
    public void addReply(String body, int commentId) {
        blogMapper.addReply(body,commentId);
    }
    @Override
    public void deleteReply(int replyId) {
        blogMapper.deleteReply(replyId);
    }
    @Override
    public List<Reply> getReply(int commentId) {
        return blogMapper.getReply(commentId);
    }

    @Override
    public String  getAutoBlog() {
        return blogMapper.getAutoBlog();
    }

    @Override
    public void changeAutoBlog(String body) {
        blogMapper.changeAutoBlog(body);
    }

    @Override
    public Blog readBlog(int blogId) {
        return blogMapper.readBlog(blogId);
    }
    @Override
    public List<Comment> getComment(int blogId, int pageNum) {
        pageNum=(pageNum-1)*5;
        return blogMapper.getComment(blogId,pageNum);
    }

    @Override
    public List<Comment> getAllComments(int pageNum) {
        pageNum=(pageNum-1)*5;
        return blogMapper.getAllComments(pageNum);
    }

    @Override
    public void acComment(int commentId) {
        blogMapper.acComment(commentId);
    }
    @Override
    public void addBlog(String title, String body) {
        blogMapper.addBlog(title,body);
    }
    @Override
    public void addTopBlog(String title, String body) {
        blogMapper.addTopBlog(title,body);
    }
    @Override
    public List<Blog> getTopBlogs() {
        return blogMapper.getTopBlogs();
    }
    @Override
    public About getAbout() {
        return blogMapper.getAbout();
    }
    @Override
    public void changeAbout(String about) {
        blogMapper.changeAbout(about);
    }
    @Override
    public Statement getStatement() {
        return blogMapper.getStatement();
    }
    @Override
    public void changeStatement(String statement) {
        blogMapper.changeStatement(statement);
    }
    @Override
    public List<Message> getMessage(int pageNum) {
        pageNum=(pageNum-1)*5;
        return blogMapper.getMessage(pageNum);
    }
    @Override
    public List<Message> getActiveMessage(int pageNum) {
        pageNum=(pageNum-1)*5;
        return blogMapper.getActiveMessage(pageNum);
    }
    @Override
    public void addMessage(String identity1, String body, String email) {
        blogMapper.addMessage(identity1,body,email);
    }
    @Override
    public void acMessage(int id) {
        blogMapper.acMessage(id);
    }
    @Override
    public void deleteMessage(int id) {
        blogMapper.deleteMessage(id);
    }
    @Override
    public void deleteBlog(int blogId) {
        blogMapper.deleteBlog(blogId);
    }
    @Override
    public void changeBlog(int blogId, String title, String body, int top) {
        blogMapper.changeBlog(blogId,title,body,top);
    }
    @Override
    public List<Blog> searchBlog(String title) {
        return blogMapper.searchBlog(title);
    }
}
