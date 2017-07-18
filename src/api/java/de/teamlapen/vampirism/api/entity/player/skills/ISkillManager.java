package de.teamlapen.vampirism.api.entity.player.skills;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;

import java.util.List;

/**
 * 1.12
 *
 * @author maxanier
 */
public interface ISkillManager {


    /**
     * @param faction
     * @return The root skill node for the given faction
     */
    SkillNode getRootSkillNode(IPlayableFaction faction);

    /**
     * A mutable copied list of all skills registered for this faction
     *
     * @param faction
     * @return
     */
    List<ISkill> getSkillsForFaction(IPlayableFaction faction);
}
