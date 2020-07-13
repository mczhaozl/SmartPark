package org.jeecg.modules.meeting.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.meeting.common.MeetStatus;
import org.jeecg.modules.meeting.entity.JsMeeting;
import org.jeecg.modules.meeting.entity.JsMeetingUser;
import org.jeecg.modules.meeting.entity.JsRecord;
import org.jeecg.modules.meeting.service.IJsMeetingService;
import org.jeecg.modules.meeting.service.IJsMeetingUserService;
import org.jeecg.modules.meeting.service.IJsRecordService;
import org.jeecg.modules.message.entity.SysMessage;
import org.jeecg.modules.message.entity.SysMessageTemplate;
import org.jeecg.modules.message.service.ISysMessageService;
import org.jeecg.modules.message.service.ISysMessageTemplateService;
import org.jeecg.modules.message.websocket.WebSocket;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.jeecg.modules.system.service.ISysUserService;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.constant.WebsocketConst;

/**
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
@Api(tags="会议室")
@RestController
@RequestMapping("/meeting/jsMeeting")
@Slf4j
public class JsMeetingController extends JeecgController<JsMeeting, IJsMeetingService> {
	@Autowired
	private IJsMeetingService jsMeetingService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IJsMeetingUserService jsMeetingUserService;
	@Autowired
	private IJsRecordService jsRecordService;
	@Autowired
	private ISysMessageService sysMessageService;
	@Autowired
	private ISysMessageTemplateService sysMessageTemplateService;
	@Autowired
	private ISysAnnouncementService sysAnnouncementService;
	@Resource
	private WebSocket webSocket;
	/**
	 * 分页列表查询
	 *
	 * @param jsMeeting
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会议室-分页列表查询")
	@ApiOperation(value="会议室-分页列表查询", notes="会议室-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(JsMeeting jsMeeting,
			@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
			HttpServletRequest req) {
		QueryWrapper<JsMeeting> queryWrapper = QueryGenerator.initQueryWrapper(jsMeeting, req.getParameterMap());
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		IPage<JsMeeting> pageList = jsMeetingService.page(page, queryWrapper);
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 查询审批人
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/queryApprove", method = RequestMethod.GET)
	public Result<?> queryApproveList(SysUser user, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize){
		Result<IPage<SysUser>> result = new Result<IPage<SysUser>>();
		Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
		//会议审批员
		IPage<SysUser> pageList= sysUserService.getUserByRoleId(page, "1169504891467464705", null);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;	
	}
	/**
	 *   添加
	 *
	 * @param jsMeeting
	 * @return
	 */
	@AutoLog(value = "会议室-添加")
	@ApiOperation(value="会议室-添加", notes="会议室-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody JsMeeting jsMeeting) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//设置状态
		jsMeeting.setStatus(MeetStatus.approve);
		//设置会议创建人
		jsMeeting.setSponsorId(sysUser.getId());
		jsMeeting.setSponsor(sysUser.getRealname());
		jsMeetingService.save(jsMeeting);
		//保存参会人员
		String representatives[] = jsMeeting.getRepresentatives().split(" ");
		for(String id:representatives) {
			JsMeetingUser jsMeetingUser = new JsMeetingUser();
			jsMeetingUser.setMeetingId(jsMeeting.getId());
			jsMeetingUser.setUserId(id);
			jsMeetingUserService.save(jsMeetingUser);
		}
		SysUser user = sysUserService.getById(jsMeeting.getApproveId());
		//发送微信消息
		SysMessage sysMessage = new SysMessage();
		sysMessage.setEsTitle("会议消息提醒");
		//查询模板
		List<SysMessageTemplate> sysMessageTemplates = sysMessageTemplateService.selectByCode("meeting");
		if(sysMessageTemplates != null && !sysMessageTemplates.isEmpty()) {
			sysMessage.setEsContent(sysMessageTemplates.get(0).getTemplateContent()
					.replace("${name}", jsMeeting.getName())
					.replace("${theme}", jsMeeting.getTheme())
					.replace("${roomName}", jsMeeting.getRoomName())
					.replace("${startTime}", DateUtil.format(jsMeeting.getStartTime(), "yyyy-MM-dd HH:mm"))
					.replace("${endTime}", DateUtil.format(jsMeeting.getEndTime(), "yyyy-MM-dd HH:mm"))
					.replace("${remark}","需要您审批，请及时处理!"));
		}else {
			sysMessage.setEsContent("您收到了一条会议提醒，需要您处理!");
		}
		//手机号码
		sysMessage.setEsReceiver(user != null?user.getPhone():"");
		//微信
		sysMessage.setEsType("3");
		sysMessageService.save(sysMessage);
		//发送系统消息
		SysAnnouncement sysAnnouncement = new SysAnnouncement();
		sysAnnouncement.setTitile("会议消息提醒");
		if(sysMessageTemplates != null && !sysMessageTemplates.isEmpty()) {
			sysAnnouncement.setMsgContent(sysMessageTemplates.get(0).getTemplateContent()
					.replace("${name}", jsMeeting.getName())
					.replace("${theme}", jsMeeting.getTheme())
					.replace("${roomName}", jsMeeting.getRoomName())
					.replace("${startTime}", DateUtil.format(jsMeeting.getStartTime(), "yyyy-MM-dd HH:mm"))
					.replace("${endTime}", DateUtil.format(jsMeeting.getEndTime(), "yyyy-MM-dd HH:mm"))
					.replace("${remark}","需要您审批，请及时处理!"));
		}else {
			sysAnnouncement.setMsgContent("您收到了一条会议提醒，需要您处理!");
		}
		sysAnnouncement.setStartTime(jsMeeting.getStartTime());
		sysAnnouncement.setEndTime(sysAnnouncement.getEndTime());
		sysAnnouncement.setBusId(jsMeeting.getId());
		sysAnnouncement.setBusType("bpm");
		sysAnnouncement.setMsgType("USER");
		sysAnnouncement.setOpenType("url");
		sysAnnouncement.setPriority("H");
		sysAnnouncement.setMsgCategory("2");
		sysAnnouncement.setSender(sysUser.getUsername());
		sysAnnouncement.setSendTime(new Date());
		sysAnnouncement.setUserIds(jsMeeting.getApproveId()+",");
		sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
		sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//已发布
		sysAnnouncementService.saveAnnouncement(sysAnnouncement);
		//利用webSocket发送系统消息
		JSONObject obj = new JSONObject();
		obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
		obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
		obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
		String[] userIdStrs = new String[] {jsMeeting.getApproveId()};
		webSocket.sendMoreMessage(userIdStrs, obj.toJSONString());
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param jsMeeting
	 * @return
	 */
	@AutoLog(value = "会议室-编辑")
	@ApiOperation(value="会议室-编辑", notes="会议室-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody JsMeeting jsMeeting) {
		jsMeetingService.updateById(jsMeeting);
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
		jsMeetingService.removeById(id);
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
		this.jsMeetingService.removeByIds(Arrays.asList(ids.split(",")));
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
		JsMeeting jsMeeting = jsMeetingService.getById(id);
		if(jsMeeting==null) {
			return Result.error("未找到对应数据");
		}

		List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, jsMeeting.getId()));
		String names = "";
		for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
			SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
			names = names + user.getRealname() + " ";
		}
		jsMeeting.setRepresentatives(names);

		return Result.ok(jsMeeting);
	}
	/**
	 * 通过查询我的待进行、已完成的会议
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "查询我的待参加会议")
	@ApiOperation(value="查询我的待参加的会议", notes="查询我的待参加的会议")
	@GetMapping(value = "/myAttendMeetings")
	public Result<?> myAttendMeetings(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//待参加会议
		List<String> status = new ArrayList<>();
		//status.add(MeetStatus.approve);
		status.add(MeetStatus.ending);
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		IPage<JsMeeting> pageList= jsMeetingService.myAttendMeeting(page, sysUser.getId(), status,null);
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 通过查询我的已完成的会议
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "查询我已参加会议")
	@ApiOperation(value="查询我已参加会议", notes="查询我查询我已参加会议")
	@GetMapping(value = "/myEndMeetings")
	public Result<?> myEndMeetings(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		List<String> status = new ArrayList<>();
		status.add(MeetStatus.end);
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		IPage<JsMeeting> pageList= jsMeetingService.myAttendMeeting(page, sysUser.getId(), status,null);
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 查询我审核的会议
	 * @return
	 */
	@AutoLog(value = "查询我审核的会议")
	@ApiOperation(value="查询我审核的会议", notes="查询我审核的会议")
	@GetMapping(value = "/queryMyApprove")
	public Result<?> queryMyApprove(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//我需要审核会议
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		IPage<JsMeeting> pageList = jsMeetingService.page(page, new QueryWrapper<JsMeeting>().lambda().eq(JsMeeting::getApproveId,sysUser.getId()).eq(JsMeeting::getStatus, MeetStatus.approve));
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 查询我已经审核的会议
	 * @return
	 */
	@AutoLog(value = "查询我已经审核的会议")
	@ApiOperation(value="查询我已经审核的会议", notes="查询我已经审核的会议")
	@GetMapping(value = "/queryMyEndApprove")
	public Result<?> queryMyEndApprove(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//我已经审核会议
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		List<String> statuStrings = new ArrayList<String>();
		statuStrings.add(MeetStatus.ending);
		statuStrings.add(MeetStatus.end);
		statuStrings.add(MeetStatus.nopass);
		IPage<JsMeeting> pageList = jsMeetingService.page(page, new QueryWrapper<JsMeeting>().lambda().eq(JsMeeting::getApproveId,sysUser.getId()).in(JsMeeting::getStatus,statuStrings ));
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 审批会议
	 * @param id
	 * @param status
	 * @param opinion
	 * @return
	 */
	@AutoLog(value = "审核会议")
	@ApiOperation(value="审核会议", notes="审核会议")
	@GetMapping(value = "/approve")
	public Result<?> approve(@RequestParam("id") String id,@RequestParam("status") String status,@RequestParam("opinion") String opinion){
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		JsMeeting jsMeeting = jsMeetingService.getById(id);
		if(status.equals("同意")) {
			jsMeeting.setStatus(MeetStatus.ending);
		}else {
			jsMeeting.setStatus(MeetStatus.nopass);
		}
		jsMeetingService.updateById(jsMeeting);
		//增加审核记录
		JsRecord record = new JsRecord();
		record.setMeetingId(jsMeeting.getId());
		record.setUsername(sysUser.getRealname());
		record.setStatus(status);
		record.setOpinion(opinion);
		record.setTime(DateUtil.now());
		jsRecordService.save(record);
		if(status.equals("同意")) {

			//发送微信消息
			SysMessage sysMessage = new SysMessage();
			sysMessage.setEsTitle("会议消息提醒");
			//查询模板
			List<SysMessageTemplate> sysMessageTemplates = sysMessageTemplateService.selectByCode("meeting");
			if(sysMessageTemplates != null && !sysMessageTemplates.isEmpty()) {
				sysMessage.setEsContent(sysMessageTemplates.get(0).getTemplateContent()
						.replace("${name}", jsMeeting.getName())
						.replace("${theme}", jsMeeting.getTheme())
						.replace("${roomName}", jsMeeting.getRoomName())
						.replace("${startTime}", DateUtil.format(jsMeeting.getStartTime(), "yyyy-MM-dd HH:mm"))
						.replace("${endTime}", DateUtil.format(jsMeeting.getEndTime(), "yyyy-MM-dd HH:mm"))
						.replace("${remark}","您有会议需要参加!"));
			}else {
				sysMessage.setEsContent("您收到了一条会议提醒，需要您处理!");
			}
			//手机号码
			//sysMessage.setEsReceiver(user != null?user.getPhone():"");
			//微信
			sysMessage.setEsType("3");
			sysMessageService.save(sysMessage);
			//发送系统消息
			SysAnnouncement sysAnnouncement = new SysAnnouncement();
			sysAnnouncement.setTitile("会议消息提醒");
			if(sysMessageTemplates != null && !sysMessageTemplates.isEmpty()) {
				sysAnnouncement.setMsgContent(sysMessageTemplates.get(0).getTemplateContent()
						.replace("${name}", jsMeeting.getName())
						.replace("${theme}", jsMeeting.getTheme())
						.replace("${roomName}", jsMeeting.getRoomName())
						.replace("${startTime}", DateUtil.format(jsMeeting.getStartTime(), "yyyy-MM-dd HH:mm"))
						.replace("${endTime}", DateUtil.format(jsMeeting.getEndTime(), "yyyy-MM-dd HH:mm"))
						.replace("${remark}","您有会议需要参加!"));
			}else {
				sysAnnouncement.setMsgContent("您收到了一条会议提醒，需要您处理!");
			}
			sysAnnouncement.setStartTime(jsMeeting.getStartTime());
			sysAnnouncement.setEndTime(sysAnnouncement.getEndTime());
			sysAnnouncement.setBusId(jsMeeting.getId());
			sysAnnouncement.setBusType("email");
			sysAnnouncement.setMsgType("USER");
			sysAnnouncement.setOpenType("url");
			sysAnnouncement.setPriority("H");
			sysAnnouncement.setMsgCategory("2");
			sysAnnouncement.setSender(sysUser.getUsername());
			sysAnnouncement.setSendTime(new Date());
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId,jsMeeting.getId()));
			String userIds = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				userIds = userIds + jsMeetingUser.getUserId() + ",";
			}
			sysAnnouncement.setUserIds(userIds);
			sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
			sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//已发布
			sysAnnouncementService.saveAnnouncement(sysAnnouncement);
			//利用webSocket发送系统消息
			JSONObject obj = new JSONObject();
			obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
			obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
			obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
			String[] userIdStrs = userIds.substring(0, (userIds.length()-1)).split(",");
			webSocket.sendMoreMessage(userIdStrs, obj.toJSONString());
		}
		return Result.ok("审核成功!");
	}

	/**
	 * 查询我预约的会议
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@AutoLog(value = "查询我预约的会议")
	@ApiOperation(value="查询我预约的会议", notes="查询我预约的会议")
	@GetMapping(value = "/mySponsorMeetings")
	public Result<?> mySponsorMeetings(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//我需要审核会议
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		IPage<JsMeeting> pageList = jsMeetingService.page(page,new QueryWrapper<JsMeeting>().lambda().eq(JsMeeting::getSponsorId,sysUser.getId()));
		for(JsMeeting meeting:pageList.getRecords()) {
			List<JsMeetingUser> jsMeetingUsers = jsMeetingUserService.list(new QueryWrapper<JsMeetingUser>().lambda().eq(JsMeetingUser::getMeetingId, meeting.getId()));
			String names = "";
			for(JsMeetingUser jsMeetingUser:jsMeetingUsers) {
				SysUser user = sysUserService.getById(jsMeetingUser.getUserId());
				names = names + user.getRealname() + " ";
			}
			meeting.setRepresentatives(names);
		}
		return Result.ok(pageList);
	}
	/**
	 * 查询我的会议安排
	 * @param isMy
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@AutoLog(value = "查询会议安排")
	@ApiOperation(value="查询会议安排", notes="查询会议安排")
	@GetMapping(value = "/meetingPlan")
	public Result<?> meetingPlan(@RequestParam("isMy")String isMy,@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//与我有关的会议
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		if(StringUtils.isEmpty(isMy)||"0".equals(isMy)) {
			IPage<JsMeeting> pageList = jsMeetingService.page(page,new QueryWrapper<JsMeeting>().lambda().eq(JsMeeting::getStatus, MeetStatus.ending));
			return Result.ok(pageList);
		}else {
			List<String> status = new ArrayList<>();
			status.add(MeetStatus.ending);
			IPage<JsMeeting> pageList= jsMeetingService.myAttendMeeting(page, sysUser.getId(), status,null);
			return Result.ok(pageList);
		}
	}
	/**
	 * 通过会议室查询我的会议安排
	 * @param isMy
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@AutoLog(value = "通过会议室查询会议安排")
	@ApiOperation(value="通过会议室查询会议安排", notes="通过会议室查询会议安排")
	@GetMapping(value = "/meetingPlanByRoom")
	public Result<?> meetingPlanByRoom(@RequestParam("isMy")String isMy,@RequestParam("room")String room,@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser == null) {
			return Result.error("用户未登录，请重新登录!");
		}
		//与我有关的会议
		Page<JsMeeting> page = new Page<JsMeeting>(pageNo, pageSize);
		if(StringUtils.isEmpty(isMy)||"0".equals(isMy)) {
			IPage<JsMeeting> pageList = jsMeetingService.page(page,new QueryWrapper<JsMeeting>().lambda().eq(JsMeeting::getRoomName, room).eq(JsMeeting::getStatus, MeetStatus.ending));
			return Result.ok(pageList);
		}else {
			List<String> status = new ArrayList<>();
			status.add(MeetStatus.ending);
			IPage<JsMeeting> pageList= jsMeetingService.myAttendMeeting(page, sysUser.getId(), status,room);
			return Result.ok(pageList);
		}
	}
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param jsMeeting
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, JsMeeting jsMeeting) {
		return super.exportXls(request, jsMeeting, JsMeeting.class, "会议室");
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
		return super.importExcel(request, response, JsMeeting.class);
	}

}
