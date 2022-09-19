package com.bob.thrillio.entities;

import com.bob.thrillio.constants.KidFriendlyStatus;

public abstract class Bookmark {
	private long id;
	private String title;
	private String profileUrl;
	private KidFriendlyStatus kidFridenlyStatus = KidFriendlyStatus.UNKNOWN;
	private User kidFridenlyMarkedBy;
	private User sharedBy;

	public abstract boolean isKidFriendlyEligible();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public KidFriendlyStatus getKidFridenlyStatus() {
		return kidFridenlyStatus;
	}

	public void setKidFridenlyStatus(KidFriendlyStatus kidFridenlyStatus) {
		this.kidFridenlyStatus = kidFridenlyStatus;
	}

	public User getKidFridenlyMarkedBy() {
		return kidFridenlyMarkedBy;
	}

	public void setKidFridenlyMarkedBy(User kidFridenlyMarkedBy) {
		this.kidFridenlyMarkedBy = kidFridenlyMarkedBy;
	}

	public User getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(User sharedBy) {
		this.sharedBy = sharedBy;
	}
}
