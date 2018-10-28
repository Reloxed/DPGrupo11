package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Note extends DomainEntity {

	private Date publishedTime;
	private String creatorComment;
	private Collection<String> otherActorComments;
	private Report report;
	private Referee referee;
	private Customer customer;
	private HandyWorker handyWorker;


    /* Atributtes */

    @Past
    @NotNull
	public Date getPublishedTime(){
		return this.publishedTime;
	}

	public void setPublishedTime(Date publishedTime){
		this.publishedTime = publishedTime;
	}

    @NotBlank
	public String getCreatorComment(){
		return this.creatorComment;
    }
    
    public void setCreatorComment(String creatorComment){
        this.creatorComment = creatorComment;
    }

    public Collection<String> getOtherActorComments(){
        return this.otherActorComments;
    }

    public void setOtherActorComments(Collection<String> otherActorComments){
        this.otherActorComments = otherActorComments;
    }

    /* Relationships */

    @NotNull
    public Report getReport(){
        return this.report;
    }

    public void setReport(Report report){
        this.report = report;
    }

    @NotNull
    public Referee getReferee(){
        return this.referee;
    }

    public void setReferee(Referee referee){
        this.referee = referee;
    }

    @NotNull
    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    @NotNull
    public HandyWorker getHandyWorker(){
        this.handyWorker = handyWorker;
    }

    public void setHandyWorker(HandyWorker handyWorker){
        this.handyWorker = handyWorker;
    }
}
