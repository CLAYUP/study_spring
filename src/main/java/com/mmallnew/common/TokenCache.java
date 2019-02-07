package com.mmallnew.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * 缓存，用于保存
 *
 * @author ：Y.
 * @date ：Created in 17:56 2019/2/7
 * @version : $version$
 */
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) {
                    return Const.NULL;
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value;
        try {
            value = localCache.get(key);
            if (Const.NULL.equals(value)) {
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("localCache get error", e);
        }
        return null;
    }
}
