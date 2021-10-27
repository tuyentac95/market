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

    private final Pattern fundPattern = Pattern.compile("<ul> <li> NAV/CCQ tại ng&#224;y (\\d{2}/\\d{2}/\\d{4}?) <strong>(\\d+.\\d+?) VNĐ</strong></li>");

    public Fund fetchNav() throws IOException {
        System.out.println("[INFO] Start fetching URL");
        URL url = new URL("https://baovietfund.com.vn/san-pham/BVPF");

        String content = urlService.getContentFromURL(url);

        Matcher matcher = fundPattern.matcher(content);

        while (matcher.find()) {
            Fund BVPF = new Fund(
                    "Quỹ Đầu tư Cổ phiếu Triển vọng Bảo Việt",
                    "BVPF",
                    LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(matcher.group(2)));
            return BVPF;
        }
        return null;
    }
}
