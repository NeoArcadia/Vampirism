package de.teamlapen.vampirism.block;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.entity.EntityDracula;
import de.teamlapen.vampirism.util.Helper;
import de.teamlapen.vampirism.util.Logger;
import de.teamlapen.vampirism.util.REFERENCE;
import de.teamlapen.vampirism.util.VampireLordData;
import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by Max on 12.08.2015.
 */
public class BlockDraculaButton extends BlockButton {

    public static final String name = "draculaButton";

    public BlockDraculaButton() {
        super(false);
        this.setCreativeTab(null);
        this.setUnlocalizedName("button");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        int i1 = world.getBlockMetadata(posX, posY, posZ);
        int j1 = i1 & 7;
        int k1 = 8 - (i1 & 8);

        if (k1 == 0 || world.provider.dimensionId != VampirismMod.castleDimensionId) {
            return true;
        } else {
            if (!world.isRemote) {
                int a = 5;
                int maxTry = 25;
                EntityDracula drac = null;
                AxisAlignedBB box;
                if ((i1 & 7) == 1) {
                    box = AxisAlignedBB.getBoundingBox(posX, posY - 1, posZ - a, posX + a, posY + 2, posZ + a);
                } else if ((i1 & 7) == 2) {
                    box = AxisAlignedBB.getBoundingBox(posX - a, posY - 1, posZ - a, posX, posY + 2, posZ + a);
                } else if ((i1 & 7) == 3) {
                    box = AxisAlignedBB.getBoundingBox(posX - a, posY - 1, posZ, posX + a, posY + 2, posZ + a);
                } else {
                    box = AxisAlignedBB.getBoundingBox(posX - a, posY - 1, posZ - a, posX + a, posY + 2, posZ);
                }
                List list = world.getEntitiesWithinAABB(EntityDracula.class, AxisAlignedBB.getBoundingBox(posX - 100, 0, posZ - 100, posX + 100, 256, posZ + 100));
                if (list.isEmpty()) {
                    if (VampireLordData.get(world).canCallDracula()) {
                        drac = (EntityDracula) EntityList.createEntityByName(REFERENCE.ENTITY.DRACULA_NAME, world);
                        boolean flag = Helper.spawnEntityInWorld(world, box, drac, maxTry);
                        if (!flag) {
                            Logger.w("DracButton", "Failed to call Dracula");
                        }
                    } else {
                        player.addChatComponentMessage(new ChatComponentTranslation("text.vampirism.no_dracula_to_call"));
                    }
                } else {
                    drac = (EntityDracula) list.get(0);
                    boolean flag = false;

                    int i = 0;
                    while (!flag && i++ < maxTry) {
                        ChunkCoordinates c = Helper.getRandomPosInBox(world, box);
                        drac.setPosition(c.posX, c.posY, c.posZ);
                        if (!(drac instanceof EntityLiving) || (drac).getCanSpawnHere()) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        Helper.teleportTo(drac, drac.posX, drac.posY, drac.posZ, true);
                    }

                }
                if (drac != null) {
                    drac.addPotionEffect(new PotionEffect(Potion.resistance.id, 50, 10));
                    drac.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 10));
                    drac.heal(100F);
                }
            }

            world.setBlockMetadataWithNotify(posX, posY, posZ, j1 + k1, 3);
            world.markBlockRangeForRenderUpdate(pos,pos);
            world.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
            world.scheduleUpdate(pos, this, this.tickRate(world));
            return true;
        }
    }


    @Override
    public int tickRate(World p_149738_1_) {
        return 250;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return 0;
    }


    @Override
    public boolean canProvidePower() {
        return false;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.stone_button);
    }

}