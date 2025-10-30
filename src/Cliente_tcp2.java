import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente_tcp2 {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 9090);
            Scanner scanner = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String msj = "";

            while (!msj.equals("chau")) {
                System.out.print("Tu mensaje: ");
                msj = scanner.nextLine();
                out.println(msj);

                try {
                    // Esperamos la respuesta (o no)
                    String response = in.readLine();




                } catch (IOException e) {
                    System.out.println("No se pudo leer respuesta del servidor.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor.");
        }
    }
}
