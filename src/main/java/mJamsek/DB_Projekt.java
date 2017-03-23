package mJamsek;

public class DB_Projekt {
	private String id_proj;
	private String proj_name;
	private String proj_desc;
	private String depl_type;
	private String proj_link;
	private String proj_about;
	private String proj_version;
	private String proj_last_update;
	private String repo_link;
	private String proj_img;
	private String active;
	private String last_update;
	
	public DB_Projekt(String ena, String dva, String tri, String stiri, String pet, String sest, String sedem, String osem, String devet, String deset, String enajst, String dvanajst ){
		this.id_proj = ena;
		this.proj_name = dva;
		this.proj_desc = tri;
		this.depl_type = stiri;
		this.proj_link = pet;
		this.proj_about = sest;
		this.proj_version = sedem;
		this.proj_last_update = osem;
		this.repo_link = devet;
		this.proj_img = deset;
		this.active = enajst;
		this.last_update = dvanajst;
	}

	public String getId_proj() {
		return id_proj;
	}

	public void setId_proj(String id_proj) {
		this.id_proj = id_proj;
	}

	public String getProj_name() {
		return proj_name;
	}

	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}

	public String getProj_desc() {
		return proj_desc;
	}

	public void setProj_desc(String proj_desc) {
		this.proj_desc = proj_desc;
	}

	public String getDepl_type() {
		return depl_type;
	}

	public void setDepl_type(String depl_type) {
		this.depl_type = depl_type;
	}

	public String getProj_link() {
		return proj_link;
	}

	public void setProj_link(String proj_link) {
		this.proj_link = proj_link;
	}

	public String getProj_about() {
		return proj_about;
	}

	public void setProj_about(String proj_about) {
		this.proj_about = proj_about;
	}

	public String getProj_version() {
		return proj_version;
	}

	public void setProj_version(String proj_version) {
		this.proj_version = proj_version;
	}

	public String getProj_last_update() {
		return proj_last_update;
	}

	public void setProj_last_update(String proj_last_update) {
		this.proj_last_update = proj_last_update;
	}

	public String getRepo_link() {
		return repo_link;
	}

	public void setRepo_link(String repo_link) {
		this.repo_link = repo_link;
	}

	public String getProj_img() {
		return proj_img;
	}

	public void setProj_img(String proj_img) {
		this.proj_img = proj_img;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
