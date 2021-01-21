package blog.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author if
 */
@Mapper
public interface ReturnMapper {
    /**
     * 请求成功参数正确返回200,查询不到返回401
     * @param msg
     * @param data
     * @return
     */
    Map<String,Object> returnMap(String msg,Object data);
}
