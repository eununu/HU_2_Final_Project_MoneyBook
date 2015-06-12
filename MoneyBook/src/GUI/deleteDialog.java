package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Vo.AttendeeData;

class deleteDialog extends JDialog
{
	/*method 5
	 *1 : 삭제할 keyword 입력 후 list만들기
	 *2 : 삭제될 list 선택 후 정보저장
	 *3 : 삭제
	 *4 : 변경된 전체 HashMap return
	 *5 : 변경전 금액 return
	*/
	
	JTextField text = new JTextField(20);
	JButton btn = new JButton("삭제");
	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
	JList dlist= new JList();
	JPanel sp = new JPanel();
	Double omoney = 0.0;
	
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> deleteItem = new ArrayList<AttendeeData>();
	
	deleteDialog(pHistory pHistory)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("삭제");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		
		//item 입력 받고 그 item이 포함된 list 만들기
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();

				DefaultListModel dlistModel = new DefaultListModel();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							dlistModel.addElement(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney());
						}
					}
				}
				dlist.setModel(dlistModel);
			}
		});
		
		JScrollPane spb = new JScrollPane(dlist);
		add(spb,BorderLayout.CENTER);
	
		//삭제될 item 선택 후 정보저장
		dlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				k = (String) dlist.getSelectedValue();
				if(k!= null) klist = k.split("/");
			}
		});
		
		sp.add(btn);
		add(sp,BorderLayout.SOUTH);
		setSize(400,300);
		
		//삭제
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0) 
				{
					deleteItem = hm.get(klist[0]);
					for(int i=0; i<deleteItem.size();i++)
					{
						if(deleteItem.get(i).getMemo().equals(klist[1]) && deleteItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							omoney= Double.parseDouble(klist[2]);
							deleteItem.remove(i);
							break;
						}
					}
					if(deleteItem.size()> 0) hm.put(klist[0],deleteItem);
					else hm.remove(klist[0]);
				}
				setVisible(false);
				dispose();
			}
		});
	}
	
	HashMap<String,ArrayList<AttendeeData>> showDialog(HashMap<String,ArrayList<AttendeeData>> hm)
	{
		this.hm = hm;
		setVisible(true);
		return hm;
	}	
	
	Double getOriginalMoney()
	{
		return omoney;
	}
}
