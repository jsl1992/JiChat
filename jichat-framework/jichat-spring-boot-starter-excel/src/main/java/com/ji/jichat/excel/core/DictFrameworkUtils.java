package com.ji.jichat.excel.core;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ji.jichat.common.pojo.KeyValue;
import com.ji.jichat.common.util.CacheUtils;
import com.ji.jichat.user.api.DictDataRpc;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * 字典工具类
 *
 * @author 芋道源码
 */
@Slf4j
public class DictFrameworkUtils {

    private static DictDataRpc dictDataRpc;

    private static final SystemDictDataVO DICT_DATA_NULL = new SystemDictDataVO();

    private static final LoadingCache<KeyValue<String, String>, SystemDictDataVO> GET_DICT_DATA_CACHE = CacheUtils.buildCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<>() {

                @Override
                public SystemDictDataVO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataRpc.getDictData(key.getKey(), key.getValue()).getCheckedData(), DICT_DATA_NULL);
                }

            });


    private static final LoadingCache<KeyValue<String, String>, SystemDictDataVO> PARSE_DICT_DATA_CACHE = CacheUtils.buildCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<>() {
                @Override
                public SystemDictDataVO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataRpc.getDictData(key.getKey(), key.getValue()).getCheckedData(), DICT_DATA_NULL);
                }

            });


    public static void init(DictDataRpc dictDataRpc) {
        DictFrameworkUtils.dictDataRpc = dictDataRpc;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }


    @SneakyThrows
    public static String getDictDataLabel(String dictType, String value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, value)).getLabel();
    }

    @SneakyThrows
    public static String parseDictDataValue(String dictType, String label) {
        return PARSE_DICT_DATA_CACHE.get(new KeyValue<>(dictType, label)).getValue();
    }


}
