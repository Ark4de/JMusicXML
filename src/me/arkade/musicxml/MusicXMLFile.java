package me.arkade.musicxml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MusicXMLFile {
	private HashMap<String, ArrayList<String>> creators = new HashMap<String, ArrayList<String>>();
	private HashMap<String, SupportData> supports = new HashMap<String, SupportData>();
	private String movementTitle = "";
	private String software = "";
	private String encodingDate = "";
	
	public MusicXMLFile() {
		setEncodingDate(Calendar.getInstance().getTime());
	}
	
	/**
	 * <p>Sets the title of this arrangement.</p>
	 * <p>The arrangement can only have one title. Setting the title more than once will overwrite the previous title.</p>
	 * @param title New title of the arrangement.
	 */
	public void setMovementTitle(String title) {
		this.movementTitle = title;
	}
	
	/**
	 * <p>Gets the title of this arrangement.</p>
	 * @return A String containing the title of this arrangement.
	 */
	public String getMovementTitle() {
		return this.movementTitle;
	}
	
	/**
	 * <p>Adds a creator for a specific type to this arrangement.</p>
	 * <p>Standard type values are <i>composer</i>, <i>lyricist</i>, and <i>arranger</i>, but type values are not limited to those three.</p>
	 * @param type The type to add the creator value to.
	 * @param value The String containing the creator.
	 */
	public void addCreator(String type, String value) {
		ArrayList<String> list;
		
		if (creators.get(type) == null) {
			// Create new ArrayList for that entry
			list = new ArrayList<String>();
			list.add(value);
			creators.put(type, list);
		} else {
			list = creators.get(type);
			list.add(value);
			creators.put(type, list);
		}
	}
	
	/**
	 * <p>Get's all the creators for a specific type.</p>
	 * <p>To check if the specified type has no creators attached, check if the returned value is <code>null</code>.</p>
	 * @param type A String of the type you want to get all the creators for.
	 * @return Returns an {@link ArrayList} containing all of the creators, or null if there are no creators under that value.
	 */
	public ArrayList<String> getCreators(String type) {
		return creators.get(type);
	}
	
	/**
	 * <p>Gets all of the creator information</p>
	 * <p>The key of the {@link HashMap} is the creator's type. Standard type values are <i>composer</i>, <i>lyricist</i>, and <i>arranger</i>. Other type values may be used.</p>
	 * <p>The value of the HashMap is an ArrayList containing all of the values that were listed as part of the creator's type.</p>
	 * <p>To check if this has no creators added, use {@link HashMap#isEmpty()} instead of checking for <code>null</code>.</p>
	 * @return {@link HashMap} with all of the creator information.
	 */
	public HashMap<String, ArrayList<String>> getAllCreators() {
		return creators;
	}
	
	/**
	 * <p>Sets the software this arrangement was created with.</p>
	 * <p>The arrangement can only have one line describing what software made it. Setting the software more than once will overwrite the previously set one.</p>
	 * @param software A String of what was described above.
	 */
	public void setSoftware(String software) {
		this.software = software;
	}
	
	/**
	 * <p>Returns the software that this arrangement was created with.</p>
	 * @return A String of what was described above.
	 */
	public String getSoftware() {
		return this.software;
	}
	
	/**
	 * <p>Sets the date this file was encoded. Stored in the MusicXML file as yyyy-mm-dd</p>
	 * @param date The date of encoding.
	 */
	public void setEncodingDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.encodingDate = df.format(date);
	}
	
	/**
	 * <p>Sets the date this file was encoded. Stored in the MusicXML file formatted as yyyy-mm-dd</p>
	 * @param year The year of encoding
	 * @param month The month of encoding
	 * @param day The day of the month of encoding
	 */
	public void setEncodingDate(int year, int month, int day) {
		this.encodingDate = year + "-" + month + "-" + day;
	}
	
	/**
	 * <p>Gets the date this file was encoded.</p>
	 * @return The date of encoding, in yyyy-mm-dd format.
	 */
	public String getEncodingDate() {
		return this.encodingDate;
	}
	
	/**
	 * <p>Marks support for a specific feature, described by the element parameter.</p>
	 * @param element The name of the feature to be marked as supported.
	 * @param type Not well described by the MusicXML specification, but it seems to be "yes" or "no" to if the feature is supported.
	 * @return A boolean; <code>false</code> if value is null but attribute is not, <code>true</code> otherwise.
	 */
	public boolean addSupport(String element, boolean type) {
		return addSupport(element, type, null, null);
	}
	
	/**
	 * <p>Marks support for a specific feature, described by the element parameter. Also adds or reassigns and attribute with a specific value.</p>
	 * @param element The name of the feature to be marked as supported.
	 * @param type Not well described by the MusicXML specification, but it seems to be "yes" or "no" to if the feature is supported.
	 * @param attribute Attribute to the supported element. Can be null, but {@link #addSupport(String, boolean)} should be used instead.
	 * @param value Value of the aforementioned attribute. Can be null only if the attribute is null, but {@link #addSupport(String, boolean)} should be used instead.
	 * @return A boolean; <code>false</code> if value is null but attribute is not, <code>true</code> otherwise.
	 */
	public boolean addSupport(String element, boolean type, String attribute, String value) {
		SupportData data;
		if (supports.get(element) == null) {
			// Element hasn't been added yet.
			data = new SupportData(type);
		} else {
			data = supports.get(element);
		}
		
		if (attribute != null) {
			if (value == null) {
				return false;
			}
			
			data.setAttributeValue(attribute, value);
		}
		
		supports.put(element, data);
		
		return true;
	}
	
	/**
	 * <p>Gets all the support data for this arrangement.</p>
	 * @return A HashMap containing all of the SupportData for this arrangement. The key is the element name, the value is the SupportData attached to it.
	 */
	public HashMap<String, SupportData> getAllSupports() {
		return this.supports;
	}
	
	/**
	 * <p>Gets the {@link SupportData} for a specific element.</p>
	 * @param element The name of the element that you request.
	 * @return The SupportData for the aformentioned element.
	 */
	public SupportData getSupport(String element) {
		return this.supports.get(element);
	}
}
