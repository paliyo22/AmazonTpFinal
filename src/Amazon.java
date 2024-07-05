
import java.io.*;
import java.time.Instant;
import java.util.*;

public class Amazon implements Serializable, Delete {

    @Serial
    private static final long serialVersionUID = 8325948381120069733L;
    private final HashSet<Account> accountList;
    private final LinkedHashSet<Product> productList;

    public Amazon() {
        productList = new LinkedHashSet<>();
        accountList = new HashSet<>();
    }

    /**
     * GETTERS
     */
    public HashSet<Account> getAccountList() {
        return accountList;
    }

    public LinkedHashSet<Product> getProductList() {
        return productList;
    }

    /**
     * METODOS
     */
    public void addAccount(Account account) {
        accountList.add(account);
    }

    public boolean addProduct(Product product) {
        return productList.add(product);
    }

    public boolean mailExist(String mail) {
        boolean aux = false;
        for (Account account : accountList) {
            if (account instanceof User user) {
                if (user.getMail().equals(mail)) {
                    aux = true;
                    break;
                }
            }
        }
        return aux;
    }

    //region Metodos Cuentas
    public Account getAccount(String userName) {
        Iterator<Account> it = accountList.iterator();
        Account aux;
        while (it.hasNext()) {
            aux = it.next();
            if (aux.getUserName().equals(userName)) {
                return aux;
            }
        }
        return null;
    }

    public void recoverAccount(Account account) {
        account.setStatus(true);
        if (account instanceof Business) {
            for (Product product : productList) {
                if (product.getBusinessId().equals(account.getId())) {
                    product.setStatus(true);
                }
            }
        }
    }

    @Override
    public void delete(Object o) {
        Account account = (Account) o;
        account.setStatus(false);
        if (account instanceof Business) {
            for (Product product : productList) {
                if (product.getBusinessId().equals(account.getId())) {
                    product.setStatus(false);
                }
            }
        }
    }
    //endregion

    //region Metodos Productos
    public ArrayList<Product> getDepartmentListProducts(EDepartment department) {
        ArrayList<Product> aux = new ArrayList<>();
        for (Product product : productList) {
            if (product.getDepartment().equals(department)) {
                aux.add(product);
            }
        }
        return aux;
    }

    public void addSales(ArrayList<Product> products) {
        for (Product e : products) {
            for (Account i : accountList) {
                if (i instanceof Business && i.getId().equals(e.getBusinessId())) {
                    ((Business) i).addSale(Instant.now(), e);
                }
            }
        }
    }

    public ArrayList<Product> getBusinessProducts(Business business) {
        ArrayList<Product> aux = new ArrayList<>();
        for (Product product : productList) {
            if (product.getBusinessId().equals(business.getId())) {
                aux.add(product);
            }
        }
        return aux;
    }
    //endregion

    public boolean userNameExist(String userName) {
        boolean aux = false;
        for (Account account : accountList) {
            if (account instanceof User user) {
                if (user.getUserName().equals(userName)) {
                    aux = true;
                    break;
                }
            }
        }
        return aux;
    }

    // region Metodos Empresas
    public void incrementPrices(float percent, Business business) {
        for (Product product : productList) {
            if (product.getBusinessId().equals(business.getId())) {
                product.incrementPrice(percent);
            }
        }
    }

    public void incrementStock(int amount, Business business) {
        for (Product product : productList) {
            if (product.getBusinessId().equals(business.getId())) {
                product.incrementStock(amount);
            }
        }
    }
    //endregion

    //region Metodos Administrador
    public void systemCleaner() {
        Iterator<Account> itA = accountList.iterator();
        Account account;
        while (itA.hasNext()) {
            account = itA.next();
            if (!account.getStatus()) {
                if (account instanceof Business) {
                    Iterator<Product> itP = productList.iterator();
                    while (itP.hasNext()) {
                        if (itP.next().getBusinessId().equals(account.getId())) {
                            itP.remove();
                        }
                    }
                }
                itA.remove();
            }
        }
    }

    public void ban(Account account) { // da de baja y genera una contraseña que el sistema no permite ingresar
        account.setStatus(false);
        account.setPassword("No entras");
    }

    //endregion






}
