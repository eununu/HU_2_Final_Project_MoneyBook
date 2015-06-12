package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Vo.AttendeeData;

class searchDialog extends JDialog
{
	/*method 2
	 *1 : 검색할 keyword 입력 list 출력
	 *2 : 검색종료
	*/
	
	JTextField text = new JTextField(20);
	JTextArea ta = new JTextArea(5,20);
	JButton btn = new JButton("종료");

	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
		
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> deleteItem = new ArrayList<AttendeeData>();
	
	searchDialog(pHistory pHistory, HashMap<String,ArrayList<AttendeeData>> hm)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		this.hm = hm;
		setTitle("검색");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		
		//검색할 item 입력받은 후 정보 출력
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				ta.setText("");
				
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							ta.append(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney()+"\n");
						}
					}
				}
				ta.setEditable(false);
			}
		});
		
		JScrollPane spb = new JScrollPane(ta);

		add(spb,BorderLayout.CENTER);
		add(btn,BorderLayout.SOUTH);
		setSize(400,300);
		
		//검색종료
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}	
}