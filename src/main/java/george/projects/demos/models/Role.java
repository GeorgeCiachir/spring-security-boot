package george.projects.demos.models;

public enum Role {

	USER("role for users, normal or premium", 1L),
	ADMIN("role for admins, site admins or ops", 2L);

	private String permissionDescription;
	private Long permissionCode;

	Role(String permissionDescription, Long permissionCode) {
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
