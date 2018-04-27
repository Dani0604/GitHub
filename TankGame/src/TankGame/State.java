package TankGame;

import TankGame.GUI.PeriodicPlayerUpdater;

interface StateEventListener {
	void onEventHostGame(MainControl mctrl);
	void onEventJoinGame(MainControl mctrl);
	void onEventStartGame(MainControl mctrl);
	void onEventCancel(MainControl mctrl);
	void onEventExit(MainControl mctrl);
}


enum State implements StateEventListener {
	MainMenu {

		@Override
		public void onEventHostGame(MainControl mctrl) {
			// TODO Auto-generated method stub
			mctrl.gctrl = new GameControl(mctrl);
			mctrl.gui.startClient("localhost");
		}
		@Override
		public void onEventJoinGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventStartGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventCancel(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventExit(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

	},
	HostGame {

		@Override
		public void onEventHostGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventJoinGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventStartGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventCancel(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventExit(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

	},
	JoinGame {

		@Override
		public void onEventHostGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventJoinGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventStartGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventCancel(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventExit(MainControl mctrl) {
			// TODO Auto-generated method stub

		}
	},
	Settings {

		@Override
		public void onEventHostGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventJoinGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventStartGame(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventCancel(MainControl mctrl) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEventExit(MainControl mctrl) {
			// TODO Auto-generated method stub

		}
	}
}


class StateMachine implements StateEventListener {
	State currentState;

	@Override
	public void onEventHostGame(MainControl mctrl) {
		// TODO Auto-generated method stub
		currentState.onEventHostGame(mctrl);
	}

	@Override
	public void onEventJoinGame(MainControl mctrl) {
		// TODO Auto-generated method stub
		currentState.onEventJoinGame(mctrl);
	}

	@Override
	public void onEventStartGame(MainControl mctrl) {
		// TODO Auto-generated method stub
		currentState.onEventStartGame(mctrl);
	}

	@Override
	public void onEventCancel(MainControl mctrl) {
		// TODO Auto-generated method stub
		currentState.onEventCancel(mctrl);
	}

	@Override
	public void onEventExit(MainControl mctrl) {
		// TODO Auto-generated method stub
		currentState.onEventExit(mctrl);
	}
}


