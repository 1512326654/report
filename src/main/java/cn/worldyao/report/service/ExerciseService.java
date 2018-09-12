package cn.worldyao.report.service;

import cn.worldyao.report.entity.Catalog;
import cn.worldyao.report.entity.Exercise;
import cn.worldyao.report.entity.Type;

import java.util.List;

public interface ExerciseService {

	/**
	 * 分类型获取题目
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public abstract List<Exercise> getExerciseType(int extypeId, int exCataId, int pageNum, int pageSize);

	/**
	 * 分类型获取题目总数
	 *
	 * @param extypeId
	 * @param exCataId
	 * @return
	 */
	public abstract int getExerciseTypeNum(int extypeId, int exCataId);

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
