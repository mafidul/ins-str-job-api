package com.tcs.ins.job.service.model;

import java.util.List;

public class JobModel {

	private Long id;
	private String jobType;
	private String entity;
	private String status;
	private List<JobParamModel> jobParam;
	private List<JobResultModel> jobResult;

	public Long getId() {
		return id;
	}

	public String getJobType() {
		return jobType;
	}

	public String getEntity() {
		return entity;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<JobParamModel> getJobParam() {
		return jobParam;
	}

	public List<JobResultModel> getJobResult() {
		return jobResult;
	}

	public void setJobParam(List<JobParamModel> jobParam) {
		this.jobParam = jobParam;
	}

	public void setJobResult(List<JobResultModel> jobResult) {
		this.jobResult = jobResult;
	}

	@Override
	public String toString() {
		return "JobModel [id=" + id + ", jobType=" + jobType + ", entity=" + entity + ", status=" + status
				+ ", jobParam=" + jobParam + ", jobResult=" + jobResult + "]";
	}
}