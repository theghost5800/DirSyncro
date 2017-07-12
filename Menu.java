package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import ProgramManage.MainThread;
import java.awt.Color;
import ProgramManage.Manager;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.JLabel;

public class Menu {
	public static ArrayList<String> logOperations;
	private JFrame frame;
	protected ExecutorService manageThread;
	protected Manager manager;
	protected boolean firstRun;
	public static JTextArea textArea;
	private JScrollPane p;
	public static DateFormat sdf;
	public static Date date;
	//private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	ExecutorService manageThread = null;
			      //  Manager manager = null;

					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		//ExecutorService manageThread=null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logOperations= new ArrayList<>();
		manager = new Manager();
		manageThread = Executors.newSingleThreadExecutor();
		firstRun = true;
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 date = new Date();
		JButton btnStart = new JButton("Start");
		btnStart.setToolTipText("Use this to start synchronization.");
		
		btnStart.setBounds(217, 11, 112, 33);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//MainThread.twain();
				//ExecutorService  manageThread = Executors.newSingleThreadExecutor();
			       
//manageThread=
				if (firstRun) {
					manageThread.execute(manager);
					firstRun = false;
				}else {
					manager = new Manager();
					manageThread = Executors.newSingleThreadExecutor();
					manageThread.execute(manager);
				}
				
				
			//	manager.setShutdown(false);
				System.out.println(manageThread.isShutdown());
			        

			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnStart);
		
		JButton stop = new JButton("Stop");
		stop.setToolTipText("Use this to stop the synchronization.");
	
		stop.setBounds(49, 11, 112, 33);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			      
			      
				manager.setShutdown(true);
		        manageThread.shutdown();
		        System.out.println( manageThread.isShutdown());
		        
		       // System.out.println(manageThread.isTerminated());
			}
		});
		stop.setBackground(null);
		frame.getContentPane().add(stop);
		
		 textArea = new JTextArea();
		 textArea.setBackground(new Color(255, 250, 240));
		textArea.setBounds(23, 78, 568, 172);
		 p= new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		p.setBounds(23,78,568,172);
		 //j.add(js);
		//JPanel j=new JPanel();
		//j.setBounds(23,78,568,172);
		 //frame.add(textArea);
		frame.getContentPane().add(p);
		////frame.add(js);
		//textArea.setLineWrap(true);
	    textArea.setEditable(false);
	    
	    JLabel lblLastActionsWith = new JLabel("Last actions with files :");
	    lblLastActionsWith.setBounds(26, 53, 135, 14);
	    frame.getContentPane().add(lblLastActionsWith);
	 textArea.setVisible(true);
	//p.setVisible(true);
		frame.setVisible (true);
		
		//		
		
		//for(String st:logOperations){
//		textArea.append(st+"\n");
//		}
		
		
	}
}
