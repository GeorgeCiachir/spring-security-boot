package george.projects.demos.models;

public enum Permission {

	VIEW_PREMIUM_USERS_PAGE("permission for premium users", 1L),
	VIEW_NORMAL_USERS_PAGE("permission for normal users", 2L),
	VIEW_AND_MODIFY_SITE_PAGES("permission for site admin", 3L),
	VIEW_APPLICATION_SPECIFICS("permission for ops", 4L);

	private String permissionDescription;
	private Long permissionCode;

	Permission(String permissionDescription, Long permissionCode) {
		this.permissionDescription = permissionDescription;
		this.permissionCode = permissionCode;
	}

	public String getPermissionDescription() {
		return permissionDescription;
	}

	public Long getPermissionCode() {
		return permissionCode;
	}
}
