package com.bob.thrillio.managers;

import java.util.List;

import com.bob.thrillio.constants.BookGenre;
import com.bob.thrillio.constants.KidFriendlyStatus;
import com.bob.thrillio.constants.MovieGenre;
import com.bob.thrillio.dao.BookmarkDao;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.partner.Shareable;

public class BookmarkManager {
	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {

	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);
		return movie;
	}

	public Book createBook(long id, String title, int publicationYear, String publisher, String[] authors,
			BookGenre genre, double amazonRating) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);
		return book;
	}

	public WebLink createWebLink(long id, String title, String url, String host) {
		WebLink webLink = new WebLink();
		webLink.setId(id);
		webLink.setTitle(title);
		webLink.setUrl(url);
		webLink.setHost(host);
		return webLink;
	}

	public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);

		dao.saveUserBookmark(userBookmark);
	}

	public void setKidFridenlyStatus(User user, KidFriendlyStatus status, Bookmark bookmark) {
		bookmark.setKidFridenlyMarkedBy(user);
		bookmark.setKidFridenlyStatus(status);

		dao.updateKidFriendlyStatus(bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		bookmark.setSharedBy(user);
		System.out.println("data: " + ((Shareable) bookmark).getItemData() + " was shared");

		dao.updateSharedBy(bookmark);
	}
}
