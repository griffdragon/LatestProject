package me.bronc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class Main extends JavaPlugin implements Listener {

	public Inventory i;

	public boolean isAlive;

	public Inventory inv;
	public ItemStack archer2;
	public ItemStack archer1;
	public String prefix = ChatColor.translateAlternateColorCodes('&', "&8[&3Baron&8] &7");

	ArrayList<Player> archerClass = new ArrayList<Player>();
	ArrayList<Player> arrowStorm = new ArrayList<Player>();
	ArrayList<Player> teleArrow = new ArrayList<Player>();
	ArrayList<Player> explosivearrow = new ArrayList<Player>();

	ArrayList<Player> burningsoul = new ArrayList<Player>();

	ArrayList<UUID> UUID = new ArrayList<UUID>();

	ArrayList<Entity> mobs = new ArrayList<Entity>();

	private int cooldowntime = 10;
	private int righttime = 5;
	private int barragetime = 15;
	private int teletime = 25;

	public boolean alive;

	private int b1t = 20;
	private int b2t = 50;
	private int b3t = 60;

	private int et = 10;

	private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> right = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> barrage = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> telearrow = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> explosive = new HashMap<UUID, Long>();

	private HashMap<Player, ArrayList<UUID>> minions = new HashMap<Player, ArrayList<UUID>>();
	private HashMap<UUID, Player> owner = new HashMap<>();

	private HashMap<UUID, Long> brute1 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> brute2 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> brute3 = new HashMap<UUID, Long>();

	private HashMap<UUID, Long> healer1 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> healer2 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> healer3 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> healer4 = new HashMap<UUID, Long>();

	private HashMap<UUID, Long> summoner1 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> summoner2 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> summoner3 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> summoner4 = new HashMap<UUID, Long>();

	private HashMap<UUID, Long> warrior = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> warrior1 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> warrior2 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> warrior3 = new HashMap<UUID, Long>();

	private HashMap<UUID, Long> mage = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> mage1 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> mage2 = new HashMap<UUID, Long>();
	private HashMap<UUID, Long> mage3 = new HashMap<UUID, Long>();

	FileConfiguration config = this.getConfig();

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("arrowstorm") && sender instanceof Player) {
			arrowStorm.add(p);
			p.sendMessage(prefix + ChatColor.GRAY + "Your Next Shot will be an arrowstorm");

		}
		if (cmd.getName().equalsIgnoreCase("telearrow") && sender instanceof Player) {
			teleArrow.add(p);
			p.sendMessage(prefix + ChatColor.GRAY + "Your Next Shot will be a TeleArrow");

		}
		if (cmd.getName().equalsIgnoreCase("skills") && sender instanceof Player) {
			skills(p);
			p.sendMessage("" + attackMod(p));
			saveConfig();
		}
		if (cmd.getName().equalsIgnoreCase("detonate") && sender instanceof Player) {

		}
		if (cmd.getName().equalsIgnoreCase("class") && sender instanceof Player) {
			if (args.length == 2) {
				Player targetPlayer = Bukkit.getPlayer(args[0]);
				String classnew = String.valueOf(args[1]);
				this.getConfig().set(targetPlayer.getName(), classnew);
				p.sendMessage(
						prefix + "Successfuly set " + targetPlayer.getName() + "'s class to " + classnew.toUpperCase());
				saveConfig();
			} else {
				joinMenu(p);
			}
		}
		return true;
	}

	public void joinMenu(Player p) {

		inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Classes");
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(4, Items.emerald());
		inv.setItem(2, Items.archer());
		inv.setItem(3, Items.brute());
		inv.setItem(1, Items.mage());
		inv.setItem(5, Items.summoner());
		inv.setItem(6, Items.warrior());
		inv.setItem(7, Items.healer());

		p.openInventory(inv);

	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		archer1 = new ItemStack(Material.BOOK);
		ItemMeta aMeta1 = archer1.getItemMeta();
		aMeta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aBarrage&7«"));
		archer1.setItemMeta(aMeta1);
		archer2 = new ItemStack(Material.BOOK);
		ItemMeta aMeta2 = archer2.getItemMeta();
		aMeta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aTelearrow&7«"));
		archer2.setItemMeta(aMeta2);
		Player p = e.getPlayer();
		this.getConfig().addDefault(p.getName(), "");
		if (this.getConfig().getString(p.getName() + ".Class").equals("Archer")) {
			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.GREEN + "Archer");
			right.put(p.getUniqueId(), System.currentTimeMillis());
			barrage.put(p.getUniqueId(), System.currentTimeMillis());
			telearrow.put(p.getUniqueId(), System.currentTimeMillis());
			explosive.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(1, archer1);
			p.getInventory().setItem(2, archer2);
			p.getInventory().setItem(3, Items.archer3());
		} else if (this.getConfig().getString(p.getName() + ".Class").equals("Brute")) {
			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.GREEN + "Brute");
			cooldown.put(p.getUniqueId(), System.currentTimeMillis());
			brute1.put(p.getUniqueId(), System.currentTimeMillis());
			brute2.put(p.getUniqueId(), System.currentTimeMillis());
			brute3.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(1, Items.brute1());
			p.getInventory().setItem(2, Items.brute2());
			p.getInventory().setItem(3, Items.brute3());
		} else if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("Healer")) {

			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.YELLOW + "Healer");
			healer1.put(p.getUniqueId(), System.currentTimeMillis());
			healer2.put(p.getUniqueId(), System.currentTimeMillis());
			healer3.put(p.getUniqueId(), System.currentTimeMillis());
			healer4.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(1, Items.healer1());
			p.getInventory().setItem(2, Items.healer2());
			p.getInventory().setItem(3, Items.healer3());
		} else if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("warrior")) {

			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.RED + "Warrior");
			warrior.put(p.getUniqueId(), System.currentTimeMillis());
			warrior1.put(p.getUniqueId(), System.currentTimeMillis());
			warrior2.put(p.getUniqueId(), System.currentTimeMillis());
			warrior3.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(2, Items.warrior1());
			p.getInventory().setItem(3, Items.warrior2());
			p.getInventory().setItem(3, Items.warrior3());
		} else if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("mage")) {

			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.DARK_PURPLE + "Mage");
			mage.put(p.getUniqueId(), System.currentTimeMillis());
			mage1.put(p.getUniqueId(), System.currentTimeMillis());
			mage2.put(p.getUniqueId(), System.currentTimeMillis());
			mage3.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(2, Items.mage1());
			p.getInventory().setItem(3, Items.mage2());
			p.getInventory().setItem(3, Items.mage3());
		} else if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("summoner")) {

			p.sendMessage(prefix + ChatColor.GRAY + "Selected class: " + ChatColor.DARK_PURPLE + "Summoner");
			summoner1.put(p.getUniqueId(), System.currentTimeMillis());
			summoner2.put(p.getUniqueId(), System.currentTimeMillis());
			summoner3.put(p.getUniqueId(), System.currentTimeMillis());
			summoner4.put(p.getUniqueId(), System.currentTimeMillis());
			p.getInventory().setItem(2, Items.summoner1());
			p.getInventory().setItem(3, Items.summoner2());
			p.getInventory().setItem(3, Items.summoner3());
		} else {
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					joinMenu(p);
				}
			}, 5L);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		archer1 = new ItemStack(Material.BOOK);
		ItemMeta aMeta1 = archer1.getItemMeta();
		aMeta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aBarrage&7«"));
		archer1.setItemMeta(aMeta1);

		archer2 = new ItemStack(Material.BOOK);
		ItemMeta aMeta2 = archer2.getItemMeta();
		aMeta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7»&aTelearrow&7«"));
		archer2.setItemMeta(aMeta2);
		if (e.getClickedInventory() != null && e.getClickedInventory().equals(i)) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(attack(p))) {
				if (config.getInt(p.getName() + ".UnusedPoints") > 0) {
					p.closeInventory();
					config.set(p.getName() + ".Attack", attackMod(p) + 1);
					config.set(p.getName() + ".UnusedPoints", unused(p) - 1);
					saveConfig();
					skills(p);
				} else {
					p.sendMessage(prefix + "No skillpoints left.");
				}
			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(totalPoints(p))) {
				p.closeInventory();
				config.set(p.getName() + ".Attack", 0);
				config.set(p.getName() + ".UnusedPoints", p.getLevel());
				saveConfig();
				skills(p);
			}
		}

		if (e.getClickedInventory() != null && e.getClickedInventory().equals(inv)) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.archer())) {
				archerClass.add(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &aArcher &7Class"));
				p.closeInventory();
				e.setCancelled(true);
				right.put(p.getUniqueId(), System.currentTimeMillis());
				barrage.put(p.getUniqueId(), System.currentTimeMillis());
				telearrow.put(p.getUniqueId(), System.currentTimeMillis());
				explosive.put(p.getUniqueId(), System.currentTimeMillis());

				String path = p.getName();

				p.getInventory().setItem(1, archer1);
				p.getInventory().setItem(2, archer2);
				p.getInventory().setItem(3, Items.archer3());

				// List<String> list = config.getStringList(path);
				// if (list == null)
				// list = new ArrayList<>();
				// list.add(p.getName().toLowerCase());
				config.set(path + ".Class", "Archer");

				saveConfig();

			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.warrior())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &6Warrior &7Class"));
				p.closeInventory();
				e.setCancelled(true);

				String path = p.getName();
				p.getInventory().setItem(1, Items.warrior1());
				p.getInventory().setItem(2, Items.warrior2());
				p.getInventory().setItem(3, Items.warrior3());
				warrior.put(p.getUniqueId(), System.currentTimeMillis());
				warrior1.put(p.getUniqueId(), System.currentTimeMillis());
				warrior2.put(p.getUniqueId(), System.currentTimeMillis());
				warrior3.put(p.getUniqueId(), System.currentTimeMillis());

				// List<String> list = config.getStringList(path);
				// if (list == null)
				// list = new ArrayList<>();
				// list.add(p.getName().toLowerCase());
				config.set(path + ".Class", "Warrior");

				saveConfig();

			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.mage())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &cMage &7Class"));
				p.closeInventory();
				e.setCancelled(true);

				String path = p.getName();
				p.getInventory().setItem(1, Items.mage1());
				p.getInventory().setItem(2, Items.mage2());
				p.getInventory().setItem(3, Items.mage3());
				mage.put(p.getUniqueId(), System.currentTimeMillis());
				mage1.put(p.getUniqueId(), System.currentTimeMillis());
				mage2.put(p.getUniqueId(), System.currentTimeMillis());
				mage3.put(p.getUniqueId(), System.currentTimeMillis());

				// List<String> list = config.getStringList(path);
				// if (list == null)
				// list = new ArrayList<>();
				// list.add(p.getName().toLowerCase());
				config.set(path + ".Class", "mage");

				saveConfig();

			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.brute())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &5Brute &7Class"));
				p.closeInventory();

				String path = p.getName();
				p.getInventory().setItem(1, Items.brute1());
				p.getInventory().setItem(2, Items.brute2());
				p.getInventory().setItem(3, Items.brute3());
				cooldown.put(p.getUniqueId(), System.currentTimeMillis());
				brute1.put(p.getUniqueId(), System.currentTimeMillis());
				brute2.put(p.getUniqueId(), System.currentTimeMillis());
				brute3.put(p.getUniqueId(), System.currentTimeMillis());

				/*
				 * List<String> list = config.getStringList(path); if (list ==
				 * null) list = new ArrayList<>();
				 * list.add(p.getName().toLowerCase());
				 */
				config.set(path + ".Class", "Brute");
				e.setCancelled(true);

				saveConfig();

			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.summoner())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &dSummoner &7Class"));
				p.closeInventory();

				String path = p.getName();
				p.getInventory().setItem(1, Items.summoner1());
				p.getInventory().setItem(2, Items.summoner2());
				p.getInventory().setItem(3, Items.summoner3());
				summoner4.put(p.getUniqueId(), System.currentTimeMillis());
				summoner1.put(p.getUniqueId(), System.currentTimeMillis());
				summoner2.put(p.getUniqueId(), System.currentTimeMillis());
				summoner3.put(p.getUniqueId(), System.currentTimeMillis());

				/*
				 * List<String> list = config.getStringList(path); if (list ==
				 * null) list = new ArrayList<>();
				 * list.add(p.getName().toLowerCase());
				 */
				config.set(path + ".Class", "Summoner");
				e.setCancelled(true);

				saveConfig();

			}
			if (e.getCurrentItem() != null && e.getCurrentItem().equals(Items.healer())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Selected &eHealer &7Class"));
				p.closeInventory();

				String path = p.getName();
				p.getInventory().setItem(1, Items.healer1());
				p.getInventory().setItem(2, Items.healer2());
				p.getInventory().setItem(3, Items.healer3());
				healer1.put(p.getUniqueId(), System.currentTimeMillis());
				healer2.put(p.getUniqueId(), System.currentTimeMillis());
				healer3.put(p.getUniqueId(), System.currentTimeMillis());
				healer4.put(p.getUniqueId(), System.currentTimeMillis());

				config.set(path + ".Class", "Healer");
				e.setCancelled(true);

				saveConfig();
			}
		}
		if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
				&& e.getCurrentItem().getItemMeta().equals(aMeta1)
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(aMeta2)
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.brute1().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.brute2().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.brute3().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.archer3().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.healer1().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.healer2().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.healer3().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.mage1().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.mage2().getItemMeta())
				|| e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
						&& e.getCurrentItem().getItemMeta().equals(Items.mage3().getItemMeta())) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void Summoner(PlayerInteractEvent e) throws InterruptedException {
		Random rd = new Random();
		int t = 0;
		Player p = e.getPlayer();
		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("summoner")) {
			if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					for (int i = 0; i < 31; i++) {
						if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
								&& p.getInventory().getItemInMainHand().getItemMeta().getLore()
										.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
							if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
									&& p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
								t = i + GetDamage.getDamage(p) + attackMod(p);
							} else {
								t = i + attackMod(p);
							}
						}
					}
					summonerRay(e.getPlayer(), e.getPlayer().getLocation(), t, EnumParticle.SMOKE_NORMAL);
				}
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (summoner1.containsKey(p.getUniqueId())) {
						long secondsleft = ((summoner1.get(p.getUniqueId()) / 1000) + 7)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							int rand = rd.nextInt(3) + 1;
							summoner1.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "Your spell successfuly summoned " + ChatColor.GREEN + rand
									+ ChatColor.GRAY + " Minion(s).");
							for (int g = 0; g < rand; g++) {
								Zombie v;
								v = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
								v.setBaby(true);
								v.setTarget(p);
								UUID.add(v.getUniqueId());
								minions.put(p, UUID);
								owner.put(v.getUniqueId(), p);
								isAlive = true;
								v.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
								this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
									public void run() {
										if (isAlive == true) {
											v.remove();
										}
									}
								}, 500L);
							}
						}
					}
				}
			}
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.summoner1())) {
					if (summoner2.containsKey(p.getUniqueId())) {
						long secondsleft = ((summoner2.get(p.getUniqueId()) / 1000) + 30)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							int rand = rd.nextInt(4) + 6;
							summoner2.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "Your spell successfuly summoned " + ChatColor.GREEN + rand
									+ ChatColor.GRAY + " Minion(s).");
							for (int g = 0; g < rand; g++) {
								Zombie v;
								v = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
								v.setBaby(true);
								v.setTarget(p);
								UUID.add(v.getUniqueId());
								minions.put(p, UUID);
								owner.put(v.getUniqueId(), p);
								v.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
								this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
									public void run() {
										v.remove();
									}
								}, 500L);
							}
						}
					}
				}
				if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.summoner2())) {
					if (summoner3.containsKey(p.getUniqueId())) {
						long secondsleft = ((summoner3.get(p.getUniqueId()) / 1000) + 10)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							summoner3.put(p.getUniqueId(), System.currentTimeMillis());
							for (Entity es : p.getNearbyEntities(50, 50, 50)) {
								isAlive = false;
								if (es instanceof LivingEntity) {
									if (minions.get(p).contains(es.getUniqueId())) {
										if (es instanceof Zombie) {
											for (Entity ents : es.getNearbyEntities(5, 5, 5)) {
												if (ents instanceof LivingEntity) {
													if (!(ents instanceof Player)) {
														((LivingEntity) ents).damage(
																5 + (attackMod(p) / 2) + (GetDamage.getDamage(p) / 2),
																p);
													}
												}
											}
											es.remove();
										}
									}
								}
							}
						}
					}
				}
			}
		} else {

		}
	}

	@EventHandler
	public void minions(EntityDamageByEntityEvent e) {

		Entity ent = e.getDamager();
		Player p = owner.get(ent.getUniqueId());
		LivingEntity et = (LivingEntity) e.getEntity();
		if (ent instanceof Player) {
			if (e.getEntity() instanceof LivingEntity) {
				if (minions.get((Player) e.getDamager()) != null
						&& minions.get((Player) e.getDamager()).contains(e.getEntity().getUniqueId())) {
					e.setCancelled(true);
					ent.sendMessage(ChatColor.GREEN + "This is " + owner.get(et.getUniqueId()).getName().toString()
							+ "'s Minion.");
				} else {
					for (Entity es : ent.getNearbyEntities(20, 20, 20)) {
						if (minions.get((Player) e.getDamager()) != null
								&& minions.get((Player) e.getDamager()).contains(es.getUniqueId())) {
							if (owner.get(es.getUniqueId()) == ent) {
								((Creature) es).setTarget(et);
							}
						}
					}
				}

			}

		}
		if (ent instanceof LivingEntity) {
			if (UUID.contains(ent.getUniqueId())) {
				e.setCancelled(true);
				if (!(et instanceof Player)) {
					et.damage(1 + (attackMod(p) / 4) + (p.getLevel() / 4), p);
				}
			}
		}

	}

	@EventHandler
	public void Mage(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		double t = 0;

		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("mage")) {
			if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					for (int i = 0; i < 31; i++) {
						if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
								&& p.getInventory().getItemInMainHand().getItemMeta().getLore()
										.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
							if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
									&& p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
								t = i + GetDamage.getDamage(p) + attackMod(p);
							} else {
								t = i + attackMod(p);
							}
						}
					}
					magespell(e.getPlayer(), e.getPlayer().getLocation(), t, EnumParticle.SMOKE_NORMAL,
							EnumParticle.FLAME);
				}
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (mage.containsKey(p.getUniqueId())) {
						long secondsleft = ((mage.get(p.getUniqueId()) / 1000) + 7)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							for (int i = 0; i < 31; i++) {
								if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
										&& p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(
												ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
									if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
											&& p.getInventory().getLeggings() != null
											&& p.getInventory().getBoots() != null) {
										t = i + GetDamage.getDamage(p) + attackMod(p);
									} else {
										t = i + attackMod(p);
									}
								}
							}
							mage.put(p.getUniqueId(), System.currentTimeMillis());
							fireball(p, p.getLocation(), (double) t * 4, EnumParticle.FLAME, EnumParticle.SMOKE_LARGE);
						}
					}
				}

			}
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (p.getInventory().getItemInMainHand().equals(Items.mage1())) {
					if (mage1.containsKey(p.getUniqueId())) {
						long secondsleft = ((mage1.get(p.getUniqueId()) / 1000) + 12)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							mage1.put(p.getUniqueId(), System.currentTimeMillis());
							new BukkitRunnable() {
								double x = 0;

								public void run() {
									x++;
									Vector dir = p.getLocation().getDirection().normalize();
									Vector vec = new Vector(dir.getX() * 1D, dir.getY() * 1D, dir.getZ() * 1D);
									p.setVelocity(vec);
									p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_SHOOT, 5, 5);
									firecharge(p, p.getLocation(), 5, EnumParticle.FLAME);
									for (Entity es : p.getNearbyEntities(3, 3, 3)) {
										if (!(es instanceof Player)) {
											if (es instanceof LivingEntity) {

												((LivingEntity) es).damage(3 + attackMod(p), p);
												((LivingEntity) es).setFireTicks(50);
											}
										}
									}
									if (x > 14) {
										this.cancel();
									}
								}
							}.runTaskTimer(this, 0, 1);
						}
					}
				}
				if (p.getInventory().getItemInMainHand().equals(Items.mage2())) {
					if (mage2.containsKey(p.getUniqueId())) {
						long secondsleft = ((mage2.get(p.getUniqueId()) / 1000) + 25)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							mage2.put(p.getUniqueId(), System.currentTimeMillis());
							burningsoul.add(p);
							p.sendMessage(ChatColor.GRAY + "You used " + ChatColor.GREEN + "Burning Soul.");
							new BukkitRunnable() {

								public void run() {
									burningsoul.remove(p);
								}
							}.runTaskLater(this, 200);
						}
					}
				}
				if (p.getInventory().getItemInMainHand().equals(Items.mage3())) {
					if (mage3.containsKey(p.getUniqueId())) {

						long secondsleft = ((mage3.get(p.getUniqueId()) / 1000) + 35)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							mage3.put(p.getUniqueId(), System.currentTimeMillis());
							for (

									int i = 0; i < 31; i++) {
								if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
										&& p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(
												ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
									if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
											&& p.getInventory().getLeggings() != null
											&& p.getInventory().getBoots() != null) {
										t = i + GetDamage.getDamage(p) + attackMod(p);
									} else {
										t = i + attackMod(p);
									}
								}
							}

							euruption(p, p.getLocation(), t / 4);
						}
					}
				}

			}
		} else {

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Healer(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		double t = 0;
		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("Healer")) {
			if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					for (int i = 0; i < 31; i++) {
						if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
								&& p.getInventory().getItemInMainHand().getItemMeta().getLore()
										.contains(ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
							if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
									&& p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null) {
								t = i + GetDamage.getDamage(p) + attackMod(p);
							} else {
								t = i + attackMod(p);
							}
						}
					}
					frostRay(e.getPlayer(), e.getPlayer().getLocation(), t, EnumParticle.SMOKE_NORMAL);
				}
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (healer1.containsKey(p.getUniqueId())) {
						long secondsleft = ((healer1.get(p.getUniqueId()) / 1000) + 5)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {

							if (p.getHealth() < p.getMaxHealth() - 5) {
								healer1.put(p.getUniqueId(), System.currentTimeMillis());
								double health = p.getHealth();
								p.setHealth(health + 4);
								p.sendMessage(ChatColor.GRAY + "You used " + ChatColor.GREEN + "cure wounds.");
							} else {
								p.sendMessage(ChatColor.GRAY + "You dont need to be healed.");
							}
						}
					}
				}
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.healer1())) {
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (healer2.containsKey(p.getUniqueId())) {
						long secondsleft = ((healer2.get(p.getUniqueId()) / 1000) + 15)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							healer2.put(p.getUniqueId(), System.currentTimeMillis());
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 1));
							for (Entity ps : p.getNearbyEntities(16, 16, 16)) {
								if (ps instanceof Player) {
									((CraftLivingEntity) ps)
											.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 1));
								}
							}
						}
					}
				}
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.healer2())) {
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (healer3.containsKey(p.getUniqueId())) {
						long secondsleft = ((healer3.get(p.getUniqueId()) / 1000) + 10)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							healer3.put(p.getUniqueId(), System.currentTimeMillis());
							healBeam(p, p.getLocation(), EnumParticle.VILLAGER_HAPPY);
						}
					}
				}
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.healer3())) {
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (healer4.containsKey(p.getUniqueId())) {
						long secondsleft = ((healer4.get(p.getUniqueId()) / 1000) + 15)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							healer4.put(p.getUniqueId(), System.currentTimeMillis());
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WEAKNESS);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WITHER);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.SLOW_DIGGING);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.SLOW);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WEAKNESS);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.POISON);
							p.setFireTicks(0);
							p.setMaxHealth(p.getHealth() + 10);
							for (Entity ps : p.getNearbyEntities(16, 16, 16)) {
								if (ps instanceof Player) {
									((CraftLivingEntity) ps).removePotionEffect(PotionEffectType.WEAKNESS);
									((CraftLivingEntity) ps).removePotionEffect(PotionEffectType.WITHER);
									((CraftLivingEntity) ps).removePotionEffect(PotionEffectType.SLOW_DIGGING);
									((CraftLivingEntity) ps).removePotionEffect(PotionEffectType.SLOW);
									((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WEAKNESS);
									((CraftLivingEntity) p).removePotionEffect(PotionEffectType.POISON);
									ps.setFireTicks(0);
									double health1 = ((Player) ps).getHealth();
									if (health1 < ((LivingEntity) ps).getMaxHealth() - 10) {
										((Player) ps).setHealth(health1 + 10);
										p.sendMessage(ChatColor.GRAY + "Healed " + ChatColor.GREEN + ps.getName());
									} else {
										((Player) ps).setHealth(health1 + (((Player) ps).getMaxHealth() - health1));
										p.sendMessage(ChatColor.GRAY + "This player is at full health.");
									}
								}
							}
						}
					}
				}
			}

		} else {

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Warrior(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("Warrior")) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
					if (warrior3.containsKey(p.getUniqueId())) {
						long secondsleft = ((warrior3.get(p.getUniqueId()) / 1000) + 17)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							warrior3.put(p.getUniqueId(), System.currentTimeMillis());
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 230, 2));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 230, 1));
						}
					}
				}
			}
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.warrior1())) {
					if (warrior.containsKey(p.getUniqueId())) {
						long secondsleft = ((warrior.get(p.getUniqueId()) / 1000) + 5)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							for (Entity es : p.getNearbyEntities(7, 7, 7)) {
								if (!(es instanceof Player)) {
									if (es instanceof LivingEntity) {
										((LivingEntity) es).damage(10 + attackMod(p), p);
										warrior.put(p.getUniqueId(), System.currentTimeMillis());
										Vector dir = p.getLocation().getDirection().normalize();
										Vector vec = new Vector(dir.getX() * 1.2D, 1D, dir.getZ() * 1.2D);
										es.setVelocity(vec);
										p.sendMessage(ChatColor.GRAY + "You used" + ChatColor.GREEN + " shockwave.");
									}
								}
							}
						}
					}
				}
				if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.warrior2())) {
					if (warrior1.containsKey(p.getUniqueId())) {
						long secondsleft = ((warrior1.get(p.getUniqueId()) / 1000) + 10)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							p.sendMessage(ChatColor.GRAY + "You Used" + ChatColor.GREEN + " charge.");
							warrior1.put(p.getUniqueId(), System.currentTimeMillis());
							Vector dir = p.getLocation().getDirection().normalize();
							Vector vec = new Vector(dir.getX() * 5D, .3D, dir.getZ() * 5D);
							p.setVelocity(vec);
							charge(p, p.getLocation(), EnumParticle.EXPLOSION_HUGE);
							for (Player ps : Bukkit.getServer().getOnlinePlayers()) {
								ps.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);
							}
							for (Entity es : p.getNearbyEntities(5, 5, 5)) {
								if (!(es instanceof Player)) {
									if (es instanceof LivingEntity) {
										((LivingEntity) es).damage(20 + attackMod(p), p);
									}
								}
							}
						}
					}
				}

				if (e.getPlayer().getInventory().getItemInMainHand().equals(Items.warrior3())) {
					if (warrior2.containsKey(p.getUniqueId())) {
						long secondsleft = ((warrior2.get(p.getUniqueId()) / 1000) + 30)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							p.sendMessage(ChatColor.GRAY + "Used" + ChatColor.GREEN + " full heal.");
							warrior2.put(p.getUniqueId(), System.currentTimeMillis());
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WEAKNESS);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WITHER);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.SLOW_DIGGING);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.SLOW);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.WEAKNESS);
							((CraftLivingEntity) p).removePotionEffect(PotionEffectType.POISON);
							p.setFireTicks(0);
							double he = p.getHealth();
							if (p.getHealth() < (p.getMaxHealth() - 20)) {
								p.setHealth(he + 20);
							} else {
								p.setHealth(he + (p.getMaxHealth() - he));
							}
						}
					}
				}
			}
		} else {

		}

	}

	@EventHandler
	public void Brute(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("Brute")) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (p.getInventory().getItemInMainHand() != null
						&& p.getInventory().getItemInMainHand().equals(Items.brute3())) {

					if (brute3.containsKey(p.getUniqueId())) {
						long secondsleft = ((brute3.get(p.getUniqueId()) / 1000) + b3t)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 230, 3));
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 230, 3));
							p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 350, 2));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 230, 3));
							brute3.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "You used" + ChatColor.GREEN + " Rampage");

						}
					}
				}
				if (p.getInventory().getItemInMainHand() != null
						&& p.getInventory().getItemInMainHand().equals(Items.brute1())) {

					if (brute1.containsKey(p.getUniqueId())) {
						long secondsleft = ((brute1.get(p.getUniqueId()) / 1000) + b1t)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 230, 1));
							brute1.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "You used" + ChatColor.GREEN + " Defensive Stance");

						}
					}
				}
				if (p.getInventory().getItemInMainHand() != null
						&& p.getInventory().getItemInMainHand().equals(Items.brute2())) {

					if (brute2.containsKey(p.getUniqueId())) {
						long secondsleft = ((brute2.get(p.getUniqueId()) / 1000) + b2t)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 560, 3));
							brute2.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "You used" + ChatColor.GREEN + " Magical Shield");

						}
					}
				}
			}
			if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)) {
				if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (cooldown.containsKey(p.getUniqueId())) {
						long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + cooldowntime)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {

							p.sendMessage(ChatColor.GRAY + "You used " + ChatColor.GREEN + "leap");
							Vector dir = p.getLocation().getDirection().normalize();
							Vector vec = new Vector(dir.getX() * 3D, .2D, dir.getZ() * 3D);
							p.setVelocity(vec);
							p.setFallDistance(-100.0F);
							p.setInvulnerable(true);
							cooldown.put(p.getUniqueId(), System.currentTimeMillis());

							new BukkitRunnable() {
								int i = 0;

								public void run() {
									i++;
									if (!(p.isOnGround())) {
										for (Entity e : p.getNearbyEntities(1.5, 1.5, 1.5)) {
											ItemStack hand = p.getInventory().getItemInMainHand();
											ItemMeta hMeta = hand.getItemMeta();
											for (int i = 0; i < 31; i++) {
												if (hMeta.getLore() != null && hMeta.getLore().contains(ChatColor
														.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
													((LivingEntity) e).damage(i * 2 + attackMod(p), p);
												}

											}
										}
										if (i > 5) {
											this.cancel();
										}
									}
								}

							}.runTaskTimer(this, 1, 1);
							new BukkitRunnable() {
								public void run() {
									p.setInvulnerable(false);
								}
							}.runTaskLater(this, 20L);

						}

					}

				}

			}
		} else {

		}

	}

	@EventHandler
	public void Archer(PlayerInteractEvent e) throws InterruptedException {

		Player p = e.getPlayer();
		ArrayList<Player> basic = new ArrayList<Player>();
		if (this.getConfig().getString(p.getName() + ".Class").equalsIgnoreCase("Archer")) {
			if (p.getInventory().getItemInMainHand().getType().equals(Material.BOW)) {
				if (e.getAction().equals(Action.LEFT_CLICK_AIR)) {
					if (teleArrow.contains(p)) {
						p.sendMessage(ChatColor.GRAY + "You Used" + ChatColor.GREEN + " Tele Arrow.");
						Location loc = p.getLocation();
						loc.add(0, 1.5, 0);
						Arrow arrow = p.getWorld().spawnArrow(loc, loc.getDirection(), 3, 1);
						arrow.setShooter(p);
						arrow.setBounce(false);
						new BukkitRunnable() {

							public void run() {

								p.teleport(arrow);
							}
						}.runTaskLater(this, 100L);
						p.playSound(loc, Sound.ENTITY_ARROW_SHOOT, 2, 2);
						teleArrow.remove(p);
					}
					if (arrowStorm.contains(p)) {
						p.sendMessage(ChatColor.GRAY + "You Used " + ChatColor.GREEN + "arrow storm.");
						new BukkitRunnable() {
							int n = 0;

							public void run() {
								n++;
								Location loc = p.getLocation();
								loc.add(0, 1.5, 0);
								Arrow arrow = p.getWorld().spawnArrow(loc, loc.getDirection(), 3, 1);
								arrow.setShooter(p);
								arrow.setBounce(false);
								p.playSound(loc, Sound.ENTITY_ARROW_SHOOT, 2, 2);
								for (int i = 0; i < 31; i++) {
									if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
											&& p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(
													ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
										if (p.getInventory().getHelmet() != null
												&& p.getInventory().getChestplate() != null
												&& p.getInventory().getLeggings() != null
												&& p.getInventory().getBoots() != null) {
											arrow.spigot().setDamage(i + GetDamage.getDamage(p) + attackMod(p));
										} else {
											arrow.spigot().setDamage(i + attackMod(p));
										}
									}
								}

								if (n > 10) {
									this.cancel();
								}
							}
						}.runTaskTimer(this, 1, 1);
						arrowStorm.remove(p);

					}
					if (!(arrowStorm.contains(p) && teleArrow.contains(p))) {

						// ItemStack hand =
						// p.getInventory().getItemInMainHand();
						// ItemMeta hMeta = hand.getItemMeta();
						Location loc = p.getLocation();
						loc.add(0, 1.5, 0);
						Arrow arrow = p.getWorld().spawnArrow(loc, loc.getDirection(), 2, 1);
						arrow.setShooter(p);
						p.playSound(loc, Sound.ENTITY_ARROW_SHOOT, 2, 2);
						/*
						 * for (int i = 0; i < 31; i++) { if (hMeta.getLore()
						 * .contains(ChatColor.translateAlternateColorCodes('&',
						 * "&8Damage: &c" + i))) { arrow.spigot().setDamage(i /
						 * 2 + .5); } }
						 */
						for (int i = 0; i < 31; i++) {
							if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
									&& p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(
											ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
								if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
										&& p.getInventory().getLeggings() != null
										&& p.getInventory().getBoots() != null) {
									arrow.spigot().setDamage(i + GetDamage.getDamage(p) + attackMod(p));
								} else {
									arrow.spigot().setDamage(i + attackMod(p));
								}
							}
						}
					}
				}
				if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if (right.containsKey(p.getUniqueId())) {
						long secondsleft = ((right.get(p.getUniqueId()) / 1000) + righttime)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							right.put(p.getUniqueId(), System.currentTimeMillis());
							Location loc = p.getLocation();
							loc.add(0, 1.5, 0);
							Arrow arrow = p.getWorld().spawnArrow(loc, loc.getDirection(), 3, 3);

							arrow.setKnockbackStrength(0);
							arrow.setCritical(true);
							arrow.setShooter(p);
							p.playSound(loc, Sound.ENTITY_ARROW_SHOOT, 2, 2);

							basic.add(p);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
								public void run() {
									basic.remove(p);
								}
							}, 400);
							for (int i = 0; i < 31; i++) {
								if (p.getInventory().getItemInMainHand().getItemMeta().getLore() != null
										&& p.getInventory().getItemInMainHand().getItemMeta().getLore().contains(
												ChatColor.translateAlternateColorCodes('&', "&8Damage: &c" + i))) {
									if (p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null
											&& p.getInventory().getLeggings() != null
											&& p.getInventory().getBoots() != null) {
										arrow.spigot().setDamage(i + GetDamage.getDamage(p) + attackMod(p));
									} else {
										arrow.spigot().setDamage(i + attackMod(p));
									}
								}

							}
						}

					}
				}
			}
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				if (p.getInventory().getItemInMainHand().equals(archer1)) {

					if (barrage.containsKey(p.getUniqueId())) {
						long secondsleft = ((barrage.get(p.getUniqueId()) / 1000) + barragetime)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft + " seconds");
						} else {
							arrowStorm.add(p);
							barrage.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "Your Next Shot will be a " + ChatColor.GREEN + "Barrage");

						}
					}
				}
				if (p.getInventory().getItemInMainHand().equals(archer2)) {

					if (telearrow.containsKey(p.getUniqueId())) {
						long secondsleft1 = ((telearrow.get(p.getUniqueId()) / 1000) + teletime)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft1 > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft1 + " seconds");
						} else {
							teleArrow.add(p);
							telearrow.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "Your Next Shot will be a " + ChatColor.GREEN + "Telearrow");
						}
					}
				}
				if (p.getInventory().getItemInMainHand() != null
						&& p.getInventory().getItemInMainHand().equals(Items.archer3())) {

					if (explosive.containsKey(p.getUniqueId())) {
						long secondsleft1 = ((explosive.get(p.getUniqueId()) / 1000) + et)
								- (System.currentTimeMillis() / 1000);

						if (secondsleft1 > 0) {
							p.sendMessage(ChatColor.RED + "Skill still on cooldown for " + secondsleft1 + " seconds");
						} else {
							explosivearrow.add(p);
							explosive.put(p.getUniqueId(), System.currentTimeMillis());
							p.sendMessage(ChatColor.GRAY + "Your Next Shot will be an " + ChatColor.GREEN
									+ "Explosive Arrow");
						}
					}
				}
			}
		} else {

		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Entity p = (Entity) e.getDamager();
		if (e.getDamager() instanceof Player) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					prefix + "&7You did &c" + e.getDamage() + " &7damage to " + e.getEntityType().toString()));
			if (((Player) p).getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)) {
				if (!(this.getConfig().getString(((Player) p).getName() + ".Class").equalsIgnoreCase("Brute"))) {
					e.setCancelled(true);
					((Player) p).sendMessage(prefix + ChatColor.RED + "Your class cant use this item.");
				}
			}
			if (((Player) p).getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
				if (!(this.getConfig().getString(((Player) p).getName() + ".Class").equalsIgnoreCase("Warrior"))) {
					e.setCancelled(true);
					((Player) p).sendMessage(prefix + ChatColor.RED + "Your class cant use this item.");
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			if (burningsoul.contains(e.getEntity())) {
				e.setCancelled(true);
				e.getEntity()
						.sendMessage(ChatColor.GREEN + "Burning soul " + ChatColor.GRAY + "protected you from damage.");
			}
		}
		if (p instanceof Arrow) {

			Entity pl = (Entity) ((Arrow) p).getShooter();
			if (pl instanceof Player) {
				pl.sendMessage(ChatColor.translateAlternateColorCodes('&',
						prefix + "&7You did &c" + e.getDamage() + " &7damage to " + e.getEntityType().toString()));
			}

		}
		Entity ent = (Entity) e.getEntity();
		((LivingEntity) ent).setMaximumNoDamageTicks(0);
		((LivingEntity) ent).setNoDamageTicks(0);

	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				prefix + "&7You cant drop stuff please use /trade or /trash"));
	}

	public void flameLord(Player p, Location l) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 65, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 100));
		for (Player ps : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable() {
				double t = 0;

				public void run() {
					t += Math.PI / 16;
					Location loc = p.getLocation();
					for (double phi = 0; phi <= 2 * Math.PI; phi += Math.PI / 1) {
						double x = 0.1 * (4 * Math.PI - t) * Math.cos(t + phi);
						double y = 0.08 * t;
						double z = 0.1 * (2 * Math.PI - t) * Math.sin(t + phi);
						loc.add(x, y, z);
						PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
								((float) (loc.getX() + x)), ((float) (loc.getY() + y)), ((float) (loc.getZ() + z)),

								0, 0, 0, 1, 0);
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet3);

						PacketPlayOutWorldParticles packet4 = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL,
								true, ((float) (loc.getX() + x)), ((float) (loc.getY() + y)),
								((float) (loc.getZ() + z)),

								0, 0, 0, 1, 0);
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet4);

						PacketPlayOutWorldParticles packet6 = new PacketPlayOutWorldParticles(EnumParticle.SNOW_SHOVEL,
								true, ((float) (loc.getX() + x)), ((float) (loc.getY() + y)),
								((float) (loc.getZ() + z)),

								0, 0, 0, 0, 10);
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet6);

						loc.subtract(x, y, z);

						if (t >= 4 * Math.PI) {

							loc.add(x, y, z);
							PacketPlayOutWorldParticles packet5 = new PacketPlayOutWorldParticles(EnumParticle.FLAME,
									true, ((float) (loc.getX() + x)), ((float) (loc.getY() + y)),
									((float) (loc.getZ() + z)),

									0, 0, 0, 1, 100);
							((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet5);

							loc.subtract(x, y, z);
							loc.add(x, y, z);
							PacketPlayOutWorldParticles packet7 = new PacketPlayOutWorldParticles(
									EnumParticle.SMOKE_NORMAL, true, ((float) (loc.getX() + x)),
									((float) (loc.getY() + y)), ((float) (loc.getZ() + z)),

									0, 0, 0, 1, 100);
							((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet7);

							loc.subtract(x, y, z);
							for (Entity es : p.getNearbyEntities(15, 15, 15)) {
								((Player) es).playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 5);
							}
							this.cancel();
						}

					}
				}

			}.runTaskTimer(this, 0, 1);
		}

	}

	@EventHandler
	public void onArrow(ProjectileHitEvent e) {
		int dmg = 0;
		Entity ent = e.getEntity();
		if (ent instanceof Arrow) {
			Arrow arrow = (Arrow) ent;
			ProjectileSource p = arrow.getShooter();
			Location aloc = arrow.getLocation();
			if (arrow.getShooter() instanceof Player) {
				if (explosivearrow.contains(arrow.getShooter())) {
					// arrow.getWorld().createExplosion(aloc, 3);
					for (Player ps : Bukkit.getServer().getOnlinePlayers()) {
						PacketPlayOutWorldParticles packet5 = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
								((float) (aloc.getX())), ((float) (aloc.getY())), ((float) (aloc.getZ())),

								0, 0, 0, 1, 500);
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet5);
						((Player) ps).playSound(aloc, Sound.ENTITY_GENERIC_EXPLODE, 5, 5);
					}
					for (Entity es : aloc.getWorld().getNearbyEntities(aloc, 7, 7, 7)) {
						if (es instanceof LivingEntity) {
							if (es != arrow.getShooter()) {
								for (int i = 0; i < 31; i++) {
									if (((CraftHumanEntity) p).getInventory().getItemInMainHand().getItemMeta()
											.getLore() != null
											&& ((CraftHumanEntity) p).getInventory().getItemInMainHand().getItemMeta()
													.getLore().contains(ChatColor.translateAlternateColorCodes('&',
															"&8Damage: &c" + i))) {
										if (((CraftHumanEntity) p).getInventory().getHelmet() != null
												&& ((CraftHumanEntity) p).getInventory().getChestplate() != null
												&& ((CraftHumanEntity) p).getInventory().getLeggings() != null
												&& ((CraftHumanEntity) p).getInventory().getBoots() != null) {
											dmg = i + GetDamage.getDamage(((Player) p));
										} else {
											dmg = i;
										}
									}
								}

								((LivingEntity) es).damage(dmg, ((Player) arrow.getShooter()));
							}
						}
					}
					explosivearrow.remove(p);
				}
			} else {
				return;
			}
		}
	}

	public void euruption(Player p, Location l, double g) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable() {
				double t = Math.PI / 4;
				Location loc = p.getLocation();

				public void run() {
					t = t + 0.1 * Math.PI;
					for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
						double x = t * Math.cos(theta);
						double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
						double z = t * Math.sin(theta);
						loc.add(x, y, z);
						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
								((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

								0, 0, 0, 0, 0);
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet);
						loc.subtract(x, y, z);

						theta = theta + Math.PI / 64;

						x = t * Math.cos(theta);
						y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
						z = t * Math.sin(theta);
						loc.add(x, y, z);
						PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE,
								true, ((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

								0, 0, 0, 0, 0);
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);

						for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
							if (e.getLocation().distance(loc) < 3.0) {
								if (!e.equals(p)) {
									if (!(e instanceof Player)) {
										if (e instanceof LivingEntity) {
											if (!(mobs.contains(e))) {
												mobs.add(e);
												((LivingEntity) e).damage(g + 5 + attackMod(p), p);
												removemobs(e);
											}
										}
									}
								}
							}
						}
						loc.subtract(x, y, z);

					}
					if (t > 20) {
						this.cancel();
					}
				}

			}.runTaskTimer(this, 0, 1);
		}
	}

	public void frostRay(Player p, Location l, double g, EnumParticle ep) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			// new BukkitRunnable() {
			// double t = 0;
			Location loc = p.getLocation();
			Vector direction = loc.getDirection();

			// public void run() {
			for (double t = 0; t < 8; t = t + 0.5) {
				// t = t + .5;
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;

				loc.add(x, y, z);
				PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
						((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

						0, 0, 0, 1, 0);
				((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
				for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
					if (e.getLocation().distance(loc) < 2.5) {
						if (!e.equals(p)) {
							if (!(e instanceof Player)) {
								if (e instanceof LivingEntity) {
									if (!(mobs.contains(e))) {
										mobs.add(e);
										((LivingEntity) e).damage(g, p);
										ps.playSound(loc, Sound.BLOCK_STONE_BREAK, 1, 10);
										new BukkitRunnable() {
											public void run() {
												mobs.remove(e);
											}
										}.runTaskLater(this, 3);
									}
								}
							}

						}
					}
				}

				loc.subtract(x, y, z);
				// if (t > 7) {
				// this.cancel();
				// }
			}
			// }
			// }.runTaskTimer(this, 0, 1);
		}
	}

	public void summonerRay(Player p, Location l, double g, EnumParticle ep) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			// new BukkitRunnable() {
			// double t = 0;
			Location loc = p.getLocation();
			Vector direction = loc.getDirection();

			// public void run() {
			for (double t = 0; t < 8; t = t + 0.5) {
				// t = t + .5;
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;

				loc.add(x, y, z);
				PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
						((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

						0, 0, 0, 1, 0);
				((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
				for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
					if (e.getLocation().distance(loc) < 2.5) {
						if (!e.equals(p)) {
							if (!(e instanceof Player)) {
								if (e instanceof LivingEntity) {
									if (!(mobs.contains(e))) {
										mobs.add(e);
										if (g > 5) {
											((LivingEntity) e).damage(g / 4, p);
										} else {
											((LivingEntity) e).damage(g, p);
										}
										ps.playSound(loc, Sound.BLOCK_STONE_BREAK, 1, 10);
										new BukkitRunnable() {
											public void run() {
												mobs.remove(e);
											}
										}.runTaskLater(this, 3);
									}
								}
							}

						}
					}
				}

				loc.subtract(x, y, z);
				// if (t > 7) {
				// this.cancel();
				// }
			}
			// }
			// }.runTaskTimer(this, 0, 1);
		}
	}

	public void magespell(Player p, Location l, double g, EnumParticle ep, EnumParticle ep2) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			// new BukkitRunnable() {
			// double t = 0;
			Location loc = p.getLocation();
			Vector direction = loc.getDirection();

			// public void run() {
			for (double t = 0; t < 8; t = t + 0.5) {
				// t = t + .5;
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;

				loc.add(x, y, z);
				PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
						((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

						0, 0, 0, 1, 0);
				((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
				PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(ep2, true,
						((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

						0, 0, 0, 1, 0);
				((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet2);
				for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
					if (e.getLocation().distance(loc) < 2.5) {
						if (!e.equals(p)) {
							if (!(e instanceof Player)) {
								if (e instanceof LivingEntity) {
									if (!(mobs.contains(e))) {
										mobs.add(e);
										((LivingEntity) e).damage(g, p);
										ps.playSound(loc, Sound.BLOCK_STONE_BREAK, 1, 10);
										e.setFireTicks(40);
										new BukkitRunnable() {
											public void run() {
												mobs.remove(e);
											}
										}.runTaskLater(this, 3);
									}
								}
							}

						}
					}
				}

				loc.subtract(x, y, z);
				// if (t > 7) {
				// this.cancel();
				// }
			}
			// }
			// }.runTaskTimer(this, 0, 1);
		}
	}

	public void firecharge(Player p, Location l, double g, EnumParticle ep) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			Location loc = p.getLocation();
			Vector direction = loc.getDirection();
			double x = direction.getX();
			double y = direction.getY() + 1;
			double z = direction.getZ();

			loc.add(x, y, z);
			PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true, ((float) (l.getX() + x)),
					((float) (l.getY() + y)), ((float) (l.getZ() + z)),

					1, 1, 1, 0, 200);
			((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);

			loc.subtract(x, y, z);
		}
	}

	public void fireball(Player p, Location l, double g, EnumParticle ep, EnumParticle ep2) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable() {
				double t = 0;
				Location loc = p.getLocation();
				Vector direction = loc.getDirection();

				public void run() {
					// for (double t = 0; t < 8; t = t + 0.5) {
					t = t + .7;
					double x = direction.getX() * t;
					double y = direction.getY() * t + 1.5;
					double z = direction.getZ() * t;

					loc.add(x, y, z);
					PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
							((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

							(float) .1, (float) .1, (float) .1, 0, 50);
					((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
					PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(ep2, true,
							((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

							0, 0, 0, 1, 0);
					((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet2);
					for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
						if (e.getLocation().distance(loc) < 2.5) {
							if (!e.equals(p)) {
								if (!(e instanceof Player)) {
									if (e instanceof LivingEntity) {
										if (!(mobs.contains(e))) {
											mobs.add(e);
											e.setFireTicks(50);
											((LivingEntity) e).damage(g, p);
											removemobs(e);

										}

									}
								}

							}
						}
					}

					loc.subtract(x, y, z);
					if (t > 20) {
						this.cancel();
					}
				}
			}.runTaskTimer(this, 0, 1);
		}
	}

	@SuppressWarnings("deprecation")
	public void healBeam(Player p, Location l, EnumParticle ep) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			// new BukkitRunnable() {
			// double t = 0;
			Location loc = p.getLocation();
			Vector direction = loc.getDirection();

			// public void run() {
			for (double t = 0; t < 12; t = t + 0.5) {
				// t = t + .5;
				double x = direction.getX() * t;
				double y = direction.getY() * t + 1.5;
				double z = direction.getZ() * t;

				loc.add(x, y, z);
				PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
						((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

						0, 0, 0, 1, 0);
				((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
				for (Entity e : loc.getWorld().getNearbyEntities(p.getLocation(), 50, 50, 50)) {
					if (e.getLocation().distance(loc) < 2.5) {
						if (!e.equals(p)) {
							if (e instanceof Player) {
								if (!(mobs.contains(e))) {
									mobs.add(e);
									double health1 = ((Player) e).getHealth();
									if (health1 < ((LivingEntity) e).getMaxHealth() - 10) {
										((Player) e).setHealth(health1 + 10);
										p.sendMessage(prefix + "Healed " + e.getName());
									} else {
										((Player) e).setHealth(health1 + (((Player) e).getMaxHealth() - health1));
										p.sendMessage(prefix + "This player is at full health.");
									}
									new BukkitRunnable() {
										public void run() {
											mobs.remove(e);
										}
									}.runTaskLater(this, 3);
								}
							}
						}
					}
				}

				loc.subtract(x, y, z);
				// if (t > 7) {
				// this.cancel();
				// }
			}
			// }
			// }.runTaskTimer(this, 0, 1);
		}
	}

	public void charge(Player p, Location l, EnumParticle ep) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable() {
				double t = 0;

				public void run() {
					// for (double t = 0; t < 12; t = t + 0.5) {
					t = t + .5;
					Location loc = p.getLocation();
					Vector direction = loc.getDirection();
					double x = direction.getX() + 1;
					double y = direction.getY();
					double z = direction.getZ() + 1;

					loc.add(x, y, z);
					PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(ep, true,
							((float) (l.getX() + x)), ((float) (l.getY() + y)), ((float) (l.getZ() + z)),

							0, 0, 0, 1, 0);
					((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet1);
					if (t > .5) {
						this.cancel();
					}
				}
			}.runTaskTimer(this, 5, 5);
		}
	}

	public void flameWheel(Player player, Location l) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable() {
				double phi = 0;
				Location loc = player.getLocation();

				public void run() {
					phi = phi + Math.PI / 40;
					double x, y, z;

					for (double t = 0; t <= 2 * Math.PI; t += Math.PI / 16) {
						for (double i = 0; i <= 1; i = i + 1) {
							x = 0.5 * (2 * Math.PI - t) * Math.cos(t + phi + i * Math.PI);
							y = 0;
							z = 0.5 * (2 * Math.PI - t) * Math.sin(t + phi + i * Math.PI);

							loc.add(x, y, z);
							PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME,
									true, ((float) (loc.getX() + x)), ((float) (loc.getY() + y)),
									((float) (loc.getZ() + z)),

									0, 0, 0, 1, 0);
							((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet);
							for (Entity e : loc.getWorld().getEntities()) {
								if (e.getLocation().distance(loc) < 1) {
									if (!e.equals(player)) {
										if (e instanceof LivingEntity) {
											((LivingEntity) e).damage(4, player);
											((LivingEntity) e).setFireTicks(80);
										}

									}
								}
							}

							loc.subtract(x, y, z);
							loc.add(x, y, z);
							PacketPlayOutWorldParticles packe1t = new PacketPlayOutWorldParticles(
									EnumParticle.SMOKE_NORMAL, true, ((float) (loc.getX() + x)),
									((float) (loc.getY() + y)), ((float) (loc.getZ() + z)),

									0, 0, 0, 1, 0);
							((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packe1t);
							loc.subtract(x, y, z);
						}

					}

					if (phi > 5 * Math.PI) {
						this.cancel();
					}
				}
			}.runTaskTimer(this, 0, 1);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (p instanceof Player) {
			e.setDeathMessage(
					org.bukkit.ChatColor.translateAlternateColorCodes('&', "&8[&cR&cI&cP&8] &7" + p.getName()));
		}
	}

	public void removemobs(Entity e) {

		new BukkitRunnable() {
			public void run() {
				mobs.remove(e);
			}
		}.runTaskLater(this, 5);
	}

	public void skills(Player p) {

		i = Bukkit.getServer().createInventory(null, 27);
		i.setItem(10, attack(p));
		i.setItem(4, totalPoints(p));
		for (int t = 0; t < 27; t++) {
			if (t != 10 && t != 4) {
				i.setItem(t, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5));
			}
		}
		p.openInventory(i);

	}

	public int attackMod(Player p) {
		int t = config.getInt(p.getName() + ".Attack");

		return t;
	}

	public int total(Player p) {
		int t = config.getInt(p.getName() + ".UnusedPoints");
		return t;
	}

	public int unused(Player p) {
		int t = config.getInt(p.getName() + ".UnusedPoints");

		return t;
	}

	public ItemStack attack(Player p) {
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aAttack Power"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&a+" + attackMod(p) + "&7/50"));
		itemMeta.setLore(bLore);

		item.setItemMeta(itemMeta);

		return item;
	}

	public ItemStack totalPoints(Player p) {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aTotal Points"));
		ArrayList<String> bLore = new ArrayList<String>();
		bLore.add(ChatColor.translateAlternateColorCodes('&', "&a" + total(p) + "&7/100"));
		itemMeta.setLore(bLore);

		item.setItemMeta(itemMeta);

		return item;
	}

	public void onLevel(PlayerLevelChangeEvent e) {
		Player p = e.getPlayer();
		if (e.getNewLevel() > e.getOldLevel()) {
			config.set(p.getName() + ".UnusedPoints", unused(p) + 1);
			saveConfig();
		}
	}

	@EventHandler
	public void kill(EntityDeathEvent e) {
		Entity killer = e.getEntity().getKiller();
		if (killer != null && UUID.contains(killer.getUniqueId())) {
			((Creature) killer).setTarget(owner.get(killer.getUniqueId()));
		} else {
			return;
		}
	}
}
