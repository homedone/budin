package indiv.budin.usercenter.controller;

import indiv.budin.common.constants.ExtendCode;
import indiv.budin.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2022/10/31 21 26
 * discription
 */
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    ResultUtil<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(key,value);
            return ResultUtil.successWithoutData();
        }catch (Exception e){
            return ResultUtil.failWithExMessage(new ExtendCode(false,5000,e.getMessage()));
        }
    }

    @RequestMapping("/set/redis/key/value/remote/time")
    ResultUtil<String> setRedisKeyValueRemoteWithTimeout(@RequestParam("key") String key, @RequestParam("value") String value,
                                                         @RequestParam("time") long time, @RequestParam("TimeUnit") TimeUnit timeunit) {
        try {
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(key,value,time,timeunit);
            return ResultUtil.successWithoutData();
        }catch (Exception e){
            return ResultUtil.failWithExMessage(new ExtendCode(false,5000,e.getMessage()));
        }
    }

    @RequestMapping("/get/redis/value/by/key/remote")
    ResultUtil<String> getRedisValueByKeyRemote(@RequestParam("key") String key) {
        try {
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String value= stringStringValueOperations.get(key);
            return ResultUtil.successWithData(value);
        }catch (Exception e){
            return ResultUtil.failWithExMessage(new ExtendCode(false,5000,e.getMessage()));
        }
    }

    @RequestMapping("/remove/redis/key/remote")
    ResultUtil<String> removeRedisKeyRemote(@RequestParam("key") String key) {
        try {
            stringRedisTemplate.delete(key);
            return ResultUtil.successWithoutData();
        }catch (Exception e){
            return ResultUtil.failWithExMessage(new ExtendCode(false,5000,e.getMessage()));
        }
    }
}
