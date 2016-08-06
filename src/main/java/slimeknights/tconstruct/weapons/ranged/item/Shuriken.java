package slimeknights.tconstruct.weapons.ranged.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nonnull;

import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.entity.EntityShuriken;

public class Shuriken extends ProjectileCore {

  private static PartMaterialType shurikenPMT = new PartMaterialType(TinkerTools.knifeBlade, HeadMaterialStats.TYPE, ExtraMaterialStats.TYPE);

  public Shuriken() {
    super(shurikenPMT, shurikenPMT, shurikenPMT, shurikenPMT);

    addCategory(Category.NO_MELEE, Category.PROJECTILE);
  }

  @Override
  public float damagePotential() {
    return 0.7f;
  }

  @Nonnull
  @Override
  public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
    if(ToolHelper.isBroken(itemStackIn)) {
      return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
    }
    ItemStack reference = getProjectileStack(itemStackIn, worldIn, playerIn);
    playerIn.getCooldownTracker().setCooldown(itemStackIn.getItem(), 4);

    if(!worldIn.isRemote) {
      EntityProjectileBase projectile = new EntityShuriken(worldIn, playerIn, 2.1f, 0f, reference);
      worldIn.spawnEntityInWorld(projectile);
    }

    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
  }

  @Override
  public NBTTagCompound buildTag(List<Material> materials) {
    ToolNBT data = new ToolNBT();
    data.head((HeadMaterialStats) materials.get(0).getStatsOrUnknown(HeadMaterialStats.TYPE),
              (HeadMaterialStats) materials.get(1).getStatsOrUnknown(HeadMaterialStats.TYPE),
              (HeadMaterialStats) materials.get(2).getStatsOrUnknown(HeadMaterialStats.TYPE),
              (HeadMaterialStats) materials.get(3).getStatsOrUnknown(HeadMaterialStats.TYPE));

    data.extra((ExtraMaterialStats) materials.get(0).getStatsOrUnknown(ExtraMaterialStats.TYPE),
               (ExtraMaterialStats) materials.get(1).getStatsOrUnknown(ExtraMaterialStats.TYPE),
               (ExtraMaterialStats) materials.get(2).getStatsOrUnknown(ExtraMaterialStats.TYPE),
               (ExtraMaterialStats) materials.get(3).getStatsOrUnknown(ExtraMaterialStats.TYPE));

    data.attack += 1f;
    //data.durability = Math.max(1, Math.round((float) data.durability / 10f));

    return data.get();
  }
}