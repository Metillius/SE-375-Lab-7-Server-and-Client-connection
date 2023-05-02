import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.StringWriter;


class PassengerThread implements Runnable {
    // I added final keyword to these so they are not changeable
    private final String filename;
    private final HashMap<String, int[]> districtPassengerCount;
    private PrintWriter printWriter;

    public PassengerThread(String s, HashMap<String, int[]> districtPassengerCount, PrintWriter printWriter) {
        this.filename = s;
        this.districtPassengerCount = districtPassengerCount;
        this.printWriter = printWriter;
    }

    public void run() {

        ArrayList<String> CaptainKiddArr = new ArrayList<String>();


        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename ));
            String line = reader.readLine();

            int passengerNumber = 0;
            int tripNumber = 0;
            double passengerPerTrip =0;


            while ((line = reader.readLine()) != null) {

                String[] x = line.split(",");
                int trips = Integer.parseInt(x[2]);
                int passengers = Integer.parseInt(x[3]);

                tripNumber += trips;
                passengerNumber += passengers;


            }

            passengerPerTrip = passengerNumber/tripNumber;


            CaptainKiddArr.add(String.valueOf(passengerNumber));
            CaptainKiddArr.add(String.valueOf(tripNumber));


            CaptainKiddArr.add(String.valueOf(passengerPerTrip));


            StringWriter stringWriter = new StringWriter();
            stringWriter.write("Total number of passengers: " + passengerNumber);
            stringWriter.write("Total number of trips: " + tripNumber) ;
            stringWriter.write("Average number of passengers per trip: " + passengerPerTrip);
            String message = stringWriter.toString();
            printWriter.println(message);

            System.out.println("Total number of passengers: " + passengerNumber );
            System.out.println("Total number of trips: " + tripNumber );
            System.out.println("Average number of passengers per trip: " + passengerPerTrip);


            //int[] counts = new int[] {passengerNumber, (int) Math.round(averagePassengers)};
            //districtPassengerCount.put(filename, counts);

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




        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Awaiting for connection");
            System.out.println(" ");
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


                System.out.println("Received file: " + destinationList.get(i));




            /*  Thread tx = new Thread(new PassengerThread(destinationList.get(i), districtPassengerCount));
                tx.start();
                tx.join();

             */
            }

            System.out.println();
            System.out.println("File Processing done. Awaiting for query...");



            //



            outToClient.writeBytes("Select a district name to server to see the stats: (Type exit to quit)" + '\n');
            System.out.println();
            clientSentence = inFromClient.readLine();
            String reformedClientSentence = clientSentence + ".csv";
            System.out.println("Received query: "+ reformedClientSentence);
            if (destinationList.contains(reformedClientSentence)){

                System.out.println("Yippie-yi-o");
                outToClient.writeBytes("Select a district name to server to see the stats: (Type exit to quit)" + '\n');
                System.out.println();

                PrintWriter printWriter = new PrintWriter(connectionSocket.getOutputStream(), true);

                Thread tx = new Thread(new PassengerThread(destinationList.get(5), districtPassengerCount,  printWriter));
                tx.start();
                tx.join();

            }

            else if (clientSentence == "exit"){

            }

            else {
                System.out.println("Invalid district :(((");
            }
        }
    }
}

