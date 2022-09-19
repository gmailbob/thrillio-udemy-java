package com.bob.thrillio;

import java.util.List;

import com.bob.thrillio.constants.KidFriendlyStatus;
import com.bob.thrillio.constants.UserType;
import com.bob.thrillio.controllers.BookmarkController;
import com.bob.thrillio.entities.*;
import com.bob.thrillio.partner.Shareable;

public class View {
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		System.out.println("\n" + user.getEmail() + " is browsing items ...");
		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				// bookmarking
				if (makeBookmarkingDecision()) {
					BookmarkController.getInstance().saveUserBookmark(user, bookmark);
					System.out.println("new item booked: " + bookmark);
				}

				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
					// mark as kid friendly
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFridenlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						KidFriendlyStatus status = makeKidFriendlyDecision();
						if (!status.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFridenlyStatus(user, status, bookmark);
							System.out.println("kid friendly status changed to " + status + " by user "
									+ user.getEmail() + ": " + bookmark);
						}
					}

					// share
					if (bookmark.getKidFridenlyStatus().equals(KidFriendlyStatus.APPROVED)
							&& bookmark instanceof Shareable && makeSharingDecision()) {
						BookmarkController.getInstance().share(user, bookmark);
						System.out.println("bookmark: " + bookmark + " shared by user " + user.getEmail());
					}
				}
			}
		}

	}

	private static boolean makeBookmarkingDecision() {
		return Math.random() < 0.5;
	}

	private static KidFriendlyStatus makeKidFriendlyDecision() {
		double r = Math.random();
		return r < 0.4 ? KidFriendlyStatus.APPROVED
				: (r < 0.8 ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN);
	}

	private static boolean makeSharingDecision() {
		return Math.random() < 0.5;
	}
}
