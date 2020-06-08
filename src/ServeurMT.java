import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {
    int nbClients;
    private int nombreSecret;
    private boolean fin;
    private String gagnant;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(234);
            nombreSecret =(int)(Math.random()*1000);
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
                 pw.println("Devinez le nombre secret entre 0 et 1000")

                 while (true){
                     String req;
                     while((req=br.readLine())!=null){
                         System.out.println(IP+"a envoyé:"+req);
                         int nb=Integer.parseInt(req);
                         if(fin==false){
                             if(nb<nombreSecret){
                                 pw.println("votre nombre est plus petit")
                             }
                             else if(nb>nombreSecret){
                                 pw.println("votre nombre est plus grand")
                             }
                             else{
                                 gagnant = IP;
                                 fin = true;
                                 pw.println("Bravo, vous avez gagné...")
                             }
                         }else{
                             pw.println("Le jeu est terminé, le gagnant est :"+gagnant);
                             System.out.println("**********");
                             System.out.println("Bravo" +IP);
                             System.out.println("**********");

                         }

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


