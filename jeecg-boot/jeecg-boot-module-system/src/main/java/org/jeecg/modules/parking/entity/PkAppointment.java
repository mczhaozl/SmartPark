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
 * @Description: 车辆预约
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Data
@TableName("pk_appointment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pk_appointment对象", description="车辆预约")
public class PkAppointment implements Serializable {
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
	/**预约车牌号*/
	@Excel(name = "预约车牌号", width = 15)
    @ApiModelProperty(value = "预约车牌号")
    private java.lang.String carNumber;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15)
    @ApiModelProperty(value = "开始时间")
    private java.lang.String startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15)
    @ApiModelProperty(value = "结束时间")
    private java.lang.String endTime;
	/**停车场ID*/
	@Excel(name = "停车场ID", width = 15)
    @ApiModelProperty(value = "停车场ID")
    private java.lang.String lotId;
	/**停车场*/
	@Excel(name = "停车场", width = 15)
    @ApiModelProperty(value = "停车场")
    private java.lang.String lotName;
	/**预约人ID*/
	@Excel(name = "预约人ID", width = 15)
    @ApiModelProperty(value = "预约人ID")
    private java.lang.String userId;
	/**预约人*/
	@Excel(name = "预约人", width = 15)
    @ApiModelProperty(value = "预约人")
    private java.lang.String username;
	/**是否支付*/
	@Excel(name = "是否支付", width = 15)
    @ApiModelProperty(value = "是否支付")
    private java.lang.String isPay;
	/**订单号*/
	@Excel(name = "订单号", width = 15)
    @ApiModelProperty(value = "订单号")
    private java.lang.String orderId;
	/**状态，已预约，已取消*/
	@Excel(name = "状态，已预约，已取消", width = 15)
    @ApiModelProperty(value = "状态，已预约，已取消")
    private java.lang.String status;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String remarks;
}
