package myspringboot.hero;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping(value="/heroes")
public class HeroController {
	private List<Hero> heroes = new ArrayList<>();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private void buildHero() {
		Hero hero1 = new Hero( 11L, "Mr. Nice"); 
		Hero hero2 = new Hero( 12L, "Narco");
		Hero hero3 = new Hero( 13L, "Bombasto");
		Hero hero4 = new Hero( 14L, "Celeritas"); 
		Hero hero5 = new Hero( 15L, "Magneta");
		Hero hero6 = new Hero( 16L, "RubberMan");
		Hero hero7 = new Hero( 17L, "Dynama");
		Hero hero8 = new Hero( 18L, "Dr IQ");
		Hero hero9 = new Hero( 19L, "Magma"); 
		Hero hero10 = new Hero( 20L, "Tornado");
		
		heroes.add(hero1);
		heroes.add(hero2);
		heroes.add(hero3);
		heroes.add(hero4);
		heroes.add(hero5);
		heroes.add(hero6);
		heroes.add(hero7);
		heroes.add(hero8);
		heroes.add(hero9);
		heroes.add(hero10);
	}
	
	public HeroController() {
		buildHero();
	}
	
	//전체 목록 조회
	@RequestMapping(method=RequestMethod.GET)
	public List<Hero> getHeroes(){
		return this.heroes;
	}
	
	//개별조희
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Hero getHero(@PathVariable("id") Long id) {
		Hero hero = this.heroes.stream().filter(value -> value.getId() == id).findFirst().get();
		logger.debug("Hero 상세정보 " + hero);
		return hero;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Hero saveHero(@RequestBody Hero hero) {
		logger.debug("Hero 등록 1 " + hero);
		Long nextId = 0L;
		if(this.heroes.size() != 0) {
			Hero lastHero = this.heroes.stream().skip(this.heroes.size() - 1).findFirst().orElse(null);
			nextId = lastHero.getId() + 1;
		}
		hero.setId(nextId);
		this.heroes.add(hero);
		logger.debug("Hero 등록 2 " + hero);
		return hero;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) {
		logger.debug("Hero 수정 1 " + hero);
		Long heroId = hero.getId();
		if(heroId > 0){
			Hero updateHero = this.heroes.stream().filter(hero2 -> hero2.getId() == heroId).findFirst().get();
			updateHero.setName(hero.getName());
			logger.debug("Hero 수정 2 " + updateHero);
			return updateHero;
		}
		
		return null;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public boolean deleteHero(@PathVariable("id") Long heroId) {
		logger.debug("Hero 삭제 id : " + heroId);
		if(heroId > 0){
			Hero delHero = this.heroes.stream().filter(hero -> hero.getId() == heroId).findFirst().get();
			this.heroes.remove(delHero);
			logger.debug("Hero 삭제된 HeroId : " + heroId);
			return true;
		}
		return false;
	}
}
