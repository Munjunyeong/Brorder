package kr.com.brorder.users;

public class Users {
	private Long userid;
	private String id;
	private String password;
	private String name;
	private String phone;
	private String role;
	private String created_data;
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreated_data() {
		return created_data;
	}
	public void setCreated_data(String created_data) {
		this.created_data = created_data;
	}
		
}
