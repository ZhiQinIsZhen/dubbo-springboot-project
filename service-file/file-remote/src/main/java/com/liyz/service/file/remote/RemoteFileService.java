package com.liyz.service.file.remote;

import com.liyz.service.file.bo.FileInfoBO;
import com.liyz.service.file.constant.FileType;

import java.io.InputStream;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 16:04
 */
public interface RemoteFileService {

    /**
     * 上传
     */
    List<String> upload(FileType fileType, List<FileInfoBO> files);

    /**
     * 下载
     */
    FileInfoBO download(FileInfoBO fileInfoBO);
}
