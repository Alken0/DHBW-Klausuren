package snatChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SnatChatWindow extends JFrame implements SnatChatFrontend {

	private SnatChatRoom room;
	private Account account;
	
	private ChatMessagesComponent msgComponent = new ChatMessagesComponent();
	
	public SnatChatWindow(SnatChatRoom room, Account account) {
		super(account.getName() + " (" + room.getRoomName() + ")");
		this.room = room;
		this.account = account;
		
//		this.setTitle(account.getName() + " (" + room.getRoomName() + ")"); // gleichwertig wie super-Aufruf
		
		this.setLayout(new BorderLayout());
		
		// ueberschrift mit namen
		JLabel lblUsername = new JLabel(account.getName(), JLabel.CENTER);
		lblUsername.setForeground(account.getColor());
		this.add(lblUsername, BorderLayout.NORTH);
		
		
		// chatnachrichten und radio buttons
		JPanel panChatMsgAndRadioButtons = new JPanel(new BorderLayout());
		panChatMsgAndRadioButtons.add(msgComponent, BorderLayout.CENTER);
		
		JPanel panRadioButtons = new JPanel(); // default: flow layout
		ButtonGroup radioGroup = new ButtonGroup();
		for ( State state : State.values() ) {
			JRadioButton radio = new JRadioButton(state.getLabel());
			radioGroup.add(radio);
			panRadioButtons.add(radio);
			// initialer status
			if ( state == account.getState() ) {
				radio.setSelected(true);
			}
			
			radio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String msg = "State of user '" + account.getName() + "' is now '" + state.getLabel() + "'";
					room.sendMessage(msg);
					account.setState(state);
				}
			});
		}
		panChatMsgAndRadioButtons.add(panRadioButtons, BorderLayout.SOUTH);

		this.add(panChatMsgAndRadioButtons, BorderLayout.CENTER);
		
		// eingabefeld und send button
		JPanel panInput = new JPanel(new BorderLayout());
		JTextField txtInput = new JTextField();
		JButton btnSend = new JButton("Send");
		panInput.add(txtInput, BorderLayout.CENTER);
		panInput.add(btnSend, BorderLayout.EAST);
		
		ActionListener sendListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = txtInput.getText();
				if ( txt.isEmpty() ) { // txt.equals("") auch ok!
					JOptionPane.showMessageDialog(panInput, String.format("Dear %s, please enter a message", account.getName()));
				} else {
					Message msg = new Message(txt, account);
					room.sendMessage(msg);
					txtInput.setText("");
				}
			}
		};
		btnSend.addActionListener(sendListener);
		txtInput.addActionListener(sendListener); // Zusatzpunkt :-)
		
		this.add(panInput, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 200);
		
		this.setVisible(true);
	}

	@Override
	public void receiveMessage(Message msg) {
		JLabel lblText = new JLabel(msg.getSender().getName() + ": " + msg.getText());
		lblText.setForeground(msg.getSender().getColor());
		addMessageLabel(lblText);
	}

	@Override
	public void receiveMessage(String text) {
		JLabel lblText = new JLabel(text);
		lblText.setForeground(Color.GRAY);
		addMessageLabel(lblText);
	}
	
	private void addMessageLabel(JLabel lbl) {
		String origText = lbl.getText();
		this.msgComponent.add(lbl);
		
		Runnable countdown = new Runnable() {
			
			@Override
			public void run() {
				int rest = 30;
				while ( rest > 0 ) {
					lbl.setText(origText + " [ " + rest + " ]");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					rest--;
				}
				msgComponent.remove(lbl);
			}
		};
		new Thread(countdown).start();
	}

	@Override
	public Account getAccount() {
		return account;
	}

}
