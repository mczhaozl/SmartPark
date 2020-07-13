package org.jeecg.modules.parking.service.impl;

import org.jeecg.modules.parking.entity.PkCar;
import org.jeecg.modules.parking.mapper.PkCarMapper;
import org.jeecg.modules.parking.service.IPkCarService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 车辆
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Service
public class PkCarServiceImpl extends ServiceImpl<PkCarMapper, PkCar> implements IPkCarService {

}
