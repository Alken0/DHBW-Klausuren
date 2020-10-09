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


	private final JLabel tokenInfo = new JLabel();

	public CoronaWarnTerm(JPhone phone) {
		this.phone = phone;
		ownTokens = CoronaWarn.loadTokens(phone);

		setLayout(new GridLayout(2, 1));
		initComponents();
		initFrameSettings();

		generateNewToken();
	}

	private void initComponents() {
		var statusLabel = new JLabel(this.status.getText());
		statusLabel.setOpaque(true);
		statusLabel.setBackground(this.status.getColor());
		statusLabel.setPreferredSize(new Dimension(0, 100));
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		add(statusLabel);

		var contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(5, 1));

		var newToken = new JButton("New Token");
		contentPanel.add(newToken);

		var checkForInfections = new JButton("Check for infections");
		contentPanel.add(checkForInfections);

		var clearTokens = new JButton("Clear tokens");
		contentPanel.add(clearTokens);

		var reportInfection = new JButton("Report infection");
		contentPanel.add(reportInfection);

		updateTokenInfo();
		contentPanel.add(tokenInfo);

		add(contentPanel);
	}

	private void generateNewToken() {
		var token = new Token();
		ownTokens.add(token);
		CoronaWarnAPI.sendToken(this);
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
		return null;
	}

	@Override
	public List<Token> getAllSeenTokens() {
		return null;
	}

	@Override
	public void tokenReceived(Token token) {

	}
}
