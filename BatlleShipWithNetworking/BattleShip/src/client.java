

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class client extends JFrame implements Runnable{
	
	Socket socket;
	JTextArea ta;
	JButton send, logout,scor;
	JTextField tf;
	
	Thread thread;
	
	DataInputStream din;
	DataOutputStream dout;
	
	String LoginName;
	
	client(String login) throws UnknownHostException, IOException{
		super(login);
		LoginName=login;
		ta = new JTextArea(20,50);
		tf=new JTextField(50);
		send=new JButton("Send");
		logout=new JButton("Logout");
		scor=new JButton("Show Score");
		
		
		
		
		send.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(LoginName+ " "+"DATA "+tf.getText().toString());
					tf.setText("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		

				logout.addActionListener(new ActionListener(){

			
					
					
					
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(LoginName+ " "+"LOGOUT"+tf.getText().toString());
					System.exit(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
				
				
				
		socket = new Socket("localhost", 5217);
		din=new DataInputStream(socket.getInputStream());
		dout=new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(LoginName);
		dout.writeUTF(LoginName+" "+"LOGIN");
		
		thread=new Thread(this);
		thread.start();
		setup();
		
	}

	private void setup() {
		// TODO Auto-generated method stub
		setSize(600, 500);
		JPanel panel=new JPanel();
		panel.add(new JScrollPane(ta));
		panel.add(tf);
		panel.add(send);
		panel.add(logout);
		panel.add(scor);
		add(panel);
		
		setVisible(true);
	}

	
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				ta.append("\n" + din.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	//test purpose
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		client client=new client("User2");
	}
	
	

}
