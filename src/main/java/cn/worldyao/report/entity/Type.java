package cn.worldyao.report.entity;

import java.util.List;

public class Type {
	private int extypeId;
	private String extypeName;
	private List<Catalog> cataList;

	public int getExtypeId() {
		return extypeId;
	}

	public void setExtypeId(int extypeId) {
		this.extypeId = extypeId;
	}

	public String getExtypeName() {
		return extypeName;
	}

	public void setExtypeName(String extypeName) {
		this.extypeName = extypeName;
	}

	public List<Catalog> getCataList() {
		return cataList;
	}

	public void setCataList(List<Catalog> cataList) {
		this.cataList = cataList;
	}

	@Override
	public String toString() {
		return "Type{" +
				"extypeId=" + extypeId +
				", extypeName='" + extypeName + '\'' +
				", cataList=" + cataList +
				'}';
	}
}
