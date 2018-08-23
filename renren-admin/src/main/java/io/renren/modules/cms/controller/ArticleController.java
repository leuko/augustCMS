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

import io.renren.modules.cms.entity.ArticleEntity;
import io.renren.modules.cms.service.ArticleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 文章管理
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-16 18:08:18
 */
@RestController
@RequestMapping("cms/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cms:article:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cms:article:info")
    public R info(@PathVariable("id") Integer id){
        ArticleEntity article = articleService.selectById(id);

        return R.ok().put("article", article);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cms:article:save")
    public R save(@RequestBody ArticleEntity article){

        articleService.insert(article);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cms:article:update")
    public R update(@RequestBody ArticleEntity article){
        //ValidatorUtils.validateEntity(article);
        articleService.updateAllColumnById(article);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cms:article:delete")
    public R delete(@RequestBody Integer[] ids){
        articleService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
