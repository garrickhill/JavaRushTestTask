package com.game.controller;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;


@RestController
@RequestMapping("/rest/players")
public class PlayerController {
    
	@Autowired
	private PlayerService playerService;

    
    @GetMapping(path = "")
    public @ResponseBody List<Player> getPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        return playerService.findAllByPage(pageNumber, pageSize, order.getFieldName(),
                name,
                title,
                race,
                profession,
                after != null ? new Date(after) : null,
                before != null ? new Date(before) : null,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }

    @GetMapping("/count")
    public @ResponseBody Integer getCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel
    ) {
        return  playerService.getCount(
                name,
                title,
                race,
                profession,
                after != null ? new Date(after) : null,
                before != null ? new Date(before) : null,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }
    
    @PostMapping
    public  @ResponseBody Player addPlayer(@RequestBody Player player) {
    	return playerService.save(player);
    }

    
    @GetMapping("/{id}")
    public @ResponseBody Player getPlayer(@PathVariable("id") Long id) {
    	return playerService.getById(id);
    }
    
    @PostMapping("/{id}")
    public @ResponseBody Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
    	return playerService.update(id, player);
    }
    
    @DeleteMapping("/{id}")
    public @ResponseBody void deletePlayer(@PathVariable("id") Long id) {
    	playerService.delete(id);
    }
    
    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public  ResponseEntity<?> handleException(Exception e) {
        return e.getClass().equals(NoSuchElementException.class) 
        		?ResponseEntity.notFound().build()
        		:ResponseEntity.badRequest().build();
    }
    
   
}
