package io.renren.modules.spider.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.spider.entity.SpiderProjectColumnEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-22 11:59:04
 */
public interface SpiderProjectColumnService extends IService<SpiderProjectColumnEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SpiderProjectColumnEntity> selectBySpiderProjectId(Integer id);

    Boolean deleteBySpiderProjectId(Integer id);
}

