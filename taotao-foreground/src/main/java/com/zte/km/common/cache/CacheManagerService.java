package com.zte.km.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Service
public class CacheManagerService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MemCacheKeys memCacheKeys;
    /**
     * 1.获取/设置redis值
     * @param cacheKey 缓存实体标识
     * @param callback 回调函数
     * @param param 参数
     * @return redis值
     */
    public <T> T getAndSet(String cacheKey, Callable<T> callback, Object... param){
        if (cacheKey == null || cacheKey.isEmpty())
            return null;
        CacheEntity cacheEntity = memCacheKeys.getCacheEntity(cacheKey);
        if (cacheEntity == null)
            return null;
        String key = cacheEntity.getKey();
        if (param !=null && param.length > 0){
            key=String.format(key,param);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        T value = (T) valueOperations.get(key);
        if (value==null){
            try {
                value = callback.call();
                Integer timeOut = cacheEntity.getTimeOut();
                if (timeOut == null || timeOut < 0){
                    valueOperations.set(key,value);
                }else {
                    valueOperations.set(key,value,timeOut, cacheEntity.getTimeUnitValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

}
