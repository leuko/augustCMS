package io.renren.modules.cms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cms.dao.ArticleCategoryDao;
import io.renren.modules.cms.entity.ArticleCategoryEntity;
import io.renren.modules.cms.service.ArticleCategoryService;


@Service("articleCategoryService")
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryDao, ArticleCategoryEntity> implements ArticleCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ArticleCategoryEntity> page = this.selectPage(
                new Query<ArticleCategoryEntity>(params).getPage(),
                new EntityWrapper<ArticleCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
