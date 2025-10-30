import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ejercicio_2_servidor {
    public static void main(String[] args) {
        try {

            //Usar el mismo puerto que en cliente
            DatagramSocket server_socket= new DatagramSocket(5030);
            System.out.println("servidor escuchando puerto "+server_socket.getLocalPort());

            // Creamo un buffer para entrada de datos.
            byte [] receiveData = new byte[1000];

            // Genera un datagrama para mensages de longitud indicada.
            DatagramPacket recibir_paquete = new DatagramPacket(receiveData, receiveData.length);

            while (true){

                server_socket.receive(recibir_paquete);

                // 5. Obtener la dirección IP y puerto del cliente
                InetAddress clientAddress = recibir_paquete.getAddress();
                int clientPort = recibir_paquete.getPort();

                // 6. Convertir los datos recibidos a String
                String receivedMessage = new String(recibir_paquete.getData(), 0, recibir_paquete.getLength());
                System.out.println("Mensaje recibido desde " + clientAddress + ":" + clientPort + ": " + receivedMessage);

                //Mandar respuesta a cliente
                 String responseMessage = "Mensaje recibido!";
                 byte[] sendData = responseMessage.getBytes();
                 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                 server_socket.send(sendPacket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
