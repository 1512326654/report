package cn.worldyao.report.dao;

import cn.worldyao.report.entity.GroupReported;
import cn.worldyao.report.entity.MemberReportStatus;
import cn.worldyao.report.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Mapper
@Resource
public interface StudentDAO {
	/**
	 * 学生登录接口
	 * @param map
	 * @return
	 */
	public abstract Student studentLogin(Map<String, Object> map);

	/**
	 * 本组组长查询本组本周组员周报提交状态
	 * @param map
	 * @return
	 */
	public abstract List<MemberReportStatus> showMemberReportStatus(Map<String, Integer> map);

	/**
	 * 数据统计
	 * @param map
	 * @return
	 */
	public abstract GroupReported showGroupDataStatistics(Map<String, Integer> map);


	/**
	 * 获取学生信息列表
	 * @param map
	 * @return
	 */
	public abstract List<Student> getStudentList(Map<String, Integer> map);

	/**
	 * 获取学生信息总数
	 * @param map
	 * @return
	 */
	public abstract int getStudentListNum(Map<String, Integer> map);


	/**
	 *
	 * 信息管理
	 * @param map
	 * @return
	 */
	public abstract int stuConnectionInfo(Map<String, Integer> map);

	/**
	 * 修改学生信息，不包括姓名
	 * @param student
	 * @return
	 */
	public abstract int alterStudentInfo(Student student);
}
