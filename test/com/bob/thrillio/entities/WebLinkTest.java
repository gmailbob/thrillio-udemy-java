package com.bob.thrillio.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bob.thrillio.managers.BookmarkManager;

class WebLinkTest {

	@Test
	void testIsKidFriendlyEligible() {
		// porn in url -- false
		WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html",
				"http://www.javaworld.com");
		assertFalse(webLink.isKidFriendlyEligible(), "porn in url - isKidFriendlyEligible must return false");

		// porn in title -- false
		webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming porn, Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
				"http://www.javaworld.com");
		assertFalse(webLink.isKidFriendlyEligible(), "porn in title - isKidFriendlyEligible must return false");

		// adult in host -- false
		webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.adult.com");
		assertFalse(webLink.isKidFriendlyEligible(), "adult in host - isKidFriendlyEligible must return false");

		// adult in url but not in host part -- true
		webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html",
				"http://www.javaworld.com");
		assertTrue(webLink.isKidFriendlyEligible(),
				"adult in url but not in host part - isKidFriendlyEligible must return true");

		// adult in title only -- true
		webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming adult, Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
				"http://www.javaworld.com");
		assertTrue(webLink.isKidFriendlyEligible(), "adult in title only - isKidFriendlyEligible must return true");
	}

}
