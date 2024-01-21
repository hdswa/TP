package simulator.control;

public class ComparisonException extends Exception {

	private static final long serialVersionUID = -8943985273521923691L;
	
	public ComparisonException() {
		super(String.format("Boodies are not equal."));
	}

	public ComparisonException(int step) {
		super(String.format("Boodies are not equal, wrong at step " + step));
	}

}
