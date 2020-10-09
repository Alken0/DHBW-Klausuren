package corona;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoronaWarnTerm extends JFrame implements CoronaWarnClient {
	private final JPhone phone;
	private WarnStatus status = WarnStatus.UNKNOWN;
	private final List<Token> receivedTokens = new ArrayList<>();
	private final List<Token> ownTokens;

	private final JLabel statusLabel = new JLabel();
	private final JLabel tokenInfo = new JLabel();
	private final JPanel contentPanel = new JPanel();

	public CoronaWarnTerm(JPhone phone) {
		this.phone = phone;
		ownTokens = CoronaWarn.loadTokens(phone);

		setLayout(new GridLayout(2, 1));
		initComponents();
		initFrameSettings();

		generateNewToken();
	}

	private void initComponents() {
		statusLabel.setOpaque(true);
		updateStatusLabel();
		statusLabel.setPreferredSize(new Dimension(0, 100));
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		add(statusLabel);

		contentPanel.setLayout(new GridLayout(5, 1));

		var newToken = new JButton("New Token");
		newToken.addActionListener(a -> generateNewToken());
		contentPanel.add(newToken);

		var checkForInfections = new JButton("Check for infections");
		checkForInfections.addActionListener(a -> checkStatus());
		contentPanel.add(checkForInfections);

		var clearTokens = new JButton("Clear tokens");
		clearTokens.addActionListener(a -> clearTokens());
		contentPanel.add(clearTokens);

		var reportInfection = new JButton("Report infection");
		reportInfection.addActionListener(a -> setSelfInfected());
		contentPanel.add(reportInfection);

		updateTokenInfo();
		contentPanel.add(tokenInfo);

		add(contentPanel);
	}

	private void setSelfInfected() {
		status = WarnStatus.INFECTED;
		CoronaWarnAPI.reportInfection(this);
		updateStatusLabel();
		disableAllButtons();
	}

	private void disableAllButtons() {
		for (var c : contentPanel.getComponents()) {
			if (c.getClass().equals(JButton.class)) {
				c.setEnabled(false);
			}
		}
	}

	private void clearTokens() {
		ownTokens.clear();
		receivedTokens.clear();
		CoronaWarn.clearTokenStore(phone);
		generateNewToken();
	}

	private void checkStatus() {
		if (CoronaWarnAPI.checkInfection(this)) {
			status = WarnStatus.ALARM;
		} else {
			status = WarnStatus.OK;
		}
		updateStatusLabel();
	}

	private void updateStatusLabel() {
		statusLabel.setText(status.getText());
		statusLabel.setBackground(status.getColor());
	}

	private void generateNewToken() {
		var token = new Token();
		ownTokens.add(token);
		CoronaWarnAPI.sendToken(this);
		CoronaWarn.saveToken(phone, token);
		updateTokenInfo();
	}

	private void updateTokenInfo() {
		tokenInfo.setToolTipText(getCurrentToken() == null ? "" : getCurrentToken().toString());
		tokenInfo.setText("Seen Tokens: " + receivedTokens.size());
	}

	private void initFrameSettings() {
		setTitle(phone.getOwner());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		// order is important for the following settings!!!
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public Token getCurrentToken() {
		try {
			return ownTokens.get(ownTokens.size() - 1);
		} catch (IndexOutOfBoundsException ignored) {
			return null;
		}
	}

	@Override
	public List<Token> getAllTokens() {
		return ownTokens;
	}

	@Override
	public List<Token> getAllSeenTokens() {
		return receivedTokens;
	}

	@Override
	public void tokenReceived(Token token) {
		receivedTokens.add(token);
	}
}
