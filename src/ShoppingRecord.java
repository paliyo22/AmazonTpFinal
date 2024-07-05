import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ShoppingRecord implements Serializable {

    private Instant date;
    private Instant deliver;
    private final ArrayList<Product> shoppingList;

    public ShoppingRecord() {

        this.shoppingList = new ArrayList<>();
    }

    /**
     * SETTERS
     */
    private void setDate() {

        date = Instant.now();
    }

    private void setDeliver(Integer days) {

        this.deliver = date.plus(days, ChronoUnit.DAYS);
    }

    /**
     * GETTERS
     */

    public String getDateToString() {
        return LocalDateTime.ofInstant(date,
                ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy "));
    }

    public Instant getDate() {
        return date;
    }

    public String getDeliver() {
        return LocalDateTime.ofInstant(deliver,
                ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy "));
    }

    public ArrayList<Product> getShoppingList() {
        return shoppingList;
    }

    /**
     * METODOS
     */
    public boolean addProduct(Product product) {
        if (product.getStock() > 0) {
            return shoppingList.add(product);
        }
        return false;
    }

    public Double total() {
        Double aux = (double) 0;
        for (Product i : shoppingList) {
            aux += i.getPrice();
        }
        return aux;
    }

    public void endPurchase(ECountry country) {
        setDate();
        int aux = 0;
        switch (country) {
            case Argentina -> aux = 10;
            case Brazil -> aux = 12;
            case Uruguay -> aux = 7;
        }
        setDeliver(aux);
    }

    @Override
    public String toString() {
        return "\t Fecha de Compra: " + getDateToString() +
                "\t Fecha de Entrega: " + getDeliver() +
                "\t Total: " + this.total();
    }

}
