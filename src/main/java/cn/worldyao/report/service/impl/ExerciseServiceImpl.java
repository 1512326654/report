package cn.worldyao.report.service.impl;

import cn.worldyao.report.dao.ExerciseDAO;
import cn.worldyao.report.entity.Catalog;
import cn.worldyao.report.entity.Exercise;
import cn.worldyao.report.entity.Type;
import cn.worldyao.report.service.ExerciseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

	@Resource
	private ExerciseDAO exerciseDAO;


	/**
	 * 分类型获取题目
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Exercise> getExerciseType(int extypeId, int exCataId, int pageNum, int pageSize) {
		Map<String,Integer> map = new HashMap<String, Integer>(4);
		/**-----------------------------------**/
		map.put("extypeId", extypeId);
		map.put("exCataId", exCataId);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		/**-----------------------------------**/
		return exerciseDAO.getExerciseType(map);
	}

	/**
	 * 分类型获取题目总数
	 *
	 * @param extypeId
	 * @param exCataId
	 * @return
	 */
	public int getExerciseTypeNum(int extypeId, int exCataId) {
		Map<String,Object> map = new HashMap<String, Object>(4);
		/**-----------------------------------**/
		map.put("exType", extypeId);
		map.put("exCata", exCataId);
		return exerciseDAO.getExerciseTypeNum(map);
	}

	/**
	 * 获取类型
	 * @return
	 */
	public List<Type> getType(){
		List<Type> list = exerciseDAO.getType();
		for (Type type : list){
			type.setCataList(getCata());
		}
		return list;
	}

	/**
	 * 获取分类
	 * @return
	 */
	public  List<Catalog> getCata(){
		for (Catalog catalog : exerciseDAO.getCata()){
			System.out.println(catalog);
		}
		return exerciseDAO.getCata();
	}
}
