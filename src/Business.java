import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Business extends User {

    private final HashMap<Instant, Product> salesList;

    public Business(String name, String password, Address address, String mail, Long number) {
        super(name, password, address, mail, number);
        this.salesList = new HashMap<>();
    }

    /**
     * GETTER
     */
    public HashMap<Instant, Product> getSalesList() {
        return salesList;
    }

    /**
     * METODOS
     */
    public void addSale(Instant date, Product product) {
        salesList.put(date, product);
    }

    public String getDateToString(Instant date){
        return LocalDateTime.ofInstant(date,
                ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy "));
    }


}
