package practice;

public class Airport {
	private String city;
	private String country;
	private String code3;

	public Airport() {
		System.out.println("RUNNING");
	}
	
	public String getCity() {
		return this.city;
	}

	public String getCountry() {
		return this.country;
	}
	
	public String getCode3() {
		return this.code3;
	}
	
	public static String findAirportsCodeBS(String toFind, Airport[] airports) {
		int low = 0;
		int high = airports.length - 1;
		int mid;
		
		while(low <= high) {
			//mid = (low+high)/2;
			//if low and high are very big low + high is very big it causes "overflow"
			//a number so big it cannot be represented. so we use this(down) instead
			mid = low + ((high-low)/2);
			int compare = toFind.compareTo(airports[mid].getCity());
			if(compare < 0) {
				high = mid - 1;
			}
			else if(compare > 0) {
				low = mid + 1;
			}
			else {
				return airports[mid].getCode3();
			}
		}
		return null;
	}
}
