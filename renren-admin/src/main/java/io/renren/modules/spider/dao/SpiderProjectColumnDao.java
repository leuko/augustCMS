package io.renren.modules.spider.dao;

import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 
 * 
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-22 11:59:04
 */
public interface SpiderProjectColumnDao extends BaseMapper<SpiderProjectColumnEntity> {
    List<SpiderProjectColumnEntity> selectBySpiderProjectId(Integer id);
    Boolean deleteBySpiderProjectId(Integer id);
}
