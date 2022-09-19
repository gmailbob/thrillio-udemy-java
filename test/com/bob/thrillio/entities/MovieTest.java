package com.bob.thrillio.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bob.thrillio.constants.MovieGenre;
import com.bob.thrillio.managers.BookmarkManager;

class MovieTest {

	@Test
	void testIsKidFriendlyEligible() {
		Movie movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941,
				new String[] { "Orson Welles", "Joseph Cotten" }, new String[] { "Orson Welles" }, MovieGenre.HORROR,
				8.5);
		assertFalse(movie.isKidFriendlyEligible(), "genre of horror - isKidFriendlyEligible must return false");

		movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941,
				new String[] { "Orson Welles", "Joseph Cotten" }, new String[] { "Orson Welles" }, MovieGenre.THRILLERS,
				8.5);
		assertFalse(movie.isKidFriendlyEligible(), "genre of thrillers - isKidFriendlyEligible must return false");
	}

}
