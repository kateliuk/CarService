package com.cqhot.api.data;

import java.util.Arrays;

public class CarTypeEntity {
	public class Type{
		public String typeid;
		public String type;
		public String link;
		

		@Override
		public String toString() {
			return "Type [typeid=" + typeid + ", type=" + type + ", link="
					+ link + "]";
		}
		
	}
		private Type[] type;
		
		public Type[] getType() {
			return type;
		}

		public void setType(Type[] type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "CarTypeEntity [type=" + Arrays.toString(type) + "]";
		}
		
	}
