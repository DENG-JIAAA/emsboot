package top.dj.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author dj
 * @date 2021/2/5
 */
@Component
@Slf4j
// https://blog.csdn.net/zhulier1124/article/details/82193182
public class RedisUtil {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 通过key获取储存在redis中的value
     *
     * @param key
     * @param indexdb 选择redis库 1-15
     * @return 成功返回value 失败返回null
     */
    public String get(String key, int indexdb) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);
            log.info(value);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 通过key获取储存在redis中的value
     *
     * @param key
     * @param indexdb 选择redis库 0-15
     * @return 成功返回value 失败返回null
     */
    public byte[] get(byte[] key, int indexdb) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     *
     * @param key
     * @param value
     * @param indexdb 选择redis库 0-15
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            jedis.set(key, value);
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "0";
        } finally {
            close(jedis);
        }
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     *
     * @param key
     * @param value
     * @param indexdb 选择redis库 0-1
     * @return 成功 返回OK 失败返回 0
     */
    public String set(byte[] key, byte[] value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "0";
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param keys 一个key 也可以是 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param indexdb 选择redis库 0-15
     * @param keys    一个key 也可以是 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(int indexdb, String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param indexdb indexdb 选择redis库 0-15
     * @param keys    keys 一个key 也可以是 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(int indexdb, byte[]... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.del(keys);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 通过key向指定的value值追加值
     *
     * @param key
     * @param str
     * @return 成功返回 -- 添加后value的长度；失败返回 -- 添加的value的长度；异常返回 -- 0L
     */
    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.append(key, str);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * 判断key是否存在(indexdb:0)
     *
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            close(jedis);
        }
    }

    /**
     * 清空当前数据库中的所有 key,此命令从不失败。(indexdb:0)
     *
     * @return 总是返回 OK
     */
    public String flushDB() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.flushDB();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param key
     * @param value   生存时间
     * @param indexdb
     * @return
     */
    public Long expire(String key, int value, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.expire(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     *
     * @param key
     * @param indexdb
     * @return key不存在时 --> -2
     * key存在但没有设置剩余生存时间 --> -1
     * 否则以秒为单位 --> key的剩余生存时间
     * 发生异常 --> 0
     */
    public Long ttl(String key, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )
     *
     * @param key
     * @return 当生存时间移除成功时 --> 1
     * 如果 key 不存在或 key 没有设置生存时间 --> 0
     * 发生异常 --> -1
     */
    public Long persist(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.persist(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 新增key,并设置 key 的生存时间 (以秒为单位)
     *
     * @param key
     * @param seconds 生存时间（单位：秒）
     * @param value
     * @return 设置成功时返回 OK 。当 seconds 参数不合法时，返回一个错误。
     */
    public String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 设置key value并制定这个键值的有效期
     *
     * @param key
     * @param value
     * @param seconds 生存时间（单位：秒）
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * 设置key value, 如果 key 已经存在则返回0, nx ==> not exist
     *
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * 当 key 存在但不是字符串类型时，返回一个错误。
     *
     * @param key
     * @param value
     * @return 返回给定 key 的旧值。当 key 没有旧值时，也即是， key 不存在时，返回 null
     */
    public String getSet(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getSet(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 关闭jedis连接，释放资源。
     *
     * @param jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
