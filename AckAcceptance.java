import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;


public class AckAcceptance implements Runnable{
DatagramSocket listener;
	public AckAcceptance(DatagramSocket listener) {
	super();
	this.listener = listener;
}
	@Override
	public void run() {
		
		
		try {
			while(true){
			
			
			byte[] ack=new byte[4];
		 	DatagramPacket ACK_packet=new DatagramPacket(ack, ack.length, FTP_Client.iNat_address, 9999);
		 	listener.receive(ACK_packet);
		 	
		 	if(ack[0]==FTP_Client.expected_ack)
	 	     {
	 	      System.out.println("ACK received:"+ack[0]);
	 	      FTP_Client.expected_ack++;
	 	      FTP_Client.base=FTP_Client.base+1;
	 	      FTP_Client.current_packet_no++;
	 	      
	         }
		 	else
		 	{
		 		
		 		FTP_Client.base=FTP_Client.expected_ack;
		 		FTP_Client.next_seq_no=FTP_Client.expected_ack;
		  	}
		}
		 	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}

}
