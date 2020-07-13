package org.jeecg.modules.meeting.job;

import java.util.List;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.meeting.common.MeetStatus;
import org.jeecg.modules.meeting.entity.JsMeeting;
import org.jeecg.modules.meeting.service.IJsMeetingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步会议状态任务
 */

@Slf4j
public class SyncStatusJob implements Job {

	@Autowired
	private IJsMeetingService jsMeetingService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		log.info(String.format("开始同步会议状态 SyncStatusJob !  时间:" + DateUtils.getTimestamp()));
		//查询会议的结束时间在本时刻之前
		List<JsMeeting> jsMeetings = jsMeetingService.list(new QueryWrapper<JsMeeting>().lambda().le(JsMeeting::getEndTime, DateUtil.now()).eq(JsMeeting::getStatus, MeetStatus.ending));
		for(JsMeeting jsMeeting:jsMeetings) {
			jsMeeting.setStatus(MeetStatus.end);
			jsMeetingService.updateById(jsMeeting);
		}
		log.info(String.format("结束同步会议状态 SyncStatusJob !  时间:" + DateUtils.getTimestamp()));
	}

}
