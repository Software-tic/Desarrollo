package com.zyos.core.common.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RowModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value0;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private String value6;
	private String value7;
	private String value8;
	private String value9;
	private String value10;

	/**
	 * Method for getting the value list without the mail that is the first
	 * column
	 * 
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<String> getValueList() throws Exception {
		try {
			ArrayList<String> vl = new ArrayList<String>();
			for (int i = 1; i < 10; i++) {
				String v = this.getValueRowAsIndex(i);
				if (v != null)
					vl.add(v);
				else
					break;
			}
			return vl;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Method for adding a value about the selected row
	 * 
	 * @param index
	 * @param value
	 * @throws Exception 
	 */
	public void setValueRow(int index, String value) throws Exception {
		try {
			if (index == 0) {
				this.value0 = value;
			} else if (index == 1) {
				this.value1 = value;
			} else if (index == 2) {
				this.value2 = value;
			} else if (index == 3) {
				this.value3 = value;
			} else if (index == 4) {
				this.value4 = value;
			} else if (index == 5) {
				this.value5 = value;
			} else if (index == 6) {
				this.value6 = value;
			} else if (index == 7) {
				this.value7 = value;
			} else if (index == 8) {
				this.value8 = value;
			} else if (index == 9) {
				this.value9 = value;
			} else if (index == 10) {
				this.value9 = value;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Method for getting the value about the selected index
	 * 
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public String getValueRowAsIndex(int index) throws Exception {
		try {
			if (index == 0) {
				return this.value0;
			} else if (index == 1) {
				return this.value1;
			} else if (index == 2) {
				return this.value2;
			} else if (index == 3) {
				return this.value3;
			} else if (index == 4) {
				return this.value4;
			} else if (index == 5) {
				return this.value5;
			} else if (index == 6) {
				return this.value6;
			} else if (index == 7) {
				return this.value7;
			} else if (index == 8) {
				return this.value8;
			} else if (index == 9) {
				return this.value9;
			} else if (index == 10) {
				return this.value10;
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public String getValue0() {
		return value0;
	}

	public void setValue0(String value0) {
		this.value0 = value0;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public String getValue6() {
		return value6;
	}

	public void setValue6(String value6) {
		this.value6 = value6;
	}

	public String getValue7() {
		return value7;
	}

	public void setValue7(String value7) {
		this.value7 = value7;
	}

	public String getValue8() {
		return value8;
	}

	public void setValue8(String value8) {
		this.value8 = value8;
	}

	public String getValue9() {
		return value9;
	}

	public void setValue9(String value9) {
		this.value9 = value9;
	}

	public String getValue10() {
		return value10;
	}

	public void setValue10(String value10) {
		this.value10 = value10;
	}

}
