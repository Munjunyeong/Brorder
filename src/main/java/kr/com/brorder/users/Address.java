package kr.com.brorder.users;

public class Address {

	private Long addressid;
	private Long userid;
	private String address;
	private String addressdetail;
	private String addresscomment;
	private String entrance;
	private String addressname;

	public Long getAddressid() {
		return addressid;
	}

	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressdetail() {
		return addressdetail;
	}

	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}

	public String getAddresscomment() {
		return addresscomment;
	}

	public void setAddresscomment(String addresscomment) {
		this.addresscomment = addresscomment;
	}

	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	public String getAddressname() {
		return addressname;
	}

	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}

}
