package blog.service;

import blog.mapper.ReturnMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author if
 */
@Service
public class ReturnService implements ReturnMapper {
    @Override
    public Map<String, Object> returnMap(String msg, Object data) {
            Map<String,Object> map=new HashMap<>();
            if(data==null){
                map.put("status",401);
                map.put("msg","请求错误");
                map.put("data", null);
            }else{
                map.put("status",200);
                map.put("msg",msg);
                map.put("data",data);
            }
            return map;
    }
}
