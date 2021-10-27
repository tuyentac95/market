package com.market.serivce;

import com.market.model.Fund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FundService {

    @Autowired
    private UrlService urlService;

    private final Pattern bvpfPattern = Pattern.compile("<ul> <li> NAV/CCQ tại ng&#224;y (\\d{2}/\\d{2}/\\d{4}?) <strong>(\\d+.\\d+?) VNĐ</strong></li>");
    private final Pattern pvbfPattern = Pattern.compile("NAV/CCQ&nbsp;</span></span>tại kỳ GD (\\d{2}/\\d{2}/\\d{4}?)<span class=\"(\\w+)\">: (\\d+,\\d+?).(\\d+) VND&nbsp;");

    public Fund fetchNav() throws IOException {
        System.out.println("[INFO] Start fetching URL");
        URL url = new URL("https://baovietfund.com.vn/san-pham/BVPF");

        String content = urlService.getContentFromURL(url);

        Matcher matcher = bvpfPattern.matcher(content);

        while (matcher.find()) {
            return new Fund(
                    "Quỹ Đầu tư Cổ phiếu Triển vọng Bảo Việt",
                    "BVPF",
                    LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(matcher.group(2)));
        }
        return null;
    }

    public Fund fetchPvbf() throws IOException {
        System.out.println("[INFO] Start fetching URL");
        URL url = new URL("https://pvcomcapital.com.vn/quy-pvbf");

        String content = urlService.getContentFromURL(url);

        Matcher matcher = pvbfPattern.matcher(content);

        while (matcher.find()) {
            return new Fund(
                    "QUỸ ĐẦU TƯ TRÁI PHIẾU PVCOM",
                    "PVBF",
                    LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(matcher.group(3).replaceAll(",", ".")));
        }

        return null;

    }
}
