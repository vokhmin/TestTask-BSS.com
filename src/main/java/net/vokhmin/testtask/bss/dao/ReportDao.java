package net.vokhmin.testtask.bss.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import net.vokhmin.testtask.bss.model.Report;

public interface ReportDao extends BaseRepository<Report, Long>, JpaSpecificationExecutor {

	@Query("select distinct r.performer from Report r")
    public List<String> findAllPerformers();
	
}
