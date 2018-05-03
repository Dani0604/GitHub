package TankGame;


public enum State implements StateEventListener {
	
	MainMenu{

		@Override
		public State onEventHostGame() {	
			return HostGame;
		}
		@Override
		public State onEventJoinGame() {
			return JoinGame;
		}

		@Override
		public State onEventStartGame() {
			return this;
		}

		@Override
		public State onEventCancel() {
			return this;
		}

		@Override
		public State onEventExit() {
			return this;
		}

	},
	HostGame {

		@Override
		public State onEventHostGame() {
			return this;
		}

		@Override
		public State onEventJoinGame() {
			return this;
		}

		@Override
		public State onEventStartGame() {
			return LobbyHost;
		}

		@Override
		public State onEventCancel() {
			return MainMenu;
		}

		@Override
		public State onEventExit() {
			return this;
		}

	},
	JoinGame {

		@Override
		public State onEventHostGame() {
			return this;
		}

		@Override
		public State onEventJoinGame() {
			return this;
		}

		@Override
		public State onEventStartGame() {
			//TODO
			return Game;
		}

		@Override
		public State onEventCancel() {
			return MainMenu;
		}

		@Override
		public State onEventExit() {
			return this;
		}

	},
	LobbyHost{

		@Override
		public State onEventHostGame() {
			return this;
		}

		@Override
		public State onEventJoinGame() {
			return this;
		}

		@Override
		public State onEventStartGame() {
			return this;
		}

		@Override
		public State onEventCancel() {
			return MainMenu;
		}

		@Override
		public State onEventExit() {
			return this;
		}
		
	},
	Lobby{

		@Override
		public State onEventHostGame() {
			return this;
		}

		@Override
		public State onEventJoinGame() {
			return this;
		}

	

		@Override
		public State onEventStartGame() {
			return this;
		}

		@Override
		public State onEventCancel() {
			return this;
		}

		@Override
		public State onEventExit() {
			return this;
		}
	},
	Ready{

		@Override
		public State onEventHostGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventJoinGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventStartGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventCancel() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventExit() {
			System.exit(0);
			return this;
		}
		
	},
	Settings {

		@Override
		public State onEventHostGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventJoinGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventStartGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventCancel() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventExit() {
			// TODO Auto-generated method stub
			return this;
		}

	
	},
	Game{

		@Override
		public State onEventHostGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventJoinGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventStartGame() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventCancel() {
			// TODO Auto-generated method stub
			return this;
		}

		@Override
		public State onEventExit() {
			return this;
		}

	
	};
	
}

