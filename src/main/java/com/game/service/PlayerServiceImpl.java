package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService{
	@Autowired
	private PlayerRepository playerRepository;

    @Override
    public List<Player> findAllByPage(Integer pageNumber, Integer pageSize, String fieldName, String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return playerRepository.findAllByParams(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel,
                PageRequest.of(pageNumber, pageSize, Sort.by(fieldName)));
    }

    @Override
    public Integer getCount(String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return playerRepository.findAllByParams(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel,
                PageRequest.of(0, Integer.MAX_VALUE)).size();
    }

	@Override
	public Player getById(Long id) {
		if(id<1) throw new IllegalArgumentException();
		return playerRepository.findById(id)
				.orElseThrow(NoSuchElementException::new);
	}

	@Override
	public void delete(Long id) {
		playerRepository.delete(getById(id));
	}

	@Override
	public Player save(Player player) {
		if(!isPlayerValid(player)) throw new IllegalArgumentException();
		return playerRepository.save(player);
	}

	@Override
	public Player update(Long id, Player source) {
		if(id<1) throw new IllegalArgumentException();
		
		Player target = playerRepository.findById(id)
				.orElseThrow(NoSuchElementException::new);
		
		if (source.getName() != null) {
        	target.setName(source.getName());
        }
        if (source.getTitle() != null) {
        	target.setTitle(source.getTitle());
        }
        if (source.getExperience() != null) {
        	target.setExperience(source.getExperience());
        }
        if (source.getBanned() != null) {
        	target.setBanned(source.getBanned());
        }
        if (source.getBirthday() != null) {
        	target.setBirthday(source.getBirthday());
        }
        if (source.getProfession() != null) {
        	target.setProfession(source.getProfession());
        }
        if (source.getRace() != null) {
        	target.setRace(source.getRace());
        }
        
        if(!isPlayerValid(target)) throw new IllegalArgumentException();
        
        return playerRepository.save(target);
	}
	
	private boolean isPlayerValid(Player player) {
    	if (player.getRace() == null || player.getProfession() == null) {
            return false;
        }
    	
    	if (player.getName().length() > 12) {
    		return false;
        }

        if (player.getTitle().length() > 30) {
        	return false;
        }

        if (player.getExperience() < 0 || player.getExperience() > 10_000_000) {
        	return false;
        }

        if (player.getBirthday().before(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime()) ||
        		player.getBirthday().after(new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime()))
        {
        	return false;
        }
    	
    	return true;
    }
}
