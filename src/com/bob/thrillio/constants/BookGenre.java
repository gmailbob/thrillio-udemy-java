package com.bob.thrillio.constants;

public enum BookGenre {
	ART("Art"), BIOGRAPHY("Biography"), CHILDREN("Children"), FICTION("Fiction"), HISTORY("History"),
	MYSTERY("Mystery"), PHILOSOPHY("Philosophy"), RELIGION("Religion"), ROMANCE("Romance"), SELF_HELP("Self help"),
	TECHNICAL("Technical");

	private final String genre;

	private BookGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}
}
