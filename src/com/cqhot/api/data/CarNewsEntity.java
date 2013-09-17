package com.cqhot.api.data;

import java.util.Arrays;

public class CarNewsEntity {
	public class SubClass{
		public String id;
		public String title;
		public String content;
		public String link;
		public String image;
		@Override
		public String toString() {
			return "TouTiao [id=" + id + ", title=" + title + ", content="
					+ content + ", link=" + link + ", image=" + image + "]";
		}
		
	}
		private SubClass[] subclass;
		
		
		public SubClass[] getSubclass() {
			return subclass;
		}


		public void setSubclass(SubClass[] subclass) {
			this.subclass = subclass;
		}


		@Override
		public String toString() {
			return "CarNewsEntity [subclass=" + Arrays.toString(subclass) + "]";
		}
		
}
