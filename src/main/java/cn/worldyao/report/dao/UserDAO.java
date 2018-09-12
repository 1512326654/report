package cn.worldyao.report.dao;

import cn.worldyao.report.entity.Student;
import cn.worldyao.report.entity.StudentUser;
import cn.worldyao.report.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 董尧
 */
@Mapper
@Resource
public interface UserDAO {

	/**
	 * 用户名验证
	 * @param userName
	 * @return
	 */
	public abstract String checkUsername(String userName);

	/**
	 * 用户注册
	 * @param map
	 * @return
	 */
	public abstract int userRegist(Map<String, String> map);

	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public abstract User userLogin(Map<String, String> map);

	/**
	 * 用户关联
	 * @param map
	 * @return
	 */
	public abstract int userConnectionInfo(Map<String, Integer> map);

	/**
	 * 关联显示
	 * @param map
	 * @return
	 */
	public abstract Student studentConnection(Map<String, Integer> map);


	/**
	 * 显示用户信息
	 * @param map
	 * @return
	 */
	public abstract List<StudentUser> showStudentInfo(Map<String, Integer> map);


	/**
	 * 用户修改密码，管理员进行重置密码
	 * @param map
	 * @return
	 */
	public abstract int alterPassword(Map<String, Object> map);


	/**
	 * 验证是否存在QQ
	 * @param userQQ
	 * @return
	 */
	public abstract User QQCheck(String userQQ);

	/**
	 * 使用QQ注册
	 * @param map
	 * @return
	 */
	public abstract int QQRegist(Map<String, String> map);


	/**
	 *
	 * @return
	 */
	public abstract List<StudentUser> showAssociationStauts();
}
