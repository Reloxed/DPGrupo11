package services;



import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;



import domain.Tag;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TagService TagService;
	


	
	
	
	// Tests
	@Test
	public void testFindAll1() {
		super.authenticate("sponsor3");
		Collection<Tag> tags = null;
		
		try {
		tags = this.TagService.findAll();
		} catch (RuntimeException e) {}
		Assert.isNull(tags);
		super.authenticate(null);
	
	}
	
	@Test
	public void testFindAll2() {
		super.authenticate("administrator1");
		Collection<Tag> tags = null;
		
		try {
		tags = this.TagService.findAll();
		} catch (RuntimeException e) {}
		Assert.isTrue(tags.size() == 4);
		super.authenticate(null);
	
	}
	
	@Test
	public void testSave1() {
		super.authenticate("administrator1");
		Tag tag, saved;
	
		Collection<Tag> tags;
		
		
		tag = new Tag();
		tag.setName("summer");
		

		saved = this.TagService.save(tag);
		
		tags = this.TagService.findAll();
		Assert.isTrue(tags.contains(saved));
	
		super.authenticate(null);
		
	}
	
	@Test
	public void testSave2() {
		super.authenticate("administrator1");
		Tag tag, saved = null;
		Collection<Tag> tags;
		
		
		tag = this.TagService.findOne(1206);  // Tag2
		tag.setName("winter");
		
		try {
		saved = this.TagService.save(tag);
		} catch (RuntimeException e) {}
		
		tags = this.TagService.findAll();
		Assert.isTrue(!tags.contains(saved));
		
		super.authenticate(null);
		
	}
	
	@Test
	public void testDelete1() {
		super.authenticate("administrator1");
		
		Tag tag;
		Collection<Tag> tags;
		
		
		tag = this.TagService.findOne(1208); // Tag2;
		
		try {
		this.TagService.delete(tag);
		} catch (RuntimeException e) {}
		
		tags = this.TagService.findAll();
		Assert.isTrue(!tags.contains(tag));
		
		super.authenticate(null);
		
	}
	
	
	
}
