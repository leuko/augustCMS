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

import io.renren.modules.spider.entity.SpiderLogEntity;
import io.renren.modules.spider.service.SpiderLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:59:36
 */
@RestController
@RequestMapping("spider/spiderlog")
public class SpiderLogController {
    @Autowired
    private SpiderLogService spiderLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("spider:spiderlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spiderLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spider:spiderlog:info")
    public R info(@PathVariable("id") Integer id){
        SpiderLogEntity spiderLog = spiderLogService.selectById(id);

        return R.ok().put("spiderLog", spiderLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("spider:spiderlog:save")
    public R save(@RequestBody SpiderLogEntity spiderLog){
        spiderLogService.insert(spiderLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("spider:spiderlog:update")
    public R update(@RequestBody SpiderLogEntity spiderLog){
        ValidatorUtils.validateEntity(spiderLog);
        spiderLogService.updateAllColumnById(spiderLog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("spider:spiderlog:delete")
    public R delete(@RequestBody Integer[] ids){
        spiderLogService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
