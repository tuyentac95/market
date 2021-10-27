package com.market.serivce;

import com.market.factory.FundFactory;
import com.market.model.Fund;
import com.market.model.FundParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

@Service
public class FundService {

    @Autowired
    private UrlService urlService;

    public Fund fetchNav(String code) throws IOException {
        FundParameter parameter = FundFactory.getFund(code);
        System.out.println("[INFO] Start fetching URL");
        URL url = new URL(parameter.getBaseURL());

        String content = urlService.getContentFromURL(url);

        Matcher matcher = parameter.getPattern().matcher(content);

        while (matcher.find()) {
            return new Fund(
                    parameter.getName(),
                    parameter.getCode(),
                    LocalDate.parse(matcher.group(parameter.getDateMatchGroup()), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(matcher.group(parameter.getNavMatchGroup()).replaceAll(",", ".")));
        }
        return null;
    }
}
