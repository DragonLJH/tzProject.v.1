package yb.dragon.common;

public class Msg {
	// 起始ip
	private String firstIp;
	// 结束ip
	private String endIp;
	// 根据起始ip转化为的long数
	private long firstNum;
	// 根据结束ip转化为的long数
	private long endNum;
	// 省份
	private String province;
	// 城市
	private String city;

	public String getFirstIp() {
		return firstIp;
	}

	public void setFirstIp(String firstIp) {
		this.firstIp = firstIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public long getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(long firstNum) {
		this.firstNum = firstNum;
	}

	public long getEndNum() {
		return endNum;
	}

	public void setEndNum(long endNum) {
		this.endNum = endNum;
	}

	public String getProvince() {
		return province.contains("省") ? province.substring(0, province.length() - 1) : province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city.contains("市") ? city.substring(0, city.length() - 1) : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Msg [firstIp=" + firstIp + ", endIp=" + endIp + ", firstNum=" + firstNum + ", endNum=" + endNum
				+ ", province=" + province + ", city=" + city + "]";
	}
}
