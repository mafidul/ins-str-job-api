package com.tcs.ins.job.service.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tcs.ins.job.repository.entity.Job;
import com.tcs.ins.job.service.model.JobSearchRequest;

public class JobSpecification implements Specification<Job> {

	private static final String ATTRIBUTE_JOB_TYPE = "jobType";
	private static final String ATTRIBUTE_ID = "id";
	private static final long serialVersionUID = 1L;
	private final JobSearchRequest jobSearchRequest;

	public JobSpecification(JobSearchRequest jobSearchRequest) {
		this.jobSearchRequest = jobSearchRequest;
	}

	@Override
	public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (jobSearchRequest.idFilteringRequired()) {
			predicates.add(criteriaBuilder.equal(root.get(ATTRIBUTE_ID), jobSearchRequest.getId()));
		}
		if (jobSearchRequest.jobTypeFilteringRequired()) {
			predicates.add(criteriaBuilder.like(root.get(ATTRIBUTE_JOB_TYPE), jobSearchRequest.getJobType() + "%"));
		}
		return andTogether(predicates, criteriaBuilder);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}
}