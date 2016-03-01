package net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener;

import java.io.File;

import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.TouhouMC_Races_Basic;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Races_Global;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_AKM;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_NNG;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_SIR;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YNK;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUM;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUS;
import net.TouhouMC.noteumaple.TouhouMC_Races_Basic.RaceSkills.Listener.Listened_Skills.Races_YUZ;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Races_EventActionListener implements Listener {
	static String pluginpre = TouhouMC_Races_Basic.tmc_Races_pre;
	public static File config = TouhouMC_Races_Basic.configfile;
	//configの呼び出しはこれを推奨
	static File file = TouhouMC_Races_Basic.configfile;
	static FileConfiguration conf = TouhouMC_Races_Basic.conf;

	//コンストラクタ リスナー登録
	public Races_EventActionListener(TouhouMC_Races_Basic plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/*イベント処理ここから*/
	//チャット装飾
	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent e){
		//前置詞に種族名を加える
		Player pl = e.getPlayer();
		String format = e.getFormat();
		if (conf.contains("user." + pl.getUniqueId())){
			boolean existrace = false;
			String inforace = "";
			for (String race : conf.getConfigurationSection("race").getKeys(false)) {
				if (race.toLowerCase().contains(conf.getString("user." + pl.getUniqueId() + ".race"))){
					existrace = true;
					inforace = race;
					break;
				}
			}
			String race;
			if (existrace){
				race = conf.getString("race." + inforace + ".display.tag");
			}else {
				race = conf.getString("user." + pl.getUniqueId() + ".race");
			}
			e.setFormat(ChatColor.WHITE + "[" + race + ChatColor.WHITE + "]" + format);
		}
	}

	//プレイヤー参加時の処理
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player pl = e.getPlayer();
		//新規登録
		if (!conf.contains("user." + pl.getUniqueId())){
			conf.set("user." + pl.getUniqueId() + ".name" , pl.getName());
			conf.set("user." + pl.getUniqueId() + ".point" , 0);
			conf.set("user." + pl.getUniqueId() + ".race" , "kedama");
			conf.set("user." + pl.getUniqueId() + ".spilit", 0);
			TouhouMC_Races_Basic.SaveTMCConfig();
		}
		conf.set("user." + pl.getUniqueId() + ".spilit", 0);
		TouhouMC_Races_Basic.SaveTMCConfig();
		//メタ初期付与
		MetadataValue casted = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, false) ;
		pl.setMetadata("casting", casted);
		MetadataValue usingmagic = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, false) ;
		pl.setMetadata("using-magic", usingmagic);
		MetadataValue spilituse = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, 0) ;
		pl.setMetadata("spilituse", spilituse);
	}

	//プレイヤー退出時の処理
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player pl = e.getPlayer();
		for (LivingEntity bat : pl.getWorld().getEntitiesByClass(Bat.class)) {
			if (bat.hasMetadata("invincible")) {
				if (pl.hasMetadata("batman")) {
					if (((MetadataValue)pl.getMetadata("batman").get(0)).asString().toString().contains(((MetadataValue)bat.getMetadata("invincible").get(0)).asString().toString())){
						bat.removeMetadata("invincible", TouhouMC_Races_Basic.plugin0);
						bat.damage(1000.0D);
					}
				}
			}
		}
		if (pl.hasMetadata("batman")) pl.removeMetadata("batman", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("casting")) pl.removeMetadata("casting", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("using-magic")) pl.removeMetadata("using-magic", TouhouMC_Races_Basic.plugin0);
		if (pl.hasMetadata("satorin0")) pl.removeMetadata("satorin0", TouhouMC_Races_Basic.plugin0);
		if (pl.getGameMode() == GameMode.SPECTATOR) pl.setGameMode(GameMode.SURVIVAL);
		if (pl.hasMetadata("freeze")) pl.removeMetadata("freeze", TouhouMC_Races_Basic.plugin0);
	}

	//クリック関連の処理(通常クリック)
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e){
		final Player pl = e.getPlayer();
		Material handitem = pl.getItemInHand().getType();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
		///右クリ
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			///グローバル
			Material dust_is_ok = pl.getItemInHand().getType() ;
			if (dust_is_ok == Material.SUGAR || dust_is_ok == Material.SULPHUR || dust_is_ok == Material.GLOWSTONE_DUST){
				Races_Global.global_charge_mana(pl, TouhouMC_Races_Basic.plugin0, pluginpre, e);
			}
			//妖魔 金斧（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("youma") || race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("tenngu") || race.equalsIgnoreCase("kennyou")){
				mana = 25;
				if(handitem == Material.GOLD_AXE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"斧を構えた！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_IDLE, 1.0F, 1.0F);
						pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
							public void run(){
								Races_YUM.youma_golden_shockwave(pl);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 20L);
					}
				}
				mana = 15;
				if(handitem == Material.WOOD_AXE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"斧を構えた！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_IDLE, 1.0F, 1.0F);
						pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
							public void run(){
								Races_YUM.youma_wooden_upper(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 15L);
					}
				}
			}
			if (race.equalsIgnoreCase("kappa"))
			{
				mana = 30;
				if(handitem == Material.STONE_AXE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"TNTを構えた！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_IDLE, 1.0F, 1.0F);
						pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable(){
							public void run(){
								Races_YUM.kappa_stone_tnt(pl);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 20L);
					}
				}
			}
			//人間魔女の回復魔法（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("mazyo")||race.equalsIgnoreCase("ninngen") ) {
				mana = 25;
				if(handitem == Material.STICK && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_heal(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 20L);
					}
				}
				//魔女の風魔法（書き込み有）（前置詞有(詠唱有)
				mana = 30;
				if(handitem == Material.WOOD_SWORD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_wind(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 10L);
					}
				}
				//魔女の土魔法（書き込み有）（前置詞有(詠唱有)
				mana = 45;
				if (handitem == Material.STONE_SWORD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_dirt(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 60L);
					}
				}
				//魔女の火魔法（書き込み有）（前置詞有(詠唱有)
				mana = 30;
				if (handitem == Material.IRON_SWORD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_fire(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 20L);
					}
				}
				//魔女の水魔法（書き込み有）（前置詞有(詠唱有)
				mana = 60;
				if (handitem == Material.DIAMOND_SWORD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_water(pl, TouhouMC_Races_Basic.plugin0,e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 50L);
					}
				}
				//魔女の雷魔法（書き込み有）（前置詞有(詠唱有)
				mana = 70;
				if (handitem == Material.GOLD_SWORD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_NNG.magic_thunder(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 70L);
					}
				}
			}
			///天狗神風書き込み有）（前置詞有（ブースター処有
			mana = 40;
			if (race.equalsIgnoreCase("tenngu") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
				int boost = 0;
				if (pl.getMetadata("spilituse").get(0).asInt() > 0 && handitem == Material.FEATHER && (pl.isSneaking())){
					boost = pl.getMetadata("spilituse").get(0).asInt();
					Races_YUM.tenngu_kamikaze(pl, boost);
					conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
					TouhouMC_Races_Basic.SaveTMCConfig();
					pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
				}
			}
			///妖精のイリュージョン（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("kobito") || race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("satori")){
				if(handitem == Material.GOLD_SPADE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana, "金の槍を掲げた！")){
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUS.yousei_illusion(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 30L);
					}
				}
			}
			///樹人の毒散布（書き込み有）（前置詞有(詠唱有)
			mana = 35;
			if (race.equalsIgnoreCase("kibito")){
				if(handitem == Material.STONE_SPADE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"花は開きつつある！")){
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.SILVERFISH_WALK, 1, -1);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUS.kibito_venom(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 45L);
					}
				}
			}
			///吸血鬼のカモフラージュ（書き込み有）（前置詞有(詠唱有)
			mana = 25;
			if (race.equalsIgnoreCase("kyuuketuki")){
				if(handitem == Material.WOOD_PICKAXE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"姿を変えつつある！")){
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BAT_HURT, 1, 0);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_AKM.kyuuketuki_vamp(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 5L);
					}
				}
			}
			mana = 40;
			//紅魔法（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("akuma")||race.equalsIgnoreCase("oni")||race.equalsIgnoreCase("kyuuketuki") ) {
				if(handitem == Material.STONE_PICKAXE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_AKM.akuma_red_magic(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 80L);
					}
				}
			}
			mana = 40;
			//精霊の召喚（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ) {
				if(handitem == Material.STONE_HOE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"召喚！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_SIR.seirei_summon(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 100L);
					}
				}
			}
			mana = 5;
			//精霊の光弾（書き込み有）(詠唱有)
			if (race.equalsIgnoreCase("seirei")||race.equalsIgnoreCase("hannrei")||race.equalsIgnoreCase("sourei")||race.equalsIgnoreCase("onnryou") ) {
				if(handitem == Material.WOOD_HOE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"詠唱！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_SIR.seirei_lightball(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 30L);
					}
				}
			}
			mana = 50;
			//半霊の召喚（書き込み有）(詠唱有)
			if (race.equalsIgnoreCase("hannrei")) {
				if(handitem == Material.GOLD_HOE && (pl.isSneaking()))
					{
					if(Races_Global.magic_iscastable(pl , mana,"行け！半霊！") ){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_CHARGE, 1, 1);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_SIR.hannrei_hannrei_ball(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 5L);
					}
				}
			}
			mana = 60;
			//騒霊のオーケストラ（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("sourei")) {
				if(handitem == Material.IRON_HOE && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana,"レッツオーケストラ！！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 0);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_SIR.hannrei_hannrei_ball(pl, TouhouMC_Races_Basic.plugin0);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 120L);
					}
				}
			}
		}
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			///妖獣人魚獣人
			if (race.equalsIgnoreCase("youzuu") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("zyuuzin")) {
				//妖獣の狼召喚（書き込み有）（前置詞有(詠唱有)
				mana = 15;
				if (handitem == Material.FISHING_ROD && (pl.isSneaking())) {
					if(Races_Global.magic_iscastable(pl , mana,"召喚！！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUZ.yuz_summon_wolf(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 30L);
					}
				}
			}
			//式のネコ召喚（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("siki") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0 ) {
				mana = 15;
				if (handitem == Material.FISHING_ROD && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana ,"召喚！！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 2);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUZ.siki_summon_oc(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 40L);
					}
				}
			}
			//妖獣の強化（書き込み有）（前置詞有(詠唱有)
			if (race.equalsIgnoreCase("youzyuu") || race.equalsIgnoreCase("zyuuzin") || race.equalsIgnoreCase("ninngyo") || race.equalsIgnoreCase("siki")) {
				mana = 35;
				if (handitem == Material.BOW && (pl.isSneaking())){
					if(Races_Global.magic_iscastable(pl , mana ,"強化魔法！")){
						MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
						pl.setMetadata("casting", casting);
						conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
						TouhouMC_Races_Basic.SaveTMCConfig();
						pl.getWorld().playSound(pl.getLocation(), Sound.DONKEY_ANGRY, 1, 1);
						pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
						TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
							public void run() {
								Races_YUZ.youzyu_gainenergy(pl, TouhouMC_Races_Basic.plugin0, e);
								MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
								pl.setMetadata("casting", casting);
							}
						}, 15L);
					}
				}
			}
		}
	}

	//クリック関連の処理(Entity)
	public void onPlayerInteractEntity(final PlayerInteractEntityEvent e){
		//非人間村人規制前置詞有
		int mana = 0;
		final Player pl = e.getPlayer();
		Material handitem = pl.getItemInHand().getType();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		if (race.equalsIgnoreCase("ninngen") == false && race.equalsIgnoreCase("mazyo") == false && race.equalsIgnoreCase("houraizin") == false && race.equalsIgnoreCase("gennzinnsin") == false && race.equalsIgnoreCase("sibito") == false && race.equalsIgnoreCase("sennninn") == false) {
			pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.GRAY + "このニンゲンは何を話しているんだろう・・・");
			pl.closeInventory();
			e.setCancelled(true);
		}
		//鬼の怪力（書き込み有）（前置詞有(詠唱有)
		mana = 30;
		if (race.equalsIgnoreCase("oni")){
			if(handitem == Material.IRON_PICKAXE && e.getRightClicked() instanceof LivingEntity && (pl.isSneaking())){
				if(Races_Global.magic_iscastable(pl , mana,"拳を構えた！")){
					final LivingEntity entity = (LivingEntity) e.getRightClicked();
					MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(true));
					pl.setMetadata("casting", casting);
					conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
					TouhouMC_Races_Basic.SaveTMCConfig();
					pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 2);
					pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
					TouhouMC_Races_Basic.plugin0.getServer().getScheduler().scheduleSyncDelayedTask(TouhouMC_Races_Basic.plugin0, new Runnable() {
						public void run() {
							Races_AKM.oni_kairiki(pl, TouhouMC_Races_Basic.plugin0, e, entity);
							MetadataValue casting = new FixedMetadataValue(TouhouMC_Races_Basic.plugin0, Boolean.valueOf(false));
							pl.setMetadata("casting", casting);
						}
					}, 20L);
				}
			}
		}
	}

	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
		int mana = 30;
		if (event.getDamager() instanceof Player) {
			Player pl = (Player) event.getDamager();
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//死人
			if (race.equalsIgnoreCase("sibito") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.sibito_deadattack(pl, TouhouMC_Races_Basic.plugin0, event);
			//現人神
			if (race.equalsIgnoreCase("gennzinnsin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_NNG.gennzinnsin_luckyattack(pl, TouhouMC_Races_Basic.plugin0, event);
			//悪魔
			if (race.equalsIgnoreCase("akuma") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_AKM.akuma_dark_attack(pl, TouhouMC_Races_Basic.plugin0, event);
			//鬼
			if (race.equalsIgnoreCase("oni") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_AKM.oni_closed_attack(pl, TouhouMC_Races_Basic.plugin0, event);
			//吸血鬼
			if (race.equalsIgnoreCase("kyuuketuki") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_AKM.kyuuketuki_shadow_attack(pl, TouhouMC_Races_Basic.plugin0, event);
			//神
			if (race.equalsIgnoreCase("kami") || race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("yakusin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_YNK.kami_faith_attack(pl, TouhouMC_Races_Basic.plugin0, event, boost, conf);
			//豊穣神
			if (race.equalsIgnoreCase("houzyousin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_YNK.houzyousin_potato(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//グローバル
			if (pl.getMetadata("spilituse").get(0).asDouble() < 0){
				event.setDamage(event.getDamage() / 2D);
				if (pl.isSneaking()){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "貴方は霊力再生モードの為本気を出せません！");
				}
			}
		}
		mana = 20;
		if (event.getEntity() instanceof Player) {
			Player pl = (Player) event.getEntity();
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//蓬莱人
			if (race.equalsIgnoreCase("houraizin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana) Races_NNG.houraizin_reverselife_Entity(pl, TouhouMC_Races_Basic.plugin0, event);
			//妖精 (小人除く)
			if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("satori") || race.equalsIgnoreCase("kibito") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana) Races_YUS.yousei_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
			//小人
			if (race.equalsIgnoreCase("kobito")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= mana) Races_YUS.kobito_glaze(pl, TouhouMC_Races_Basic.plugin0, event);
			//サトリ
			if (race.equalsIgnoreCase("satori")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 50) Races_YUS.satori_satori(pl, TouhouMC_Races_Basic.plugin0, event);
			//精霊
			if (race.equalsIgnoreCase("seirei")|| race.equalsIgnoreCase("hannrei")|| race.equalsIgnoreCase("sourei")|| race.equalsIgnoreCase("onnryou") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana && (pl.isSneaking()) ) Races_SIR.seirei_mighty_guard(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//怨霊
			if (race.equalsIgnoreCase("onnryou")&& conf.getInt("user." + pl.getUniqueId() + ".split") >= 20) Races_SIR.onnryou_never_vanish(pl, TouhouMC_Races_Basic.plugin0, event, boost);
			//神
			if (race.equalsIgnoreCase("kami") || race.equalsIgnoreCase("houzyousin") || race.equalsIgnoreCase("yakusin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_YNK.kami_faith_defence(pl, TouhouMC_Races_Basic.plugin0, event, boost, conf);
			//厄神
			if (race.equalsIgnoreCase("yakusin") && conf.getInt("user." + pl.getUniqueId() + ".split") >= mana)
				Races_YNK.yakusin_darkside(pl, TouhouMC_Races_Basic.plugin0, event);
			//グローバル
			if (pl.getMetadata("spilituse").get(0).asDouble() < 0){
				event.setDamage(event.getDamage() * 1.5D);
				if (pl.isSneaking()){
					pl.sendMessage(TouhouMC_Races_Basic.tmc_Races_pre + ChatColor.RED + "貴方は霊力再生モードの為非常に柔いです！");
				}
			}
		}
	}

	//ダメージ関連の処理(攻撃以外)
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		int mana = 25;
		Entity ent = e.getEntity();
		if(ent instanceof Player){
			Player pl = (Player) ent;
			int boost = pl.getMetadata("spilituse").get(0).asInt();
			String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
			//河童
			if (race.equalsIgnoreCase("kappa") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana){
				if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) e.setCancelled(true);
			}
			//天狗
			if(race.equalsIgnoreCase("tenngu") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana){
				if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
					if(pl.isSneaking()){
						double rnd = Math.random();
						if (rnd > 0.5D){
							if (rnd > 0.3D){
								e.setCancelled(true);
							}else {
								e.setDamage(e.getDamage() / 15.0D);
							}
						}else {
							e.setDamage(e.getDamage() / 10.0D);
						}
					}else {
						e.setDamage(e.getDamage() / 8.0D);
					}
				}
			}
			//妖精
			if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("satori") || race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("kobito") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_YUS.yousei_fall_protection(pl, TouhouMC_Races_Basic.plugin0, e);
			//悪魔
			if (race.equalsIgnoreCase("akuma")|| race.equalsIgnoreCase("oni")|| race.equalsIgnoreCase("kyuuketuki")&& conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_AKM.akuma_antiheat_body(pl, TouhouMC_Races_Basic.plugin0, e);
			//吸血鬼
			if (race.equalsIgnoreCase("kyuuketuki") && conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_AKM.kyuuketuki_antiallfire_body(pl, TouhouMC_Races_Basic.plugin0, e);
			//精霊
			if (race.equalsIgnoreCase("seirei")|| race.equalsIgnoreCase("hannrei")|| race.equalsIgnoreCase("sourei")|| race.equalsIgnoreCase("onnryou")&& conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana && (pl.isSneaking())) Races_SIR.seirei_mighty_guard(pl, TouhouMC_Races_Basic.plugin0, e, boost);
			//豊穣神
			if (race.equalsIgnoreCase("houzyousin")&& conf.getInt("user." + pl.getUniqueId() + ".spilit") >= mana) Races_YNK.houzyousin_feed(pl, TouhouMC_Races_Basic.plugin0, e);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player pl = event.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
		//人魚高水泳書き込み有）（ブースター処有
		mana = 1;
		if (race.equalsIgnoreCase("ninngyo") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
			int boost = 0;
			if (pl.getMetadata("spilituse").get(0).asInt() > 0) boost = 1;
			Races_YUZ.ninngyo_swimming(pl, TouhouMC_Races_Basic.plugin0, event,boost);
			if (boost == 1){
				conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
				TouhouMC_Races_Basic.SaveTMCConfig();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent e){
		Player pl = e.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		int mana = 0;
		//妖精羽ばたき
		mana = 5;
		if (race.equalsIgnoreCase("yousei") || race.equalsIgnoreCase("kobito") || race.equalsIgnoreCase("kibito") || race.equalsIgnoreCase("satori") && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana){
			if (!pl.isOnGround() && pl.isSneaking() && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana ){
				Races_YUS.yousei_feather(pl, TouhouMC_Races_Basic.plugin0);
				conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
				TouhouMC_Races_Basic.SaveTMCConfig();
				pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
			}
		}
		//仙人の壁抜
		mana = 10;
		if (race.equalsIgnoreCase("sennnin")) {
			if ((!pl.isOnGround()) && (pl.isSneaking()) && conf.getDouble("user." + pl.getUniqueId() + ".spilit") >= mana) {
				Races_NNG.sennnin_passthough(pl, TouhouMC_Races_Basic.plugin0);
				conf.set("user." + pl.getUniqueId() + ".spilit", conf.getDouble("user." + pl.getUniqueId() + ".spilit") - mana);
				TouhouMC_Races_Basic.SaveTMCConfig();
				pl.sendMessage(pluginpre + ChatColor.GREEN + "霊力" + ChatColor.LIGHT_PURPLE + conf.getDouble(new StringBuilder("user.").append(pl.getUniqueId()).append(".spilit").toString()));
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		//リスポンをトリガーとして大体力調整
		Player pl = event.getPlayer();
		String race = conf.getString("user." + pl.getUniqueId() + ".race").toString();
		if (race.equalsIgnoreCase("youma") || race.equalsIgnoreCase("kappa") || race.equalsIgnoreCase("tenngu")){
			pl.setMaxHealth(120.0D);
		}else if(race.equalsIgnoreCase("kennyou")){
			pl.setMaxHealth(150.0D);
		}else{
			pl.setMaxHealth(100D);
		}
	}

	//ここから先 追加
	//釣りをキャンセル
	@EventHandler
	public void onPlayerFishing(PlayerFishEvent e){
		e.setCancelled(true);
	}
	//エンチャントをキャンセル
	@EventHandler
	public void onEnchantments(EnchantItemEvent e){
		e.setCancelled(true);
	}
}
