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
import org.jeecg.modules.parking.entity.PkTrafficInfo;
import org.jeecg.modules.parking.service.IPkTrafficInfoService;

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
 * @Description: 交通资讯
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Api(tags="交通资讯")
@RestController
@RequestMapping("/parking/pkTrafficInfo")
@Slf4j
public class PkTrafficInfoController extends JeecgController<PkTrafficInfo, IPkTrafficInfoService> {
	@Autowired
	private IPkTrafficInfoService pkTrafficInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pkTrafficInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "交通资讯-分页列表查询")
	@ApiOperation(value="交通资讯-分页列表查询", notes="交通资讯-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PkTrafficInfo pkTrafficInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PkTrafficInfo> queryWrapper = QueryGenerator.initQueryWrapper(pkTrafficInfo, req.getParameterMap());
		Page<PkTrafficInfo> page = new Page<PkTrafficInfo>(pageNo, pageSize);
		IPage<PkTrafficInfo> pageList = pkTrafficInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pkTrafficInfo
	 * @return
	 */
	@AutoLog(value = "交通资讯-添加")
	@ApiOperation(value="交通资讯-添加", notes="交通资讯-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PkTrafficInfo pkTrafficInfo) {
		pkTrafficInfoService.save(pkTrafficInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pkTrafficInfo
	 * @return
	 */
	@AutoLog(value = "交通资讯-编辑")
	@ApiOperation(value="交通资讯-编辑", notes="交通资讯-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PkTrafficInfo pkTrafficInfo) {
		pkTrafficInfoService.updateById(pkTrafficInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "交通资讯-通过id删除")
	@ApiOperation(value="交通资讯-通过id删除", notes="交通资讯-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		pkTrafficInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "交通资讯-批量删除")
	@ApiOperation(value="交通资讯-批量删除", notes="交通资讯-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pkTrafficInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "交通资讯-通过id查询")
	@ApiOperation(value="交通资讯-通过id查询", notes="交通资讯-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PkTrafficInfo pkTrafficInfo = pkTrafficInfoService.getById(id);
		if(pkTrafficInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(pkTrafficInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pkTrafficInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PkTrafficInfo pkTrafficInfo) {
        return super.exportXls(request, pkTrafficInfo, PkTrafficInfo.class, "交通资讯");
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
        return super.importExcel(request, response, PkTrafficInfo.class);
    }

}
