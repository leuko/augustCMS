package io.renren.modules.cms.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cms.entity.ArticleCategoryEntity;

import java.util.Map;

/**
 * 文章类别
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-20 15:10:44
 */
public interface ArticleCategoryService extends IService<ArticleCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

