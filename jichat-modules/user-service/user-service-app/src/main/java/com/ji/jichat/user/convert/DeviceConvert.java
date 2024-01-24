package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.Device;
import com.ji.jichat.user.api.dto.DeviceDTO;
import com.ji.jichat.user.api.vo.DeviceVO;

/**
 * 设备表 Convert
 *
 * @author jisl
 * @since 2024-01-24
 */
@Mapper
public interface DeviceConvert {

    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    Device convert(DeviceVO bean);

    Device convert(DeviceDTO bean);

    DeviceVO convert(Device bean);

    List<DeviceVO> convertList(List<Device> list);




}
