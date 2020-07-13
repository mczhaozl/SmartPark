package org.jeecg.modules.meeting.service.impl;

import java.util.List;

import org.jeecg.modules.meeting.entity.JsMeeting;
import org.jeecg.modules.meeting.mapper.JsMeetingMapper;
import org.jeecg.modules.meeting.service.IJsMeetingService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
@Service
public class JsMeetingServiceImpl extends ServiceImpl<JsMeetingMapper, JsMeeting> implements IJsMeetingService {

	@Override
	public Page<JsMeeting> myAttendMeeting(Page<JsMeeting> page,String userId, List<String> status,String room) {
		
		return baseMapper.myAttendMeeting(page,userId, status,room);
	}

}
