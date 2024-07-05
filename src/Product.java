import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    private static long autoInc = 0;
    private final Long id;
    private final Long businessId;
    private String name;
    private Integer stock;
    private Double price;
    private final EDepartment department;
    private Boolean status;

    public Product(Long businessId, String name, Integer stock, Double price,
                   EDepartment department) {
        this.id = autoInc;
        this.businessId = businessId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.department = department;
        this.status = true;
        autoInc = 1 + autoInc;
    }

    public long getId() {
        return id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public EDepartment getDepartment() {
        return department;
    }


    public Boolean getStatus() {
        return status;
    }

    /**
     * SETTERS
     */
    public static void setAutoInc(long autoInc) {
        Product.autoInc = autoInc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * METODOS
     */
    public void incrementPrice(float percent) {
        price = price * (1 + (percent / 100));
    }

    public void incrementStock(int amount) {
        stock += amount;
    }

    public void sold() {
        stock--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(businessId, product.businessId) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId, name);
    }

    @Override
    public String toString() {
        return name + "\t$" + price;
    }
}
