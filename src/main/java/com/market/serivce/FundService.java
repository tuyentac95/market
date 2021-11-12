package com.market.serivce;

import com.market.factory.FundFactory;
import com.market.model.Fund;
import com.market.model.SFund;
import com.market.model.FundParameter;
import com.market.repository.FundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
public class FundService {

    @Autowired
    private UrlService urlService;

    @Autowired
    private FundRepository repository;

    public SFund fetchNav(String code) throws IOException {
        FundParameter parameter = FundFactory.getFund(code);
        System.out.println("[INFO] Start fetching URL");
        URL url = new URL(parameter.getBaseURL());

        String content = urlService.getContentFromURL(url);
        content = content.replaceAll("&nbsp;", " ");

        Matcher matcher = parameter.getPattern().matcher(content);

        while (matcher.find()) {
            return new SFund(
                    parameter.getName(),
                    parameter.getCode(),
                    LocalDate.parse(matcher.group(parameter.getDateMatchGroup()), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(matcher.group(parameter.getNavMatchGroup()).replaceAll(",", ".")));
        }
        return null;
    }

    public void saveFunds(List<Fund> funds, String date) {
        List<Fund> oldList = repository.findAll();
        List<Fund> newList = funds.stream().map(fund -> {
            String code = fund.getCode();
            Fund f = oldList.stream().filter(f1 -> code.equals(f1.getCode())).findFirst().orElse(fund);
            f.setNav(fund.getNav());
            f.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return f;
        }).collect(Collectors.toList());
        repository.saveAll(newList);
    }

    public Fund findFund(String code) {
        return repository.findByCode(code).orElse(null);
    }
}
