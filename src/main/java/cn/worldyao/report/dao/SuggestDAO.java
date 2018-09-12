package cn.worldyao.report.dao;

import cn.worldyao.report.entity.Suggest;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author 董尧
 */
@Mapper
public interface SuggestDAO {

	/**
	 * 提交意见
	 * @param suggest
	 * @return
	 */
	public abstract int addSuggest(Suggest suggest);
}
