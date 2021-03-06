/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.apikit.output.scopes;

import static org.mule.tools.apikit.output.MuleConfigGenerator.HTTP_NAMESPACE;

import org.mule.tools.apikit.model.APIKitConfig;

import org.jdom2.Element;

public class MainFlowsUtils {

  private MainFlowsUtils() {}

  public static final String DEFAULT_STATUS_CODE_SUCCESS_VALUE =
      "#[vars." + APIKitConfig.DEFAULT_HTTP_STATUS_NAME + " default 200]";
  public static final String DEFAULT_STATUS_CODE_ERROR_VALUE =
      "#[vars." + APIKitConfig.DEFAULT_HTTP_STATUS_NAME + " default 500]";
  public static final String DEFAULT_OUTBOUND_HEADERS_MAP_VALUE =
      "#[vars." + APIKitConfig.DEFAULT_OUTBOUND_HEADERS_MAP_NAME + " default {}]";
  public static final String DEFAULT_ERROR_BODY_CONTENT = "#[payload]";

  public static Element generateListenerSource(String httpListenerConfigRef, String path) {
    Element httpListener = new Element("listener", HTTP_NAMESPACE.getNamespace());
    httpListener.setAttribute("config-ref", httpListenerConfigRef);
    httpListener.setAttribute("path", path);

    Element headers = new Element("headers", HTTP_NAMESPACE.getNamespace());
    headers.setText(DEFAULT_OUTBOUND_HEADERS_MAP_VALUE);

    Element errorHeaders = new Element("headers", HTTP_NAMESPACE.getNamespace());
    errorHeaders.setText(DEFAULT_OUTBOUND_HEADERS_MAP_VALUE);

    Element responseBuilder = new Element("response", HTTP_NAMESPACE.getNamespace());
    responseBuilder.setAttribute("statusCode", DEFAULT_STATUS_CODE_SUCCESS_VALUE);
    responseBuilder.addContent(headers);
    httpListener.addContent(responseBuilder);

    Element errorBody = new Element("body", HTTP_NAMESPACE.getNamespace());
    errorBody.addContent(DEFAULT_ERROR_BODY_CONTENT);

    Element errorResponseBuilder = new Element("error-response", HTTP_NAMESPACE.getNamespace());
    errorResponseBuilder.setAttribute("statusCode", DEFAULT_STATUS_CODE_ERROR_VALUE);
    errorResponseBuilder.addContent(errorBody);
    errorResponseBuilder.addContent(errorHeaders);
    httpListener.addContent(errorResponseBuilder);
    return httpListener;
  }

}
