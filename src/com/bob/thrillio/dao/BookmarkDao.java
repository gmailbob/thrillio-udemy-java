package com.bob.thrillio.dao;

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
		DataStore.add(userBookmark);
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
}
