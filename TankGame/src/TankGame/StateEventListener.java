package TankGame;



public interface StateEventListener {
	State onEventHostGame();
	State onEventJoinGame();
	State onEventStartGame();
	State onEventCancel();
	State onEventExit();
}