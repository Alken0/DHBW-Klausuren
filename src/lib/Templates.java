package lib;

import javax.swing.*;

public class Templates {

	public static void ui_actionListener() {
		// action listener on textarea invokes on enter
		var button = new JButton();
		button.addActionListener(e -> {
			System.out.println("action");
			System.out.println("performed");
		});
	}

	public static void threadsRunnableAndLambda() {
		// do not forget ".start()"!
		Runnable run = () -> {
			System.out.println(System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis());
		};
		new Thread(run).start();
	}

	public static void threadsOnlyLambda() {
		// do not forget ".start()"!
		new Thread(() -> {
			System.out.println(System.currentTimeMillis());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis());
		}).start();
	}
}
