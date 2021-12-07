// package dataExport;

// import java.util.ArrayList;

// public class TagList {

// 	ArrayList<String> firstPartList;
// 	ArrayList<String> secondPartList;

// 	public TagList() {
// 		firstPartList = new ArrayList<String>();
// 		secondPartList = new ArrayList<String>();
// 	}

// 	public void add(String first, String second) {
// 		firstPartList.add(first);
// 		secondPartList.add(second);
// 	}

// 	public String get(Integer list, Integer position){

// 		if (position < firstPartList.size()){
// 			if (list == 1) {
// 				return(firstPartList.get(position));
// 			}
// 			if (list == 2) {
// 				return(secondPartList.get(position));
// 			}
// 		}

// 		return("EMPTY");
// 	}

// 	public Integer getSize() {
// 		return(firstPartList.size());
// 	}
// }
