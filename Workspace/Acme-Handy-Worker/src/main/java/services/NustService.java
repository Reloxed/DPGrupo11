package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Customer;

import domain.Nust;

import repositories.NustRepository;

@Service
@Transactional
public class NustService {

	// Managed repository ------------------------------
	@Autowired
	private NustRepository nustRepository;

	// Supporting services -----------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ActorService actorService;

	@Autowired
	private UtilityService				utilityService;

	// Constructors ------------------------------------

	public NustService() {
		super();
	}

	// CRUD methods -----------------------------------


	public Nust create(){
		Nust result;
		Customer principal;

		principal=this.customerService.findByPrincipal();
		Assert.notNull(principal);
		final String ticker = this.utilityService.generateTickerNust();
		result=new Nust();

		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		result.setTicker(ticker);
		result.setIsFinal(false);


		return result;

	}
	public Nust save(final Nust nust,boolean finalMode){
		Nust saved;
		Actor principal;

		Date publishedMoment;



		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(principal instanceof Customer);
		Assert.notNull(nust.getFixUpTask());
		Assert.notNull(nust.getPicture());
		Assert.notNull(nust.getTicker());
		Assert.notNull(nust.getBody());

		publishedMoment = new Date(System.currentTimeMillis()-1);
		nust.setPublishedMoment(publishedMoment);
		nust.setIsFinal(finalMode);
		saved = this.nustRepository.save(nust);
		Collection<Nust> n=new ArrayList<Nust>(nust.getFixUpTask().getNusts());
		n.add(saved);

		nust.getFixUpTask().setNusts(n);

		Assert.notNull(saved);
		Assert.isTrue(saved.getId()!= 0);

		return saved;

	}

	public void delete(final Nust nust){
		Customer principal;

		Assert.notNull(nust);
		Assert.isTrue(nust.getId()!=0);

		principal=this.customerService.findByPrincipal();
		Assert.notNull(principal);

		if(nust.getIsFinal()==false){
			this.nustRepository.delete(nust);
		}

		nust.getFixUpTask().getNusts().remove(nust);

	}

	public Collection<Nust> findAll(){
		Collection<Nust> results;

		results=this.nustRepository.findAll();

		return results;

	}
	public Nust findOne(final int nustId){
		Nust result;

		result=this.nustRepository.findOne(nustId);
		Assert.notNull(result);

		return result;
	}
	// Other business methods

	public Double[] findDataNustsPerFixUpTask() {
		final Double[] result = new Double[2];

		final Double d1 = this.nustRepository.avgNustsPublishedperFixUpTask();
		final Double d2 = this.nustRepository.devStandardNustPublished();

		result[0] = d1;
		result[1] = d2;

		return result;
	}

	public Double devStandardNustPublished() {
		Double res;
		res=this.nustRepository.devStandardNustPublished();
		return res;
	}


	public Double avgNustsPublishedperFixUpTask(){
		Double res;
		res=this.nustRepository.avgNustsPublishedperFixUpTask();
		return res;
	}




	public Double ratioNustsFinalMode(){
		Double res;
		res=this.nustRepository.ratioNustsFinalMode();
		return res;
	}

	public Double ratioNustsDraftMode(){
		Double res;
		res=this.nustRepository.ratioNustsDraftMode();
		return res;
	}

	public Collection<Nust> nustsPublished(){
		Collection<Nust> res;
		res=this.nustRepository.nustsPublished();
		return res;
	}

}
