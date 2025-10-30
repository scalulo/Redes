import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ejercicio_1_cliente {
    public static void main(String[] args) {

        try {
            DatagramSocket cliente_socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("0.0.0.0"); // Reemplazar con la dirección IP del servidor

            int puerto_de_server=5030;

            String msj="Hola";
            byte[] mandar_msj = msj.getBytes();

            DatagramPacket mandar_paquete= new DatagramPacket(mandar_msj, mandar_msj.length,serverAddress,puerto_de_server);

            cliente_socket.send(mandar_paquete);
            System.out.println("mensaje enviado correctamente");


            // Esperar respuesta
            byte[] receiveData = new byte[1000];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            cliente_socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Respuesta del servidor: " + response);
            cliente_socket.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
