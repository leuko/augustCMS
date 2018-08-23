package io.renren.modules.spider.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.spider.dao.SpiderLogDao;
import io.renren.modules.spider.entity.SpiderLogEntity;
import io.renren.modules.spider.service.SpiderLogService;


@Service("spiderLogService")
public class SpiderLogServiceImpl extends ServiceImpl<SpiderLogDao, SpiderLogEntity> implements SpiderLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SpiderLogEntity> page = this.selectPage(
                new Query<SpiderLogEntity>(params).getPage(),
                new EntityWrapper<SpiderLogEntity>()
        );

        return new PageUtils(page);
    }

}
