package org.jeecg.modules.meeting.service;

import java.util.List;

import org.jeecg.modules.meeting.entity.JsMeeting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
public interface IJsMeetingService extends IService<JsMeeting> {
	/**
	 * 我参与的会议
	 * @param userId
	 * @param status
	 * @return
	 */
	public Page<JsMeeting> myAttendMeeting(Page<JsMeeting> page,String userId,List<String> status,String room);

}
