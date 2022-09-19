package com.bob.thrillio.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bob.thrillio.constants.BookGenre;
import com.bob.thrillio.managers.BookmarkManager;

class BookTest {

	@Test
	void testIsKidFriendlyEligible() {
		Book book = BookmarkManager.getInstance().createBook(4000, "Walden", 1854, "Wilder Publications",
				new String[] { "Henry David Thoreau" }, BookGenre.PHILOSOPHY, 4.3);
		assertFalse(book.isKidFriendlyEligible(), "genre of philosophy - isKidFriendlyEligible must return false");

		book = BookmarkManager.getInstance().createBook(4000, "Walden", 1854, "Wilder Publications",
				new String[] { "Henry David Thoreau" }, BookGenre.SELF_HELP, 4.3);
		assertFalse(book.isKidFriendlyEligible(), "genre of self help - isKidFriendlyEligible must return false");
	}

}
