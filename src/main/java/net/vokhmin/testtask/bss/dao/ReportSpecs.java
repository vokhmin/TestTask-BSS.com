package net.vokhmin.testtask.bss.dao;

import java.sql.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.vokhmin.testtask.bss.model.Report;
import net.vokhmin.testtask.bss.model.Report_;

import org.springframework.data.jpa.domain.Specification;

public class ReportSpecs {

	public static Specification<Report> equalStartDate(final Date date) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(Report_.startDate), date);
			}
		};
	}
	
	public static Specification<Report> geStartDate(final Date date) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.greaterThanOrEqualTo(root.get(Report_.startDate), date);
			}
		};
	}
	
	public static Specification<Report> equalEndDate(final Date date) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(Report_.endDate), date);
			}
		};
	}
	
	public static Specification<Report> leEndDate(final Date date) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.lessThanOrEqualTo(root.get(Report_.endDate), date);
			}
		};
	}
	
	public static Specification<Report> equalPerformer(final String value) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(Report_.performer), value);
			}
		};
	}
	
}
