package org.jeecg.modules.meeting.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
@Data
@TableName("js_meeting")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="js_meeting对象", description="会议")
public class JsMeeting implements Serializable {
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
	/**会议名称*/
	@Excel(name = "会议名称", width = 15)
    @ApiModelProperty(value = "会议名称")
    private java.lang.String name;
	/**会议主题*/
	@Excel(name = "会议主题", width = 15)
    @ApiModelProperty(value = "会议主题")
    private java.lang.String theme;
	/**会议开始时间*/
	@Excel(name = "会议开始时间", width = 15)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "会议开始时间")
    private java.util.Date startTime;
	/**会议结束时间*/
	@Excel(name = "会议结束时间", width = 15)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "会议结束时间")
    private java.util.Date endTime;
	/**会议室ID*/
	@Excel(name = "会议室ID", width = 15)
    @ApiModelProperty(value = "会议室ID")
    private java.lang.String roomId;
	/**会议室*/
	@Excel(name = "会议室", width = 15)
    @ApiModelProperty(value = "会议室")
    private java.lang.String roomName;
	/**发起人*/
    @ApiModelProperty(value = "发起人ID")
    private java.lang.String sponsorId;
	/**发起人*/
	@Excel(name = "发起人", width = 15)
    @ApiModelProperty(value = "发起人")
    private java.lang.String sponsor;
	/**审批人ID*/
    @ApiModelProperty(value = "审批人ID")
    private java.lang.String approveId;
	/**审批人*/
	@Excel(name = "审批人", width = 15)
    @ApiModelProperty(value = "审批人")
    private java.lang.String approve;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**
	 * 参会人员
	 */
	@TableField(exist = false)
	private java.lang.String representatives;
	
}
