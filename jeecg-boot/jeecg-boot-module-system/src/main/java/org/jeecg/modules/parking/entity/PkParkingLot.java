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
 * @Description: 停车场
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Data
@TableName("pk_parking_lot")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pk_parking_lot对象", description="停车场")
public class PkParkingLot implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**总车位*/
	@Excel(name = "总车位", width = 15)
    @ApiModelProperty(value = "总车位")
    private java.lang.Integer totalLot;
	/**空闲车位*/
	@Excel(name = "空闲车位", width = 15)
    @ApiModelProperty(value = "空闲车位")
    private java.lang.Integer freeLot;
	/**提示*/
	@Excel(name = "提示", width = 15)
    @ApiModelProperty(value = "提示")
    private java.lang.String tips;
	/**提示标签*/
	@Excel(name = "提示标签", width = 15)
    @ApiModelProperty(value = "提示标签")
    private java.lang.String tipTags;
	/**支付标签*/
	@Excel(name = "支付标签", width = 15)
    @ApiModelProperty(value = "支付标签")
    private java.lang.String payTags;
	/**类型，室内，室外，公园，道路*/
	@Excel(name = "类型，室内，室外，公园，道路", width = 15)
    @ApiModelProperty(value = "类型，室内，室外，公园，道路")
    private java.lang.String type;
	/**经度*/
	@Excel(name = "经度", width = 15)
    @ApiModelProperty(value = "经度")
    private java.lang.Double longitude;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
    @ApiModelProperty(value = "纬度")
    private java.lang.Double latitude;
	/**评分*/
	@Excel(name = "评分", width = 15)
    @ApiModelProperty(value = "评分")
    private java.lang.String score;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private java.lang.String images;
	/**支付码*/
	@Excel(name = "支付码", width = 15)
    @ApiModelProperty(value = "支付码")
    private java.lang.String payCode;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String address;
	/**扩展字段1*/
	@Excel(name = "扩展字段1", width = 15)
    @ApiModelProperty(value = "扩展字段1")
    private java.lang.String ext1;
	/**扩展字段2*/
	@Excel(name = "扩展字段2", width = 15)
    @ApiModelProperty(value = "扩展字段2")
    private java.lang.String ext2;
	/**扩展字段3*/
	@Excel(name = "扩展字段3", width = 15)
    @ApiModelProperty(value = "扩展字段3")
    private java.lang.String ext3;
	/**扩展字段4*/
	@Excel(name = "扩展字段4", width = 15)
    @ApiModelProperty(value = "扩展字段4")
    private java.lang.String ext4;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;
}
