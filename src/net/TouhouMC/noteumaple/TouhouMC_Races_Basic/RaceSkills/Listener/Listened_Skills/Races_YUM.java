package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills;

import java.util.List;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Races_Global;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Races_YUM extends JavaPlugin {
	////�A�N�e�B�u�X�L���n
	///�ړ��X�L���n
	public static void tenngu_kamikaze(Player pl, int boost){
		if (boost > 0 && boost < 3){
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(8.0D));
			//pl.setFallDistance(-30F);
			//�C�x���g���X�i�[�ֈڍs (EntityDamageEvent)
		}else {
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 0.0F);
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, -1.0F);
			pl.setVelocity(pl.getLocation().getDirection().multiply(14.0D));
			//pl.setFallDistance(-10F);
			//�C�x���g���X�i�[�ֈڍs (EntityDamageEvent)
		}
	}

	///�U���X�L���n
	public static void youma_golden_shockwave(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.YELLOW + "���̕��őS�Ă𐁂���΂��I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 0.0F);
		pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double(enemy.getLocation().getX() - pl.getLocation().getX()).doubleValue(), 0.0D, new Double(enemy.getLocation().getZ() - pl.getLocation().getZ()).doubleValue())));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
			}
		}
	}

	public static void kappa_stone_tnt(Player pl){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "�΂̕���TNT�𓊂����I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.FIRE_IGNITE, 1.0F, 0.0F);
		Location location = pl.getEyeLocation();
		float pitch = location.getPitch() / 180.0F * 3.1415927F;
		float yaw = location.getYaw() / 180.0F * 3.1415927F;
		double motX = -Math.sin(yaw) * Math.cos(pitch);
		double motZ = Math.cos(yaw) * Math.cos(pitch);
		double motY = -Math.sin(pitch);
		Vector velocity = new Vector(motX, motY, motZ).multiply(1.3D);
        TNTPrimed tnt = (TNTPrimed)pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        MetadataValue shooter = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, pl.getName());
        tnt.setMetadata("kappa-tnt", shooter);
        tnt.setVelocity(velocity);
        tnt.setIsIncendiary(true);
        tnt.setFireTicks(20);
        tnt.setFuseTicks(20);
		Races_Global.NoDamageTick(TouhouMC_Races_Basic.plugin0, pl, 15, 30);
	}

	public static void youma_wooden_upper(Player pl, Plugin plugin){
		pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GOLD + "���Œn�ʂ�@���グ���I�I");
		pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 0.0F);
		pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
		List<Entity> enemys = pl.getNearbyEntities(7.0D, 7.0D, 7.0D);
		for (Entity enemy : enemys) {
			if ((enemy instanceof LivingEntity)){
				enemy.setVelocity(enemy.getVelocity().add(new Vector(0.0D, 1.5D, 0.0D)));
				enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1.0F, 0.0F);
			}
		}
	}
}
