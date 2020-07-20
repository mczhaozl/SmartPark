package org.jeecg.modules.parking.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 车辆
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Data
@TableName("pk_car")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pk_car对象", description="车辆")
public class PkCar implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private java.lang.String carNumber;
	/**车辆类型，新能源*/
	@Excel(name = "车辆类型，新能源", width = 15)
    @ApiModelProperty(value = "车辆类型，新能源")
	@Dict(dicCode = "car_type")
    private java.lang.String type;
	/**车辆颜色*/
	@Excel(name = "车辆颜色", width = 15)
    @ApiModelProperty(value = "车辆颜色")
    private java.lang.String carColor;
	/**用户Id*/
	@Excel(name = "用户Id", width = 15)
    @ApiModelProperty(value = "用户Id")
    private java.lang.String userId;
	/**用户名称*/
	@Excel(name = "用户名称", width = 15)
    @ApiModelProperty(value = "用户名称")
    private java.lang.String username;
	/**发动机号*/
	@Excel(name = "发动机号", width = 15)
    @ApiModelProperty(value = "发动机号")
    private java.lang.String motor;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;
}
