package com.yanshen.weibo.Util;//package com.yanshen.weibo.Util;
//
//import com.yanshen.weibo.Util.CommonUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @Auther: taohaifeng
// * @Date: 2018/9/19 15:25
// * @Description:
// */
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis.cluster")
////@ConditionalOnClass({JedisCluster.class})
//public class RedisConfig {
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//    private List<String> nodes;
////    @Value("${spring.redis.jedis.pool.max-idle}")
////    private int maxIdle;
////    @Value("${spring.redis.pool.jedis.max-wait}")
////    private long maxWaitMillis;
////    @Value("${spring.redis.timeout}")
////    private int timeout;
////    @Value("${spring.redis.commandTimeout}")
////    private int commandTimeout;
//
//    @Bean
//    public JedisPool getJedisPool(){
//        String[] cNodes = CommonUtil.symbolCN2EN(clusterNodes).split(",");
//        if(cNodes == null || cNodes.length <= 0){
//            throw new RuntimeException("配置文件未设置 Redis 端口和IP列表");
//        }
//
//        if(cNodes.length > 1) {
//        	return null;
//        }
//
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(30);
//		config.setMaxIdle(10);
//		config.setMaxWaitMillis(2000);
//
//		String[] hp = CommonUtil.symbolCN2EN(cNodes[0]).split(":");
//		// 获得连接池
//		JedisPool jedisPool = new JedisPool(config, hp[0], Integer.parseInt(hp[1]));
//		return jedisPool;
//    }
//
//    @Bean
//    public JedisCluster getJedisCluster(){
//        String[] cNodes = CommonUtil.symbolCN2EN(clusterNodes).split(",");
//        if(cNodes == null || cNodes.length <= 0){
//            throw new RuntimeException("配置文件未设置 Redis 端口和IP列表");
//        }
//
//        if(cNodes.length == 1) {
//        	return null;
//        }
//
//        Set<HostAndPort> nodes = new HashSet<>();
//        Arrays.stream(cNodes).forEach(node -> {
//            String[] hp = CommonUtil.symbolCN2EN(node).split(":");
//            if(hp == null || hp.length < 2){
//                throw new RuntimeException("Redis 端口和IP 不存在");
//            }
//            if(hp !=null && hp.length>1){
//                nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
//            }
//        });
//
//        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(30);
//        jedisPoolConfig.setMaxIdle(10);
//        JedisCluster jedisCluster = new JedisCluster(nodes, jedisPoolConfig);
//        return jedisCluster;
//    }
////
////    /**
////     * 设置数据存入redis 的序列化方式
////     *</br>redisTemplate序列化默认使用的jdkSerializeable,存储二进制字节码,导致key会出现乱码，所以自定义
////     *序列化类
////     *
////     * @paramredisConnectionFactory
////     */
////    @Bean
////    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {
////        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
////        redisTemplate.setConnectionFactory(redisConnectionFactory);
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper objectMapper =new ObjectMapper();
////        objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
////        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
////
////        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
////
////        redisTemplate.afterPropertiesSet();
////
////        return redisTemplate;
////    }
//
//    /**
//     * RedisTemplate配置
//     * @param factory
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        RedisSerializer redisObjectSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(redisObjectSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * 生成redis连接
//     * @return
//     */
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        if (nodes.size() > 1) {
//            //集群的项目配置
//            RedisClusterConfiguration configuration = new RedisClusterConfiguration(nodes);
//            configuration.setMaxRedirects(5);
//            //设置 客户端Jedis的连接
//            return new LettuceConnectionFactory(configuration);
//        } else {
//            String[] redisParams = nodes.get(0).split(":");
//            // 单例项目配置
//            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(redisParams[0], Integer.valueOf(redisParams[1]));
//            standaloneConfiguration.setDatabase(1);
//            return new LettuceConnectionFactory(standaloneConfiguration);
//        }
//    }
//
//    public List<String> getNodes() {
//        return nodes;
//    }
//
//    public void setNodes(List<String> nodes) {
//        this.nodes = nodes;
//    }
//}
