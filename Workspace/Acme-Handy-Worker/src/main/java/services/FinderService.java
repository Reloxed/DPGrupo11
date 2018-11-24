package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;



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
		
		return result;
		
	}
	public Collection<Finder> findAll(){
		//tiene sentido si un handyworker puede tener una collecion de busquedas
		Collection<Finder> result;
		result=this.finderRepository.findAll();
		
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
		Collection<Finder> finders;//poner @OneToMany un handyworker puede tener una collecion de finders
		//Date currentMoment;
		
		
		Assert.notNull(finderId);
		Assert.isTrue(finderId.getId()==0);//compruebo que todavia no se ha guardado en la DB
		
		principal=this.handyWorkerService.findByPrincipal();
		
		Assert.notNull(principal);
		
		//currentMoment=new Date(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		//guarda el momento de la busqueda en segundos 
		//finderId.setSearchMoment(currentMoment);
		
		if(finderId.getStartMoment()!=null && finderId.getEndMoment()!=null ){
			Assert.isTrue(finderId.getStartMoment().before(finderId.getEndMoment()));
			
		}else if(finderId.getPriceHigh()!= null && finderId.getPriceLow()!= null){
			Assert.isTrue(finderId.getPriceHigh()>=finderId.getPriceLow());
		}
		//comrobar mas atributos?
		
		//no tengo que saber exacatmente quien es el HandyWorker no?
		
		result=this.finderRepository.save(finderId);
		Assert.notNull(result);
		//añadir busqueda a la collecion de busquedas
		/*finders=new ArrayList<Finder>(principal.getFinder());
		finders.add(result);
		principal.setFinder(finders);
		*/
		Assert.isTrue(finderId.getId()!=0);
		
		return result;
		
	}
	//expiración de la busqueda cuanto termina tiempo caché
	public void  delete(final Finder finderId){
		Assert.notNull(finderId);
		Assert.isTrue(finderId.getId()!= 0);
		Assert.isTrue(this.finderRepository.exists(finderId.getId()));
		
		this.finderRepository.delete(finderId);
		
	}
	
	
	
	
	//Other business methods--------
	
	public Collection<FixUpTask> search(final Finder finderId,final int paginas ){
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
		
		result=this.finderRepository.searchFixUpTasks(startMoment, endMoment,  priceLow,  priceHigh,  keyWord,new PageRequest(0, paginas));
		Assert.notNull(result);
		
		finderId.setFixuptask(new ArrayList<FixUpTask>(result));
		
		return result;
		
		
		
		
	}
	
	
	
	
}
