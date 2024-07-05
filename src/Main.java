public class Main {
    public static void main(String[] args) {
        Amazon amazon = FileCast.getFile("Amazon.dat");
        Menu.menu(amazon);
        FileCast.toFile("Amazon.dat", amazon);
    }
}