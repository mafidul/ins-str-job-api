package com.tcs.ins.job.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tcs.ins.job.service.model.JobModel;
import com.tcs.ins.job.service.model.JobSearchRequest;

public interface JobService {

	JobModel getJobById(Long jobId);

	JobModel createJob(JobModel jobModel);

	JobModel updateJob(JobModel jobModel);

	Page<JobModel> search(PageRequest pageRequest, JobSearchRequest searchRequest);

	void deleteJobById(Long id);
}