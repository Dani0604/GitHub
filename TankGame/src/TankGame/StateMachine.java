package TankGame;

import GUI_Pack.GUI;

public class StateMachine implements StateEventListener {
	public State currentState = State.MainMenu;
	private GUI gui;
	private GameControl gctrl;


	void setGui(GUI g){
		gui = g;
	}

	@Override
	public State onEventHostGame() {
		// TODO Auto-generated method stub
		switch (currentState){
		case MainMenu:
			gui.mainmenu.frm.setVisible(false);
			gui.hostGame.frm.setVisible(true);
			break;
		default:
			break;
		}
		currentState = currentState.onEventHostGame();
		return currentState;
	}

	@Override
	public State onEventJoinGame() {
		// TODO Auto-generated method stub
		switch (currentState){
		case MainMenu:
			gui.mainmenu.frm.setVisible(false);
			gui.joinGame.frm.setVisible(true);
			break;
		default:
			break;
		}
		currentState = currentState.onEventJoinGame();
		return currentState;
	}

	@Override
	public State onEventStartGame() {
		// TODO Auto-generated method stub
		switch (currentState){
		case HostGame:
			gui.hostGame.frm.setVisible(false);
			gui.lobby.frm.setVisible(true);
			gui.lobby.btnStart.setVisible(true);
			gctrl = new GameControl(this);
			//gctrl.startGame();
			gctrl.startServer();
			gui.startClient("localhost");
			break;
		case LobbyHost:
			if (gctrl.players.size() > 1){
				currentState = State.Game;
				gctrl.startGame();
			}
			break;
		case JoinGame:
			gui.startClient(gui.joinGame.textField_2.getText());
			gui.lobby.frm.setVisible(true);
			gui.joinGame.frm.setVisible(false);
			gui.lobby.btnStart.setVisible(false);
			break;
		default:
			break;
		}
		currentState = currentState.onEventStartGame();
		return currentState;
	}

	@Override
	public State onEventCancel() {
		switch (currentState){
		case HostGame:
			gctrl.closeServer();
			gui.closeClient();
			gui.mainmenu.frm.setVisible(true);
			gui.hostGame.frm.setVisible(false);
			break;
		case JoinGame:
			gui.closeClient();
			gui.mainmenu.frm.setVisible(true);
			gui.joinGame.frm.setVisible(false);
			break;
		case LobbyHost:
			gctrl.closeServer();
			gui.closeClient();
			gui.mainmenu.frm.setVisible(true);
			gui.lobby.frm.setVisible(false);
			break;
		default:
			break;
		}
		currentState = currentState.onEventCancel();
		return currentState;
	}

	@Override
	public State onEventExit() {
		// TODO Auto-generated method stub
		System.exit(0);
		currentState = currentState.onEventExit();
		return currentState;
	}
}


