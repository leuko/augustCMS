package io.renren.modules.spider.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.spider.entity.SpiderLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:59:36
 */
public interface SpiderLogService extends IService<SpiderLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

