package net.vokhmin.testtask.bss.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import net.vokhmin.testtask.bss.model.Report;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import static org.springframework.data.jpa.domain.Specifications.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath:/META-INF/persistence-context.xml")
@Transactional(readOnly = true)
public class ReportDaoTest extends AbstractDBUnitTest {

	@Autowired
	private ReportDao reportDao;

	final String[] dataSetLocation = {
			"META-INF/itest/dbunit-preset.xml" 
	};

	@Override
	protected List<String> getDataSetFiles() {
		return Arrays.asList(this.dataSetLocation);
	}

	@Test
	public void testFindAllPerformers() {
		List<String> expected = jdbcTemplate.queryForList(
				"select distinct performer from reports", String.class);
		List<String> xp = reportDao.findAllPerformers();
		assertNotNull(xp);
		assertEquals(expected.size(), xp.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xp.get(i));
		}
	}
	
	@Test
	public void testFindAll() {
		int count = jdbcTemplate.queryForInt("select count(*) from reports");
		Iterable<Report> xr = reportDao.findAll();
		assertNotNull(xr);
		int size = 0;
		for (Report r : xr) size++;
		assertTrue("Performer's vlues must be more than 0", size == count);
	}
	
	@Test
	public void testFindAllPageble() {
		List<Long> expected = jdbcTemplate.queryForList(
				"select id from reports order by id limit 2, 2", Long.class);
		Sort sort = new Sort("id");
		Page<Report> xr = reportDao.findAll(new PageRequest(1, 2, sort));
		assertNotNull(xr);
		assertEquals(expected.size(), xr.getNumberOfElements());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xr.getContent().get(i).getId());
		}
	}
	
	@Test
	public void testGeStartDateReportsSpecification() {
		List<Long> expected = jdbcTemplate.queryForList(
				"select id from reports where start_date >= '2012-04-12' order by id", Long.class);
		List<Report> xr = reportDao.findAll(ReportSpecs.geStartDate(new Date(2012 - 1900, 04 - 1, 12)), new Sort("id"));
		assertNotNull(xr);
		assertEquals(expected.size(), xr.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xr.get(i).getId());
		}
		Report r = xr.get(0);
		assertEquals(102L, r.getId().longValue());
	}
	
	@Test
	public void testLeEndDateReportsSpecification() {
		List<Long> expected = jdbcTemplate.queryForList(
				"select id from reports where end_date <= '2012-04-14' order by id", Long.class);
		List<Report> xr = reportDao.findAll(ReportSpecs.leEndDate(new Date(2012 - 1900, 04 - 1, 14)), new Sort("id"));
		assertNotNull(xr);
		assertEquals(expected.size(), xr.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xr.get(i).getId());
		}
		Report r = xr.get(0);
		assertEquals(101L, r.getId().longValue());
	}
	
	@Test
	public void testEqualPerformerReportsSpecification() {
		List<Long> expected = jdbcTemplate.queryForList(
				"select id from reports where performer like 'Performer-B' order by id", Long.class);
		List<Report> xr = reportDao.findAll(ReportSpecs.equalPerformer("Performer-B"), new Sort("id"));
		assertNotNull(xr);
		assertEquals(expected.size(), xr.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xr.get(i).getId());
			assertEquals("Performer-B", xr.get(i).getPerformer());
		}
		Report r = xr.get(0);
		assertEquals(103L, r.getId().longValue());
	}
	
	@Test
	public void testComplexSpecification() {
		Date startDate = new Date(2012 - 1900, 4 - 1, 12);
		Date endDate = new Date(2012 - 1900, 4 - 1, 14);
		String performer = "Performer-B";
		List<Long> expected = jdbcTemplate.queryForList(
				"select id from reports where start_date >= '2012-04-12' and end_date <= '2012-04-14' and performer like 'Performer-B' order by id", 
				Long.class);
		List<Report> xr = reportDao.findAll(
				where(ReportSpecs.geStartDate(startDate))
				.and(ReportSpecs.leEndDate(endDate))
				.and(ReportSpecs.equalPerformer(performer)));
		assertNotNull(xr);
		assertEquals(expected.size(), xr.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), xr.get(i).getId());
			assertEquals("Performer-B", xr.get(i).getPerformer());
		}
	}
	
}
