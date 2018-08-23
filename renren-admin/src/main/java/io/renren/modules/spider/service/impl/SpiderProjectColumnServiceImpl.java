package io.renren.modules.spider.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.spider.dao.SpiderProjectColumnDao;
import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import io.renren.modules.spider.service.SpiderProjectColumnService;


@Service("spiderProjectColumnService")
public class SpiderProjectColumnServiceImpl extends ServiceImpl<SpiderProjectColumnDao, SpiderProjectColumnEntity> implements SpiderProjectColumnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SpiderProjectColumnEntity> page = this.selectPage(
                new Query<SpiderProjectColumnEntity>(params).getPage(),
                new EntityWrapper<SpiderProjectColumnEntity>()
        );

        return new PageUtils(page);
    }

}
