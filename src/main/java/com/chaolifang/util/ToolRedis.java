package com.chaolifang.util;

import org.springframework.stereotype.Component;

/**
 * @author 方法内加锁的方式 经过了jemeter压力测试没有问题 目前项目不适用 缺少依赖 tryGetLock releaseLock
 */
@Component
public class ToolRedis {
    /*@Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean tryGetLock(String lockKey, String requestId, int expireTime){
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(lockKey.getBytes(), requestId.getBytes(), Expiration.seconds(expireTime) , RedisStringCommands.SetOption.ifAbsent());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean releaseLock(String lockKey, String requestId) {
        try{
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    Object result = redisConnection.eval(script.getBytes(), ReturnType.BOOLEAN,1,lockKey.getBytes(),requestId.getBytes());
                    if("true".equalsIgnoreCase(result.toString())){
                        return true;
                    }
                    return false;
                }
            });
        }catch (Exception e){

        }
        return false;
    }*/

}
