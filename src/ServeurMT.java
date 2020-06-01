import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {
    int nbClients;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(234);
            while (true){
                Socket s = ss.accept();
                ++nbClients;
                new Conversation(s,nbClients).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


     class Conversation extends Thread {
        private Socket socket;
        private int numeroClients;

         public Conversation(Socket socket, int num) {
             super();
             this.socket = socket;
             this.numeroClients = num;
         }

         @Override
         public void run() {
             try {
                 InputStream is = socket.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr);

                 OutputStream os = socket.getOutputStream();
                 PrintWriter pw = new PrintWriter(os,true);

                 String IP=socket.getRemoteSocketAddress().toString();

                 System.out.println("Connexion du client numero"+numeroClients+"IP="+IP);
                 pw.println("Bienvenue, vous êtes le client numéro"+numeroClients);

                 while (true){
                     String req = br.readLine();
                     System.out.println(IP+"a envoyé:"+req);
                     if(req!=null){
                         String rep = "Size="+req.length();
                         pw.println(rep);

                     }


                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }


         }
     }


    public static void main(String[] args){
        new ServeurMT().start();

    }
}


