package io.renren.modules.cms.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cms.entity.ArticleEntity;

import java.util.Map;

/**
 * 文章管理
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-16 18:08:18
 */
public interface ArticleService extends IService<ArticleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

