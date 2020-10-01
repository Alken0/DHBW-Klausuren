package exam;

import javax.swing.*;

public class MAIN {

	public static void main(String[] args) {
		setLookAndFeel();

		var group = new FrameGroup();
		group.register(new Frame(group));
		group.register(new Frame(group));
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
