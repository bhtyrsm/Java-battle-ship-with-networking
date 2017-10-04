

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	
	int count=0; //her client oluþturudulduðunda gemiler tekrar ekrana yazdýrýlýyor.Bunu engellemek için tanýmladým.
	             //Ama bu seferde ilk client dýþýndaki clientlerde gemiler gözükmüyor.
	           // sadece ilk clientte gemiler gözüküyor.Bu yüzden bu kontrolu kullanmadým.
	
	ShipsAndControl area=new ShipsAndControl(); 
	static Vector ClientSockets;
	static Vector LoginNames;
	
	server()throws IOException{
		ServerSocket server=new ServerSocket(5217);
		ClientSockets=new Vector();
		LoginNames = new Vector();
		
		while(true){
			Socket client=server.accept();
			AcceptClient acceptClient=new AcceptClient(client);
		}
	}
	
	
	//test purpose
	public static void main(String[] args) throws IOException{
		
		server client=new server();
	}
	
	
	class AcceptClient extends Thread{
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;
		
		AcceptClient(Socket client)throws IOException{
			ClientSocket=client;
			din=new DataInputStream(ClientSocket.getInputStream());
			dout=new DataOutputStream(ClientSocket.getOutputStream());
			
			String LoginName=din.readUTF();
			LoginNames.add(LoginName);
			ClientSockets.add(ClientSocket);
			
			start();
			
		}
		
		public void run(){
			while (true){
				try {
					String msgFromClient=din.readUTF();
					StringTokenizer st=new StringTokenizer(msgFromClient);
					String LoginName=st.nextToken();
					String MsgType=st.nextToken();
					int lo=-1;
					String msg="";
					while(st.hasMoreTokens()){
						msg=msg+st.nextToken();
					}
					
					if(MsgType.equals("LOGIN")){
						//if(count<1){
							
						//count=1;
						for(int i=0; i<LoginNames.size();i++){
							Socket pSocket=(Socket) ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF("Gemiler Baþarýlý bir þekilde yerleþtirildi ! Satýr ve Sutun numaralarý 0,0 dan baþlamaktadýr. \n"+area.AreaArrayToString()+"\n Ýpucu 1 : hedeflediðiniz geminin lütfen SATIR VE SUTUN bilgisini belirtilen formatta giriniz\n Örneðin ; 1. satýr ve 2. sutunda bulunan bir gemiyi vurmak için 1-2 formatýnda ,"
									+ "\n0. satýr ve 0. sutunda yer alan gemiyi vurmak için 0-0 formatýnda  giriniz\nÝpucu 2 : Vurulan gemi alanýna 9 yazýlacaktýr\n");
						}
						//}
						
					}
					else if(MsgType.equals("LOGOUT")){
						for(int i=0; i<LoginNames.size();i++){
							if(LoginName==LoginNames.elementAt(i))
								lo=i;
							Socket pSocket=(Socket) ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF(LoginName+" has  logged out.");
						}
						if(lo>=0){
							LoginNames.removeElementAt(lo);
							ClientSockets.removeElementAt(lo);
						}
						
					}
					else{
						for(int i=0; i<LoginNames.size();i++){
							Socket pSocket=(Socket) ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF(LoginName+": "+msg);
							if(area.ParseAndhit(msg))
							{
								pOut.writeUTF("SÝSTEM: Atýþ baþarýlý :"+LoginName);
							    pOut.writeUTF("Atýþ Sonrasý Gemilerin Durumu :\n"+area.AreaArrayToString());
							}
							else
							pOut.writeUTF("SÝSTEM: Atýþ baþarýsýz :"+LoginName);
							
						}
					}
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
/*
		public void createArea()
		{
			board=new int [10][10];
			
			
			
			
		}
		*/
		
	}
}
