import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("J'attends la connexion d'un client");
            Socket s= ss.accept();
            InputStream is=s.getInputStream();
            OutputStream os = s.getOutputStream();
            System.out.println("J'attends un nombre");
            int nb = is.read();
            int rep =  nb*8;
            System.out.println("J'envoie la réponse");
            os.write(rep);
            s.close();
        } catch (IOException e){
            e.printStackTrace();
        }



    }
}
