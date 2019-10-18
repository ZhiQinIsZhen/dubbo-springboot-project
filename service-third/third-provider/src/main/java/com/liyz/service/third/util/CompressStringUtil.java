package com.liyz.service.third.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/20 16:27
 */
@Slf4j
public final class CompressStringUtil {

    /**
     * 解压缩
     * @param compressed
     * @return
     */
    public static final String decompress(byte[] compressed) {
        if (compressed == null)
            return null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            ZipEntry entry = zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = new String(out.toByteArray(),"GBK");
        } catch (IOException e) {
            decompressed = null;
            throw new RuntimeException("解压缩字符串数据出错", e);
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                    log.debug("关闭ZipInputStream", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭ByteArrayInputStream", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.debug("关闭ByteArrayOutputStream", e);
                }
            }
        }
        return decompressed;
    }
}
