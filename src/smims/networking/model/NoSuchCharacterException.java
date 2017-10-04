package smims.networking.model;

public class NoSuchCharacterException extends Exception {
	private static final long serialVersionUID = 7187609514831425868L;
	
	public NoSuchCharacterException() {}
	public NoSuchCharacterException(String message) {
		super(message);
	}
	
	public NoSuchCharacterException(Throwable innerException) {
		super(innerException);
	}
	
	public NoSuchCharacterException(String message, Throwable innerException) {
		super(message, innerException);
	}

}
