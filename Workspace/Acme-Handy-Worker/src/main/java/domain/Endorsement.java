package domain;

import java.util.Date;

public class Endorsement extends DomainEntity {

	private Date publishedDate;
	private String comment;
	public Customer customer;
	public HandyWorker handyWorker;

	
	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
