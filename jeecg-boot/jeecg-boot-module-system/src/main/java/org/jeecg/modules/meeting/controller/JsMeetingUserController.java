package org.jeecg.modules.meeting.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.meeting.entity.JsMeetingUser;
import org.jeecg.modules.meeting.service.IJsMeetingUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
@Api(tags="会议室")
@RestController
@RequestMapping("/meeting/jsMeetingUser")
@Slf4j
public class JsMeetingUserController extends JeecgController<JsMeetingUser, IJsMeetingUserService> {
	@Autowired
	private IJsMeetingUserService JsMeetingUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param JsMeetingUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会议室-分页列表查询")
	@ApiOperation(value="会议室-分页列表查询", notes="会议室-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(JsMeetingUser JsMeetingUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<JsMeetingUser> queryWrapper = QueryGenerator.initQueryWrapper(JsMeetingUser, req.getParameterMap());
		Page<JsMeetingUser> page = new Page<JsMeetingUser>(pageNo, pageSize);
		IPage<JsMeetingUser> pageList = JsMeetingUserService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param JsMeetingUser
	 * @return
	 */
	@AutoLog(value = "会议室-添加")
	@ApiOperation(value="会议室-添加", notes="会议室-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody JsMeetingUser JsMeetingUser) {
		JsMeetingUserService.save(JsMeetingUser);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param JsMeetingUser
	 * @return
	 */
	@AutoLog(value = "会议室-编辑")
	@ApiOperation(value="会议室-编辑", notes="会议室-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JsMeetingUser JsMeetingUser) {
		JsMeetingUserService.updateById(JsMeetingUser);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会议室-通过id删除")
	@ApiOperation(value="会议室-通过id删除", notes="会议室-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		JsMeetingUserService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会议室-批量删除")
	@ApiOperation(value="会议室-批量删除", notes="会议室-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.JsMeetingUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会议室-通过id查询")
	@ApiOperation(value="会议室-通过id查询", notes="会议室-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		JsMeetingUser JsMeetingUser = JsMeetingUserService.getById(id);
		if(JsMeetingUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(JsMeetingUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param JsMeetingUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JsMeetingUser JsMeetingUser) {
        return super.exportXls(request, JsMeetingUser, JsMeetingUser.class, "会议室");
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
        return super.importExcel(request, response, JsMeetingUser.class);
    }

}
