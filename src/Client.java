import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class Client extends User implements Delete {

    private final ArrayList<ShoppingRecord> purchaseList;

    public Client(String name, String password, Address address, String mail, Long number) {
        super(name, password, address, mail, number);
        this.purchaseList = new ArrayList<>();
    }

    /**
     * GETTER
     */
    public ArrayList<ShoppingRecord> getPurchaseList() {
        return purchaseList;
    }

    /**
     * METODOS
     */
    public void addPurchase(ShoppingRecord shopping) {
        purchaseList.addFirst(shopping);
    }

    public ShoppingRecord getShoppingRecord(int number) {
        return purchaseList.get(number);
    }


    @Override
    public String toString() {
        return super.toString();

    }

    @Override
    public void delete(Object o) {
        Instant date = (Instant) o;
        purchaseList.removeIf(shoppingRecord -> date.minus(90, ChronoUnit.DAYS).isAfter(shoppingRecord.getDate()));
    }
}
