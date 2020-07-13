package org.jeecg.modules.parking.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.parking.entity.PkAppointment;
import org.jeecg.modules.parking.service.IPkAppointmentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 车辆预约
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Api(tags="车辆预约")
@RestController
@RequestMapping("/parking/pkAppointment")
@Slf4j
public class PkAppointmentController extends JeecgController<PkAppointment, IPkAppointmentService> {
	@Autowired
	private IPkAppointmentService pkAppointmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pkAppointment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "车辆预约-分页列表查询")
	@ApiOperation(value="车辆预约-分页列表查询", notes="车辆预约-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PkAppointment pkAppointment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PkAppointment> queryWrapper = QueryGenerator.initQueryWrapper(pkAppointment, req.getParameterMap());
		Page<PkAppointment> page = new Page<PkAppointment>(pageNo, pageSize);
		IPage<PkAppointment> pageList = pkAppointmentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pkAppointment
	 * @return
	 */
	@AutoLog(value = "车辆预约-添加")
	@ApiOperation(value="车辆预约-添加", notes="车辆预约-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PkAppointment pkAppointment) {
		pkAppointmentService.save(pkAppointment);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pkAppointment
	 * @return
	 */
	@AutoLog(value = "车辆预约-编辑")
	@ApiOperation(value="车辆预约-编辑", notes="车辆预约-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PkAppointment pkAppointment) {
		pkAppointmentService.updateById(pkAppointment);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "车辆预约-通过id删除")
	@ApiOperation(value="车辆预约-通过id删除", notes="车辆预约-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pkAppointmentService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "车辆预约-批量删除")
	@ApiOperation(value="车辆预约-批量删除", notes="车辆预约-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pkAppointmentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "车辆预约-通过id查询")
	@ApiOperation(value="车辆预约-通过id查询", notes="车辆预约-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PkAppointment pkAppointment = pkAppointmentService.getById(id);
		if(pkAppointment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pkAppointment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pkAppointment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PkAppointment pkAppointment) {
        return super.exportXls(request, pkAppointment, PkAppointment.class, "车辆预约");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PkAppointment.class);
    }

}
