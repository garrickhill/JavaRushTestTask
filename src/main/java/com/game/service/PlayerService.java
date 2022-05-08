package com.game.service;

import java.util.Date;
import java.util.List;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

public interface PlayerService {
    List<Player> findAllByPage(Integer pageNumber,
                               Integer pageSize,
                               String fieldName,
                               String name,
                               String title,
                               Race race,
                               Profession profession,
                               Date after,
                               Date before,
                               Boolean banned,
                               Integer minExperience,
                               Integer maxExperience,
                               Integer minLevel,
                               Integer maxLevel);


    Integer getCount(String name,
                  String title,
                  Race race,
                  Profession profession,
                  Date after,
                  Date before,
                  Boolean banned,
                  Integer minExperience,
                  Integer maxExperience,
                  Integer minLevel,
                  Integer maxLevel);


	Player getById(Long id);

	void delete(Long id);


	Player save(Player player);


	Player update(Long id, Player player);
}
