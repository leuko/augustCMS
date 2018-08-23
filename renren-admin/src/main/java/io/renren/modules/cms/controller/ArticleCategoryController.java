package io.renren.modules.cms.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cms.entity.ArticleCategoryEntity;
import io.renren.modules.cms.service.ArticleCategoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 文章类别
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-20 15:10:44
 */
@RestController
@RequestMapping("cms/articlecategory")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cms:articlecategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cms:articlecategory:info")
    public R info(@PathVariable("id") Integer id){
        ArticleCategoryEntity articleCategory = articleCategoryService.selectById(id);

        return R.ok().put("articleCategory", articleCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cms:articlecategory:save")
    public R save(@RequestBody ArticleCategoryEntity articleCategory){
        articleCategoryService.insert(articleCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cms:articlecategory:update")
    public R update(@RequestBody ArticleCategoryEntity articleCategory){
        ValidatorUtils.validateEntity(articleCategory);
        articleCategoryService.updateAllColumnById(articleCategory);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cms:articlecategory:delete")
    public R delete(@RequestBody Integer[] ids){
        articleCategoryService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
