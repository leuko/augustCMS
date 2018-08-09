package io.renren.service;

import io.renren.dao.SysGeneratorDao;
import io.renren.utils.LeuGenUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class CustomGeneratorService {

    private String tmpTableName = "CustomGeneratorService_" + RandomStringUtils.randomNumeric(5);
    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return sysGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return sysGeneratorDao.queryTotal(map);
    }

    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorDao.queryColumns(tableName);
    }

    public void generatorCode(String project, String moduleName, String packageName, String tableName, List<String> sqlColumns) throws Exception {

        Map<String, String> queryTable = queryTable(tableName);
        if(queryTable!=null){
            throw new Exception("生成错误：表"+tableName+"已经存在！");
        }
        //生成临时表
        sysGeneratorDao.createTmpTable(tmpTableName, sqlColumns);

        //查询表信息
        Map<String, String> table = queryTable(tmpTableName);

        //查询列信息
        List<Map<String, String>> columns = queryColumns(tmpTableName);

        // 重置表名
        table.put("tableName", tableName);

        //生成代码
        LeuGenUtils.generatorCode(table, columns, project, moduleName, packageName, sqlColumns);

        //删除临时表
        sysGeneratorDao.dropTmpTable(tmpTableName);
    }

}
