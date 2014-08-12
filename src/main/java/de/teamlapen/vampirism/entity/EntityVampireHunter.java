package de.teamlapen.vampirism.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityVampireHunter extends EntityMob {

	public EntityVampireHunter(World p_i1738_1_) {
		super(p_i1738_1_);
		
		this.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

}