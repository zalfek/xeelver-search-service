package com.xeelver.searchservice.services;

import com.xeelver.searchservice.cache.FlightQueryCacheObject;
import com.xeelver.searchservice.repositories.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService implements CacheService<FlightQueryCacheObject> {
    private final RedisRepository<String> redisRepository;
    private final RedisRepository<FlightQueryCacheObject> flightQueryCacheObjectRedisRepository;

    //region String
    @Override
    public void setObjectAsString(String key, String value) {
        redisRepository.putValue(key, value);
        redisRepository.setExpire(key, 1, TimeUnit.HOURS);
    }

    @Override
    public String getObjectAsString(String key) {
        return redisRepository.getValue(key);
    }
    //endregion


    //region List
    @Override
    public void addToList(String key, FlightQueryCacheObject programmer) {
        flightQueryCacheObjectRedisRepository.addList(key, programmer);
    }

    @Override
    public List<FlightQueryCacheObject> getListMembers(String key) {
        return flightQueryCacheObjectRedisRepository.getListMembers(key);
    }

    @Override
    public Long getListSize(String key) {
        return flightQueryCacheObjectRedisRepository.getListSize(key);
    }
    //endregion


    //region Set
    @Override
    public void addToSet(String key, FlightQueryCacheObject... programmers) {
        flightQueryCacheObjectRedisRepository.addToSet(key, programmers);
    }

    @Override
    public Set<FlightQueryCacheObject> getSetMembers(String key) {
        return flightQueryCacheObjectRedisRepository.getSetMembers(key);
    }
    //endregion


    //region Hash
    @Override
    public void saveHash(String key, FlightQueryCacheObject flightQueryCacheObject) {
        flightQueryCacheObjectRedisRepository.saveHash(key, 1, flightQueryCacheObject);
    }

    @Override
    public FlightQueryCacheObject findInHash(String key, int id) {
        return flightQueryCacheObjectRedisRepository.findInHash(key, id);
    }

    @Override
    public void deleteHash(String key, int id) {
        flightQueryCacheObjectRedisRepository.deleteHash(key, id);
    }
    //endregion
}
