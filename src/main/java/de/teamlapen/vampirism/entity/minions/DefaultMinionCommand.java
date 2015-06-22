package de.teamlapen.vampirism.entity.minions;

import de.teamlapen.vampirism.util.REFERENCE;
import net.minecraft.util.ResourceLocation;

public abstract class DefaultMinionCommand implements IMinionCommand {

	private final static ResourceLocation defaultIcons = new ResourceLocation(REFERENCE.MODID+":textures/gui/minion_commands.png");
	private final int id;
	
	public DefaultMinionCommand(int id){
		this.id=id;
	}
	@Override
	public ResourceLocation getIconLoc() {
		return defaultIcons;
	}

	@Override
	public int getId() {
		return id;
	}


	
	@Override
	public String toString() {
		return getUnlocalizedName() + ":" + id;
	}
	
	@Override
	public boolean canBeActivated(IMinion minion){
		return true;
	}

}
