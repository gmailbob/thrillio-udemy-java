package com.bob.thrillio;

import com.bob.thrillio.constants.*;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.managers.*;

import java.sql.*;
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

	public static void loadData() {
		/*- Old fashioned code to load driver:
		 * Class.forName("com.mysql.jdbc.Driver")
		 * My choice: com.mysql.jdbc.Driver.class.getName() (no checked exception)
		 * 
		 * However, class com.mysql.jdbc.Driver is deprecated. The new driver class is com.mysql.cj.jdbc.Driver 
		 * What's more, since Java 6 (JDBC 4.0) it is usually not necessary to manually load the driver class
		 * https://stackoverflow.com/questions/52344453/loading-class-com-mysql-jdbc-driver-is-deprecated-message
		 * 
		 * "The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary"
		 * -- from com.mysql.jdbc.Driver byte code
		 */

		// try-with-resource
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio", "root", "");
				Statement stmt = conn.createStatement()) {
			loadUsers(stmt);
			loadWebLinks(stmt);
			loadMovies(stmt);
			loadBooks(stmt);
			// add by myself
			clearUserBookmark(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void loadUsers(Statement stmt) throws SQLException {
		String query = "select * from user";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			long id = rs.getLong("id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			Gender gender = Gender.values()[rs.getInt("gender_id")];
			UserType userType = UserType.values()[rs.getInt("user_type_id")];
			users.add(UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType));
		}
	}

	private static void loadWebLinks(Statement stmt) throws SQLException {
		String query = "select * from weblink";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			String url = rs.getString("url");
			String host = rs.getString("host");
			bookmarkList.add(BookmarkManager.getInstance().createWebLink(id, title, url, host));
		}
		bookmarks.add(bookmarkList);
	}

	private static void loadMovies(Statement stmt) throws SQLException {
		String query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
				+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
				+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
				+ "m.id = md.movie_id and md.director_id = d.id group by m.id";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast = rs.getString("cast").split(",");
			String[] directors = rs.getString("directors").split(",");
			int genre_id = rs.getInt("movie_genre_id");
			MovieGenre genre = MovieGenre.values()[genre_id];
			double imdbRating = rs.getDouble("imdb_rating");

			Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast, directors,
					genre, imdbRating/* , values[7] */);
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
	}

	private static void loadBooks(Statement stmt) throws SQLException {
		String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
				+ " from Book b, Publisher p, Author a, Book_Author ba "
				+ "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
		ResultSet rs = stmt.executeQuery(query);

		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int publicationYear = rs.getInt("publication_year");
			String publisher = rs.getString("name");
			String[] authors = rs.getString("authors").split(",");
			int genre_id = rs.getInt("book_genre_id");
			BookGenre genre = BookGenre.values()[genre_id];
			double amazonRating = rs.getDouble("amazon_rating");

			java.sql.Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate: " + createdDate);
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println("timeStamp: " + timeStamp);
			System.out.println("localDateTime: " + timeStamp.toLocalDateTime());

			System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear
					+ ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre
					+ ", amazonRating: " + amazonRating);

			Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, publicationYear, publisher, authors,
					genre, amazonRating/* , values[7] */);
			bookmarkList.add(bookmark);
		}
		bookmarks.add(bookmarkList);
	}

	private static void clearUserBookmark(Statement stmt) throws SQLException {
		String query = "delete from user_movie";
		stmt.executeUpdate(query);
		query = "delete from user_book;";
		stmt.executeUpdate(query);
		query = "delete from user_weblink;";
		stmt.executeUpdate(query);
	}
}
