package com.tcs.ins.job.api;

import static com.tcs.ins.support.Constant.REQUEST_PARAM_MAPPING;
import static com.tcs.ins.support.Constant.REQUEST_PARAM_PAGE_NUMBER;
import static com.tcs.ins.support.Constant.REQUEST_PARAM_PAGE_SIZE;
import static com.tcs.ins.support.Constant.REQUEST_PARAM_SORT_BY;
import static com.tcs.ins.support.Constant.REQUEST_PARAM_SORT_DIRECTION;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcs.ins.job.service.JobService;
import com.tcs.ins.job.service.model.JobModel;
import com.tcs.ins.job.service.model.JobSearchRequest;;

@RestController
@RequestMapping(REQUEST_PARAM_MAPPING)
class JobApi {

	private static final String SORT_DIRECTION_ASC = "asc";

	private final JobService jobService;

	JobApi(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JobModel> getJobById(@PathVariable Long id) {
		return ResponseEntity.ok(jobService.getJobById(id));
	}

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Page<JobModel>> search(
			@RequestParam(name = REQUEST_PARAM_PAGE_NUMBER, required = true) Integer pageNumber,
			@RequestParam(name = REQUEST_PARAM_PAGE_SIZE, required = true) Integer pageSize,
			@RequestParam(name = REQUEST_PARAM_SORT_BY, required = false) String sortBy,
			@RequestParam(name = REQUEST_PARAM_SORT_DIRECTION, required = false) String sortDirection,
			@RequestParam Map<String, String> requestParam) {
		JobSearchRequest searchRequest = new JobSearchRequest(requestParam);
		PageRequest pageRequest = pageRequest(pageNumber, pageSize, sortBy, sortDirection);
		Page<JobModel> page = jobService.search(pageRequest, searchRequest);
		return ResponseEntity.ok(page);
	}

	private PageRequest pageRequest(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		if (StringUtils.hasText(sortBy)) {
			Direction direction = StringUtils.hasText(sortDirection)
					&& SORT_DIRECTION_ASC.equalsIgnoreCase(sortDirection) ? Direction.ASC : Direction.DESC;
			return PageRequest.of(pageNumber, pageSize, Sort.by(new Order(direction, sortBy)));
		}
		return PageRequest.of(pageNumber, pageSize);
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JobModel> createJob(@RequestBody JobModel jobModel, UriComponentsBuilder ucb) {
		JobModel create = jobService.createJob(jobModel);

		return ResponseEntity.created(ucb.path(REQUEST_PARAM_MAPPING + "/{id}").buildAndExpand(create.getId()).toUri())
				.body(create);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<JobModel> updateJob(@PathVariable Long id, @RequestBody JobModel jobModel,
			UriComponentsBuilder ucb) {
		jobModel.setId(id);
		JobModel update = jobService.updateJob(jobModel);
		return ResponseEntity.ok(update);
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> deleteJobById(@PathVariable Long id) {
		jobService.deleteJobById(id);
		return ResponseEntity.ok(null);
	}
}
