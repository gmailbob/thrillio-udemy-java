package com.bob.thrillio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bob.thrillio.*;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.entities.WebLink.DownloadStatus;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserBookmark(UserBookmark userBookmark) {
		// The instructor was concatenating query string in the video, So I tried
		// prepared statement.
		// https://stackoverflow.com/questions/12745186/passing-parameters-to-a-jdbc-preparedstatement
		Bookmark bookmark = userBookmark.getBookmark();
		if (bookmark instanceof Book) {
			saveUserBookmark("user_book", "book_id", userBookmark);
		} else if (bookmark instanceof Movie) {
			saveUserBookmark("user_movie", "movie_id", userBookmark);
		} else if (bookmark instanceof WebLink) {
			saveUserBookmark("user_weblink", "weblink_id", userBookmark);
		}
	}

	private void saveUserBookmark(String table, String field, UserBookmark userBookmark) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio", "root", "");
				PreparedStatement stmt = conn
						.prepareStatement(String.format("insert into %s (user_id, %s) values (?, ?)", table, field))) {
			stmt.setLong(1, userBookmark.getUser().getId());
			stmt.setLong(2, userBookmark.getBookmark().getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*- Note generics are invariant. This is also working
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 * return (List<WebLink>)new ArrayList(DataStore.getBookmarks().get(0));
	 * 
	 * Real work should use SQL queries
	 */
	public List<WebLink> getAllWebLinks() {
		List<WebLink> links = new ArrayList<>();
		for (Bookmark bookmark : DataStore.getBookmarks().get(0)) {
			links.add((WebLink) bookmark);
		}
		return links;
	}

	public List<WebLink> getWebLinks(DownloadStatus status) {
		List<WebLink> links = new ArrayList<>();
		for (Bookmark bookmark : DataStore.getBookmarks().get(0)) {
			WebLink link = (WebLink) bookmark;
			if (link.getDownloadStatus() == status) {
				links.add(link);
			}
		}
		return links;
	}

	public void updateKidFriendlyStatus(Bookmark bookmark) {
		String table = "";
		if (bookmark instanceof Book) {
			table = "book";
		} else if (bookmark instanceof Movie) {
			table = "movie";
		} else if (bookmark instanceof WebLink) {
			table = "weblink";
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio", "root", "");
				PreparedStatement stmt = conn.prepareStatement(String.format(
						"update %s set kid_friendly_status = ?, kid_friendly_marked_by = ? where id = ?", table))) {
			stmt.setInt(1, bookmark.getKidFridenlyStatus().ordinal());
			stmt.setLong(2, bookmark.getKidFridenlyMarkedBy().getId());
			stmt.setLong(3, bookmark.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateSharedBy(Bookmark bookmark) {
		String table = "";
		if (bookmark instanceof Book) {
			table = "book";
		} else if (bookmark instanceof WebLink) {
			table = "weblink";
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio", "root", "");
				PreparedStatement stmt = conn
						.prepareStatement(String.format("update %s set shared_by = ? where id = ?", table))) {
			stmt.setLong(1, bookmark.getSharedBy().getId());
			stmt.setLong(2, bookmark.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
