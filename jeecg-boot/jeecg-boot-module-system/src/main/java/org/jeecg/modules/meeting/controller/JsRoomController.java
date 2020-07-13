package org.jeecg.modules.meeting.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.meeting.common.MeetStatus;
import org.jeecg.modules.meeting.entity.JsMeeting;
import org.jeecg.modules.meeting.entity.JsRoom;
import org.jeecg.modules.meeting.service.IJsMeetingService;
import org.jeecg.modules.meeting.service.IJsRoomService;

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
@RequestMapping("/meeting/jsRoom")
@Slf4j
public class JsRoomController extends JeecgController<JsRoom, IJsRoomService> {
	@Autowired
	private IJsRoomService jsRoomService;
	@Autowired
	private IJsMeetingService jsMeetingService;
	/**
	 * 分页列表查询
	 *
	 * @param jsRoom
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会议室-分页列表查询")
	@ApiOperation(value="会议室-分页列表查询", notes="会议室-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(JsRoom jsRoom,
			@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
			HttpServletRequest req) {
		QueryWrapper<JsRoom> queryWrapper = QueryGenerator.initQueryWrapper(jsRoom, req.getParameterMap());
		Page<JsRoom> page = new Page<JsRoom>(pageNo, pageSize);
		IPage<JsRoom> pageList = jsRoomService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param jsRoom
	 * @return
	 */
	@AutoLog(value = "会议室-添加")
	@ApiOperation(value="会议室-添加", notes="会议室-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody JsRoom jsRoom) {
		jsRoomService.save(jsRoom);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param jsRoom
	 * @return
	 */
	@AutoLog(value = "会议室-编辑")
	@ApiOperation(value="会议室-编辑", notes="会议室-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JsRoom jsRoom) {
		jsRoomService.updateById(jsRoom);
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
		jsRoomService.removeById(id);
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
		this.jsRoomService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过时间段查询
	 * @param startTime 开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@AutoLog(value = "会议室-通过时间段查询")
	@ApiOperation(value="会议室-通过时间段查询", notes="会议室-通过时间段查询")
	@GetMapping(value = "/queryByDate")
	public Result<?> queryByDate(@RequestParam(name="startTime",required=true) String startTime,@RequestParam(name="endTime",required=true) String endTime) {
		//查出会议已占用的会议室
		/*
		 * List<JsMeeting> meetings = jsMeetingService.list(new
		 * QueryWrapper<JsMeeting>().lambda().notIn(JsMeeting::getStatus,
		 * MeetStatus.end).ge(true,JsMeeting::getEndTime , startTime)); List<String>
		 * roomNames = new ArrayList<>(); for(JsMeeting jsMeeting:meetings) {
		 * roomNames.add(jsMeeting.getRoomName()); }
		 */
		List<JsRoom> jsRooms = jsRoomService.list();
		//List<JsRoom> rooms = new ArrayList<>();
		//排除已经占用完的会议室
		/*
		 * for(JsRoom room:jsRooms) { if(!roomNames.contains(room.getName())) {
		 * rooms.add(room); } }
		 */

		return Result.ok(jsRooms);
	}


	@AutoLog(value = "会议室-通过id查询")
	@ApiOperation(value="会议室-通过id查询", notes="会议室-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		JsRoom jsRoom = jsRoomService.getById(id);
		if(jsRoom==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(jsRoom);
	}
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param jsRoom
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, JsRoom jsRoom) {
		return super.exportXls(request, jsRoom, JsRoom.class, "会议室");
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
		return super.importExcel(request, response, JsRoom.class);
	}

}
