package com.bob.thrillio.controllers;

import com.bob.thrillio.constants.KidFriendlyStatus;
import com.bob.thrillio.entities.Bookmark;
import com.bob.thrillio.entities.User;
import com.bob.thrillio.managers.BookmarkManager;

public class BookmarkController {
	private static BookmarkController instance = new BookmarkController();

	private BookmarkController() {
	}

	public static BookmarkController getInstance() {
		return instance;
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
	}

	public void setKidFridenlyStatus(User user, KidFriendlyStatus status, Bookmark bookmark) {
		BookmarkManager.getInstance().setKidFridenlyStatus(user, status, bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user, bookmark);
	}
}
