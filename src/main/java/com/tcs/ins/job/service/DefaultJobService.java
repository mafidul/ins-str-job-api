package com.tcs.ins.job.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tcs.ins.job.exception.ApplicationException;
import com.tcs.ins.job.repository.JobRepository;
import com.tcs.ins.job.repository.entity.Job;
import com.tcs.ins.job.service.mapper.JobMapper;
import com.tcs.ins.job.service.model.JobModel;
import com.tcs.ins.job.service.model.JobSearchRequest;
import com.tcs.ins.job.service.specification.JobSpecification;

@Service
public class DefaultJobService implements JobService {
	private final JobRepository jobRepository;
	private final JobMapper jobMapper;

	DefaultJobService(JobRepository jobRepository, JobMapper jobMapper) {
		this.jobRepository = jobRepository;
		this.jobMapper = jobMapper;
	}

	@Override
	public JobModel getJobById(Long id) {
		Job job = getOrThrow(id);
		return jobMapper.toModel(job);
	}

	private Job getOrThrow(Long id) {
		Optional<Job> optionalJob = jobRepository.findById(id);
		if (optionalJob.isEmpty()) {
			throw ApplicationException.noRecordFound("Job not Found");
		}
		return optionalJob.get();
	}

	@Override
	public JobModel createJob(JobModel jobModel) {
		System.out.println("Job Model Data : " + jobModel.toString());
		Job createJob = jobMapper.toEntiry(jobModel);
		Job saveJob = jobRepository.save(createJob);
		System.out.println("Job Model Data after: " + jobMapper.toModel(saveJob).toString());
		return jobMapper.toModel(saveJob);
	}

	@Override
	public JobModel updateJob(JobModel jobModel) {
		Job job = getOrThrow(jobModel.getId());
		if (StringUtils.hasLength(jobModel.getJobType())) {
			job.setJobType(jobModel.getJobType());
		}
		if (StringUtils.hasLength(jobModel.getEntity())) {
			job.setEntity(jobModel.getEntity());
		}
		if (StringUtils.hasLength(jobModel.getStatus())) {
			job.setStatus(jobModel.getStatus());
		}
		Job saveJob = jobRepository.save(job);
		return jobMapper.toModel(saveJob);
	}

	@Override
	public void deleteJobById(Long id) {
		jobRepository.deleteById(id);
	}

	@Override
	public Page<JobModel> search(PageRequest pageRequest, JobSearchRequest searchRequest) {
		Page<Job> page = jobRepository.findAll(new JobSpecification(searchRequest), pageRequest);
		List<JobModel> content = jobMapper.toModel(page);
		return new PageImpl<JobModel>(content, pageRequest, page.getTotalElements());
	}
}