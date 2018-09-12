package cn.worldyao.report.entity;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 * @author 董尧
 */
public class JSONDate {
	private int code;
	private String msg;
	private long count;
	private Object data;
	private List list;


	public JSONDate(List list, long count){
		this.code = 0;
		this.list = list;
		this.count = count;
	}

	public JSONDate(String msg, List list){
		this.msg = msg;
		this.code = 0;
		this.list = list;
	}

	public JSONDate(String msg, int code, List list){
		this.msg = msg;
		this.code = code;
		this.list = list;
	}

	@Override
	public String toString() {
		return "{" +
				"\"code\":" + code +
				", \"msg\":\"" + msg + '\"' +
				", \"count\":" + count +
				", \"data\":" + JSON.toJSONString(list) +
				'}';
	}
}
