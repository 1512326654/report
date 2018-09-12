package cn.worldyao.report.dao;

import cn.worldyao.report.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AdminDAO {
	/**
	 * 管理员登录接口
	 * @param map
	 * @return
	 */
	public abstract Admin adminLogin(Map<String, Object> map);
}
