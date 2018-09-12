package cn.worldyao.report.entity;

public class Answer {
	private int exId;
	private String exAn;

	public int getExId() {
		return exId;
	}

	public void setExId(int exId) {
		this.exId = exId;
	}

	public String getExAn() {
		return exAn;
	}

	public void setExAn(String exAn) {
		this.exAn = exAn;
	}

	@Override
	public String toString() {
		return "Answer{" +
				"exId=" + exId +
				", exAn='" + exAn + '\'' +
				'}';
	}
}
