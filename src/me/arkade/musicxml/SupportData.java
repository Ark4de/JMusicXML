package me.arkade.musicxml;

import java.util.HashMap;

/**
 * <p>A class containing a <i>supports</i> element's type and attributes.</p>
 * @author Ark4de
 *
 */
public class SupportData {
	private boolean type;
	private HashMap<String, String> attributes = new HashMap<String, String>();
	
	/**
	 * <p>Constructor for SupportData.</p>
	 * @param type Required by MusicXML specification. <code>true</code> for yes, <code>false</code> for no.
	 */
	public SupportData(boolean type) {
		this.type = type;
	}
	
	/**
	 * <p>Returns the type set. Stored in MusicXML as yes or no instead of true or false.</p>
	 * @return A boolean; <code>true</code> for yes, <code>false</code> for no.
	 */
	public boolean getType() {
		return this.type;
	}
	
	/**
	 * <p>Sets the type. Stored in MusicXML as yes or no instead of true or false.</p>
	 * @param type A boolean of the type. <code>true</code> for yes, <code>false</code> for no.
	 */
	public void setType(boolean type) {
		this.type = type;
	}
	
	/**
	 * <p>Gets all of the attributes.</p>
	 * @return A {@link HashMap} containing all of the attributes, with the name as the key and the attribute values as the value.
	 */
	public HashMap<String, String> getAllAttributes() {
		return this.attributes;
	}
	
	/**
	 * <p>Gets the value of an attribute.</p>
	 * @param attribute The name of the attribute to get.
	 * @return A String containing the value of the attribute if it exists, or null if the attribute does not exist.
	 */
	public String getAttributeValue(String attribute) {
		return attributes.get(attribute);
	}
	
	/**
	 * <p>Sets an attribute to a specific value.</p>
	 * <p>An attribute can only have one value, so setting an attribute's value twice will overwrite the previous one.</p>
	 * @param attribute The attribute to be modified.
	 * @param value The value of the aforementioned attribute.
	 */
	public void setAttributeValue(String attribute, String value) {
		this.attributes.put(attribute, value);
	}
}
