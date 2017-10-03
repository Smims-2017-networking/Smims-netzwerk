package smims.networking.messages;

public class ExceptionMessage {
	private Exception exception;
	
	protected ExceptionMessage() {}
	
	public ExceptionMessage(Exception exception) {
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
}
