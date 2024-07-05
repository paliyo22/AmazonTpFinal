import java.io.*;


public class FileCast {

    public static void toFile(String path, Amazon data) {
        File save = new File(path);
        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(new FileOutputStream(save, false));
            output.writeObject(data);
            output.close();
            System.out.println("Archivo guardado con exito");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public static Amazon getFile(String path) {
        Amazon aux = new Amazon();
        File save = new File(path);
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(new FileInputStream(save));
            aux = (Amazon) input.readObject();
            input.close();

        } catch (IOException ex) {
            System.out.println("El archivo no existe o esta dañado");
        } catch (ClassNotFoundException e) {
            System.out.println("Los datos de alrchivo no coinciden con los datos requeridos");
        }
        return aux;
    }


}
