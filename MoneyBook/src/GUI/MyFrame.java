package GUI;

import javax.swing.*;

import Vo.AttendeeData;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Vo.AttendeeData;
import Action.ExcelHandle;

public class MyFrame extends JFrame
{
	public pHistory jpanel01 = null;
    public pMonth jpanel02 = null;
    public pCategory jpanel03 = null;
    static HashMap<String,ArrayList<AttendeeData>> hm = new HashMap();
    static ArrayList<AttendeeData> data = new ArrayList<AttendeeData>();
    
    MyFrame()
    {
    	ExcelHandle eh= new ExcelHandle();
		
    	JMenuBar mb= new JMenuBar();
    	JMenu menu= new JMenu("File");
    	JMenuItem menuOpen= new JMenuItem("open");
    	JMenuItem menuSave= new JMenuItem("save");
    	JMenuItem menuExit= new JMenuItem("exit");
    	
		MyOpenActionListener listenerOpen= new MyOpenActionListener();
//		MySaveActionListener listenerSave= new MySaveActionListener();
		menu.add(menuOpen);
		menu.add(menuSave);
		menu.add(menuExit);
		menuOpen.addActionListener(listenerOpen);
//		menuSave.addActionListener(listenerSave);
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{	
				System.exit(0);
			}
		});
		mb.add(menu);
		setJMenuBar(mb);
		
		setTitle("Money Book");
		hm = eh.ExcelParser();
		
		jpanel01 = new pHistory(hm);
		jpanel02 = new pMonth();
        jpanel03 = new pCategory();
        
        JTabbedPane jtab = new JTabbedPane();   //  JTabbedPane  객체 생성
        
        jtab.addTab("내역", jpanel01 );
        jtab.addTab("월별", jpanel02 );
        jtab.addTab("분류", jpanel03 );
        
        add(jtab);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,800);
        setVisible(true);
    }
    
    class MyOpenActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			File f;
			ExcelHandle eh= new ExcelHandle();
			
			JFileChooser fc= new JFileChooser();
			int answer= fc.showOpenDialog(null);
			if(answer==JFileChooser.APPROVE_OPTION)
			{
				f= fc.getSelectedFile();
			/*	try {
					hm = eh.ExcelParser(f);
					new MyFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
		}
	}

/*	class MySaveActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			File f;
			JFileChooser fc= new JFileChooser();
			int answer= fc.showSaveDialog(null);
			if(answer== JFileChooser.APPROVE_OPTION)
			{
				f= fc.getSelectedFile();
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(new FileWriter(f));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for(int i=0;i<u.size();i++)
				{
					pw.printf("%s %s %d : %d %s\r\n"
							,u.get(i).date,u.get(i).homeTeamName,u.get(i).homeScore,u.get(i).awayScore,u.get(i).awayTeamName);
				}
				pw.close();
			}
		}
	}*/

    public static void main(String[] args) 
    {
        new MyFrame();
    }
}

