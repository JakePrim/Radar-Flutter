package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestKill {

    @Autowired
    private StringRedisTemplate springRedisTemplate;

    @RequestMapping("kill")
    @ResponseBody
    public String kill() {
        //1. 从Redis获取 手机库存数量
        int phoneCount = Integer.parseInt(springRedisTemplate.opsForValue().get("phone"));
        //2. 判断手机的数量是否够秒杀的
        if (phoneCount > 0) {
            phoneCount--;
            //修改Redis的库存数据
            springRedisTemplate.opsForValue().set("phone", String.valueOf(phoneCount));
            System.out.println("库存减一，剩余：" + phoneCount);
        } else {
            return "库存不足";
        }

        return "";
    }
}
