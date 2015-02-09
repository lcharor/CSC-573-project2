import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class FTP_Client {
//public static ArrayList<Integer> ACK_LIST=new ArrayList<Integer>();
public static InetAddress iNat_address;
public static int base;
public static int expected_ack;
public static int current_packet_no;
public static long next_seq_no;
public static void main(String args[]) throws IOException{
	 Scanner in = new Scanner(System.in);
	 //Initializing the variables
	  System.out.println("Enter Request");
	  String Request=in.nextLine();
	  String[] FTP_REQUEST=Request.split(" ");
	  String Server_Address=FTP_REQUEST[1];
	  int Server_Port=Integer.parseInt(FTP_REQUEST[2]);
	  String FileName=FTP_REQUEST[3];
	  int N=Integer.parseInt(FTP_REQUEST[4]);
	  final int MSS=Integer.parseInt(FTP_REQUEST[5]);
	  int sequence_no=0;
	  int ACK=0;
	  iNat_address=InetAddress.getByName("LOCALHOST");
	  System.out.println(iNat_address);
	  final DatagramSocket client=new DatagramSocket(9999);
	  Runnable ack_acceptance=new AckAcceptance(client);
	  Thread ackListeningThread=new Thread(ack_acceptance);
	  ackListeningThread.start();
	  final ByteArrayOutputStream baos = new ByteArrayOutputStream(Server_Port);
	  final ObjectOutputStream oos = new ObjectOutputStream(baos);
	  
     //converting file to byte array
      File file=new File(FileName);
      final byte[] file_byte_array=read(file);
      //MAKING UDP PACKET LIST
    int MAX_Packet_No=(file_byte_array.length)/MSS;
    System.out.println(MAX_Packet_No);
    int rcvd_ack=0;
    base=0;next_seq_no=0;current_packet_no=0;expected_ack=0;
    while(expected_ack<=MAX_Packet_No)
      {
    	
          if(next_seq_no<base+N){
    		byte[] data =makepacket(base, next_seq_no, MSS,file_byte_array);
    		DatagramPacket packet = new DatagramPacket(data, data.length, iNat_address, 7735);
    		client.send(packet);
    		System.out.println("packet sent:"+next_seq_no);
    		next_seq_no++;
    		
    		
    	}
       else{
    	    Timer t=new Timer();
	 		TimerTask tt=new TimerTask() {
				@Override
				public void run() {
				System.out.println("Waiting for ACK:"+expected_ack);
				next_seq_no=expected_ack;
				
				}
			};
	 		t.scheduleAtFixedRate(tt,1000,10000);
    	    
	 	    
  	   }
}	
/*while(true){
       byte[] ack=new byte[4];
 	   DatagramPacket ACK_packet=new DatagramPacket(ack, ack.length, iNat_address, 9999);
 	   client.receive(ACK_packet);
 	   if(ack[0]==expected_ack){
 	   System.out.println("ACK received:"+ack[0]);
 	   expected_ack++;
 	   }
    }*/
}
public static byte[] makepacket(int base,long sequence_number,int MSS,byte[] file_byte_array) throws UnsupportedEncodingException{
	
	byte[] data=new byte[MSS+1];
	BigInteger seq=BigInteger.valueOf(sequence_number);
	byte[] seq_no=new byte[4];
	seq_no=seq.toByteArray();
	
	int start=(int) (sequence_number*MSS);
	
	for(int i=start;i<=start+MSS;i++)
	{
		if(i==file_byte_array.length-1)
		break;
		data[i-start]=file_byte_array[i];
		
	}
	
	byte[] combined=new byte[data.length+seq_no.length];
	for (int i = 0; i < combined.length; i++)
	{
	    combined[i] = i < seq_no.length ? seq_no[i] : data[i - seq_no.length];
	}
	return combined;
	}
public static byte[] read(File file) throws IOException {

    
    ByteArrayOutputStream ous = null;
    InputStream ios = null;
    try {
        byte[] buffer = new byte[4096];
        ous = new ByteArrayOutputStream();
        ios = new FileInputStream(file);
        int read = 0;
        while ( (read = ios.read(buffer)) != -1 ) {
            ous.write(buffer, 0, read);
        }
    } finally { 
        try {
             if ( ous != null ) 
                 ous.close();
        } catch ( IOException e) {
        }

        try {
             if ( ios != null ) 
                  ios.close();
        } catch ( IOException e) {
        }
    }
    return ous.toByteArray();
}

}