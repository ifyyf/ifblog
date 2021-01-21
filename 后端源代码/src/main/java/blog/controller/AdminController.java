package blog.controller;

import blog.service.AdminService;
import blog.service.ReturnService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author if
 */
@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ReturnService returnService;
    /**
     * post请求
     * 管理员登录
     * @param jsonObject
     * @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JSONObject jsonObject){
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        return returnService.returnMap("登陆成功",adminService.adminLogin(username,password));
    }

    /**
     * 测试连接数据库
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/getLogin")
    public Map<String, Object> getLogin(@RequestParam("username")String username,@RequestParam("password")String password){
        return returnService.returnMap("登陆成功",adminService.adminLogin(username,password));
    }
}
