package com.l2r.initialize;

import com.l2r.utils.CacheUtil;
import com.l2r.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by messi on 2019-12-05.  缓存的bean名称为"Dict"
 */
@Component
public class CacheInit implements CommandLineRunner {
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private CacheUtil cacheUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... strings) throws Exception {
        logger.info("========================初始化缓存数据 开始========================");
        securityUtil.Init();
        cacheUtil.loadAllCache();
        logger.info("========================初始化缓存数据 完成========================");
    }

}
