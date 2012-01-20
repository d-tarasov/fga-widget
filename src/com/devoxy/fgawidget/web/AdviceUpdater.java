/*
 * Copyright 2012 Oxygen Development
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devoxy.fgawidget.web;

import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmitriy Tarasov.
 * <p/>
 * Email: dm.vl.tarasov@gmail.com
 * Date: 18.01.12
 * Time: 21:31
 */
public class AdviceUpdater {

    private static final String RSS_FEED_URL = "http://feeds.feedburner.com/365advices?format=xml";

    public static String getTodayAdvice() {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(RSS_FEED_URL);
            NodeList items = doc.getElementsByTagName("item");
            Node item = items.item(0);
            NodeList itemParts = item.getChildNodes();
            for (int i = 0; i < itemParts.getLength(); i++) {
                if ("title".equals(itemParts.item(i).getNodeName())) {
                    Node ii = itemParts.item(i);
                    return ii.getFirstChild().getNodeValue();
                }
            }
        } catch (ParserConfigurationException e) {
            // TODO logging
        } catch (SAXException e) {
            // TODO logging
        } catch (IOException e) {
            // TODO logging
        }
        return null;
    }
}
