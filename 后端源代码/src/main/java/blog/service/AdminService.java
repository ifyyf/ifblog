package blog.service;

import blog.bean.Admin;
import blog.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author if
 */
@Service
public class AdminService implements AdminMapper {
    @Resource
    private AdminMapper adminMapper;
    @Override
        public Admin adminLogin(String username, String password) {
        return adminMapper.adminLogin(username,password);
    }
}
