package com.tcs.ins.job.repository.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.tcs.ins.job.repository.Auditable;

@Entity(name = "JOB")
@Audited
public class Job extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "JOBTYPE")
	private String jobType;
	@Column(name = "ENTITY")
	private String entity;
	@Column(name = "STATUS")
	private String status;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getStatus() {
		return status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "JOBPARAM_ID", referencedColumnName = "ID")
	@NotAudited
	private List<JobParam> jobParam;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "JOBRESULT_ID", referencedColumnName = "ID")
	@NotAudited
	private List<JobResult> jobResult;

	public List<JobParam> getJobParam() {
		return jobParam;
	}

	public List<JobResult> getJobResult() {
		return jobResult;
	}

	public void setJobParam(List<JobParam> jobParam) {
		this.jobParam = jobParam;
	}

	public void setJobResult(List<JobResult> jobResult) {
		this.jobResult = jobResult;
	}
}
