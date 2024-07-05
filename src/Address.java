import java.io.Serializable;

public class Address implements Serializable {

    private final ECountry country;
    private final Integer postCode;
    private final String street;

    public Address(ECountry country, Integer postCode, String street) {
        this.country = country;
        this.postCode = postCode;
        this.street = street;
    }

    /**
     * GETTERS
     */
    public ECountry getCountry() {
        return country;
    }

    /**
     * METODOS
     */
    @Override
    public String toString() {
        return "\n\tPais: " + country +
                "\n\tCodigo Postal: " + postCode +
                "\n\tCalle: " + street;


    }
}
