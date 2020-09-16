package com.example.repository;

import com.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
// 缓存策略cacheNames 在ehcache.xml中配置
@CacheConfig(cacheNames = "user")
@Repository
public class EhcacheRepository {

    private final CacheManager cacheManager;

    private final UserRepository userRepository;

    public EhcacheRepository(CacheManager cacheManager, UserRepository userRepository) {
        this.cacheManager = cacheManager;
        this.userRepository = userRepository;
    }

    //    name指定缓存名称 key用来指定Spring缓存返回结果
//    @Cacheable(key="'user'")中user要加''单引号 表示这是一个字符串
//
//    @Cacheable 用于select操作
//    @CachePut 用于insert update操作
//    @CacheEvict 用于delete操作
//    @Cacheable和@CachePut和@CacheEvict的key值必须相同 当insert delete update select时数据会统一

    @CachePut(key = "#p0.id")
    public User save(User user) {
        if (user.getId() == null) {
            log.info("新增");
        } else {
            log.info("修改");
            List list = cacheManager.getCache("user").get("users", List.class);
            list.removeIf(item -> ((User) item).getId().equals(user.getId()));
            list.add(user);
        }
        return userRepository.save(user);
    }

    @Cacheable(key = "#p0")
    public Optional<User> findById(Long id) {
        log.info("查询单个");
        return userRepository.findById(id);
    }

    @Cacheable(key = "'users'")
    public List<User> findAll() {
        log.info("查询多个");
        return userRepository.findAll();
    }

    @CacheEvict(key = "#id")
    public void deleteById(Long id) {
        log.info("删除");
        cacheManager.getCache("user").get("users", List.class)
                .removeIf(item -> ((User) item).getId().equals(id));
        userRepository.deleteById(id);
    }
}