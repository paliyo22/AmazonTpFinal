
import java.time.Instant;
import java.util.*;

public class Menu {

    private static final Scanner key = new Scanner(System.in);

    // region Menu Principal
    public static void menu(Amazon amazon) {
        int option;
        long accountAux = 0;
        long productAux = 0;
        for (Account account : amazon.getAccountList()) {
            if (accountAux <= account.getId()) {
                accountAux = account.getId() + 1;
            }
        }
        for (Product product : amazon.getProductList()) {
            if (productAux <= product.getId()) {
                productAux = product.getId() + 1;
            }
        }
        Account.setAutoInc(accountAux);
        Product.setAutoInc(productAux);
        do {
            System.out.println("===============Main Menu===========");
            System.out.println("\t1. Catálogo");
            System.out.println("\t2. Ingresar");
            System.out.println("\t3. Crear nueva cuenta");
            System.out.println("\t4. Salir");

            try {
                option = key.nextInt();
            } catch (NoSuchElementException ex) {
                option = 0;
                key.next();
            }
            switch (option) {
                case 1:
                    EDepartment department = showCategory();
                    if (department != null) {
                        showProducts(amazon.getDepartmentListProducts(department));
                        try {
                            key.nextLine(); // limpia el boofer
                            key.nextLine(); // Espera el enter para continuar (seguramente se puede hacer mejor no encontre como)
                        } catch (Exception _) {
                        }
                    }
                    break;
                case 2:
                    logIn(amazon);
                    break;
                case 3:
                    createAccount(amazon);
                    break;
                case 4:
                    System.out.println("\tChau");
                    break;
                default:
                    System.out.println("\tOpción inválida, Intente de nuevo.");
            }
        } while (option != 4);
        key.close();
    }
    //endregion

    //region Selecciona/Muestra Categorias
    private static EDepartment showCategory() {
        int option;
        boolean flag = true;
        do {
            System.out.println("===========Categorias==========");
            System.out.println("\t0. Salir");
            for (EDepartment e : EDepartment.values()) {
                System.out.println("\t" + (e.ordinal() + 1) + ". " + e.name());
            }
            try {
                option = key.nextInt();

            } catch (NoSuchElementException e) {
                System.out.println("\tError, debe ingresar un numero");
                key.next();
                option = -2;
            }
            if (option < 0 || option > EDepartment.values().length) {
                System.out.println("\tEl numero ingresado no forma parte de las opciones");
            } else flag = false;
        } while (flag);
        if (option != 0) {
            return EDepartment.values()[option - 1];
        } else {
            return null;
        }
    }

    private static void showProducts(ArrayList<Product> products) {
        System.out.println("=================================");
        System.out.println("\t0. Atras");
        if (!products.isEmpty()) {
            int i = 1;
            for (Product e : products) {
                if (e.getStatus() || e.getStock() > 0) {
                    System.out.println("\t" + i + ". " + e);
                    i++;
                }
            }
        } else System.out.println("\tNo hay productos registrados en esta categoria actualmente");
    }
    //endregion

    //region LogIn
    private static void logIn(Amazon amazon) {
        Account account;
        boolean flag = true;
        String aux;
        do {
            System.out.println("============Log In==============");
            System.out.println("\tPara cancelar ingrese '0'");
            System.out.println("\tIngrese su usuario ");
            try {
                aux = key.next();
                if (!aux.equals("0")) {
                    account = amazon.getAccount(aux);
                    if (account != null) {
                        System.out.println("\tIngrese su contraseña:");
                        if (account.getPassword().equals(key.next())) {
                            if (!account.getStatus()) {
                                System.out.println("\tHola " + account.getUserName() + " te extañabamos");
                                amazon.recoverAccount(account);
                                System.out.println("\tTu cuenta ha sido restaurada");
                            }
                            if (account instanceof Client) {
                                clientMenu((Client) account, amazon);
                            } else {
                                if (account instanceof Business) {
                                    businessMenu((Business) account, amazon);
                                } else {
                                    adminMenu((Admin) account, amazon);
                                }
                            }
                            flag = false;
                        } else {
                            System.out.println("\tContraseña erronea");
                        }
                    } else {
                        System.out.println("\tUsuario erroneo");
                    }
                } else {
                    flag = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError en el igreso de datos, intente nuevamente");
            }
        } while (flag);
    }
    //endregion

    //region Menu Empresa/Cliente/Admin
    private static void adminMenu(Admin account, Amazon amazon) {
        int option;
        Account aux;
        do {
            System.out.println("=========Menu Administrador=========");
            System.out.println("\t1. Ver lista de usuarios.");
            System.out.println("\t2. Ver lista de Productos.");
            System.out.println("\t3. Banear usuario.");
            System.out.println("\t4. Persistir el sistema.");
            System.out.println("\t5. Limpieza del sistema.");
            System.out.println("\t6. Cambiar Contraseña.");
            System.out.println("\t7. Crear nueva cuenta de admin.");
            System.out.println("\t8. Salir.");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                option = 10;
                key.next();
            }
            switch (option) {
                case 1:
                    System.out.println("==========Lista de Clientes=========");
                    if(!amazon.getAccountList().isEmpty()) {
                        for (Account account1 : amazon.getAccountList()) {
                            System.out.println("\tId: " + account1.getId() + "\tNombre: " + account1.getUserName() + "\tContraseña: " + account1.getPassword() + "\tEstado: "+account1.getStatus());
                        }
                    }else System.out.println("No existen Clientes registrados actualmente");
                    try{
                        key.nextLine();
                        key.nextLine();
                    }catch (Exception _){}
                    break;
                case 2:
                    System.out.println("==========Lista de Productos=========");
                    if(!amazon.getProductList().isEmpty()) {
                        for (Product product : amazon.getProductList()) {
                            System.out.println("\tId: " + product.getId() + "\tIdNegocio: " + product.getBusinessId() + "\tNombre: " + product.getName());
                        }
                    }else System.out.println("No existen Productos registrados actualmente");
                    try{
                        key.nextLine();
                        key.nextLine();
                    }catch (Exception _){}
                    break;
                case 3:
                    System.out.println("\tIngrese el usuario a banear");
                    try {
                        aux = amazon.getAccount(key.next());
                        if (aux != null) {
                            amazon.ban(aux);
                        } else System.out.println("\tEl nombre de usuario no existe");

                    } catch (NoSuchElementException e) {
                        System.out.println("\tError en el igreso de datos, intente nuevamente");
                    }
                    break;
                case 4:
                    FileCast.toFile("Amazon.dat", amazon);
                    break;
                case 5:
                    amazon.systemCleaner();
                    break;
                case 6:
                    changePassword(account);
                    break;
                case 7:
                    amazon.addAccount(new Admin(newUserName(amazon), newPassword()));
                    break;
                case 8:
                    break;
                default:
                    System.out.println("\tEsa opcion no existe, Intente nuevamente");
            }
        } while (option != 8);
    }

    private static void businessMenu(Business account, Amazon amazon) {
        int option;
        boolean flag;
        do {
            System.out.println("============ " + account.getUserName() + " ==========");
            System.out.println("\t1. Ver perfil");
            System.out.println("\t2. Ver/Modificar productos");
            System.out.println("\t3. Agregar nuevo producto");
            System.out.println("\t4. Aumentar precios %");
            System.out.println("\t5. Aumentar existencias de todos los productos");
            System.out.println("\t6. Mostras Ventas");
            System.out.println("\t7. Salir");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                option = 0;
                key.next();
            }
            switch (option) {
                case 1:
                    if (showProfile(account, amazon) != 0) {
                        option = 7;
                    }
                    break;
                case 2:
                    showBusinessProducts(account, amazon);
                    break;
                case 3:
                    if (newProduct(account, amazon)) {
                        System.out.println("\tEl producto ya se encuentra publicado");
                    } else System.out.println("\tYa tienes este producto publicado");
                    break;
                case 4:
                    do {
                        System.out.println("\tIngrese el porcentaaje que quiere aumentar");
                        try {
                            amazon.incrementPrices(key.nextFloat(), account);
                            flag = true;
                        } catch (NoSuchElementException e) {
                            System.out.println("\tError en el igreso de datos, intente nuevamente");
                            key.next();
                            flag = false;
                        }
                    } while (!flag);
                    break;
                case 5:
                    do {
                        System.out.println("\tIngrese la cantidad de Stock a agregar");
                        try {
                            amazon.incrementStock(key.nextInt(), account);
                            flag = true;
                        } catch (NoSuchElementException e) {
                            System.out.println("\tError en el igreso de datos, intente nuevamente");
                            key.next();
                            flag = false;
                        }
                    } while (!flag);
                    break;
                case 6:
                    for (Map.Entry<Instant, Product> sale : account.getSalesList().entrySet()) {
                        System.out.println("\tFecha: " + account.getDateToString(sale.getKey()) + "\t" + sale.getValue().getName() + "\t$" + sale.getValue().getPrice());
                    }
                    break;
                case 7:
                    System.out.println("\tHasta la proxima");
                    break;
                default:
                    System.out.println("\tEsa opcion no existe, Intente nuevamente");
            }
        } while (option != 7);
    }

    private static void clientMenu(Client account, Amazon amazon) {
        int option;

        do {
            System.out.println("============== Hola " + account.getUserName() + "=============");
            System.out.println("\t1. Ver perfil");
            System.out.println("\t2. Ir de compras");
            System.out.println("\t3. Ver compras");
            System.out.println("\t4. Salir");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                option = 0;
                key.next();
            }
            switch (option) {
                case 1:
                    if (showProfile(account, amazon) != 0) {
                        option = 4;
                    }
                    break;
                case 2:
                    goShopping(account, amazon);
                    break;
                case 3:
                    showPurchaseRecord(account);
                    break;
                case 4:
                    System.out.println("\tHasta la proxima");
                    break;
                default:
                    System.out.println("\tOpcion invalida. Intente nuevamente");
            }
        } while (option != 4);

    }
    //endregion

    //region Detalles/Modificacion/Creacion de Producto
    private static void showBusinessProducts(Business account, Amazon amazon) {
        int option;
        int aux;
        ArrayList<Product> productList = amazon.getBusinessProducts(account);
        do {
            aux = 0;
            System.out.println("================== Mis Productos =============");
            System.out.println("\t0. Salir");
            for (Product e : productList) {
                aux++;
                System.out.println("\t" + aux + ". " + e.toString());
            }
            try {
                option = key.nextInt();
                if (option > 0 && aux >= option) {
                    showProductMenu(productList.get(option - 1));
                } else if (option != 0) {
                    System.out.println("\tOpcion invalida. Intente nuevamente");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                option = -1;
            }
        } while (option != 0);

    }

    private static boolean newProduct(Business account, Amazon amazon) {
        return amazon.addProduct(new Product(account.getId(), newProductName(), newStock(), newPrice(), newDepartment()));
    }

    private static void showProductMenu(Product product) {
        int option;
        do {
            System.out.println("\t1. Nombre: " + product.getName());
            System.out.println("\t2. Stock: " + product.getStock());
            System.out.println("\t3. Precio: $" + product.getPrice());
            if (product.getStatus()) {
                System.out.println("\t4. Dar de baja");
            } else {
                System.out.println("\t4. Dar de alta");
            }
            System.out.println("\t5. Salir");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                option = 0;
                key.next();
            }
            switch (option) {
                case 1:
                    product.setName(newProductName());
                    break;
                case 2:
                    product.setStock(newStock());
                    break;
                case 3:
                    product.setPrice(newPrice());
                    break;
                case 4:
                    product.setStatus(!product.getStatus());
                    if (product.getStatus()){
                        product.setStock(newStock());
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\tOpcion invalida, Intente nuevamente");
            }
        } while (option != 5);
    }

    private static EDepartment newDepartment() {
        boolean flag = false;
        EDepartment department = null;
        do {
            System.out.println("\tSeleccione la categoria:");
            for (EDepartment e : EDepartment.values()) {
                System.out.println("\t" + (e.ordinal() + 1) + ". " + e.name());
            }
            try {
                department = EDepartment.values()[key.nextInt() - 1];
                flag = true;
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("\tIngrese una opcion valida por favor.");
            }
        } while (!flag);
        return department;
    }

    private static double newPrice() {
        double price;
        do {
            System.out.println("\tIngrese el precio");
            try {
                price = key.nextDouble();
                if (price < 0) {
                    System.out.println("\tNo puede tomar valores negativos.\nIntente nuevamente.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                price = -1;
            }
        } while (price < 0);
        return price;
    }

    private static int newStock() {
        int stock = -1;
        boolean flag = false;
        do {
            System.out.println("\tCantidad en Stock");
            try {
                stock = key.nextInt();
                if (stock >= 0) {
                    flag = true;
                } else System.out.println("\tNo puede tomar valores negativos.\n\tIntente nuevamente.");

            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            }
        } while (!flag);
        return stock;
    }

    private static String newProductName() {
        String name;
        boolean flag = true;
        do {
            System.out.println("\tIngrese el nombre del producto");
            try {
                key.nextLine();
                name = key.nextLine();
                flag = false;
            } catch (NoSuchElementException e) {
                name = "";
            }
        } while (flag);
        return name;
    }
    //endregion

    //region Muestra/Modifica Perfil
    private static int showProfile(User account, Amazon amazon) {
        int option;
        do {
            System.out.println("============ Mis datos =============");
            System.out.println(account.toString());
            System.out.println("============ Modificar Datos =============");
            System.out.println("\t0. Para salir");
            System.out.println("\t1. Cambiar Nombre");
            System.out.println("\t2. Cambiar Contraseña");
            System.out.println("\t3. Cambiar Direccion");
            System.out.println("\t4. Cambiar Numero");
            System.out.println("\t5. Borrar cuenta");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                key.next();
                option = -1;
            }
            switch (option) {
                case 0:
                    break;
                case 1:
                    account.setUserName(newUserName(amazon));
                    break;
                case 2:
                    changePassword(account);
                    break;
                case 3:
                    account.setAddress(newAddress());
                    break;
                case 4:
                    account.setNumber(newNumber());
                    break;
                case 5:
                    amazon.delete(account);
                    System.out.println("Su cuenta fue dada de baja.");
                    break;
                default:
                    System.out.println("\tOpcion invalida. Intente nuevamente");
            }
        } while (option != 0 && option != 5);
        return option;
    }
    //endregion

    //region Cambio de Contraseña
    private static void changePassword(Account account) {
        String password;
        boolean flag = true;
        do {
            System.out.println("=========== Nueva Contraseña=========");
            System.out.println("\tIngrese su contraseña");
            try {
                password = key.next();
                if (account.getPassword().equals(password)) {
                    System.out.println("\tIngrese una nueva contraseña");
                    password = key.next();
                    if (!account.getPassword().equals(password)) {
                        System.out.println("\tVerifique la contraseña");
                        if (key.nextLine().equals(password)) {
                            flag = false;
                            account.setPassword(password);
                        } else {
                            throw new PasswordException("\tLas contraseñas no coinciden intente devuelta");
                        }
                    }
                } else {
                    throw new PasswordException("\tContraseña incorrecta. Enter para continuar");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
            } catch (PasswordException e) {
                System.out.println(e.getMessage());
            }
        } while (flag);
    }
    //endregion

    //region Registro de compras
    private static void showPurchaseRecord(Client account) {
        int option;
        int aux;
        do {
            do {
                System.out.println("============= Compras Realizadas =============");
                aux = 1;
                System.out.println("\tSeleccione para ver detalles");
                System.out.println("\t0. Salir");
                System.out.println("\t1. Para limpiar los registros anteriores a 90 dias");
                for (ShoppingRecord e : account.getPurchaseList()) {
                    aux++;
                    System.out.println("\t" + aux + ". " + e.toString());
                }
                try {
                    option = key.nextInt();
                    if (option < 0 || option > aux) {
                        System.out.println("\tEse registro no existe, intente nuevamente");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("\tError al ingresar datos. Intente nuevamente");
                    option = -1;
                    key.next();
                }
            } while (option < 0 || option > aux);
            if (option > 1) {
                System.out.println(account.getPurchaseList().get(option-2).toString());
                for (Product e : account.getShoppingRecord(option - 2).getShoppingList()) {
                    System.out.println("\t" + e.toString());
                }
                System.out.println("\tTotal: $" + account.getShoppingRecord(option - 2).total());
                System.out.println("\tEnter para volver atras");
                try {
                    key.nextLine();
                    key.nextLine();
                } catch (NoSuchElementException _) {
                }
            } else if (option == 1) {
                account.delete(Instant.now());
            }
        } while (option != 0);
    }
    //endregion

    //region Realizar Compra
    private static void goShopping(Client account, Amazon amazon) {
        ShoppingRecord purchase = new ShoppingRecord();
        int option = -1;
        EDepartment aux;
        ArrayList<Product> productList;
        do {
            aux = showCategory();
            if (aux != null) {
                productList = amazon.getDepartmentListProducts(aux);
                do {
                    showProducts(productList);
                    try {
                        option = key.nextInt();
                        if (option > 0 && option <= productList.size()) {
                            if (purchase.addProduct(productList.get(option - 1))) {
                                System.out.println("Agregado al carrito");
                            } else {
                                System.out.println("En este momento se encuentra agotado");
                            }
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("\tError al ingresar datos. Intente nuevamente");
                        key.next();
                    }
                } while (option != 0);
            }
        } while (aux != null);
        if (!purchase.getShoppingList().isEmpty()) {
            do {
                option = 3;
                System.out.println("============= Carrito =========");
                for (Product e : purchase.getShoppingList()) {
                    System.out.println(e.toString());
                }
                System.out.println("\tTotal: " + purchase.total());
                System.out.println("\t0. Cancelar compra");
                System.out.println("\t1. Realizar Compra");
                try {
                    option = key.nextInt();
                } catch (NoSuchElementException e) {
                    System.out.println("\tError al ingresar datos. Intente nuevamente");
                    key.next();
                }
            } while (option != 0 && option != 1);
            if (option == 1) {
                for (Product product : purchase.getShoppingList()) {
                    product.sold();
                }
                amazon.addSales(purchase.getShoppingList());
                purchase.endPurchase(account.getAddress().getCountry());
                account.addPurchase(purchase);
                System.out.println("\tSu pedido llegara el " + purchase.getDeliver());
            } else System.out.println("\tSe cancelo la compra");
        }
    }
    //endregion

    //region Menu/Creacion de Cuentas
    private static void createAccount(Amazon amazon) {
        int option;
        Account aux;

        do {
            System.out.println("=================Nueva cuenta================");
            System.out.println("\t1. Crear cliente");
            System.out.println("\t2. Crear empresa");
            System.out.println("\t0. Cancelar");
            try {
                option = key.nextInt();
            } catch (NoSuchElementException e) {
                option = 3;
                key.next();
            }
            switch (option) {
                case 0:
                    break;
                case 1:
                    aux = newClient(amazon);
                    if (aux != null) {
                        clientMenu((Client) aux, amazon);
                    }
                    break;
                case 2:
                    aux = newBusiness(amazon);
                    if (aux != null) {
                        businessMenu((Business) aux, amazon);
                    }
                    break;
                default:
                    System.out.println("\tError al ingresar datos. Intente nuevamente");
            }
        } while (option < 0 || option > 2);
    }

    private static Business newBusiness(Amazon amazon) {
        String aux;
        System.out.println("=============Crear Cuenta Empresarial===========");
        aux = newMail(amazon);
        if (aux != null) {
            Business business = new Business(newUserName(amazon), newPassword(), newAddress(), aux, newNumber());
            amazon.addAccount(business);
            return business;
        } else return null;
    }

    private static Client newClient(Amazon amazon) {
        String aux;
        System.out.println("============Crear Cuenta===========");
        aux = newMail(amazon);
        if (aux != null) {
            Client client = new Client(newUserName(amazon), newPassword(), newAddress(), aux, newNumber());
            amazon.addAccount(client);
            return client;
        } else return null;
    }
    //endregion

    //region Validaciones de Datos
    private static String newUserName(Amazon amazon) {
        String name = "";
        boolean flag = true;
        do {
            try {
                System.out.println("\tIngrese el nombre");
                name = key.next();
                if (!amazon.userNameExist(name)) {
                    flag = false;
                } else {
                    System.out.println("\tEl nombre de usuario no esta disponible");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            }
        } while (flag);
        return name;
    }

    private static String newPassword() {
        String password = "";
        boolean flag = true;
        do {

            System.out.println("\tIngrese una contraseña");
            try {
                password = key.next();
                System.out.println("\tVerifique la contraseña");
                if (key.next().equals(password)) {
                    flag = false;
                } else {
                    throw new PasswordException("\tLas contraseñas no coinciden intente devuelta");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            } catch (PasswordException e) {
                System.out.println(e.getMessage());
            }
        } while (flag);
        return password;
    }

    private static String newMail(Amazon amazon) {
        String mail = "";
        boolean flag = true;
        key.nextLine();
        do {
            System.out.println("\tIngrese un mail:");
            try {
                mail = key.nextLine();
                if (!mail.equals("0")) {
                    if (!User.emailValidation(mail)) {
                        System.out.println("\tFormato incorrecto");
                    } else if (amazon.mailExist(mail)) {
                        System.out.println("\tEste mail ya esta registrado");
                        System.out.println("\tIngrese '0' para cancelar");
                    } else {
                        flag = false;
                    }
                } else return null;
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            }
        } while (flag);
        return mail;
    }


    private static long newNumber() {
        long number = 0;
        boolean flag = false;
        do {
            try {
                System.out.println("\tIngrese un numero de telefono :");
                number = key.nextLong();
                flag = true;
            } catch (NoSuchElementException ex) {
                key.next();
                System.out.println("\tError al ingresar datos. Intente nuevamente");
            }

        } while (!flag);
        return number;
    }

    private static Address newAddress() {
        boolean flag = false;
        ECountry country = null;
        int postalCode = 0;
        String street;
        do {
            System.out.println("\tSeleccione el pais:");
            for (ECountry e : ECountry.values()) {
                System.out.println("\t" + (e.ordinal() + 1) + ". " + e.name());
            }
            try {
                country = ECountry.values()[key.nextInt() - 1];
                flag = true;
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                key.next();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("\tError, el valor ingresado no existe. Intente nuevamente");
            }
        } while (!flag);
        do {
            System.out.println("\tIngrese su codigo postal:");
            try {
                postalCode = key.nextInt();
                flag = false;
            } catch (InputMismatchException e) {
                System.out.println("\tError, el valor ingresado no existe. Intente nuevamente");
                key.next();
            }
        } while (flag);
        do {
            try {
                key.nextLine();
                System.out.println("\tIngrese su direccion (ej: pepito 2456):");
                street = key.nextLine();
                flag = true;
            } catch (NoSuchElementException e) {
                System.out.println("\tError al ingresar datos. Intente nuevamente");
                street = "";
            }
        } while (!flag);
        return new Address(country, postalCode, street);
    }
    //endregion
}
