

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	
	int count=0; //her client olu�turuduldu�unda gemiler tekrar ekrana yazd�r�l�yor.Bunu engellemek i�in tan�mlad�m.
	             //Ama bu seferde ilk client d���ndaki clientlerde gemiler g�z�km�yor.
	           // sadece ilk clientte gemiler g�z�k�yor.Bu y�zden bu kontrolu kullanmad�m.
	
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
							pOut.writeUTF("Gemiler Ba�ar�l� bir �ekilde yerle�tirildi ! Sat�r ve Sutun numaralar� 0,0 dan ba�lamaktad�r. \n"+area.AreaArrayToString()+"\n �pucu 1 : hedefledi�iniz geminin l�tfen SATIR VE SUTUN bilgisini belirtilen formatta giriniz\n �rne�in ; 1. sat�r ve 2. sutunda bulunan bir gemiyi vurmak i�in 1-2 format�nda ,"
									+ "\n0. sat�r ve 0. sutunda yer alan gemiyi vurmak i�in 0-0 format�nda  giriniz\n�pucu 2 : Vurulan gemi alan�na 9 yaz�lacakt�r\n");
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
								pOut.writeUTF("S�STEM: At�� ba�ar�l� :"+LoginName);
							    pOut.writeUTF("At�� Sonras� Gemilerin Durumu :\n"+area.AreaArrayToString());
							}
							else
							pOut.writeUTF("S�STEM: At�� ba�ar�s�z :"+LoginName);
							
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
