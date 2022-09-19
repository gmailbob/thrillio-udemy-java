package com.bob.thrillio.constants;

public enum UserType {
	USER("user"), EDITOR("editor"), CHIEF_EDITOR("chiefeditor");

	private final String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
