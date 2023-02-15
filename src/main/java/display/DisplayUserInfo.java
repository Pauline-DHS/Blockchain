package display;

import utilisateur.Utilisateur;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class DisplayUserInfo extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private JLabel _usernameLabel;
	private JLabel _userSoldeLabel;
	private Utilisateur _user;
	
	public DisplayUserInfo(Utilisateur user) {
		this._user = user;
		this._usernameLabel = new JLabel(user.getUsername());
		this._userSoldeLabel = new JLabel(": " + Float.toString(user.getSolde()) + "€");
		
		this.add(_usernameLabel);
		this.add(_userSoldeLabel);
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(new Color(200, 200, 200));
	}

	@Override
	public void run() {
		while(true) {
			this._userSoldeLabel.setText(": " + Float.toString(_user.getSolde()) + "€");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.getInstance().addError(e.getMessage());
			}
		}
		
	}
}