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

import io.renren.modules.spider.entity.SpiderProjectEntity;
import io.renren.modules.spider.service.SpiderProjectService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 数据采集
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:58:10
 */
@RestController
@RequestMapping("spider/spiderproject")
public class SpiderProjectController {
    @Autowired
    private SpiderProjectService spiderProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("spider:spiderproject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spiderProjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spider:spiderproject:info")
    public R info(@PathVariable("id") Integer id){
        SpiderProjectEntity spiderProject = spiderProjectService.selectById(id);

        return R.ok().put("spiderProject", spiderProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("spider:spiderproject:save")
    public R save(@RequestBody SpiderProjectEntity spiderProject){
        spiderProjectService.insert(spiderProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("spider:spiderproject:update")
    public R update(@RequestBody SpiderProjectEntity spiderProject){
        ValidatorUtils.validateEntity(spiderProject);
        spiderProjectService.updateAllColumnById(spiderProject);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("spider:spiderproject:delete")
    public R delete(@RequestBody Integer[] ids){
        spiderProjectService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
