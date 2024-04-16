package com.orive.security.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TeamService {

    private static final Logger logger = Logger.getLogger(TeamService.class.getName());

    @Autowired
    private TeamRepository teamRepository;

    public void saveTeam(Team team) {
        try {
            teamRepository.save(team);
            logger.log(Level.INFO, "Team saved successfully: " + team.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to save team: " + team.toString(), e);
        }
    }

    public Team getTeamById(Long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isPresent()) {
            return optionalTeam.get();
        } else {
            logger.log(Level.WARNING, "Team not found with ID: " + teamId);
            return null;
        }
    }

    public void updateTeam(Team team) {
        try {
            Optional<Team> existingTeamOptional = teamRepository.findById(team.getTeamId());
            if (existingTeamOptional.isPresent()) {
                Team existingTeam = existingTeamOptional.get();
                if (team.getTeamname() != null) {
                    existingTeam.setTeamname(team.getTeamname());
                }
                if (team.getAreaname() != null) {
                    existingTeam.setAreaname(team.getAreaname());
                }
                if (team.getTeammembersname() != null) {
                    existingTeam.setTeammembersname(team.getTeammembersname());
                }
                if (team.getTask() != null) {
                    existingTeam.setTask(team.getTask());
                }
                teamRepository.save(existingTeam);
                logger.log(Level.INFO, "Team updated successfully: " + existingTeam.toString());
            } else {
                logger.log(Level.WARNING, "Team not found with ID: " + team.getTeamId());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update team: " + team.toString(), e);
        }
    }


    public void deleteTeam(Long teamId) {
        try {
            teamRepository.deleteById(teamId);
            logger.log(Level.INFO, "Team deleted successfully with ID: " + teamId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to delete team with ID: " + teamId, e);
        }
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

