package io.renren.modules.spider.controller;

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

import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import io.renren.modules.spider.service.SpiderProjectColumnService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-22 11:59:04
 */
@RestController
@RequestMapping("spider/spiderprojectcolumn")
public class SpiderProjectColumnController {
    @Autowired
    private SpiderProjectColumnService spiderProjectColumnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("spider:spiderprojectcolumn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spiderProjectColumnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spider:spiderprojectcolumn:info")
    public R info(@PathVariable("id") Integer id){
        SpiderProjectColumnEntity spiderProjectColumn = spiderProjectColumnService.selectById(id);

        return R.ok().put("spiderProjectColumn", spiderProjectColumn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("spider:spiderprojectcolumn:save")
    public R save(@RequestBody SpiderProjectColumnEntity spiderProjectColumn){
        spiderProjectColumnService.insert(spiderProjectColumn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("spider:spiderprojectcolumn:update")
    public R update(@RequestBody SpiderProjectColumnEntity spiderProjectColumn){
        ValidatorUtils.validateEntity(spiderProjectColumn);
        spiderProjectColumnService.updateAllColumnById(spiderProjectColumn);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("spider:spiderprojectcolumn:delete")
    public R delete(Integer ids){
        spiderProjectColumnService.deleteById(ids);
        return R.ok();
    }

}
