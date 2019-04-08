package com.cargotracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "series_table")
public class SeriesTable {

	private String name;
	private String prefix;
	private int nextSeries;

	@Id
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "next_series")
	public int getNextSeries() {
		return nextSeries;
	}

	public void setNextSeries(int nextSeries) {
		this.nextSeries = nextSeries;
	}

	@Column(name = "prefix")
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
