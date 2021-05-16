package com.xeelver.searchservice.CacheObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("HotelOffer")
public class HotelQueryCacheObject implements CacheObject{
    private String id;
    private String payload;
}
