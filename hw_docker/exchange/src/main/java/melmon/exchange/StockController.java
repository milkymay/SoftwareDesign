package melmon.exchange;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final StockManager stockManager = new StockManager();

    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam int quantity, @RequestParam int price) {
        stockManager.addStock(name, quantity, price);
        return "Stock info updated";
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam String name, @RequestParam int quantity) {
        try {
            stockManager.buyStock(name, quantity);
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format("Successfully bought %d of stocks %s", quantity, name);
    }

    @RequestMapping("/sell")
    public String sell(@RequestParam String name, @RequestParam int quantity) {
        try {
            stockManager.sellStock(name, quantity);
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format("Successfully sold %d of stocks %s", quantity, name);
    }

    @RequestMapping("/get_price")
    public String getPrice(@RequestParam String name) {
        int value;
        try {
            value = stockManager.getPrice(name);
        } catch (Exception e) {
            return e.getMessage();
        }
        return Integer.toString(value);
    }

    @RequestMapping("/update_price")
    public String updatePrice(@RequestParam String name, @RequestParam int price) {
        try {
            stockManager.updatePrice(name, price);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Successfully updated price";
    }
}
