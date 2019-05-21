package catchmind.main;

import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class CM_Server extends JFrame implements ActionListener
{
	JPanel contentPane, panel_Main, panel_TextArea, panel_Btn;
	JScrollPane scrollPane;
	JTextArea textArea;
	JLabel label_ServerStatus;
	JButton btn_ServerStart;
	JButton btn_ServerClose;
	
	ServerSocket ss;
	Socket s;
	int port = 7777;
	public static final int MAX_CLIENT = 4;
	int readyPlayer;
	int score;
	boolean gameStart;
	String line = "";
	LinkedHashMap<String, DataOutputStream> clientList = new LinkedHashMap<String, DataOutputStream>();
	LinkedHashMap<String, Integer> clientInfo = new LinkedHashMap<String, Integer>();
	
	public void init(){
		setTitle("JAVA CatchMind Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 10, 0));
		
		panel_Main = new JPanel();
		contentPane.add(panel_Main);
		panel_Main.setLayout(new BoxLayout(panel_Main, BoxLayout.Y_AXIS));
		
		label_ServerStatus = new JLabel("[ Server Waiting .. ]");
		label_ServerStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_ServerStatus.setPreferredSize(new Dimension(96, 50));
		panel_Main.add(label_ServerStatus);
		label_ServerStatus.setHorizontalTextPosition(SwingConstants.CENTER);
		label_ServerStatus.setHorizontalAlignment(SwingConstants.CENTER);
		label_ServerStatus.setFont(new Font("�����ٸ����", Font.PLAIN, 20));
		
		panel_TextArea = new JPanel();
		panel_Main.add(panel_TextArea);
		panel_TextArea.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_TextArea.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		panel_Btn = new JPanel();
		panel_Btn.setPreferredSize(new Dimension(10, 43));
		panel_Btn.setAutoscrolls(true);
		panel_Main.add(panel_Btn);
		panel_Btn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btn_ServerStart = new JButton(" ���� ���� ");
		btn_ServerStart.setHorizontalTextPosition(SwingConstants.CENTER);
		btn_ServerStart.setPreferredSize(new Dimension(120, 40));
		btn_ServerStart.setFocusPainted(false);
		btn_ServerStart.setFont(new Font("�����ٸ����", Font.BOLD, 16));
		btn_ServerStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_ServerStart.setForeground(Color.WHITE);
		btn_ServerStart.setBackground(Color.DARK_GRAY);
		btn_ServerStart.setBorder(null);
		panel_Btn.add(btn_ServerStart);
		btn_ServerStart.addActionListener(this);
		
		btn_ServerClose = new JButton(" ���� ���� ");
		btn_ServerClose.setHorizontalTextPosition(SwingConstants.CENTER);
		btn_ServerClose.setPreferredSize(new Dimension(120, 40));
		btn_ServerClose.setFocusPainted(false);
		btn_ServerClose.setFont(new Font("�����ٸ����", Font.BOLD, 16));
		btn_ServerClose.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_ServerClose.setForeground(Color.WHITE);
		btn_ServerClose.setBackground(Color.DARK_GRAY);
		btn_ServerClose.setBorder(null);
		panel_Btn.add(btn_ServerClose);
		btn_ServerClose.addActionListener(this);
		btn_ServerClose.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e){ // '���� ���� & ����' ��ư
		if(e.getSource() == btn_ServerStart){
			new Thread(){
				public void run() {
					try{
						Collections.synchronizedMap(clientList);
						ss = new ServerSocket(port);
						label_ServerStatus.setText("[ Server Started ]");
						textArea.append("[ ������ ���۵Ǿ����ϴ� ]" + "\n");
						btn_ServerStart.setEnabled(false);
						btn_ServerClose.setEnabled(true);
						while(true){
							s = ss.accept();
							if ((clientList.size())+1>MAX_CLIENT || gameStart == true){
								s.close();
							}else{
								Thread gm = new GameManager(s);
								gm.start();
							}
							
						}
					}catch(IOException io){}
				}
			}.start();
		}else if(e.getSource() == btn_ServerClose){
			int select = JOptionPane.showConfirmDialog(null, "������ ���� �����Ͻðڽ��ϱ�?", "JAVA CatchMind Server", JOptionPane.OK_CANCEL_OPTION);
			try{
				if(select == JOptionPane.YES_OPTION){
					ss.close();
					label_ServerStatus.setText("[ Server Closed ]");
					textArea.append("[ ������ ����Ǿ����ϴ� ]" + "\n");
					btn_ServerStart.setEnabled(true);
					btn_ServerClose.setEnabled(false);
				}
			}catch(IOException io){}
		}
	}
	
	public void showSystemMsg(String msg){ // �ý��� �޽��� �� ��ɾ� ��Ʈ��
		Iterator<String> it = clientList.keySet().iterator();
		while(it.hasNext()){
			try{
				DataOutputStream dos = clientList.get(it.next());
				dos.writeUTF(msg);
				dos.flush();
			}catch(IOException io){}
		}
	}


	// ���� Ŭ���� (������ �ý��� �޽���)
	public class GameManager extends Thread
	{
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;
							
		public GameManager(Socket s){
			this.s = s;
			try{
				dis = new DataInputStream(this.s.getInputStream());
				dos = new DataOutputStream(this.s.getOutputStream());
			}catch(IOException io){}
		}
		
		public void run(){
			String clientName = "";
			try{
				clientName = dis.readUTF();
				if(!clientList.containsKey(clientName)){ // �ߺ� �г��� ����
					clientList.put(clientName, dos);
					clientInfo.put(clientName, score);
				}else if(clientList.containsKey(clientName)){
					s.close(); // �г����� �ߺ��϶� ���Ͽ��� �ź�
				}
				showSystemMsg("[ " + clientName + "���� �����ϼ̽��ϴ�. ]\n(���� ������ �� : " + clientList.size() + "�� / 4��)");
				textArea.append("[ ���� ������ ��� (�� " + clientList.size() + "�� ������) ]\n");
				Iterator<String> it1 = clientList.keySet().iterator();
				while(it1.hasNext()) textArea.append(it1.next() + "\n");
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
				
				setClientInfo();
				
				while(dis != null){
					String msg = dis.readUTF();
					filter(msg);
				}
			}catch(IOException io){
				clientList.remove(clientName); clientInfo.remove(clientName); // Ŭ���̾�Ʈ ����� ����
				closeAll();
				showSystemMsg("[ " + clientName + "���� �����ϼ̽��ϴ�. ]\n(���� ������ �� : " + clientList.size() + "�� / 4��)");
				textArea.append("[ ���� ������ ��� (�� " + clientList.size() + "�� ������) ]\n");
				Iterator<String> it1 = clientList.keySet().iterator();
				while(it1.hasNext()) textArea.append(it1.next() + "\n");
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
				
				setClientInfo();
				
				showSystemMsg("//GmEnd"); // Ŭ���̾�Ʈ �����, ��� ���� ����
				readyPlayer = 0; // ���ο� Ŭ���̾�Ʈ�� �����ص� ���� ���ۿ� ������ ������ ���� �ʱ�ȭ
			}
		}
		
		public void closeAll(){
			try{
				if(dos != null) dos.close();
				if(dis != null) dis.close();
				if(s != null) s.close();
			}catch(IOException ie){}
		}
		
		public void setClientInfo(){
			String[] keys = new String[clientInfo.size()];
			int[] values = new int[clientInfo.size()];
			int index = 0;
			for(Map.Entry<String, Integer> mapEntry : clientInfo.entrySet()){
			    keys[index] = mapEntry.getKey();
			    values[index] = mapEntry.getValue();
			    index++;
			}
			for(int i=0; i<clientList.size(); i++){
				showSystemMsg("//CList" + keys[i] + " " + values[i] + "#" + i); // ��ɾ� : Ŭ���̾�Ʈ ��� ����
			}
		}
		
		public void filter(String msg) { // ��ɾ� ����
			String temp = msg.substring(0, 7);
			
			if(temp.equals("//Chat ")){ // ��ɾ� : �Ϲ� ä�� ����
				answerCheck(msg.substring(7).trim());
				showSystemMsg(msg.substring(7));
			}else if(temp.equals("//Ready")){ // ��ɾ� : Ŭ���̾�Ʈ �غ�
				 readyPlayer++;
				 if(readyPlayer >= 2 && readyPlayer == clientList.size()){
					 for(int i=3; i>0; i--){
						 try{
						 	showSystemMsg("[ ��� �����ڵ��� �غ�Ǿ����ϴ�. ]\n[ " + i + "�� �� ������ �����մϴ� .. ]");
						 	Thread.sleep(1000);
						 }catch(InterruptedException ie){}
					 }
					 ArrayList<String> authList = new ArrayList<String>(); // ���� ������ ���� ����
					 Iterator<String> it = clientList.keySet().iterator();
					 while(it.hasNext()) authList.add(it.next());
					 Random rd = new Random();
					 showSystemMsg("//Auth " + authList.get(rd.nextInt(authList.size()))); // ��ɾ� : ���� ������ ���� ����
					 
					 Exam ex = new Exam(); ex.start(); // ���� ����
					 Timer tm = new Timer(); tm.start(); // Ÿ�̸� ����
					 gameStart = true;
					 showSystemMsg("//Start"); // ��ɾ� : ���� ����
				 }
			}else if(temp.equals("//Mouse")){ // ��ɾ� : ���콺 ��ǥ ����
				showSystemMsg(msg);
			}else if(temp.equals("//Color")){ // ��ɾ� : �÷� ����
				showSystemMsg(msg);
			}else if(temp.equals("//Erase")){ // ��ɾ� : �����
				showSystemMsg(msg);
			}else if(temp.equals("//ErAll")){ // ��ɾ� : ��� �����
				showSystemMsg(msg);
			}else if(temp.equals("//GmGG ")){ // ��ɾ� : ���� ���� (�����ڰ� ������ �������� ���)
				showSystemMsg("[ �����ڰ� ������ �����߽��ϴ� !! ]");
				showSystemMsg(msg);
				readyPlayer = 0;
				gameStart = false;
			}else if(temp.equals("//GmEnd")){ // ��ɾ� : ���� ���� (�ð� �ʰ��� ��Ż�� �߻����� ������ ����Ǵ� ���)
				showSystemMsg("[ ������ ����Ǿ����ϴ� !! ]");
				showSystemMsg(msg);
				readyPlayer = 0;
				gameStart = false;
			}
		}
		
		public void answerCheck(String msg){ // ���� üũ
			String tempNick = msg.substring(0, msg.indexOf(" ")); // ������ ���� Ŭ���̾�Ʈ�� �г���
			String tempAns = msg.substring(msg.lastIndexOf(" ") + 1); // ���� ����
			
			if(tempAns.equals(line)){
				showSystemMsg("//GmEnd");
				showSystemMsg("[ " + tempNick + "�� ���� !! ]");
				clientInfo.put(tempNick, clientInfo.get(tempNick) + 1); // ������ ���� �߰�
				readyPlayer = 0; // ���ο� ������ �����ϱ� ���� ���� �ʱ�ȭ
				gameStart = false;
				setClientInfo();
			}
		}
	}
	
	// ���� Ŭ���� - ���� ���� ����
	class Exam extends Thread
	{
		int i = 0;
		BufferedReader br;
		
		public void run(){
			Random r = new Random();
			int n = r.nextInt(30);
			try{
				FileReader fr = new FileReader("wordlist.txt");
				br = new BufferedReader(fr);
				for(i=0;i<=n;i++){
					line = br.readLine();
				}
				showSystemMsg("//RExam" + line);
			}catch(IOException ie){}
		}
	}
	
	// ���� Ŭ���� - Ÿ�̸�
	class Timer extends Thread
	{
		long preTime = System.currentTimeMillis();
		public void run() {
			try{
				while(gameStart == true){
					sleep(10);
					long time = System.currentTimeMillis() - preTime;
					showSystemMsg("//Timer" + (toTime(time)));
					if(toTime(time).equals("00 : 00")){
						showSystemMsg("//GmEnd");
						readyPlayer = 0;
						gameStart = false;
						break;
					}else if(readyPlayer==0){
						break;
					}
				}
			}catch (Exception e){}
		}
		
		String toTime(long time){
			int m = (int)(3-(time / 1000.0 / 60.0));
			int s = (int)(60-(time % (1000.0 * 60) / 1000.0));
			//int ms = (int)(100-(time % 1000 / 10.0));
			return String.format("%02d : %02d ", m, s);
		}
	}
	
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					CM_Server cms = new CM_Server();
					cms.init();
					cms.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}