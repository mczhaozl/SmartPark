package org.jeecg.modules.meeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.meeting.entity.JsMeeting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 会议室
 * @Author: jeecg-boot
 * @Date:   2020-06-13
 * @Version: V1.0
 */
public interface JsMeetingMapper extends BaseMapper<JsMeeting> {
	/**
	 * 我参与的会议
	 * @param userId
	 * @param status
	 * @return
	 */
	public Page<JsMeeting> myAttendMeeting(Page<JsMeeting> page,@Param("userId")String userId,@Param("status")List<String> status,@Param("room")String room);
}
