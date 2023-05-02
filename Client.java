import jdk.swing.interop.SwingInterOpUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client{
    public static void main(String argv[]) throws Exception {

        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Connection established");



        outToServer.writeBytes("Balcova.csv,Bornova.csv,Gaziemir.csv,Karabaglar.csv,Karsiyaka.csv,Konak.csv,Seferihisar.csv,Urla.csv" +'\n');
        outToServer.flush();
        modifiedSentence = inFromServer.readLine();
        System.out.println( modifiedSentence);
        String selection = inFromUser.readLine();
        outToServer.writeBytes(selection + '\n');
        String answer= inFromServer.readLine();
        System.out.println(answer);

        String ThreadAnswer = inFromServer.readLine();
        String ThreadAnswer2 = inFromServer.readLine();
        String ThreadAnswer3 = inFromServer.readLine();
        System.out.println(ThreadAnswer);
        System.out.println(ThreadAnswer2);
        System.out.println(ThreadAnswer3);






        clientSocket.close();
    }

}

