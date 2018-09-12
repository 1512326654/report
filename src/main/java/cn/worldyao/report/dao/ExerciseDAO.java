package cn.worldyao.report.dao;

import cn.worldyao.report.entity.Catalog;
import cn.worldyao.report.entity.Exercise;
import cn.worldyao.report.entity.Type;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.ws.soap.MTOM;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExerciseDAO {

	/**
	 * 分类型获取题目
	 * @param map
	 * @return
	 */
	public abstract List<Exercise> getExerciseType(Map<String, Integer> map);

	/**
	 * 分类型获取题目总数
	 * @param map
	 * @return
	 */
	public abstract int getExerciseTypeNum(Map<String, Object> map);

	/**
	 * 获取类型
	 * @return
	 */
	public abstract List<Type> getType();

	/**
	 * 获取分类
	 * @return
	 */
	public abstract List<Catalog> getCata();
}
