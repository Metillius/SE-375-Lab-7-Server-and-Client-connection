import jdk.swing.interop.SwingInterOpUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Client{
    public static void main(String argv[]) throws Exception {

        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Connection established");


        ArrayList<String> destinationList = new ArrayList<>();
        destinationList.add("Balcova");
        destinationList.add("Bornova");
        destinationList.add("Gaziemir");
        destinationList.add("Karabaglar");
        destinationList.add("Karsiyaka");
        destinationList.add("Konak");
        destinationList.add("Balcova");
        destinationList.add("Seferihisar");
        destinationList.add("Urla");


        outToServer.writeBytes("Balcova.csv,Bornova.csv,Gaziemir.csv,Karabaglar.csv,Karsiyaka.csv,Konak.csv,Seferihisar.csv,Urla.csv" +'\n');
        outToServer.flush();
        modifiedSentence = inFromServer.readLine();

        System.out.println( modifiedSentence);
        String selection = inFromUser.readLine();
        while(!selection.equals("exit") ){


            outToServer.writeBytes(selection + '\n');
            String answer= inFromServer.readLine();
            System.out.println(answer);



            if(destinationList.contains(selection)){

                String ThreadAnswer = inFromServer.readLine();
                String ThreadAnswer2 = inFromServer.readLine();
                String ThreadAnswer3 = inFromServer.readLine();

                System.out.println(ThreadAnswer);
                System.out.println(ThreadAnswer2);
                System.out.println(ThreadAnswer3);
            }

            else {
                System.out.println("Invalid entry");
            }



            modifiedSentence = inFromServer.readLine();

            System.out.println( modifiedSentence);
            selection = inFromUser.readLine();




        }
        if (modifiedSentence == "exit"){
            System.out.println("Thank you for using the system...");
        }

    /*    else{
            System.out.println("Invalid entry");
        }

     */





        clientSocket.close();
    }

}

