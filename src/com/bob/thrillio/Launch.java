package com.bob.thrillio;

import java.util.List;

import com.bob.thrillio.bgjobs.WebpageDownloaderTask;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.managers.*;

public class Launch {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;

	private static void loadData() {
		System.out.println("Loading data ...");
		DataStore.loadData();

		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();
	}

	private static void start() {
		for (User user : users) {
			View.browse(user, bookmarks);
		}
	}

	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}

	public static void main(String[] args) {
		loadData();
		start();
		runDownloaderJob();
	}
}
