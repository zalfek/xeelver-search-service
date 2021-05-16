package com.xeelver.searchservice.services;

import java.util.*;

public interface CacheService<T> {


    void setObjectAsString(String key, String value);

    String getObjectAsString(String key);

    void addToList(String key, T object);

    List<T> getListMembers(String key);

    Long getListSize(String key);

    void addToSet(String key, T... object);

    Set<T> getSetMembers(String key);

    void saveHash(String key, T object);

    Object findInHash(String key, int id);

    void deleteHash(String key, int id);

}
