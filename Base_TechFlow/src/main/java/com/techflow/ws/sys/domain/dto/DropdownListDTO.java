package com.techflow.ws.sys.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DropdownListDTO implements Serializable {
	
	public static final Integer DROPDOWNLIST=1;
	public static final Integer DROPDOWNSELECT=2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5557507183235367272L;
	private String value;
	private String text;	// Dropdown list
	private String label;	// Vue-select - Dropdown com multipla seleção
	private Boolean disabled;
	
	// Status
	private Integer nextStatusId;
	private Boolean selectAttendant;
	private Boolean scheduleDate;
	private List<Integer> parent;
	
	private String className;
	private String variant;
	
	public DropdownListDTO(Integer type, String value, String text) {
		if(type == DROPDOWNSELECT) {
			this.value = value;
			this.label = text;
		}
		else {
			this.value = value;
			this.text = text;
		}
	}
	
	public DropdownListDTO(String value, String text, Boolean disabled) {
		this.value = value;
		this.text = text;
		this.disabled = disabled;
	}
	
	public DropdownListDTO(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public DropdownListDTO(String value, String text, String label) {
		this.value = value;
		this.text = text;
		this.label = label;
	}
	
	public DropdownListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getNextStatusId() {
		return nextStatusId;
	}

	public void setNextStatusId(Integer nextStatusId) {
		this.nextStatusId = nextStatusId;
	}

	public Boolean getSelectAttendant() {
		return selectAttendant;
	}

	public void setSelectAttendant(Boolean selectAttendant) {
		this.selectAttendant = selectAttendant;
	}

	public Boolean getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Boolean scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public List<Integer> getParent() {
		return parent;
	}

	public void setParent(List<Integer> parent) {
		this.parent = parent;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}
	
	
	
}
