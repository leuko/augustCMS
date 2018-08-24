/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.oss.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.oss.cloud.CloudStorageService;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.dao.SysOssDao;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysOssEntity> page = this.selectPage(
                new Query<SysOssEntity>(params).getPage()
        );

        return new PageUtils(page);
    }

    /**
     * @param originalFilename
     * @param content
     * @return
     */
    public SysOssEntity upload(String originalFilename, byte[] content) {

        //上传文件
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(content, suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        insert(ossEntity);
        return ossEntity;
    }

    public SysOssEntity upload(String originalFilename, InputStream inputStream) throws IOException {


        //上传文件
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        CloudStorageService oss = OSSFactory.build();
//        oss.upload(inputStream, oss.getPath(oss., suffix))

        String url = OSSFactory.build().uploadSuffix(inputStream, suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        insert(ossEntity);
        return ossEntity;
    }

}
