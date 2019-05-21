import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;


public class CM_Client extends JFrame implements ActionListener
{
	JPanel contentPane, panel_Main, panel_Chat, panel_Exam, panel_Canvas, panel_Option;
	JButton btn_Ready, btn_Exit, btn_Color1, btn_Color2, btn_Color3, btn_Color4, btn_Color5, btn_Erase, btn_EraseAll, btn_GG;
	JLabel label_Canvas, label_Exam, label_Exam_Sub, label_Timer, label_Client1, label_Client2, label_Client3, label_Client4;
	Label label_Client1_Sub, label_Client2_Sub, label_Client3_Sub, label_Client4_Sub;
	JTextField textField;
	JTextArea textArea;
	JScrollPane scrollPane;
	Canvas canvas;
	Color color;
	Graphics g;
	Graphics2D g2d;
	
	int port = 7777;
	String playerName, playerScore, playerIdx;
	boolean gameStart, auth;
	
	public CM_Client(){
		// �⺻ ����
		setFont(new Font("�����ٸ����", Font.PLAIN, 13));
		setVisible(true);
		setResizable(false);
		setTitle("JAVA CatchMind Client Ver.181017");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
		
		// ���̽� �г�
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		panel_Main = new JPanel();
		panel_Main.setFont(new Font("�����ٸ����", Font.PLAIN, 13));
		panel_Main.setBackground(new Color(228, 242, 254));
		contentPane.add(panel_Main);
		panel_Main.setLayout(null);
		
		// ������ ��� ����
		JPanel panel_ClientList = new JPanel();
		panel_ClientList.setBackground(new Color(228, 242, 254));
		panel_ClientList.setBorder(new LineBorder(new Color(127, 219, 254), 4, true));
		panel_ClientList.setBounds(10, 105, 156, 500);
		panel_Main.add(panel_ClientList);
		panel_ClientList.setLayout(null);
		
		label_Client1 = new JLabel(new ImageIcon("image\\p0.png"));
		label_Client1.setBackground(Color.GRAY);
		label_Client1.setBounds(18, 15, 120, 80);
		panel_ClientList.add(label_Client1);
		
		label_Client1_Sub = new Label("[ �г��� & ���� ]");
		label_Client1_Sub.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		label_Client1_Sub.setAlignment(Label.CENTER);
		label_Client1_Sub.setBackground(Color.WHITE);
		label_Client1_Sub.setBounds(18, 95, 120, 30);
		panel_ClientList.add(label_Client1_Sub);
		
		label_Client2 = new JLabel(new ImageIcon("image\\p0.png"));
		label_Client2.setBackground(Color.GRAY);
		label_Client2.setBounds(18, 135, 120, 80);
		panel_ClientList.add(label_Client2);
		
		label_Client2_Sub = new Label("[ �г��� & ���� ]");
		label_Client2_Sub.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		label_Client2_Sub.setAlignment(Label.CENTER);
		label_Client2_Sub.setBackground(Color.WHITE);
		label_Client2_Sub.setBounds(18, 215, 120, 30);
		panel_ClientList.add(label_Client2_Sub);
		
		label_Client3 = new JLabel(new ImageIcon("image\\p0.png"));
		label_Client3.setBackground(Color.GRAY);
		label_Client3.setBounds(18, 255, 120, 80);
		panel_ClientList.add(label_Client3);
		
		label_Client3_Sub = new Label("[ �г��� & ���� ]");
		label_Client3_Sub.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		label_Client3_Sub.setAlignment(Label.CENTER);
		label_Client3_Sub.setBackground(Color.WHITE);
		label_Client3_Sub.setBounds(18, 335, 120, 30);
		panel_ClientList.add(label_Client3_Sub);
		
		label_Client4 = new JLabel(new ImageIcon("image\\p0.png"));
		label_Client4.setBackground(Color.GRAY);
		label_Client4.setBounds(18, 375, 120, 80);
		panel_ClientList.add(label_Client4);
		
		label_Client4_Sub = new Label("[ �г��� & ���� ]");
		label_Client4_Sub.setFont(new Font("�����ٸ����", Font.BOLD, 13));
		label_Client4_Sub.setAlignment(Label.CENTER);
		label_Client4_Sub.setBackground(Color.WHITE);
		label_Client4_Sub.setBounds(18, 455, 120, 30);
		panel_ClientList.add(label_Client4_Sub);
		
		// ���� ���� ����
		panel_Exam = new JPanel();
		panel_Exam.setBounds(10, 10, 1245, 85);
		panel_Main.add(panel_Exam);
		panel_Exam.setLayout(null);
		
		JLabel label_Exam_Back = new JLabel(new ImageIcon("image\\exam_bg.png"));
		label_Exam_Back.setOpaque(true);
		label_Exam_Back.setBounds(0, 0, 1245, 85);
		panel_Exam.add(label_Exam_Back);
		panel_Exam.setLayout(null);
		
		label_Exam = new JLabel(new ImageIcon("image\\exam.png"));
		label_Exam.setOpaque(false);
		label_Exam.setBounds(166, 10, 803, 65);
		label_Exam_Back.add(label_Exam);
		
		label_Exam_Sub = new JLabel();
		label_Exam_Sub.setOpaque(false);
		label_Exam_Sub.setBounds(45, 0, 803, 65);
		label_Exam_Sub.setFont(new Font("�����ٸ����", Font.PLAIN, 24));
		label_Exam_Sub.setForeground(Color.BLACK);
		label_Exam_Sub.setHorizontalAlignment(SwingConstants.CENTER);
		label_Exam.add(label_Exam_Sub);
		
		// ���� ��ư ����
		btn_Ready = new JButton(new ImageIcon("image\\ready.png"));
		btn_Ready.setFocusPainted(false);
		btn_Ready.setBorderPainted(false);
		btn_Ready.setContentAreaFilled(false);
		btn_Ready.setBounds(991, 11, 115, 65);
		label_Exam_Back.add(btn_Ready);
		
		btn_Exit = new JButton(new ImageIcon("image\\close.png"));
		btn_Exit.setPressedIcon(new ImageIcon("image\\close_clicked.png"));
		btn_Exit.setFocusPainted(false);
		btn_Exit.setBorderPainted(false);
		btn_Exit.setContentAreaFilled(false);
		btn_Exit.setBounds(1118, 11, 115, 65);
		label_Exam_Back.add(btn_Exit);
		btn_Exit.addActionListener(this);
		
		// �ΰ� ����
		JLabel label_Logo = new JLabel("�ΰ� ����");
		label_Logo.setOpaque(true);
		label_Logo.setForeground(Color.WHITE);
		label_Logo.setBackground(Color.GRAY);
		label_Logo.setBorder(null);
		label_Logo.setHorizontalAlignment(SwingConstants.CENTER);
		label_Logo.setFont(new Font("�����ٸ����", Font.PLAIN, 20));
		label_Logo.setBounds(12, 10, 142, 65);
		label_Exam_Back.add(label_Logo);
		
		// ä�� ����
		panel_Chat = new JPanel();
		panel_Chat.setBounds(992, 105, 263, 567);
		panel_Main.add(panel_Chat);
		panel_Chat.setLayout(null);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setFont(new Font("�����ٸ����", Font.PLAIN, 13));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 263, 535);
		panel_Chat.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(127, 219, 254), 4, true));
		textArea.setFont(new Font("�����ٸ����", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.WHITE);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(22, 103, 152), 4, true));
		textField.setBackground(Color.WHITE);
		textField.setBounds(0, 537, 263, 30);
		panel_Chat.add(textField);
		textField.setColumns(10);
		
		// ĵ���� ����
		JLabel labell_Canvas_Top = new JLabel(new ImageIcon("image\\canvas.png"));
		labell_Canvas_Top.setBounds(176, 105, 802, 34);
		labell_Canvas_Top.setOpaque(true);
		panel_Main.add(labell_Canvas_Top);
		
		panel_Canvas = new JPanel();
		panel_Canvas.setOpaque(false);
		panel_Canvas.setBounds(177, 105, 800, 500);
		panel_Main.add(panel_Canvas);
		panel_Canvas.setLayout(new BorderLayout(0, 0));

		canvas = new Brush();
		canvas.setBackground(Color.WHITE);
		panel_Canvas.add(canvas, BorderLayout.CENTER);
		CanvasHandler ch = new CanvasHandler(); // ĵ���� �ڵ鷯
		canvas.addMouseMotionListener(ch);
		
		panel_Option = new JPanel();
		panel_Option.setOpaque(true);
		panel_Option.setBackground(Color.WHITE);
		panel_Option.setBounds(10, 615, 967, 57);
		panel_Main.add(panel_Option);
		panel_Option.setLayout(null);
		
		btn_Color1 = new JButton(new ImageIcon("image\\red.png"));
		btn_Color1.setPressedIcon(new ImageIcon("image\\red_clicked.png"));
		btn_Color1.setFocusPainted(false);
		btn_Color1.setContentAreaFilled(false);
		btn_Color1.setBorder(null);
		btn_Color1.setBounds(179, 10, 75, 37);
		panel_Option.add(btn_Color1);
		btn_Color1.addActionListener(ch);
		
		btn_Color2 = new JButton(new ImageIcon("image\\green.png"));
		btn_Color2.setPressedIcon(new ImageIcon("image\\green_clicked.png"));
		btn_Color2.setFocusPainted(false);
		btn_Color2.setContentAreaFilled(false);
		btn_Color2.setBorder(null);
		btn_Color2.setBounds(266, 10, 75, 37);
		panel_Option.add(btn_Color2);
		btn_Color2.addActionListener(ch);
		
		btn_Color3 = new JButton(new ImageIcon("image\\blue.png"));
		btn_Color3.setPressedIcon(new ImageIcon("image\\blue_clicked.png"));
		btn_Color3.setFocusPainted(false);
		btn_Color3.setContentAreaFilled(false);
		btn_Color3.setBorder(null);
		btn_Color3.setBounds(353, 10, 75, 37);
		panel_Option.add(btn_Color3);
		btn_Color3.addActionListener(ch);
		
		btn_Color4 = new JButton(new ImageIcon("image\\yellow.png"));
		btn_Color4.setPressedIcon(new ImageIcon("image\\yellow_clicked.png"));
		btn_Color4.setFocusPainted(false);
		btn_Color4.setContentAreaFilled(false);
		btn_Color4.setBorder(null);
		btn_Color4.setBounds(440, 10, 75, 37);
		panel_Option.add(btn_Color4);
		btn_Color4.addActionListener(ch);
		
		btn_Color5 = new JButton(new ImageIcon("image\\black.png"));
		btn_Color5.setPressedIcon(new ImageIcon("image\\black_clicked.png"));
		btn_Color5.setFocusPainted(false);
		btn_Color5.setContentAreaFilled(false);
		btn_Color5.setBorder(null);
		btn_Color5.setBounds(527, 10, 75, 37);
		panel_Option.add(btn_Color5);
		btn_Color5.addActionListener(ch);
		
		btn_Erase = new JButton(new ImageIcon("image\\erase.png"));
		btn_Erase.setPressedIcon(new ImageIcon("image\\erase_clicked.png"));
		btn_Erase.setFocusPainted(false);
		btn_Erase.setContentAreaFilled(false);
		btn_Erase.setBorder(null);
		btn_Erase.setBounds(641, 10, 60, 37);
		panel_Option.add(btn_Erase);
		btn_Erase.addActionListener(ch);
		
		btn_EraseAll = new JButton(new ImageIcon("image\\eraseall.png"));
		btn_EraseAll.setPressedIcon(new ImageIcon("image\\eraseall_clicked.png"));
		btn_EraseAll.setFocusPainted(false);
		btn_EraseAll.setContentAreaFilled(false);
		btn_EraseAll.setBorder(null);
		btn_EraseAll.setBounds(713, 10, 90, 37);
		panel_Option.add(btn_EraseAll);
		btn_EraseAll.addActionListener(ch);
		
		btn_GG = new JButton(new ImageIcon("image\\gg.png"));
		btn_GG.setPressedIcon(new ImageIcon("image\\gg_clicked.png"));
		btn_GG.setFocusPainted(false);
		btn_GG.setContentAreaFilled(false);
		btn_GG.setBorder(null);
		btn_GG.setBounds(855, 10, 100, 37);
		panel_Option.add(btn_GG);
		
		// Ÿ�̸� ����
		JLabel label_Timer_Back = new JLabel(new ImageIcon("image\\time.png"));
		label_Timer_Back.setOpaque(true);
		label_Timer = new JLabel("00 : 00");
		label_Timer.setHorizontalTextPosition(SwingConstants.CENTER);
		label_Timer.setHorizontalAlignment(SwingConstants.CENTER);
		label_Timer.setFont(new Font("�����ٸ����", Font.PLAIN, 24));
		label_Timer.setForeground(Color.BLACK);
		label_Timer_Back.setBounds(0, 0, 158, 57);
		label_Timer.setBounds(0, 10, 158, 57);
		panel_Option.add(label_Timer_Back);
		label_Timer_Back.add(label_Timer);
		
		startChat();
	}
	
	// ä�� ���� ����
	public void startChat(){
		String nickName = CM_Login.nickName;
		String ip = CM_Login.ip;

		try{
			Socket s = new Socket(ip, port);
			Sender sender = new Sender(s, nickName);
			Listener listener = new Listener(s);
			new Thread(sender).start();
			new Thread(listener).start();
			
			textField.addKeyListener(new Sender(s, nickName));
			btn_Ready.addActionListener(new Sender(s, nickName));
			btn_Color1.addActionListener(new Sender(s, nickName));
			btn_Color2.addActionListener(new Sender(s, nickName));
			btn_Color3.addActionListener(new Sender(s, nickName));
			btn_Color4.addActionListener(new Sender(s, nickName));
			btn_Color5.addActionListener(new Sender(s, nickName));
			btn_Erase.addActionListener(new Sender(s, nickName));
			btn_EraseAll.addActionListener(new Sender(s, nickName));
			btn_GG.addActionListener(new Sender(s, nickName));
			canvas.addMouseMotionListener(new Sender(s, nickName));
		}catch(UnknownHostException uh){
			JOptionPane.showMessageDialog(null, "ȣ��Ʈ�� ã�� �� �����ϴ�!", "ERROR", JOptionPane.WARNING_MESSAGE);
		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "���� ���� ����!\n������ ���� �ִ� �� �����ϴ�.", "ERROR", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}	
	}
	
	// ��ư �׼� �̺�Ʈ ó��
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btn_Exit){
			int select = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "Exit", JOptionPane.OK_CANCEL_OPTION);
			if(select == JOptionPane.YES_OPTION) System.exit(0);
		}
	}
	
	// ���� Ŭ���� - �۽�
	class Sender extends Thread implements KeyListener, ActionListener, MouseMotionListener
	{
		DataOutputStream dos;
		Socket s;
		String nickName;

		Sender(Socket s, String nickName){
			this.s = s;
			try{
				dos = new DataOutputStream(this.s.getOutputStream());
				this.nickName = nickName;
			}catch(IOException io){}
		}

		public void run(){
			try{
				dos.writeUTF(nickName);
			}catch(IOException io){}
		}
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == btn_Ready){ // '�غ�' ��ư
				try{
					dos.writeUTF("//Chat " + "[ " + nickName + " �� �غ� �Ϸ� ! ]");
					dos.flush();
					dos.writeUTF("//Ready");
					dos.flush();
					btn_Ready.setEnabled(false);
				}catch(IOException io){}
			}else if(e.getSource() == btn_Color1){ // ���� ���� ��ư
				try{
					dos.writeUTF("//Color" + "Red");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_Color2){
				try{
					dos.writeUTF("//Color" + "Green");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_Color3){
				try{
					dos.writeUTF("//Color" + "Blue");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_Color4){
				try{
					dos.writeUTF("//Color" + "Yellow");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_Color5){
				try{
					dos.writeUTF("//Color" + "Black");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_Erase){ // '�����' ��ư
				try{
					dos.writeUTF("//Erase");
					dos.flush();
				}catch(IOException io){}
			}else if(e.getSource() == btn_EraseAll){ // '��� �����' ��ư
				try{
					if(auth == true){
						dos.writeUTF("//ErAll");
						dos.flush();
					}
				}catch(IOException io){}
			}else if(e.getSource() == btn_GG){ // '����' ��ư
				try{
					if(auth == true){
						dos.writeUTF("//GmGG ");
						dos.flush();
					}
				}catch(IOException io){}
			}
		}
		
		public void keyReleased(KeyEvent e){ // ä�� �Է�
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String chat = textField.getText();
				textField.setText("");
				try{
					dos.writeUTF("//Chat " + nickName + " : " + chat);
					dos.flush();
				}catch(IOException io){}
			}
		}
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
		
		public void mouseDragged(MouseEvent e){ // ���콺 ��ǥ ����
		    try{
		    	if(auth == true){
		    		int x = e.getX(); int y = e.getY();
		    		dos.writeUTF("//Mouse" + x + "." + y);
		    		dos.flush();
		    	}
		    }catch(IOException io){}
		}
		public void mousePressed(MouseEvent e){}
		public void mouseMoved(MouseEvent e){}
	}

	// ���� Ŭ���� - ����
	class Listener extends Thread
	{
		Socket s;
		DataInputStream dis;

		Listener(Socket s){
			this.s = s;
			try{
				dis = new DataInputStream(this.s.getInputStream());
			}catch(IOException io){}
		}

		public void run(){
			while(dis != null){
				try{
					String msg = dis.readUTF();
					if(msg.startsWith("//CList")){ // ��ɾ� : Ŭ���̾�Ʈ ��� ����
						playerName = msg.substring(7, msg.indexOf(" "));
						playerScore = msg.substring(msg.indexOf(" ") + 1, msg.indexOf("#"));
						playerIdx = msg.substring(msg.indexOf("#") + 1);
						updateClientList();
					}else if(msg.startsWith("//Start")){ // ��ɾ� : ���� ���� (+Ÿ�̸�)
						gameStart = true;
						g = canvas.getGraphics(); // ĵ���� ���� �ʱ�ȭ
						g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						Brush canvas2 = (Brush)canvas;
						canvas2.color = Color.BLACK;
						color = Color.BLACK;
						bgm("//Play"); // BGM ���
					}else if(msg.equals("//GmGG ")){ // ��ɾ� : ���� ����
						gameStart = false;
						auth = false;
						textField.setEnabled(true);
						btn_Ready.setEnabled(true);
						label_Timer.setText("00 : 00");
						bgm("//Stop"); // BGM ����
					}else if(msg.equals("//GmEnd")){
						gameStart = false;
						auth = false;
						textField.setEnabled(true);
						btn_Ready.setEnabled(true);
						label_Timer.setText("00 : 00");
						bgm("//Stop"); // BGM ����
					}else if(msg.startsWith("//RExam")){ // ��ɾ� : ���� ���� ����
						if(auth == true){
							label_Exam_Sub.setText(msg.substring(7));
						}else{
							label_Exam_Sub.setText(" ??? ");
						}
					}else if(msg.startsWith("//Auth ")){ // ��ɾ� : ������ ���� �ο�
						if(CM_Login.nickName.equals(msg.substring(7))){
							auth = true;
							textArea.append("[ ����� ���� �������Դϴ� !! ]" + "\n");
							textField.setEnabled(false);
						}
					}else if(msg.startsWith("//Mouse")){ // ��ɾ� : ĵ���� ����
						if(auth == false){
							int tempX = Integer.parseInt(msg.substring(7, msg.indexOf("."))); 
							int tempY = Integer.parseInt(msg.substring(msg.indexOf(".") + 1));
							g = canvas.getGraphics();
							g2d = (Graphics2D)g;
							g2d.setColor(color);
				            g2d.setStroke(new BasicStroke(6));
				            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		                    g.drawLine(tempX, tempY, tempX, tempY);
						}
					}else if(msg.startsWith("//Timer")){
						label_Timer.setText(msg.substring(7));
					}else if(msg.startsWith("//Color")){ // ��ɾ� : �÷� ����
						String temp = msg.substring(7);
						switch(temp){
							case "Red": color = Color.RED; break;
							case "Green": color = Color.GREEN; break;
							case "Blue": color = Color.BLUE; break;
							case "Yellow": color = Color.YELLOW; break;
							case "Black": color = Color.BLACK; break;
						}
					}else if(msg.equals("//Erase")){ // Ŭ���̾�Ʈ�� ��ɾ� : �����
						color = Color.WHITE;
					}else if(msg.equals("//ErAll")){ // Ŭ���̾�Ʈ�� ��ɾ� : ��� �����
						g = canvas.getGraphics();
						g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					}else{ // ä�� ���
						textArea.append(msg + "\n");
						scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					}
				}catch(IOException io){
					textArea.append("[ �������� ������ �������ų� �г����ߺ�, �����ʰ�, ���ӽ����� �Դϴ�...\nȮ���� �ٽ������� �ּ���\n3�� �� ���α׷��� �����մϴ� .. ]");
					try{
						Thread.sleep(3000);
						System.exit(0);
					}catch(InterruptedException it){}
				}
			}
		}
		
		public void updateClientList(){ // Ŭ���̾�Ʈ ��� �߰�
			ImageIcon ii;
			if(Integer.parseInt(playerIdx) == 0){
				ii = new ImageIcon("image\\p1.png");
				ii.getImage().flush();
				label_Client1.setIcon(ii);
				label_Client1_Sub.setText("[ " + playerName + " / " + "���� : " + playerScore + " ]");
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 1){
				ii = new ImageIcon("image\\p2.png");
				ii.getImage().flush();
				label_Client2.setIcon(ii);
				label_Client2_Sub.setText("[ " + playerName + " / " + "���� : " + playerScore + " ]");
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 2){
				ii = new ImageIcon("image\\p3.png");
				ii.getImage().flush();
				label_Client3.setIcon(ii);
				label_Client3_Sub.setText("[ " + playerName + " / " + "���� : " + playerScore + " ]");
				deleteClientList();
			}else if(Integer.parseInt(playerIdx) == 3){
				ii = new ImageIcon("image\\p4.png");
				ii.getImage().flush();
				label_Client4.setIcon(ii);
				label_Client4_Sub.setText("[ " + playerName + " / " + "���� : " + playerScore + " ]");
				deleteClientList();
			}
		}
		
		public void deleteClientList(){ // Ŭ���̾�Ʈ ��� ����
			ImageIcon ii2;
			ii2 = new ImageIcon("image\\p0.png");
			if(Integer.parseInt(playerIdx) == 0){
				label_Client2.setIcon(ii2);
				label_Client2_Sub.setText("[ �г��� & ���� ]");
				label_Client3.setIcon(ii2);
				label_Client3_Sub.setText("[ �г��� & ���� ]");
				label_Client4.setIcon(ii2);
				label_Client4_Sub.setText("[ �г��� & ���� ]");
			}else if(Integer.parseInt(playerIdx) == 1){
				label_Client3.setIcon(ii2);
				label_Client3_Sub.setText("[ �г��� & ���� ]");
				label_Client4.setIcon(ii2);
				label_Client4_Sub.setText("[ �г��� & ���� ]");
			}else if(Integer.parseInt(playerIdx) == 2){
				label_Client4.setIcon(ii2);
				label_Client4_Sub.setText("[ �г��� & ���� ]");
			}
		}
		
		public void bgm(String play){ // BGM ��� & ����
			new Thread(){ 
				public void run(){	
					try{
						AudioInputStream ais = AudioSystem.getAudioInputStream(new File("bgm\\bgm.wav"));
						Clip clip = AudioSystem.getClip();
						if(play.equals("//Play")){
							clip.stop();
							clip.open(ais);
				            clip.start();
				            clip.loop(Clip.LOOP_CONTINUOUSLY);
						}else if(play.equals("//Stop")){
							System.out.println("����");
							clip.close();
						}
					}catch(Exception e){}
				}
			}.start();
		}
	}

	// ���� Ŭ���� - ĵ���� �ڵ鷯
	class CanvasHandler extends JFrame implements ActionListener, MouseMotionListener
	{	
		int x1, x2, y1, y2;
		public void mouseDragged(MouseEvent e){
		    x2 = e.getX(); y2 = e.getY();
		    ((Brush)canvas).x = x2; ((Brush)canvas).y = y2;
		    canvas.repaint();
		}
		public void mousePressed(MouseEvent e){}
		public void mouseMoved(MouseEvent e){}
		
		public void actionPerformed(ActionEvent e){
			Object obj = e.getSource();
			Brush canvas2 = (Brush)canvas;
		   
			if(auth == true){
			    if(obj == btn_Color1){
				    canvas2.color = Color.RED;
			    }else if(obj == btn_Color2){
			    	canvas2.color = Color.GREEN;
			    }else if(obj == btn_Color3){
			    	canvas2.color = Color.BLUE;
			    }else if(obj == btn_Color4){
			    	canvas2.color = Color.YELLOW;
			    }else if(obj == btn_Color5){
			    	canvas2.color = Color.BLACK;
			    }else if(obj == btn_Erase){
			    	canvas2.color = canvas.getBackground();
			    }else if(obj == btn_EraseAll){
			    	Graphics g = canvas2.getGraphics();
				    g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 
			    }
			}
		}
	}
	
	// ���� Ŭ���� - ĵ���� �귯�� ����
	class Brush extends Canvas
	{
		int x;
		int y;
		Color color = Color.BLACK;

		public void paintComponent(Graphics g){
			if(gameStart == true && auth == true){
				Graphics2D g2d = (Graphics2D)g;
	            g2d.setColor(color);
	            g2d.setStroke(new BasicStroke(6));
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2d.drawLine(x, y, x, y);
			}
		}
		
		public void update(Graphics g){
			paintComponent(g);
		}
	}
}