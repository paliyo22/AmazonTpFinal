import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class User extends Account {

    private Address address;
    private final String mail;
    private Long number;


    public User(String userName, String password, Address address, String mail, Long number) {
        super(userName, password);
        this.address = address;
        this.mail = mail;
        this.number = number;
    }

    /**
     * SETTERS
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * GETTERS
     */
    public Address getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }

    /**
     * METODOS
     */
    public static Boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@((hotmail|gmail)+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\tMail: " + mail +
                "\n\tTelefono: " + number +
                address.toString();
    }
}
