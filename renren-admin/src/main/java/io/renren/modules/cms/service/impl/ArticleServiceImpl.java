package io.renren.modules.cms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cms.dao.ArticleDao;
import io.renren.modules.cms.entity.ArticleEntity;
import io.renren.modules.cms.service.ArticleService;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ArticleEntity> page = this.selectPage(
                new Query<ArticleEntity>(params).getPage(),
                new EntityWrapper<ArticleEntity>()
        );

        return new PageUtils(page);
    }

}
