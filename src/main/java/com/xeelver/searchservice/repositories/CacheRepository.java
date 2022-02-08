package com.xeelver.searchservice.repositories;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface CacheRepository<T> {
    void putValue(String key, T value);
    T getValue(String key);
    void setExpire(String key, long timeout, TimeUnit unit);
    void addList(String key, T value);
    List<T> getListMembers(String key);
    Long getListSize(String key);
    void addToSet(String key, T... values);
    Set<T> getSetMembers(String key);
    void saveHash(String key, Integer id, T value);
    T findInHash(String key, int id);
    void deleteHash(String key, int id);

}
