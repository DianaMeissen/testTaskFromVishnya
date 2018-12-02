package com.vishnya;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class FindTag {
    private static final Logger log = Logger.getLogger(FindTag.class);

    private final String neededId;
    private Element originalElement;


    public FindTag() {
        neededId = "make-everything-ok-button";
    }

    public void findObject(String pathToOrigin, String pathToSample) throws IOException {
        findByPath(pathToOrigin);
        checkSamples(pathToSample);
    }

    private void findByPath (String path) throws IOException {
        File someFile = new File(path);
        Document doc;
        Element content;
        String checkedPath = checkThisPath(path);

        try {
            for (File file : someFile.listFiles()) {
                if (file.getName().contains(".html")) {
                    File html = new File(checkedPath + file.getName());
                    doc = Jsoup.parse(html, "UTF-8");
                    content = doc.getElementById(neededId);
                    if (content != null) {
                        originalElement = content;
                        Elements els = content.parents();
                        log.info("Searched tag is into file named " + file.getName() + " " + getAllParents(els) + content.tagName());
                    }
                }
            }
        } catch (NullPointerException e) {
            log.error("Can't find such directory");
        }
    }

    private String checkThisPath (String path) {
        if (!path.substring(path.length() - 1).equals("/")) {
            return path + "/";
        } else {
            return path;
        }
    }

    private StringBuffer getAllParents(Elements els) {
        StringBuffer allParents = new StringBuffer();
        for (int i = els.size() - 1; i >= 0; i--) {
            allParents.append(els.get(i).tagName());
            allParents.append(" > ");
        }
        return allParents;
    }

    private void checkSamples(String path) throws IOException {
        File someFile = new File(path);
        Document doc;
        String checkedPath = checkThisPath(path);

        try {
            for (File file : someFile.listFiles()) {
                if (file.getName().contains(".html")) {
                    File html = new File(checkedPath + file.getName());
                    doc = Jsoup.parse(html, "UTF-8");
                    Elements allTagElements = doc.getElementsByTag(originalElement.tagName());
                    for (Element el : allTagElements) {
                        if (compareToOriginEl(el)) {
                            Elements els = el.parents();
                            log.info("Common element is in file " + file.getName() + " " + getAllParents(els) + " " + el.tagName());
                            log.info(el);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            log.error("Can't find such directory");
        }
    }

    private boolean compareToOriginEl(Element el) {
        boolean result = false;
        if (el.attr("title").contains(originalElement.attr("title")) || el.attr("href").contains(originalElement.attr("href"))) {
            String[] allClasses = originalElement.attr("class").split(" ");
            String[] allWords = originalElement.text().split(" ");
            for (int i = 0; i < allClasses.length; i++) {
                if (el.attr("class").contains(allClasses[i])) {
                    for (int j = 0; j < allWords.length; j++) {
                        if (el.text().contains(allWords[j])) {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }
}
