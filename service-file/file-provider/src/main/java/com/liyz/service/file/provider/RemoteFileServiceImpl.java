package com.liyz.service.file.provider;

import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.base.util.DateUtil;
import com.liyz.service.file.bo.FileInfoBO;
import com.liyz.service.file.config.FileSnowflakeIdUtil;
import com.liyz.service.file.constant.FileType;
import com.liyz.service.file.exception.RemoteFileServiceException;
import com.liyz.service.file.model.FileInfoDO;
import com.liyz.service.file.remote.RemoteFileService;
import com.liyz.service.file.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 16:06
 */
@Slf4j
@Service(version = "1.0.0", protocol = "hessian")
public class RemoteFileServiceImpl implements RemoteFileService {

    private static final String DEFAULT_ROOT_PATH = "/file";

    @Autowired
    FileInfoService fileInfoService;
    @Autowired
    FileSnowflakeIdUtil fileSnowflakeIdUtil;

    @Override
    public List<String> upload(FileType fileType, List<FileInfoBO> files) {
        if (fileType == null || files == null || files.size() <= 0) {
            throw new RemoteFileServiceException(CommonCodeEnum.ParameterError);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder path = new StringBuilder();
        List<String> list = new ArrayList<>(files.size());
        for (FileInfoBO file : files) {
            String[] exts = file.getFileName().split("\\.");
            FileInfoDO fileInfoDO = new FileInfoDO();
            fileInfoDO.setFileKey(String.valueOf(fileSnowflakeIdUtil.getId()));
            fileInfoDO.setFileName(file.getFileName());
            fileInfoDO.setFileContentType(file.getFileContentType());
            fileInfoDO.setFileType(fileType.getCode());
            fileInfoDO.setFileExt(exts[exts.length-1]);
            fileInfoDO.setCreateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
            fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
            //判断文件是否已经存在
            String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
            FileInfoDO fileDO = fileInfoService.getByMd5(fileMd5);
            if (Objects.isNull(fileDO)) {
                path.append(DEFAULT_ROOT_PATH).append(fileType.getSubPath()).append(localDateTime.getYear()).append("/")
                        .append(localDateTime.getMonthValue()).append("/").append(localDateTime.getDayOfMonth());
                File upFile = new File(path.toString());
                upFile.setWritable(true, false);
                if (!upFile.exists()) {
                    upFile.mkdirs();
                }
                path.append("/").append(fileInfoDO.getFileKey()).append(".").append(fileInfoDO.getFileExt());
                File dest = new File(path.toString());
                try {
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(file.getBytes()), dest);
                } catch (IOException e) {
                    log.error("save file fail error : ", e);
                }
            } else {
                path.append(fileDO.getFilePath());
            }
            fileInfoDO.setFileMd5(fileMd5);
            fileInfoDO.setFilePath(path.toString());
            fileInfoService.save(fileInfoDO);
            path.setLength(0);
            list.add(fileInfoDO.getFileKey());
        }
        return list;
    }

    @Override
    public FileInfoBO download(FileInfoBO fileInfoBO) {
        FileInfoDO fileInfoDO = fileInfoService.getOne(CommonConverterUtil.beanCopy(fileInfoBO, FileInfoDO.class));
        if (fileInfoDO == null) {
            throw new RemoteFileServiceException(CommonCodeEnum.NoData);
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(fileInfoDO.getFilePath()));
            FileInfoBO result = CommonConverterUtil.beanCopy(fileInfoDO, FileInfoBO.class);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int len;
            while((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            fis.close();
            result.setBytes(bos.toByteArray());
            return result;
        } catch (Exception e) {
            log.error("download fail error", e);
            throw new RemoteFileServiceException(CommonCodeEnum.NoData);
        }
    }

    @Override
    public void delete(FileInfoBO fileInfoBO) {
        //这里的删除为逻辑删除，非物理删除
        FileInfoDO fileInfoDO = new FileInfoDO();
        fileInfoDO.setFileKey(fileInfoBO.getFileKey());
        fileInfoDO.setFileType(fileInfoBO.getFileType());
        fileInfoDO.setIsInactive(1);
        fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(LocalDateTime.now()));
        fileInfoService.updateById(fileInfoDO);
    }

    @Override
    public String update(FileInfoBO fileInfoBO) {
        FileType fileType = FileType.getByCode(fileInfoBO.getFileType());
        FileInfoDO old = fileInfoService.getById(fileInfoBO.getFileKey());
        if (Objects.isNull(old)) {
            throw new RemoteFileServiceException(CommonCodeEnum.OldFileNotExsist);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder path = new StringBuilder();
        String[] exts = fileInfoBO.getFileName().split("\\.");
        FileInfoDO fileInfoDO = new FileInfoDO();
        fileInfoDO.setFileKey(String.valueOf(fileSnowflakeIdUtil.getId()));
        fileInfoDO.setFileName(fileInfoBO.getFileName());
        fileInfoDO.setFileContentType(fileInfoBO.getFileContentType());
        fileInfoDO.setFileType(fileType.getCode());
        fileInfoDO.setFileExt(exts[exts.length-1]);
        fileInfoDO.setCreateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
        fileInfoDO.setUpdateTime(DateUtil.convertLocalDateTimeToDate(localDateTime));
        //判断文件是否已经存在
        String fileMd5 = DigestUtils.md5DigestAsHex(fileInfoBO.getBytes());
        FileInfoDO fileDO = fileInfoService.getByMd5(fileMd5);
        if (Objects.isNull(fileDO)) {
            path.append(DEFAULT_ROOT_PATH).append(fileType.getSubPath()).append(localDateTime.getYear()).append("/")
                    .append(localDateTime.getMonthValue()).append("/").append(localDateTime.getDayOfMonth());
            File upFile = new File(path.toString());
            upFile.setWritable(true, false);
            if (!upFile.exists()) {
                upFile.mkdirs();
            }
            path.append("/").append(fileInfoDO.getFileKey()).append(".").append(fileInfoDO.getFileExt());
            File dest = new File(path.toString());
            try {
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(fileInfoBO.getBytes()), dest);
            } catch (IOException e) {
                log.error("save file fail error : ", e);
            }
        } else {
            path.append(fileDO.getFilePath());
        }
        fileInfoDO.setFilePath(path.toString());
        FileInfoDO newDO = CommonConverterUtil.beanCopy(old, FileInfoDO.class);
        newDO.setIsInactive(1);
        newDO.setFileKey(fileInfoDO.getFileKey());
        fileInfoService.save(newDO);

        fileInfoDO.setFileMd5(fileMd5);
        fileInfoDO.setFileKey(old.getFileKey());
        fileInfoDO.setCreateTime(old.getCreateTime());
        fileInfoService.updateById(fileInfoDO);
        return fileInfoDO.getFileKey();
    }
}
