package com.market.gold.serivce;

import com.market.gold.GoldRepository;
import com.market.gold.model.Gold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GoldService {

    @Autowired
    private GoldRepository repository;

    private final Pattern groupPattern = Pattern.compile("<tr class=\"danger\"> <td colspan=\"5\"> <strong>(.*?)</strong> </td> </tr> " +
            "(<tr> <td>(.*?)</td> <td> ([0-9]+,[0-9]{3}) (.*?)</td> <td> ([0-9]+,[0-9]{3}) (.*?)</td> " +
            "<td class=\"hidden-xs\">([0-9]+,[0-9]{3})</td> " +
            "<td class=\"hidden-xs\">([0-9]+,[0-9]{3})</td> </tr> )+");

    private final Pattern goldPattern = Pattern.compile("<tr> <td>(.*?)</td> " +
            "<td> ([0-9]+,[0-9]{3}) (.*?)</td> " +
            "<td> ([0-9]+,[0-9]{3}) (.*?)</td> " +
            "<td class=\"hidden-xs\">([0-9]+,[0-9]{3})</td> " +
            "<td class=\"hidden-xs\">([0-9]+,[0-9]{3})</td> </tr>");

    private void saveGolds(List<Gold> golds) {
        System.out.println("[INFO] Save golds to db");
        repository.saveAll(golds);
    }

    public List<Gold> fetchPriceByDate(String date) throws IOException {
        LocalDate fetchDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return fetchPriceByDate(fetchDate);
    }

    public List<Gold> fetchPriceByDate(LocalDate date) throws IOException {
        System.out.println("[INFO] Start fetching URL");
        String baseURL = "https://blogtygia.com/gia-vang-ngay-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        URL url = new URL(baseURL + date.format(formatter) + ".html");

        String content = getContentFromURL(url);
        Matcher groupMatcher = groupPattern.matcher(content);

        List<Gold> golds = new ArrayList<>();
        while (groupMatcher.find()) {
            String groupName = groupMatcher.group(1);
            List<Gold> group = extractListGoldInGroupWithDate(groupName, groupMatcher.group(0), date);
            golds.addAll(group);
        }

        saveGolds(golds);

        return golds;
    }

    private String getContentFromURL(URL url) throws IOException {
        System.out.println("[INFO] Handle content from URL");

        Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();

        content = content.replaceAll("\\n+", "");
        content = content.replaceAll("\\s+", " ");
        return content;
    }

    private List<Gold> extractListGoldInGroupWithDate(String groupName, String content, LocalDate date) {
        System.out.println("[INFO] Start extract data from content");
        List<Gold> golds = new ArrayList<>();
        Matcher goldMatcher = goldPattern.matcher(content);
        while (goldMatcher.find()) {
            String name = goldMatcher.group(1);
            Integer bidPrice = Integer.valueOf(goldMatcher.group(2).replaceAll(",", ""));
            Integer askPrice = Integer.valueOf(goldMatcher.group(4).replaceAll(",", ""));

            Gold gold = Gold.builder()
                    .name(name)
                    .groupName(groupName)
                    .bidPrice(bidPrice)
                    .askPrice(askPrice)
                    .date(date)
                    .build();

            golds.add(gold);
        }
        return golds;
    }

    public List<Gold> getPriceByDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return repository.findByDate(localDate).orElse(new ArrayList<>());
    }
}
