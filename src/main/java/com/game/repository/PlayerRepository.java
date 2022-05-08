package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
   	 @Query("SELECT p FROM Player p WHERE " +
	            "(:name IS NULL OR p.name LIKE %:name%) AND " +
	            "(:title IS NULL OR p.title LIKE %:title%) AND " +
	            "(:after IS NULL OR p.birthday >= :after) AND " +
	            "(:before IS NULL OR p.birthday <= :before) AND " +
	            "(:minExperience IS NULL OR p.experience >= :minExperience) AND " +
	            "(:maxExperience IS NULL OR p.experience <= :maxExperience) AND " +
	            "(:minLevel IS NULL OR p.level >= :minLevel) AND " +
	            "(:maxLevel IS NULL OR p.level <= :maxLevel) AND " +
	            "(:race IS NULL OR p.race = :race) AND " +
	            "(:profession IS NULL OR p.profession = :profession) AND " +
	            "(:banned IS NULL OR p.banned = :banned)")
    List<Player> findAllByParams(@Param("name") String name,
                                 @Param("title") String title,
                                 @Param("race")Race race,
                                 @Param("profession")Profession profession,
                                 @Param("after") Date after,
                                 @Param("before")Date before,
                                 @Param("banned")Boolean banned,
                                 @Param("minExperience")Integer minExperience,
                                 @Param("maxExperience")Integer maxExperience,
                                 @Param("minLevel")Integer minLevel,
                                 @Param("maxLevel")Integer maxLevel,
                                 Pageable pageable);
}
