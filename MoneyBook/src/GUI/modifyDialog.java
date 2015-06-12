package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Vo.AttendeeData;

class modifyDialog extends JDialog
{
	JTextField text = new JTextField(20);
	JButton btn = new JButton("수정");
	String item;
	HashMap<String,ArrayList<AttendeeData>> hm;
	String k;
	String klist[];
	
	JList mlist = new JList();
	JPanel sp = new JPanel(new BorderLayout(5,5));
	JPanel scp = new JPanel(new GridLayout(3,2));
	JLabel lada = new JLabel("날짜");
	JLabel lamo = new JLabel("금액");
	JLabel lame = new JLabel("메모");
	JTextField date = new JTextField(20);
	JTextField money = new JTextField(20);
	JTextField memo = new JTextField(20);
	JPanel ssp = new JPanel();
	Double omoney = 0.0;
	Double cmoney = 0.0;
	
	ArrayList<AttendeeData> changeItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> checkItem = new ArrayList<AttendeeData>();
	ArrayList<AttendeeData> originItem = new ArrayList<AttendeeData>();
	AttendeeData modifyItem = new AttendeeData();
	
	modifyDialog(pHistory pHistory)
	{
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("수정");
		setLayout(new BorderLayout(5,5));
		add(text,BorderLayout.NORTH);
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = text.getText();
				Set<String> keys = hm.keySet();
				Iterator<String> it = keys.iterator();

				DefaultListModel mlistModel = new DefaultListModel();
				
				while(it.hasNext()) {
					String d = it.next();
					checkItem = hm.get(d);
					for(int i=0; i<checkItem.size(); i++)
					{
						if(item!=null && checkItem.get(i).getMemo().indexOf(item)!=-1) 
						{
							mlistModel.addElement(d + "/" + checkItem.get(i).getMemo() + "/" + checkItem.get(i).getMoney());
						}
					}
				}
				mlist.setModel(mlistModel);
			}
		});
		
		JScrollPane spb = new JScrollPane(mlist);

		add(spb,BorderLayout.CENTER);
	
		mlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				k = (String) mlist.getSelectedValue();
				if(k!= null) klist = k.split("/");
			}
		});
		
		scp.add(lada);
		scp.add(date);
		scp.add(lamo);
		scp.add(money);
		scp.add(lame);
		scp.add(memo);
		ssp.add(btn);
		
		sp.add(scp,BorderLayout.CENTER);
		sp.add(ssp,BorderLayout.SOUTH);
		
		add(sp,BorderLayout.SOUTH);
		setSize(400,300);
		
		//klist : 수정할 아이템의 정보 
		//klist[0]: 날짜 / klist[1]: 메모 / klist[2]: 금액
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0 && item!= null) 
				{
					originItem = hm.get(klist[0]); //그날 산 아이템 list
					
					for(int i=0; i<originItem.size();i++)
					{
						if(originItem.get(i).getMemo().equals(klist[1]) && originItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							//그날 산 것 중 i번째 아이템 수정
							omoney= Double.parseDouble(klist[2]);
							cmoney= Double.parseDouble(money.getText());
							
							modifyItem.setDate(date.getText());
							modifyItem.setMoney(Double.parseDouble(money.getText()));
							modifyItem.setMemo(memo.getText());
							modifyItem.setType(originItem.get(i).getType());
							modifyItem.setIo(originItem.get(i).getIo());
							
							originItem.remove(i);
							
							System.out.println(klist[0] + klist[1] + klist[2]);
							System.out.println(modifyItem.getDate() + modifyItem.getMoney() + modifyItem.getMemo());
							System.out.println(originItem.size());
							//지우고도 다른 아이템이 있으면 list에서 안지움
							if(originItem.size()> 0) hm.put(klist[0], originItem);
							else hm.remove(klist[0]);
							
							if(hm.containsKey(modifyItem.getDate()))
								changeItem = hm.get(modifyItem.getDate());
							
							changeItem.add(modifyItem);
							
							hm.put(date.getText(),changeItem);
							System.out.println();
							break;
						}
					}				
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
	
	Double getChangedMoney()
	{
		return cmoney;
	}
}