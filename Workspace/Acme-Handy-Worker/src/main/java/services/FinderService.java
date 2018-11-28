package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;



import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;



import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	//Managed repository-----------
	
	@Autowired
	private FinderRepository finderRepository;
	
	//Un usuario no autenticado pueda hacer busquedas como contemplo eso?
	
	//Supporting services ----------
	//@Autowired
	//private FixUpTaskService fixUptaskService;
	
	private HandyWorkerService handyWorkerService;
	
	//Constructor -----------------------
		public FinderService() {
			super();
		}
	
	
	
	
	//Simple CRUD methods-------
	
	public Finder create(){
		Finder result;
		HandyWorker principal;
		
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		result=new Finder();
		//tengo que saber que handyWorker va a realizar la busqueda exactamente para algo?
		result.setFixuptask(new ArrayList<FixUpTask>());
		result.setSearchMoment(new Date(System.currentTimeMillis()-1));
		
		return result;
		
	}
	public Collection<Finder> findAll(){
		Collection<Finder> result;
		result=this.finderRepository.findAll();
		Assert.notNull(result);
		
		return result;
		
		
		
	} 
	
	public Finder findOne(final int finderId){
		Finder result;
		HandyWorker principal;
		
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		
		result=this.finderRepository.findOne(finderId);
		Assert.notNull(result);
		
		return result;
		
	}
	
	public Finder save(Finder finderId){
		Finder result;
		HandyWorker principal;
		Date currentMoment;
		
		
		Assert.notNull(finderId);
		Assert.isTrue(finderId.getId()==0);
		
		principal=this.handyWorkerService.findByPrincipal();
		
		Assert.notNull(principal);
		Assert.isTrue(finderId.getId()==principal.getFinder().getId());
		
		currentMoment=new Date(System.currentTimeMillis() - 1);
		
		finderId.setSearchMoment(currentMoment);
		
		if(finderId.getStartMoment()!=null && finderId.getEndMoment()!=null ){
			Assert.isTrue(finderId.getStartMoment().before(finderId.getEndMoment()));
			
		}else if(finderId.getPriceHigh()!= null && finderId.getPriceLow()!= null){
			Assert.isTrue(finderId.getPriceHigh()>=finderId.getPriceLow());
		}
		
		result=this.finderRepository.save(finderId);
		Assert.notNull(result);

		Assert.isTrue(finderId.getId()!=0);
		
		return result;
		
	}
	//expiración de la busqueda cuanto termina tiempo caché
	public void  deleteExpireFinders(final int resultsInCache){
		Finder finder;
		Collection<Finder> finders;
		Collection<Finder> findersDelete;
		Collection<Finder> currentFinders;
		
		HandyWorker principal;
		Date currentMoment;
		Date expireCache;
		
		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		currentMoment=new Date();
		expireCache=DateUtils.addHours(currentMoment, resultsInCache);
		
		finders=new ArrayList<Finder>();
		finder=principal.getFinder();
		finders.add(finder);
		//solo tenemos uno en caché,OJO
		findersDelete=new ArrayList<Finder>();
		currentFinders=new ArrayList<Finder>(finders);
		
		for (Finder f : finders)
			if (f.getSearchMoment().before(expireCache)) {
				findersDelete.add(f);
				this.finderRepository.delete(f);
			}

		currentFinders.removeAll(findersDelete);
		
		
		
		
	}
	
	
	
	
	//Other business methods--------
	
	public Finder findByPrincipal() {
		Finder finder;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		finder = principal.getFinder();

		return finder;
	}
	
/*	
	public Collection<FixUpTask> search(final Finder finderId,final int nResults ){
		String keyWord;
		Double priceLow;
		Double priceHigh;
		Date startMoment;
		Date endMoment;
		Collection<FixUpTask> result;
		HandyWorker principal;
		Category category;//hay que poder buscar por categoria y garantia hay q hacer query
		Warranty warranty;//tengo que tener los ids de esa categoria y garantia
		
		principal=this.handyWorkerService.findByPrincipal();
		
		Assert.notNull(principal);
		
		if(nResults>100){
			throw new IllegalArgumentException();
		}
		
		startMoment=finderId.getStartMoment();
		endMoment=finderId.getEndMoment();
		priceLow=finderId.getPriceLow();
		priceHigh=finderId.getPriceHigh();
		keyWord=finderId.getKeyWord();
		
		if(startMoment==null){
			
		startMoment=new Date();
			
		}else if(endMoment==null){
			
		endMoment=new Date(9999999999999L);
			
		}else if(priceLow==null){
			priceLow=0.;
			
		}else if(priceHigh==null){
			priceHigh=100000.;
			
		}else if(keyWord==null){
			
			keyWord="";
		}
		category=finderId.getCategory();
		warranty=finderId.getWarranty();
		result=this.finderRepository.searchFixUpTasks(startMoment, endMoment,  priceLow,  priceHigh,  keyWord,new PageRequest(0, nResults),category,warranty);
		Assert.notNull(result);
		
		finderId.setFixuptask(new ArrayList<FixUpTask>(result));
		
		return result;
		
		
		
		
	}
	*/
	
	
	
}
