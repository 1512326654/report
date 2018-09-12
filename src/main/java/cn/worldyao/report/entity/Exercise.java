package cn.worldyao.report.entity;

public class Exercise {
	private int exId;
	private String exContent;
	private int exType;
	private int exCata;
	private String exAn;

	private String anA;
	private String anB;
	private String anC;
	private String ansD;
	private String anE;
	private String anF;
	private String anG;
	private String anH;

	private String BY;
	private String BY1;
	private String BY2;
	private String BY3;
	private String BY4;

	public int getExId() {
		return exId;
	}

	public void setExId(int exId) {
		this.exId = exId;
	}

	public String getExContent() {
		return exContent;
	}

	public void setExContent(String exContent) {
		this.exContent = exContent;
	}

	public String getAnA() {
		return anA;
	}

	public void setAnA(String anA) {
		this.anA = anA;
	}

	public String getAnB() {
		return anB;
	}

	public void setAnB(String anB) {
		this.anB = anB;
	}

	public String getAnC() {
		return anC;
	}

	public void setAnC(String anC) {
		this.anC = anC;
	}

	public String getAnsD() {
		return ansD;
	}

	public void setAnsD(String ansD) {
		this.ansD = ansD;
	}

	public String getAnE() {
		return anE;
	}

	public void setAnE(String anE) {
		this.anE = anE;
	}

	public String getAnF() {
		return anF;
	}

	public void setAnF(String anF) {
		this.anF = anF;
	}

	public String getAnG() {
		return anG;
	}

	public void setAnG(String anG) {
		this.anG = anG;
	}

	public String getAnH() {
		return anH;
	}

	public void setAnH(String anH) {
		this.anH = anH;
	}

	@Override
	public String toString() {
		return "Exercise{" +
				"exId=" + exId +
				", exContent='" + exContent + '\'' +
				", anA='" + anA + '\'' +
				", anB='" + anB + '\'' +
				", anC='" + anC + '\'' +
				", ansD='" + ansD + '\'' +
				", anE='" + anE + '\'' +
				", anF='" + anF + '\'' +
				", anG='" + anG + '\'' +
				", anH='" + anH + '\'' +
				'}';
	}
}
