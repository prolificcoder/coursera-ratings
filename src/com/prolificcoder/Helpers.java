package com.prolificcoder;

public class Helpers {
	public static Integer average(int upvotes, int downvotes) {
		int total = upvotes + downvotes;
		if (total == 0)
			return 0;
		return (int) (((float)upvotes/total) * 100);
	}
}
