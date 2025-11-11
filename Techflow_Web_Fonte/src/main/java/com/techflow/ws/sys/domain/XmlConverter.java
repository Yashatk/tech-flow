package com.techflow.ws.sys.domain;

import java.util.ArrayList;
import java.util.List;

public class XmlConverter {

	public static XmlTag convertToXmlTag(XmlTag xmlTag, String xml) {

		char currentChar = ' ';
		// XmlTag xmlTag = new XmlTag();

		// String openTag = "";
		// Boolean isOpenTag = false;

		Boolean inTag = false;
		String currentTag = "";
		String currentValue = "";

		Boolean inParam = false;
		String currentParam = "";

		Boolean inParamValue = false;
		Boolean inQuotedParamValue = false;
		String currentParamValue = "";

		Boolean inComment = false;
		String currentComment = "";

		List<KeyPair> attributes = new ArrayList<KeyPair>();

		for (Integer c = xmlTag.getPointer(); c < xml.length(); c++) {

			currentChar = xml.charAt(c);

			if (!inComment) {
				if (!inQuotedParamValue) {
					if (!inTag) {
						// Esperando por <
						if (currentChar == '<') {
							inTag = true;
							// isOpenTag = true;
							currentValue = currentValue.trim();
						} else {
							currentValue += currentChar;
						}
					} else {
						if (currentChar == '!' && xml.charAt(c - 1) == '<') {
							// Início de comentário
							inComment = true;
							currentComment = "";
							inTag = false;
						} else if (currentChar == '>') {

							if (xml.charAt(c - 1) == '?' && currentTag.startsWith("?")) {

								// Finaliza header
								XmlTag header = new XmlTag();
								header.setKey(currentTag);
								header.setAttributes(attributes);
								xmlTag.addTag(header);

								attributes = new ArrayList<KeyPair>();
								// openTag = "";
								// isOpenTag = false;

							} else {
								// Finaliza Tag
								if (currentTag.startsWith("/") && currentTag.indexOf(xmlTag.getKey()) > 0) {
									
									// Fim da tag (recursiva)
									
									xmlTag.setValue(currentValue);
									xmlTag.setAttributes(attributes);
									xmlTag.setPointer(c);
									return xmlTag;
								} else
								if (xml.charAt(c - 1) == '/') {
								
									// Pode ser necessário comparar com a xmlTag.getKey
									// Encerra tag porém adiciona na Lista de Tags
									XmlTag newTag = new XmlTag();
									newTag.setKey(currentTag);
									newTag.setValue(currentValue.trim());
									newTag.setAttributes(attributes);
									xmlTag.addTag(newTag);

								} else {
									// openTag = currentTag;
									// isOpenTag = true;

									XmlTag newTag = new XmlTag();
									newTag.setKey(currentTag);
									newTag.setValue(currentValue.trim());
									newTag.setAttributes(attributes);
									newTag.setPointer(c + 1);
									// Adiciona tag recursivo
									// Avança o cursor

									XmlTag nextTag = convertToXmlTag(newTag, xml);
									c = nextTag.getPointer();
									xmlTag.addTag(nextTag);
								}
							}

							currentTag = "";
							currentValue = "";
							currentParam = "";
							currentParamValue = "";

							inTag = false;
							inParam = false;
							inParamValue = false;

							attributes = new ArrayList<>();

						} else if (currentChar == ' ' || currentChar == '\r' || currentChar == '\n') {
							inParam = true;
						} else {
							if (inParam) {
								if (currentParam == "") {
									// Esperando parâmetro. Válido até encontrar branco ou final
									if (currentChar != ' ' && currentChar != '\r' && currentChar != '\n') {
										currentParam += currentChar;
									}
								} else if (inParamValue) {
									if (currentChar == '\"') {
										inQuotedParamValue = true;
									} else {
										if (currentChar == ' ' || currentChar == '\r' || currentChar == '\n') {
											attributes.add(new KeyPair(currentParam, currentParamValue));
											inParam = true;
											currentParam = "";
											inQuotedParamValue = false;
											inParamValue = false;
											currentParamValue = "";
										} else {
											currentParamValue += currentChar;
										}
									}

								} else if (currentParam != "") {
									if (currentChar == '=') {
										inParamValue = true;
									} else {
										currentParam += currentChar;
									}
								}

							} else {
								if (attributes.size() <= 0)
									currentTag += currentChar;
							}
						}

					}
				} else {
					// Dentro de parâmetros entre aspas
					if (currentChar == '\"') {
						// Finaliza o parâmetro e o valor

						attributes.add(new KeyPair(currentParam, currentParamValue));

						inParam = false;
						currentParam = "";

						inQuotedParamValue = false;
						inParamValue = false;
						currentParamValue = "";
					} else {
						currentParamValue += currentChar;
					}
				}
			} else {
				if (currentChar == '>') {
					inComment = false;
					xmlTag.setComment(currentComment);
					currentComment = "";
					currentTag = "";
				} else {
					currentComment += currentChar;
				}
			}
		}

		return xmlTag;
	}

}
