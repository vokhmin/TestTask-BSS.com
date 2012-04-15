package net.vokhmin.testtask.bss.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Table Reports:
 * 	ID			Numberic
 * 	StartDate	Date
 * 	EndDate		Date
 * 	Performer	VARCHAR
 * 	Activity	VARCHAR
 * 
 * @author a.vokhmin
 *
 */
@Entity
@Table(name = Report.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = Report.TABLE_NAME,
	indexes = { 
		@org.hibernate.annotations.Index(name = "ix_start_date", columnNames = {Report.COLUMN_START_DATE}),
		@org.hibernate.annotations.Index(name = "ix_end_date", columnNames = {Report.COLUMN_END_DATE}),
		@org.hibernate.annotations.Index(name = "ix_performer", columnNames = {Report.COLUMN_PERFORMER})
	} )
@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class Report implements Serializable {	
	public static final String TABLE_NAME = "reports";
	public static final String COLUMN_START_DATE = "start_date";
	public static final String COLUMN_END_DATE = "end_date";
	public static final String COLUMN_PERFORMER = "performer";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = COLUMN_START_DATE)
	private Date startDate;
	
	@Column(name = COLUMN_END_DATE)
	private Date endDate;
	
	@Column(name = COLUMN_PERFORMER, nullable = false, length = 255)
	private String performer;
	
	@Column(name = "activity")
	private String activity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
}
