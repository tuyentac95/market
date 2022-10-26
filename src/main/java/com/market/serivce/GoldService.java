package com.market.serivce;

import com.market.model.Gold;
import com.market.model.GoldCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GoldService {

//    @Autowired
//    private GoldRepository repository;

    @Autowired
    private UrlService urlService;

//    @Autowired
//    private GoldCategoryRepository categoryRepository;

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
//        repository.saveAll(golds);
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

        String content = urlService.getContentFromURL(url);
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
//        return repository.findByDate(localDate).orElse(new ArrayList<>());
        return null;
    }

    public List<Gold> fetchPriceByMonth(String month) {
        LocalDate startDate = LocalDate.parse("01-" + month + "-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int daysOfMonth = startDate.lengthOfMonth();
        LocalDate endDate = startDate.plusDays(daysOfMonth);
        LocalDate fetchDay = startDate;
        List<Gold> golds = new ArrayList<>();
        while (endDate.compareTo(fetchDay) > 0) {
            try {
                System.out.println("Fetch price of day: " + fetchDay);
                List<Gold> goldsOfDate = fetchPriceByDate(fetchDay);
                golds.addAll(goldsOfDate);
                fetchDay = fetchDay.plusDays(1);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException | IOException e) {
                System.out.println("Error in day: " + fetchDay);
                e.printStackTrace();
                return null;
            }
        }
        System.out.println("Save golds to DB");
        saveGolds(golds);
        return golds;
    }

    public List<GoldCategory> getCategories() {
//        List<GoldCategory> categories = categoryRepository.getAllCategories().get();
//        categories.forEach((category) -> {
//            System.out.println(category);
//            GoldCategory newCat = GoldCategory.builder().name(category.getName()).group_name(category.getGroup_name()).build();
//            System.out.println(newCat);
//            categoryRepository.save(newCat);
//            System.out.println("Saved");
//        });
//        return categoryRepository.findAll();
        return null;
    }

    public GoldCategory getCategory(Long id) {
//        return categoryRepository.findById(id).orElse(null);
        return null;
    }

    public List<Gold> fetchPriceByType(Long id, LocalDate from, LocalDate to) {
//        GoldCategory category = categoryRepository.findById(id).orElse(null);
//        if (category != null) {
//            return repository.findByType(category.getName(), category.getGroup_name(), from, to).orElse(null);
//        }
        return null;
    }
}
