package com.market.serivce;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

@Service
public class UrlService {

    public String getContentFromURL(URL url) throws IOException {
        System.out.println("[INFO] Handle content from URL");

        Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();

        content = content.replaceAll("\\n+", "");
        content = content.replaceAll("\\s+", " ");
        return content;
    }
}
