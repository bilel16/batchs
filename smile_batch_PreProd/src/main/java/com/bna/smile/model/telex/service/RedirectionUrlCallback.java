package com.bna.smile.model.telex.service;

import microsoft.exchange.webservices.data.IAutodiscoverRedirectionUrl;

public class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
	public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) {
		return redirectionUrl.toLowerCase().startsWith("https://");
	}
}