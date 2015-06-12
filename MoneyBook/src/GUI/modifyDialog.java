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
	/*method 6
	 *1 : 수정할 keyword 입력 후 list만들기
	 *2 : 수정될 list 선택 후 정보저장
	 *3 : 수정할 정보 저장, 수정
	 *4 : 변경된 전체 HashMap return
	 *5 : 변경전 금액 return
	 *6 : 변경후 금액 return
	*/
	
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
		
		//item 입력 받고 그 item이 포함된 리스트 만들기
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
	
		//수정하기위해 선택된 리스트의 값 저장하기
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
		
		//klist : 수정될 아이템의 정보
		//JTextField : 수정할 아이템 정보 입력 받음
		//날짜: klist[0] -> date
		//메모: klist[1] -> memo
		//금액: klist[2] -> money
		
		//수정
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klist.length> 0 && item!= null) 
				{
					//그날 산 아이템 list
					originItem = hm.get(klist[0]); 
					
					for(int i=0; i<originItem.size();i++)
					{
						if(originItem.get(i).getMemo().equals(klist[1]) && originItem.get(i).getMoney().equals(Double.parseDouble(klist[2])))
						{
							//그날 산 것 중 i번째 아이템 수정
							//omoney: 원래금액, cmoney: 바뀐금액 / 전체 지출금액 계산위해 필요
							
							omoney= Double.parseDouble(klist[2]);
							cmoney= Double.parseDouble(money.getText());
							
							//수정할 아이템 정보 update
							modifyItem.setDate(date.getText());
							modifyItem.setMoney(Double.parseDouble(money.getText()));
							modifyItem.setMemo(memo.getText());
							modifyItem.setType(originItem.get(i).getType());
							modifyItem.setIo(originItem.get(i).getIo());
							
							//수정될 아이템 지우기
							originItem.remove(i);
							
							//지우고도 그날 산 다른 아이템이 있으면 전체 날짜 list에서 안지움
							if(originItem.size()> 0) hm.put(klist[0], originItem);
							else hm.remove(klist[0]);
							
							//수정된 날짜에 다른 것들 샀으면 그 list받아서 거기에 추가
							//아니면 새로운 list 생성
							if(hm.containsKey(modifyItem.getDate()))
								changeItem = hm.get(modifyItem.getDate());
							
							changeItem.add(modifyItem);
							hm.put(date.getText(),changeItem);
							
							break;
						}
					}				
				}
				//Dialog 끝
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