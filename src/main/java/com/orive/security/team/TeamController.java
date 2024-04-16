package com.orive.security.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/teams")
@Tag(name = "Team")
@CrossOrigin(origins = "*")
public class TeamController {

    private static final Logger logger = Logger.getLogger(TeamController.class.getName());

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<String> saveTeam(@RequestBody Team team) {
        try {
            teamService.saveTeam(team);
            return ResponseEntity.status(HttpStatus.CREATED).body("Team saved successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to save team: " + team.toString(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save team");
        }
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable("teamId") Long teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<String> updateTeam(@PathVariable("teamId") Long teamId, @RequestBody Team team) {
        team.setTeamId(teamId);
        try {
            teamService.updateTeam(team);
            return ResponseEntity.ok("Team updated successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update team with ID: " + teamId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update team");
        }
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@PathVariable("teamId") Long teamId) {
        try {
            teamService.deleteTeam(teamId);
            return ResponseEntity.ok("Team deleted successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to delete team with ID: " + teamId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete team");
        }
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        if (!teams.isEmpty()) {
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

