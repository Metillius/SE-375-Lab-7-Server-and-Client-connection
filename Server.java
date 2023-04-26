import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;


class PassengerThread implements Runnable {
    // I added final keyword to these so they are not changeable
    private final String filename;
    private final HashMap<String, int[]> districtPassengerCount;

    public PassengerThread(String s, HashMap<String, int[]> districtPassengerCount) {
        this.filename = s;
        this.districtPassengerCount = districtPassengerCount;
    }

    public void run() {

        ArrayList<String> CaptainKiddArr = new ArrayList<String>();


        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename ));
            String line = reader.readLine();

            int passengerNumber = 0;
            int tripNumber = 0;
            int days = 0;

            while ((line = reader.readLine()) != null) {

                String[] x = line.split(",");
                int trips = Integer.parseInt(x[2]);
                int passengers = Integer.parseInt(x[3]);

                tripNumber += trips;
                passengerNumber += passengers;
                days++;
            }

            double averagePassengers = (double) passengerNumber / days;

            CaptainKiddArr.add(String.valueOf(passengerNumber));
            CaptainKiddArr.add(String.valueOf(averagePassengers));


            System.out.println(filename + " Total Passengers: " + passengerNumber + " Avg Passengers: "+ averagePassengers);


            int[] counts = new int[] {passengerNumber, (int) Math.round(averagePassengers)};
            districtPassengerCount.put(filename, counts);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        String clientSentence;
        String capitalizedSentence;




        ServerSocket welcomeSocket = new ServerSocket(193);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Awaiting for connection");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            System.out.println("Connection Established");

            String input = inFromClient.readLine() + '\n';;

            String[] destinations = input.split(",");

            ArrayList<String> destinationList = new ArrayList<>();

            for (String destination : destinations) {
                destinationList.add(destination.trim());
            }

            HashMap<String, int[]> districtPassengerCount = new HashMap<>();

            for (int i = 0; i < destinationList.size(); i++) {



                Thread tx = new Thread(new PassengerThread(destinationList.get(i), districtPassengerCount));
                tx.start();
                tx.join();
            }




      //          outToClient.writeBytes(clientSentence); // outToClient.writeBytes(capitalizedSentence);
        }
    }
}
