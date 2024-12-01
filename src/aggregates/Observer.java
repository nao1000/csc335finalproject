package aggregates;

public interface Observer {

	public void updateInfo(String nStr);
	
	public String getName();
}
