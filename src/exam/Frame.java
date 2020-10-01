package exam;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame implements UpdatedFrame {

	private final FrameGroup group;

	public Frame(FrameGroup group) {
		this.group = group;

		setLayout(new BorderLayout()); // TODO
		initContent();
		initFrameSettings();
	}

	private void initContent() {

	}

	private void initFrameSettings() {
		setTitle("FRAME-TITLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		// order is important for the following settings!!!
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void invokeChange(String c) {
		group.updateFrames(c);
	}

	@Override
	public void receiveChange(String c) {
		System.out.println("receiveChange: " + c);
	}
}
