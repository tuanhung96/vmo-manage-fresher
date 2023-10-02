package com.vmo.demo.dao;

import com.vmo.demo.entity.Fresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FresherRepositoryRedis {
    private static final String KEY = "FRESHER";
    private RedisTemplate redisTemplate;

    @Autowired
    public FresherRepositoryRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveFresher(Fresher fresher) {
        try {
            redisTemplate.opsForHash().put(KEY, fresher.getId(), fresher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Fresher> findAll() {
        return redisTemplate.opsForHash().values(KEY);
    }

    public Fresher findById(Integer id) {
        return (Fresher) redisTemplate.opsForHash().get(KEY, id);
    }

    public void deleteById(Integer id) {
        redisTemplate.opsForHash().delete(KEY, id);
    }
}
