package com.bob.thrillio.constants;

public enum KidFriendlyStatus {
	APPROVED("approved"), REJECTED("rejected"), UNKNOWN("unknown");

	private final String status;

	private KidFriendlyStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
