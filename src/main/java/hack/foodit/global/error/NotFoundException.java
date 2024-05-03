package hack.foodit.global.error;

public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super("NOT FOUND");
  }

  public NotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
