package com.tcs.ins.job.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.tcs.ins.job.repository.entity.Job;
import com.tcs.ins.job.service.model.JobModel;

@Mapper(componentModel = "spring")
public interface JobMapper {
	JobModel toModel(Job source);

	Job toEntiry(JobModel source);

	List<JobModel> toModel(Page<Job> page);
}