package me.arkade.musicxml;

import java.util.ArrayList;
import java.util.HashMap;

public class MusicXMLFile {
	private String movementTitle = "";
	private HashMap<String, ArrayList<String>> creators = new HashMap<String, ArrayList<String>>();
	private String software = "";
	
	protected MusicXMLFile() {
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
}
