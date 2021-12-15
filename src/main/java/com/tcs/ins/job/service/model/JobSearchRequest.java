package com.tcs.ins.job.service.model;

import java.util.Map;

import org.springframework.util.StringUtils;

public class JobSearchRequest {

	private static final String REQUEST_PARAM_ID = "id";
	private static final String REQUEST_PARAM_JOB_TYPE = "jobType";
	private final Long id;
	private final String jobType;

	public JobSearchRequest(Map<String, String> requestParam) {
		String idStr = requestParam.get(REQUEST_PARAM_ID);
		if (StringUtils.hasLength(idStr)) {
			this.id = Long.valueOf(idStr);
		} else {
			this.id = null;
		}
		this.jobType = requestParam.get(REQUEST_PARAM_JOB_TYPE);
	}

	public boolean idFilteringRequired() {
		return id != null;
	}

	public boolean jobTypeFilteringRequired() {
		return StringUtils.hasText(jobType);
	}

	public Long getId() {
		return id;
	}

	public String getJobType() {
		return jobType;
	}
}