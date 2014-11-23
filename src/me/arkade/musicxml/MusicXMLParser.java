package me.arkade.musicxml;

import java.io.File;
import java.io.FileNotFoundException;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class MusicXMLParser {
	public static void main(String args[]) {
		MusicXMLFile musicXMLFile = null;
		
		try {
			musicXMLFile = MusicXMLParser.parse(new File("Lavender Town.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Title: " + musicXMLFile.getMovementTitle());
		if (!musicXMLFile.getAllCreators().isEmpty()) {
			if (musicXMLFile.getCreators("composer") != null)
				for (String str : musicXMLFile.getCreators("composer"))
					System.out.println("Composer: " + str);
			
			if (musicXMLFile.getCreators("lyricist") != null)
				for (String str : musicXMLFile.getCreators("lyricist"))
					System.out.println("Lyricist: " + str);
			
			if (musicXMLFile.getCreators("arranger") != null)
				for (String str : musicXMLFile.getCreators("arranger"))
					System.out.println("Arranger: " + str);
		} else {
			System.out.println("No creators.");
		}
		System.out.println("Software: " + musicXMLFile.getSoftware());
		System.out.println("Encoding Date: " + musicXMLFile.getEncodingDate());
		if (!musicXMLFile.getAllSupports().isEmpty()) {
			System.out.println("======");
			for (String element : musicXMLFile.getAllSupports().keySet()) {
				SupportData sdata = musicXMLFile.getSupport(element);
				System.out.println("Element: " + element);
				System.out.println("  Type: " + (sdata.getType() ? "yes" : "no"));
				
				if (!sdata.getAllAttributes().isEmpty()) {
					for (String attribute : sdata.getAllAttributes().keySet()) {
						System.out.println("  Attribute: " + attribute);
						System.out.println("    Value: " + sdata.getAttributeValue(attribute));
					}
				}
			}
			System.out.println("======");
		} else {
			System.out.println("No supports.");
		}
	}
	
	public static MusicXMLFile parse(File musicXMLFile) throws FileNotFoundException, MusicXMLParserException {
		if (!musicXMLFile.exists()) {
			throw new FileNotFoundException("Specified MusicXML file does not exist: " + musicXMLFile.getAbsolutePath());
		}
		
		Document doc = null;
		
		try {
			Builder parser = new Builder();
			doc = parser.build(musicXMLFile);
		} catch (Exception ex) {
			throw new MusicXMLParserException(ex);
		}
		
		MusicXMLFile musicXML = new MusicXMLFile();
		
		// score-partwise
		Element scorepartwise = doc.getRootElement();
		if (!scorepartwise.getAttributeValue("version").equalsIgnoreCase("3.0")) {
			// TODO: Add support for other MusicXML versions.
			throw new MusicXMLParserException("MusicXML file must be version 3.0, version detected was: " + scorepartwise.getAttributeValue("version"));
		}
		
		Elements elems = scorepartwise.getChildElements();
		for (int i = 0; i < elems.size(); i++) {
			// TODO: work tag
			// TODO: movement-number tag
			
			Element rootChild = elems.get(i);
			if (rootChild.getLocalName().equalsIgnoreCase("movement-title")) {
				musicXML.setMovementTitle(rootChild.getValue());
			} else if (rootChild.getLocalName().equalsIgnoreCase("identification")) {
				for (int i1 = 0; i1 < rootChild.getChildElements().size(); i1++) {
					Element identChild = rootChild.getChildElements().get(i1);
					
					if (identChild.getLocalName().equalsIgnoreCase("creator")) {
						String type = identChild.getAttributeValue("type");
						
						if (type == null) {
							throw new MusicXMLParserException("Missing 'type' attribute for a creator tag.");
						}
						
						musicXML.addCreator(type, identChild.getValue());
					} else if (identChild.getLocalName().equalsIgnoreCase("encoding")) {
						for (int i2 = 0; i2 < identChild.getChildElements().size(); i2++) {
							Element encodingChild = identChild.getChildElements().get(i2);
							
							if (encodingChild.getLocalName().equalsIgnoreCase("software")) {
								musicXML.setSoftware(encodingChild.getValue());
							} else if (encodingChild.getLocalName().equalsIgnoreCase("encoding-date")) {
								try {
									String[] date = encodingChild.getValue().split("-");
									musicXML.setEncodingDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
								} catch (Exception ex) {
									throw new MusicXMLParserException("Malformed date in 'encoding-date' tag: " + encodingChild.getValue());
								}
							} else if (encodingChild.getLocalName().equalsIgnoreCase("supports")) {
								if (encodingChild.getAttributeValue("element") == null || encodingChild.getAttributeValue("type") == null) {
									throw new MusicXMLParserException("Malformed supports tag. Tag is missing 'element' attribute or 'type' attribute.");
								}
								
								if (encodingChild.getAttributeValue("attribute") != null) {
									boolean good = musicXML.addSupport(encodingChild.getAttributeValue("element"),
														(encodingChild.getAttributeValue("type").equalsIgnoreCase("yes") ? true : false),
														encodingChild.getAttributeValue("attribute"),
														encodingChild.getAttributeValue("value"));
									if (!good) {
										throw new MusicXMLParserException("The 'value' attribute for a 'supports' tag was non-existant, but an 'attribute' attribute existed for that same tag.");
									}
								} else {
									musicXML.addSupport(encodingChild.getAttributeValue("element"), (encodingChild.getAttributeValue("type").equalsIgnoreCase("yes") ? true : false));
								}
							}
							
							// TODO: encoder tag
							// TODO: encoding-description tag
						}
					}
					
					// TODO: rights tag
					// TODO: source tag
					// TODO: relation tag
					// TODO: miscellaneous tag
				}
			}
		}
		
		return musicXML;
	}
	
	public static class MusicXMLParserException extends Exception {
		private static final long serialVersionUID = 1533560061771948528L;

		public MusicXMLParserException(String message) {
			super(message);
		}
		
		public MusicXMLParserException(Exception cause) {
			super(cause);
		}
	}
}
