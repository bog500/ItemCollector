package mc.itemcollector;

public enum ListType {
	
	ALL, MISSING, COLLECTED;

	ListType() {

	}
	
	public static ListType getListType(String s) {
		switch(s.toLowerCase()) {
		case "all":
			return ListType.ALL;
			
		case "collected":
			return ListType.COLLECTED;
			
			default:
				return ListType.ALL;
		}
	}

}
