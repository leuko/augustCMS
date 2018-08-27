package io.renren.modules.spider.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import io.renren.modules.spider.entity.SpiderProjectEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据采集
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:58:10
 */
public interface SpiderProjectService extends IService<SpiderProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer selectLastKey();

}

