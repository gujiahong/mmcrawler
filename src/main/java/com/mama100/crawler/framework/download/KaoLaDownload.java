package com.mama100.crawler.framework.download;

import com.mama100.crawler.framework.domain.KaoLaItemPage;

public class KaoLaDownload extends BaseDownload<KaoLaItemPage> implements Downloadable<KaoLaItemPage> {

	@Override
	public KaoLaItemPage download(String url) {
		return super.baseDownload(url);
	}

}
