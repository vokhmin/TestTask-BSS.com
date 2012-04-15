package net.vokhmin.testtask.bss.model;

import java.sql.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Report.class)
public class Report__ {	
	
	public static volatile SingularAttribute<Report, Long> id;
	public static volatile SingularAttribute<Report, Date> startDate;
	public static volatile SingularAttribute<Report, Date> endDate;
	public static volatile SingularAttribute<Report, String> performer;
	public static volatile SingularAttribute<Report, String> activity;
	
}
