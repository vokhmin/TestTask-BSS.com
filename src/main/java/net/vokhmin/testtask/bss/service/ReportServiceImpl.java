package net.vokhmin.testtask.bss.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.vokhmin.testtask.bss.dao.ReportDao;
import net.vokhmin.testtask.bss.model.ReportList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Path(ReportServiceImpl.REPORTS_URL)
@Transactional
public class ReportServiceImpl {
	protected static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	public static final String REPORTS_URL = "/reports";
	
	private ReportDao reportDao;
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Transactional(readOnly = true)
	public ModelAndView viewAll() {
		ReportList reports = new ReportList();
		reports.addReports(reportDao.findAll());
		return new ModelAndView("reports", "reports", reports);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Transactional(readOnly = true)
	public ModelAndView viewSubset() {
		return new ModelAndView("subset", "subset", null);
	}
	
	@GET
	@Path("data")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Transactional(readOnly = true)
	public ReportList getll() {
		ReportList reports = new ReportList();
		reports.addReports(reportDao.findAll());
		return reports;
	}
	
}
