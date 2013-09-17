package com.cqhot.api.data;

import java.util.Arrays;

public class TopListEntity {
	public class Top{
		public String id;
		public String title;
		public String link;
		public String image;
		@Override
		public String toString() {
			return "Top [id=" + id + ", title=" + title + ", link=" + link
					+ ", image=" + image + "]";
		}
		
	}
	private Top[] top;
	public Top[] getTop() {
		return top;
	}
	public void setTop(Top[] top) {
		this.top = top;
	}
	@Override
	public String toString() {
		return "TopListEntity [top=" + Arrays.toString(top) + "]";
	}
	
}
