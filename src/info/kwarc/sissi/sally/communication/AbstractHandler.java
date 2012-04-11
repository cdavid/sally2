package info.kwarc.sissi.sally.communication;

public abstract class AbstractHandler {
	private int id;
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public abstract void sendMessage(String message);
	public abstract void onOpen();
	public abstract void onClose(int code, String message);
	public abstract void onMessage(String message);
	
}
