package com.bob.thrillio;

import com.bob.thrillio.constants.*;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.managers.*;
import com.bob.thrillio.util.IOUtil;
import java.util.*;

public class DataStore {
	private static List<User> users = new ArrayList<>();

	public static List<User> getUsers() {
		return users;
	}

	private static List<List<Bookmark>> bookmarks = new ArrayList<>();

	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	private static List<UserBookmark> userBookmarks = new ArrayList<>();

	public static void loadData() {
		loadUsers();
		loadWebLinks();
		loadMovies();
		loadBooks();
	}

	private static void loadUsers() {
		List<String> data = new ArrayList<>();
		IOUtil.read(data, "User");
		for (String row : data) {
			String[] values = row.split("\t");

			Gender gender = Gender.MALE;
			if (values[5].equals("f")) {
				gender = Gender.FEMALE;
			} else if (values[5].equals("t")) {
				gender = Gender.TRANSGENDER;
			}

			users.add(UserManager.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3],
					values[4], gender, UserType.valueOf(values[6])));
		}
	}

	private static void loadWebLinks() {
		List<String> data = new ArrayList<>();
		IOUtil.read(data, "WebLink");
		List<Bookmark> bList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			bList.add(BookmarkManager.getInstance().createWebLink(Long.parseLong(values[0]), values[1], values[2],
					values[3]/* , values[4] */));
		}
		bookmarks.add(bList);
	}

	private static void loadMovies() {
		List<String> data = new ArrayList<>();
		IOUtil.read(data, "Movie");
		List<Bookmark> bList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			String[] cast = values[3].split(",");
			String[] directors = values[4].split(",");
			bList.add(BookmarkManager.getInstance().createMovie(Long.parseLong(values[0]), values[1], "",
					Integer.parseInt(values[2]), cast, directors, MovieGenre.valueOf(values[5]),
					Double.parseDouble(values[6])/* , values[7] */));
		}
		bookmarks.add(bList);
	}

	private static void loadBooks() {
		List<String> data = new ArrayList<>();
		IOUtil.read(data, "Book");
		List<Bookmark> bList = new ArrayList<>();
		for (String row : data) {
			String[] values = row.split("\t");
			String[] authors = values[4].split(",");
			bList.add(BookmarkManager.getInstance().createBook(Long.parseLong(values[0]), values[1],
					Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5]),
					Double.parseDouble(values[6])/* , values[7] */));
		}
		bookmarks.add(bList);
	}

	public static void add(UserBookmark userBookmark) {
		userBookmarks.add(userBookmark);
	}
}
