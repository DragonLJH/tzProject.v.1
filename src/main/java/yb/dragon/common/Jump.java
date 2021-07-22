package yb.dragon.common;

public class Jump {

	private String jumpurl;
	private String url;
	private String uuid;
	private String shield;

	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}

	public String getJumpurl() {
		return jumpurl;
	}

	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Jump [jumpurl=" + jumpurl + ", url=" + url + ", uuid=" + uuid + ", shield=" + shield + "]";
	}
	

}
