package cn.worldyao.report.entity;

public class Suggest {
	private int suId;
	private int stuClass;
	private String stuName;
	private String suContent;

	private String BY;
	private String BY1;
	private String BY2;
	private String BY3;
	private String BY4;

	public int getSuId() {
		return suId;
	}

	public void setSuId(int suId) {
		this.suId = suId;
	}

	public int getStuClass() {
		return stuClass;
	}

	public void setStuClass(int stuClass) {
		this.stuClass = stuClass;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getSuContent() {
		return suContent;
	}

	public void setSuContent(String suContent) {
		this.suContent = suContent;
	}

	public String getBY() {
		return BY;
	}

	@Override
	public String toString() {
		return "Suggest{" +
				"suId=" + suId +
				", stuClass=" + stuClass +
				", stuName='" + stuName + '\'' +
				", suContent='" + suContent + '\'' +
				'}';
	}
}
