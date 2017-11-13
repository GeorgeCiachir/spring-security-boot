package george.projects.demos.models;

public class Permission {

	private Long id;
	private String name;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}


//	VIEW_PREMIUM_USERS_PAGE("permission for premium users", 1L),
//	VIEW_NORMAL_USERS_PAGE("permission for normal users", 2L),
//	VIEW_AND_MODIFY_SITE_PAGES("permission for site admin", 3L),
//	VIEW_APPLICATION_SPECIFICS("permission for ops", 4L);