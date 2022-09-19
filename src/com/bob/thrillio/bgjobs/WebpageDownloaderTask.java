package com.bob.thrillio.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

import com.bob.thrillio.dao.BookmarkDao;
import com.bob.thrillio.entities.WebLink;
import com.bob.thrillio.entities.WebLink.DownloadStatus;
import com.bob.thrillio.util.HttpConnect;
import com.bob.thrillio.util.IOUtil;

public class WebpageDownloaderTask implements Runnable {
	private static BookmarkDao dao = new BookmarkDao();
	private static final long TIME_FRAME = 3L;
	private boolean downloadAll = false;
	private ExecutorService downloadPool = Executors.newFixedThreadPool(5);

	private static class Downloader implements Callable<WebLink> {
		private WebLink webLink;

		public Downloader(WebLink link) {
			webLink = link;
		}

		@Override
		public WebLink call() {
			try {
				if (webLink.getUrl().endsWith(".pdf")) {
					webLink.setDownloadStatus(DownloadStatus.NOT_ELIGIBLE);
				} else {
					webLink.setDownloadStatus(DownloadStatus.FAILED);
					webLink.setHtmlPage(HttpConnect.download(webLink.getUrl()));
				}

			} catch (MalformedURLException | URISyntaxException e) {
				e.printStackTrace();
			}
			return webLink;
		}
	}

	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			List<WebLink> webLinks = getWebLinks();
			if (webLinks.size() > 0) {
				download(webLinks);
			} else {
				System.out.println("No new web links to download!");
			}

			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
				downloadPool.shutdown();
			}
		}

		downloadPool.shutdown();
	}

	private void download(List<WebLink> webLinks) {
		List<Downloader> tasks = new ArrayList<>();
		for (WebLink webLink : webLinks) {
			tasks.add(new Downloader(webLink));
		}
		try {
			for (Future<WebLink> future : downloadPool.invokeAll(tasks, TIME_FRAME, TimeUnit.SECONDS)) {
				if (!future.isCancelled()) {
					WebLink webLink = future.get();
					String htmlPage = webLink.getHtmlPage();
					if (htmlPage != null) {
						IOUtil.write(htmlPage, webLink.getId());
						webLink.setDownloadStatus(DownloadStatus.SUCCESS);
						System.out.println("Download success: " + webLink.getUrl());
					} else {
						System.out.println("Webpage not downloaded: " + webLink.getUrl());
					}
				} else {
					System.out.println("\nTask is cancelled --> " + Thread.currentThread());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	private List<WebLink> getWebLinks() {
		List<WebLink> links;
		if (downloadAll) {
			links = dao.getAllWebLinks();
			downloadAll = false;
		} else {
			links = dao.getWebLinks(DownloadStatus.NOT_ATTEMPTED);
		}
		return links;
	}
}
