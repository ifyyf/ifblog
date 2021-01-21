package blog.controller;

import blog.bean.Admin;
import blog.bean.Blog;
import blog.service.AdminService;
import blog.service.BlogService;
import blog.service.ReturnService;
import com.alibaba.fastjson.JSONObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author if
 */
@RestController
@CrossOrigin
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private AdminService adminService;
    @Value("${upload.path}")
    private String baseFolderPath;
    private String alyPath="C:/Program Files/apps/project/uploads/img/";
    /**
     * 返回所有未置顶博客
     * @param pageNum
     * @return
     */
    @GetMapping("/getBlogs")
    public Map<String, Object> getBlogs(@PathParam("pageNum")int pageNum){

        return returnService.returnMap("返回博客列表成功",blogService.getBlogs(pageNum));
    }
    /**
     * 阅读博客
     * @param blogId
     * @return
     */
    @GetMapping("/readBlog")
    public Map<String, Object> readBlog(@PathParam("blogId")int blogId){
        return returnService.returnMap("返回博客成功",blogService.readBlog(blogId));
    }
    /**
     * 接收前端图片
     * @param request
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(HttpServletRequest request, MultipartFile image) throws IOException {
//        File baseFolder = new File(baseFolderPath);
        File baseFolder = new File(alyPath);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
        StringBuffer url = new StringBuffer();
        String[] imageFoot =image.getOriginalFilename().split("\\.");
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            //保存图片
//            String imgFilePath=baseFolderPath+imgName;
            String imgFilePath=alyPath+imgName;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(image.getBytes());
            out.flush();
            out.close();
            if("gif".equals(imageFoot[1])){
                url.append("/img/"+imgName);
            }else {
                /**
                 * String.format("%.1f",testFile1.length()/1024.0)获取图片大小得到的结果是String
                 * 通过int picInt= Float.valueOf(pic).intValue()强制将String转为int
                 * picInt即图片大小
                 * 大于120kb的进行压缩
                 */
                File testFile1 = new File(imgFilePath);
                String pic = String.format("%.1f", testFile1.length() / 1024.0);
                int picInt = Float.valueOf(pic).intValue();

                if (picInt >= 120) {
                    Double outInt = 0.25;
                    if (picInt > 120 && picInt <= 250) {
                        outInt = 0.8;
                    } else if (picInt > 250 && picInt <= 400) {
                        outInt = 0.7;
                    } else {
                        outInt = 0.25;
                    }
                    /**
                     * 图片压缩
                     * outputQuality：输出的图片质量，范围：0~1,  1为最高质量
                     * 注意使用该方法时输出的图片格式必须为jpg（即outputFormat("jpg")
                     * 否则若是输出png格式图片，则该方法作用无效【这其实应该算是bug】
                     */
                    Thumbnails.of(imgFilePath)
                            .scale(1f)
                            .outputQuality(outInt)
                            .outputFormat("jpg")
//                            .toFile(baseFolderPath + imgName + "T");
                .toFile(alyPath+ imgName + "T");
                    //删除原图片
                    File deFile = new File(imgFilePath);
                    if (deFile.isFile() && deFile.exists()) {
                        deFile.delete();
                    }
                    imgFilePath = imgName + "T.jpg";
                    url.append("/img/" + imgFilePath);
                } else {
                    url.append("/img/" + imgName);
                }
            }
            JSONObject object = new JSONObject();
            object.put("url", url);
            return returnService.returnMap("上传成功",object);
        } catch (IOException e) {
            return returnService.returnMap("上传失败",null);
        }
    }
    /**
     * 添加博客
     * @param jsonObject
     * @return
     */
    @PostMapping("/addBlog")
    public Map<String, Object> addBlog(@RequestBody JSONObject jsonObject){
        /**
         * title是博客标题
         * 分离出context的title的方法
         */
        String context=jsonObject.getString("context");
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            String[] array=context.split("\n",2);
            String[] array1=array[0].split("# ",2);
            String title=array1[1];
            blogService.addBlog(title,context);
            blogService.changeAutoBlog("# 标题\n正文");
            return returnService.returnMap("发布成功","发布成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 修改博客
     * @param jsonObject
     * @return
     */
    @PostMapping("/changeBlog")
    public Map<String,Object> changeBlog(@RequestBody JSONObject jsonObject){
        String context=jsonObject.getString("context");
        int blogId=jsonObject.getIntValue("blogId");
        int top=jsonObject.getIntValue("top");
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            String[] array=context.split("\n",2);
            String[] array1=array[0].split("# ",2);
            String title=array1[1];
            blogService.changeBlog(blogId,title,context,top);
            return returnService.returnMap("修改成功","修改成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }

    }
    /**
     * 删除博客
     * @param blogId
     * @return
     */
    @GetMapping("/deleteBlog")
    public Map<String,Object> deleteBlog(@PathParam("blogId")int blogId,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.deleteBlog(blogId);
            return returnService.returnMap("删除博客成功","删除博客成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }

    }
    /**
     * 返回所有置顶博客
     * @return
     */
    @GetMapping("/getTopBlogs")
    public Map<String, Object> getTopBlogs(){
        return returnService.returnMap("返回置顶博客成功",blogService.getTopBlogs());
    }
    /**
     * 返回关于我的
     * @return
     */
    @GetMapping("/getAbout")
    public Map<String, Object> getAbout(){
        return returnService.returnMap("返回关于我的",blogService.getAbout());
    }
    /**
     * 修改关于我的
     * @param jsonObject
     * @return
     */
    @PostMapping("/changeAbout")
    public Map<String, Object> changeAbout(@RequestBody JSONObject jsonObject){
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.changeAbout(jsonObject.getString("about"));
            return returnService.returnMap("修改成功","修改成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }

    }
    /**
     * 返回网站声明
     * @return
     */
    @GetMapping("/getStatement")
    public Map<String, Object> getStatement(){
        return returnService.returnMap("返回网站声明",blogService.getStatement());
    }
    /**
     * 修改网站声明
     * @param jsonObject
     * @return
     */
    @PostMapping("/changeStatement")
    public Map<String, Object> changeStatement(@RequestBody JSONObject jsonObject){
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.changeStatement(jsonObject.getString("statement"));
            return returnService.returnMap("修改成功","修改成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 分页返回留言
     * @param pageNum
     * @return
     */
    @GetMapping("/getMessage")
    public Map<String, Object> getMessage(@PathParam("pageNum") int pageNum){
        return returnService.returnMap("返回留言成功",blogService.getMessage(pageNum));
    }
    /**
     * 添加留言
     * @param identity1
     * @param body
     * @param email
     * @return
     */
    @GetMapping("/addMessage")
    public Map<String,Object> addMessage(@PathParam("identity1")String identity1,@PathParam("body") String body,@PathParam("email")String email){
        blogService.addMessage(identity1,body,email);
        return returnService.returnMap("添加留言成功","添加留言成功");
    }
    /**
     * 返回未激活留言
     * @param pageNum
     * @return
     */
    @GetMapping("/getActiveMessage")
    public Map<String,Object> getActiveMessage(@PathParam("pageNum") int pageNum,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            return returnService.returnMap("返回未激活留言",blogService.getActiveMessage(pageNum));
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 激活留言
     * @param id
     * @return
     */
    @GetMapping("/acMessage")
    public Map<String,Object> acMessage(@PathParam("id")int id,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.acMessage(id);
            return returnService.returnMap("激活成功","激活成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 删除留言
     * @param id
     * @return
     */
    @GetMapping("/deleteMessage")
    public Map<String,Object> deleteMessage(@PathParam("id")int id,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.deleteMessage(id);
            return returnService.returnMap("删除成功","删除成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 查询博客
     * @param title
     * @return
     */
    @GetMapping("/searchBlog")
    public Map<String,Object> searchBlog(@PathParam("title")String title){
        return returnService.returnMap("返回博客成功",blogService.searchBlog(title));
    }
    /**
     * 返回未激活评论
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/getComments")
    public Map<String, Object> getComments(@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            return returnService.returnMap("返回评论成功",blogService.getComments());
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 返回激活评论
     * @param pageNum
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/getAllComments")
    public Map<String, Object> getAllComments( @PathParam("pageNum")int pageNum,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            return returnService.returnMap("返回评论成功",blogService.getAllComments(pageNum));
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 添加评论
     * @param identity1
     * @param email
     * @param body
     * @param blogId
     * @return
     */
    @GetMapping("/addComment")
    public Map<String, Object> addComment(@PathParam("identity1") String identity1,@PathParam("email") String email,@PathParam("body") String body,@PathParam("blogId") int blogId){
            blogService.addComment(identity1,email,body,blogId);
            return returnService.returnMap("添加评论成功","添加评论成功");
    }
    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @GetMapping("/deleteComment")
    public Map<String, Object> deleteComment( @PathParam("commentId")int commentId,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.deleteComment(commentId);
            return returnService.returnMap("删除评论成功","删除评论成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }

    }
    /**
     * 返回单个博客评论
     * @param blogId
     * @param pageNum
     * @return
     */
    @GetMapping("/getComment")
    public Map<String, Object> getComment(@PathParam("blogId")int blogId, @PathParam("pageNum")int pageNum){
        return returnService.returnMap("返回评论成功",blogService.getComment(blogId,pageNum));
    }
    /**
     * 添加回复
     * @param body
     * @param commentId
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/addReply")
    public Map<String,Object> addReply(@PathParam("body")String body,@PathParam("commentId")int commentId,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.addReply(body,commentId);
            return returnService.returnMap("添加回复成功","添加成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }

    }
    /**
     * 删除回复
     * @param replyId
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/deleteReply")
    public Map<String,Object> deleteReply(@PathParam("replyId")int replyId,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.deleteReply(replyId);
            return returnService.returnMap("删除评论成功","删除成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 返回回复
     * @param commentId
     * @return
     */
    @GetMapping("/getReply")
    public Map<String, Object> getReply(@PathParam("commentId")int commentId){
        return returnService.returnMap("返回回复成功",blogService.getReply(commentId));
    }
    /**
     * 激活评论
     * @param commentId
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/acComment")
    public Map<String,Object> acComment(@PathParam("commentId")int commentId,@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.acComment(commentId);
            return returnService.returnMap("激活评论成功","激活成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 自动保存博客
     * @param jsonObject
     * @return
     */
    @PostMapping("/changeAutoBlog")
    public Map<String,Object> changeAutoBlog(@RequestBody JSONObject jsonObject){
        String context=jsonObject.getString("context");
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            blogService.changeAutoBlog(context);
            return returnService.returnMap("自动保存成功","自动保存成功");
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
    /**
     * 获取自动保存的博客
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/getAutoBlog")
    public Map<String,Object> getAutoBlog(@PathParam("username") String username,@PathParam("password") String password){
        Admin admin=adminService.adminLogin(username,password);
        if(admin!=null){
            return returnService.returnMap("返回成功",blogService.getAutoBlog());
        }else{
            return returnService.returnMap("无权限操作",null);
        }
    }
}
