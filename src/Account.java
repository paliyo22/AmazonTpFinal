import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Serializable {

    private static long autoInc = 0;
    private final Long id;
    private String userName;
    private String password;
    private Boolean status;


    public Account(String userName, String password) {
        this.id = autoInc;
        this.userName = userName;
        this.password = password;
        this.status = true;
        autoInc = autoInc + 1;
    }

    /**
     * SETTERS
     */
    public void setUserName(String name) {
        this.userName = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static void setAutoInc(long autoInc) {
        Account.autoInc = autoInc;
    }

    /**
     * GETTERS
     */
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(userName, account.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    /**
     * METODOS
     */

    @Override
    public String toString() {
        return "\tNombre: " + userName;
    }
}
