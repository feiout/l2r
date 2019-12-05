package com.l2r.sysConfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.config.CacheConfigurationListener;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by messi on 2019-12-05.
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig implements CachingConfigurer{

        @Bean(name = "User")
        public EhCacheFactoryBean elbCaching() {
            EhCacheFactoryBean ehCacheManagerFactoryBean = new EhCacheFactoryBean();
            ehCacheManagerFactoryBean.setCacheName("User");
            ehCacheManagerFactoryBean.setCacheManager(ehCacheManager());
            return ehCacheManagerFactoryBean;
        }

        @Bean(destroyMethod = "shutdown")
        public net.sf.ehcache.CacheManager ehCacheManager() {
            try {
                return net.sf.ehcache.CacheManager.newInstance(new ClassPathResource("ehcache.xml").getInputStream());
            } catch (CacheException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Bean
        @Override
        public CacheManager cacheManager() {
            return new EhCacheCacheManager(ehCacheManager());
        }

        @Override
        public CacheResolver cacheResolver() {
            return null;
        }

        @Override
        public KeyGenerator keyGenerator() {
            return new SimpleKeyGenerator();
        }

        @Override
        public CacheErrorHandler errorHandler() {
            return null;
        }
}
