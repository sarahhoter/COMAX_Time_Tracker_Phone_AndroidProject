package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Bosatnica Ion
 * @since 2013-05-16
 *
 */
public class Branch {

		private String name = null;
		private String code = null;
		private String C = null;


		public Branch() {

		}

		public Branch(JSONObject category) throws JSONException {

			setName(category.getString("Nm"));
			setCode(category.getString("Kod"));
			setC(category.getString("C"));

		}

		public String getC() {
			return C;
		}

		public void setC(String c) {
			C = c;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}


		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Category{");
			sb.append("name='").append(name).append('\'');
			sb.append(", code='").append(code).append('\'');
			sb.append(", C='").append(C).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}


