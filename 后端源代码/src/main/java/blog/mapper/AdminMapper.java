package blog.mapper;

import blog.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * @author if
 */
@Mapper
public interface AdminMapper {
    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    Admin adminLogin(@Param("username") String username, @Param("password") String password);


}
