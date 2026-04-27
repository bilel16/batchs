package com.bna.commun.security.servlet;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;

public class AuthenticationProcessingFilterBna extends AuthenticationProcessingFilter {
 /// private final static Log log = LogFactory.getLog(AuthenticationProcessingFilterBna.class);

  @Override
  protected String determineTargetUrl(HttpServletRequest request) {

    return getDefaultTargetUrl();
  }
}
