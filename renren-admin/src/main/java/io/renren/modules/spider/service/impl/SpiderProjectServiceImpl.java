package io.renren.modules.spider.service.impl;

import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.spider.dao.SpiderProjectDao;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import io.renren.modules.spider.service.SpiderProjectService;


@Service("spiderProjectService")
public class SpiderProjectServiceImpl extends ServiceImpl<SpiderProjectDao, SpiderProjectEntity> implements SpiderProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SpiderProjectEntity> page = this.selectPage(
                new Query<SpiderProjectEntity>(params).getPage(),
                new EntityWrapper<SpiderProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer selectLastKey() {
        return this.baseMapper.selectLastKey();
    }


    public SpiderProjectEntity selectByIdWithColumn(Integer id){

        return this.baseMapper.selectByIdWithColumn(id);

    }

}
