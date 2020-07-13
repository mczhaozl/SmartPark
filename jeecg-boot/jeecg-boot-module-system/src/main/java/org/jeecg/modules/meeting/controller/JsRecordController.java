package org.jeecg.modules.meeting.controller;

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
import org.jeecg.modules.meeting.entity.JsRecord;
import org.jeecg.modules.meeting.service.IJsRecordService;

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
 * @Description: 审批记录
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
@Api(tags="审批记录")
@RestController
@RequestMapping("/meeting/jsRecord")
@Slf4j
public class JsRecordController extends JeecgController<JsRecord, IJsRecordService> {
	@Autowired
	private IJsRecordService jsRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param jsRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "审批记录-分页列表查询")
	@ApiOperation(value="审批记录-分页列表查询", notes="审批记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(JsRecord jsRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<JsRecord> queryWrapper = QueryGenerator.initQueryWrapper(jsRecord, req.getParameterMap());
		Page<JsRecord> page = new Page<JsRecord>(pageNo, pageSize);
		IPage<JsRecord> pageList = jsRecordService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param jsRecord
	 * @return
	 */
	@AutoLog(value = "审批记录-添加")
	@ApiOperation(value="审批记录-添加", notes="审批记录-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody JsRecord jsRecord) {
		jsRecordService.save(jsRecord);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param jsRecord
	 * @return
	 */
	@AutoLog(value = "审批记录-编辑")
	@ApiOperation(value="审批记录-编辑", notes="审批记录-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JsRecord jsRecord) {
		jsRecordService.updateById(jsRecord);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "审批记录-通过id删除")
	@ApiOperation(value="审批记录-通过id删除", notes="审批记录-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		jsRecordService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "审批记录-批量删除")
	@ApiOperation(value="审批记录-批量删除", notes="审批记录-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.jsRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "审批记录-通过id查询")
	@ApiOperation(value="审批记录-通过id查询", notes="审批记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		JsRecord jsRecord = jsRecordService.getById(id);
		if(jsRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(jsRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param jsRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JsRecord jsRecord) {
        return super.exportXls(request, jsRecord, JsRecord.class, "审批记录");
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
        return super.importExcel(request, response, JsRecord.class);
    }

}
