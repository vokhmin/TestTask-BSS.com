package net.vokhmin.testtask.bss.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Reports class is a simple wrapper around a collection of Reports instances.
 * 
 * @author a.vokhmin
 *
 */
@XmlRootElement(name = "reports")
public class ReportList {

    private Collection<Report> reports = new ArrayList<Report>();

	@XmlElement(name = "report")
    public Collection<Report> getReports()
    {
        return reports;
    }

    public void setReports(Collection<Report> reports)
    {
        this.reports = reports;
    }

	public void addReports(Iterable<Report> reports) {
		for (Report r : reports) {
			this.reports.add(r);
		}
	}
    	
}
