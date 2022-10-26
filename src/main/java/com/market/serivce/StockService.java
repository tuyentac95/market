package com.market.serivce;

import com.market.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

//    @Autowired
//    private StockRepository repository;

    public void saveStocks(List<Stock> stocks, String date) {
//        List<Stock> oldList = repository.findAll();
//        List<Stock> newList = stocks.stream().map(stock -> {
//            String code = stock.getCode();
//            Stock s = oldList.stream().filter(s1 -> code.equals(s1.getCode())).findFirst().orElse(stock);
//            s.setPrice(stock.getPrice());
//            s.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            return s;
//        }).collect(Collectors.toList());
//        repository.saveAll(newList);
        System.out.println("Save stocks");
    }

    public Stock findStock(String code) {
//        return repository.findByCode(code).orElse(null);
        return null;
    }
}
