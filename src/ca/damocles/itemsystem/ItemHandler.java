package ca.damocles.itemsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.enchantsystem.Rune;
import ca.damocles.enchantsystem.Enchantment.EnchantTypes;
import ca.damocles.itemblueprints.Armor;
import ca.damocles.itemblueprints.Bow;
import ca.damocles.itemblueprints.Ring;
import ca.damocles.itemblueprints.Scroll;
import ca.damocles.itemblueprints.Weapon;
import ca.damocles.itemsystem.ItemType.ArmorTypes;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.itemsystem.ItemType.RuneType;
import ca.damocles.itemsystem.ItemType.ScrollType;
import ca.damocles.utils.Message;
import mkremins.fanciful.FancyMessage;

public class ItemHandler implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	ca.damocles.enchantsystem.Enchantment enchantclass = new ca.damocles.enchantsystem.Enchantment();
	Rune rune = new Rune();
	
	public ItemHandler() {}
	
	@EventHandler
	public void onRingInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
			if(new ItemType(event.getPlayer().getInventory().getItemInMainHand()).getType().equals(ItemTypes.RING)){
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onRingBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(new ItemType(player.getInventory().getItemInMainHand()).getType().equals(ItemTypes.RING)){
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onEnderPearl(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getItem() != null){
				if(event.getItem().getType().equals(Material.EYE_OF_ENDER)){
					if(event.getItem().hasItemMeta()){
						if(event.getItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW + "Slot Rune")){
							event.setCancelled(true);
							event.getPlayer().updateInventory();
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemApply(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		
		if(event.getCurrentItem() == null){
			return;
		}
		
		if(event.getCurrentItem() != null){
			
			ItemType currentType = new ItemType(event.getCurrentItem());
			
			if(currentType.getType().equals(ItemTypes.BOW) || currentType.getType().equals(ItemTypes.RING) || currentType.getType().equals(ItemTypes.ARMOR) || currentType.getType().equals(ItemTypes.WEAPON)){
				
				if(event.getCursor() != null){
					
					ItemType cursorType = new ItemType(event.getCursor());
					
					if(cursorType.getType().equals(ItemTypes.SCROLL)){
						Scroll scroll = new Scroll(event.getCursor());
						
						if(currentType.getType().equals(ItemTypes.BOW)){
							Bow bow = new Bow(event.getCurrentItem());
							if(bow.getUpgrades() == 0){
								return;
							}
							
							int amount = scroll.getAmount();
							
							if(scroll.getType().equals(ScrollType.DAMAGE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									bow.setUpgrades(bow.getUpgrades()-1);
									bow.setDamage(bow.getDamage()+amount);
									
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(bow.isProtected()){
											event.setCancelled(true);
											bow.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										bow.setUpgrades(bow.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.AGILITY)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									bow.setUpgrades(bow.getUpgrades()-1);
									bow.setAgi(bow.getAgi()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(bow.isProtected()){
											event.setCancelled(true);
											bow.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										bow.setUpgrades(bow.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.INTELLIGENCE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									bow.setUpgrades(bow.getUpgrades()-1);
									bow.setInt(bow.getInt()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(bow.isProtected()){
											event.setCancelled(true);
											bow.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										bow.setUpgrades(bow.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.HITPOINTS)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									bow.setUpgrades(bow.getUpgrades()-1);
									bow.setHp(bow.getHp()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(bow.isProtected()){
											event.setCancelled(true);
											bow.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										bow.setUpgrades(bow.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.STRENGTH)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									bow.setUpgrades(bow.getUpgrades()-1);
									bow.setStr(bow.getStr()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(bow.isProtected()){
											event.setCancelled(true);
											bow.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										bow.setUpgrades(bow.getUpgrades()-1);
									}
								}
							}
							
							bow.setAppliedUpgrades(bow.getAppliedUpgrades() + 1);
							ItemStack item = bow.getItemStack();
							if(bow.getAppliedUpgrades() >= 3){
								item.addEnchantment(Enchantment.getById(69), 1);
							}
							event.setCurrentItem(item);
							event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
							return;
						}
						
						if(currentType.getType().equals(ItemTypes.RING)){
							Ring ring = new Ring(event.getCurrentItem());
							
							if(ring.getUpgrades() == 0){
								return;
							}
							
							int amount = scroll.getAmount();
							
							if(scroll.getType().equals(ScrollType.DAMAGE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									ring.setUpgrades(ring.getUpgrades()-1);
									ring.setDamage(ring.getDamage()+amount);
									
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(ring.isProtected()){
											event.setCancelled(true);
											ring.unprotect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										ring.setUpgrades(ring.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.AGILITY)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									ring.setUpgrades(ring.getUpgrades()-1);
									ring.setAgi(ring.getAgi()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(ring.isProtected()){
											event.setCancelled(true);
											ring.unprotect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										ring.setUpgrades(ring.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.INTELLIGENCE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									ring.setUpgrades(ring.getUpgrades()-1);
									ring.setInt(ring.getInt()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(ring.isProtected()){
											event.setCancelled(true);
											ring.unprotect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										ring.setUpgrades(ring.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.HITPOINTS)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									ring.setUpgrades(ring.getUpgrades()-1);
									ring.setHp(ring.getHp()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(ring.isProtected()){
											event.setCancelled(true);
											ring.unprotect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										ring.setUpgrades(ring.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.STRENGTH)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									ring.setUpgrades(ring.getUpgrades()-1);
									ring.setStr(ring.getStr()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(ring.isProtected()){
											event.setCancelled(true);
											ring.unprotect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										ring.setUpgrades(ring.getUpgrades()-1);
									}
								}
							}
							
							ring.setAppliedUpgrades(ring.getAppliedUpgrades() + 1);
							ItemStack item = ring.getItemStack();
							if(ring.getAppliedUpgrades() >= 3){
								item.addEnchantment(Enchantment.getById(69), 1);
							}
							event.setCurrentItem(item);
							event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
							return;
						}
						
						if(currentType.getType().equals(ItemTypes.ARMOR)){
							Armor armor = new Armor(event.getCurrentItem());
							
							if(armor.getUpgrades() == 0){
								return;
							}
							
							int amount = scroll.getAmount();
							if(scroll.getType().equals(ScrollType.ARMOR)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									armor.setUpgrades(armor.getUpgrades()-1);
									armor.setArmor(armor.getArmor()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(armor.isProtected()){
											event.setCancelled(true);
											armor.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										armor.setUpgrades(armor.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.AGILITY)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									armor.setUpgrades(armor.getUpgrades()-1);
									armor.setAgi(armor.getAgi()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(armor.isProtected()){
											event.setCancelled(true);
											armor.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										armor.setUpgrades(armor.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.INTELLIGENCE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									armor.setUpgrades(armor.getUpgrades()-1);
									armor.setInt(armor.getInt()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(armor.isProtected()){
											event.setCancelled(true);
											armor.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										armor.setUpgrades(armor.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.HITPOINTS)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									armor.setUpgrades(armor.getUpgrades()-1);
									armor.setHp(armor.getHp()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(armor.isProtected()){
											event.setCancelled(true);
											armor.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										armor.setUpgrades(armor.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.STRENGTH)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									armor.setUpgrades(armor.getUpgrades()-1);
									armor.setStr(armor.getStr()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(armor.isProtected()){
											event.setCancelled(true);
											armor.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										armor.setUpgrades(armor.getUpgrades()-1);
									}
								}
							}
							
							armor.setAppliedUpgrades(armor.getAppliedUpgrades() + 1);
							ItemStack item = armor.getItemStack();
							if(armor.getAppliedUpgrades() >= 3){
								item.addEnchantment(Enchantment.getById(69), 1);
							}
							event.setCurrentItem(item);
							event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
							return;
							
						}
						
						if(currentType.getType().equals(ItemTypes.WEAPON)){
							Weapon weapon = new Weapon(event.getCurrentItem());
							
							if(weapon.getUpgrades() == 0){
								return;
							}
							
							int amount = scroll.getAmount();
							if(scroll.getType().equals(ScrollType.DAMAGE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									weapon.setUpgrades(weapon.getUpgrades()-1);
									weapon.setDamage(weapon.getDamage()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(weapon.isProtected()){
											event.setCancelled(true);
											weapon.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										weapon.setUpgrades(weapon.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.AGILITY)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									weapon.setUpgrades(weapon.getUpgrades()-1);
									weapon.setAgi(weapon.getAgi()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(weapon.isProtected()){
											event.setCancelled(true);
											weapon.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										weapon.setUpgrades(weapon.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.INTELLIGENCE)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									weapon.setUpgrades(weapon.getUpgrades()-1);
									weapon.setInt(weapon.getInt()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(weapon.isProtected()){
											event.setCancelled(true);
											weapon.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										weapon.setUpgrades(weapon.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.HITPOINTS)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									weapon.setUpgrades(weapon.getUpgrades()-1);
									weapon.setHp(weapon.getHp()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(weapon.isProtected()){
											event.setCancelled(true);
											weapon.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										weapon.setUpgrades(weapon.getUpgrades()-1);
									}
								}
							}
							if(scroll.getType().equals(ScrollType.STRENGTH)){
								if(new Random().nextInt(100) <= scroll.getSuccess()){
									event.setCancelled(true);
									weapon.setUpgrades(weapon.getUpgrades()-1);
									weapon.setStr(weapon.getStr()+amount);
								}else{
									if(new Random().nextInt(100) <= scroll.getDestroy()){
										if(weapon.isProtected()){
											event.setCancelled(true);
											weapon.unProtect();
										}else{
											event.setCancelled(true);
											event.setCurrentItem(null);
											event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
											return;
										}
									}else{
										event.setCancelled(true);
										weapon.setUpgrades(weapon.getUpgrades()-1);
									}
								}
							}
							
							weapon.setAppliedUpgrades(weapon.getAppliedUpgrades() + 1);
							ItemStack item = weapon.getItemStack();
							if(weapon.getAppliedUpgrades() >= 3){
								item.addEnchantment(Enchantment.getById(69), 1);
							}
							event.setCurrentItem(item);
							event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
							return;
							
						}
						return;
					}
					
					
					if(cursorType.getRuneType().equals(RuneType.PROTECTION)){
						if(currentType.getType().equals(ItemTypes.ARMOR)){
							Armor detailItem = new Armor(event.getCurrentItem());
							if(!(detailItem.isProtected())){
								event.setCancelled(true);
								detailItem.protect();
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have successfully Protected your equip!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		return;
							}else{
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"Item is already Protected!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								event.setCancelled(true);
								return;
							}
						}else if(currentType.getType().equals(ItemTypes.BOW)){
							Bow detailItem = new Bow(event.getCurrentItem());
							if(!(detailItem.isProtected())){
								event.setCancelled(true);
								detailItem.protect();
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have successfully Protected your equip!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		return;
							}else{
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"Item is already Protected!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								event.setCancelled(true);
								return;
							}
						}else if(currentType.getType().equals(ItemTypes.WEAPON)){
							Weapon detailItem = new Weapon(event.getCurrentItem());
							if(!(detailItem.isProtected())){
								event.setCancelled(true);
								detailItem.protect();
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have successfully Protected your equip!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		return;
							}else{
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"Item is already Protected!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								event.setCancelled(true);
								return;
							}
						}else if(currentType.getType().equals(ItemTypes.RING)){
							Ring detailItem = new Ring(event.getCurrentItem());
							if(!(detailItem.isProtected())){
								event.setCancelled(true);
								detailItem.protect();
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have successfully Protected your equip!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		return;
							}else{
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"Item is already Protected!");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								event.setCancelled(true);
								return;
							}
						}else{
							return;
						}
						
					}
					
					if(cursorType.getRuneType().equals(RuneType.SLOT)){
						if(currentType.getType().equals(ItemTypes.ARMOR)){
							Armor detailItem = new Armor(event.getCurrentItem());
							if(event.getCursor().hasItemMeta()){
								event.setCancelled(true);
								int slots = rune.getSlotsFromRune(event.getCursor());
								detailItem.setSlots(detailItem.getSlots() + rune.getSlotsFromRune(event.getCursor()));
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
								String item = new FancyMessage("                     You added "+slots+" to ")
										.color(ChatColor.YELLOW)
										.then("this")
										.color(ChatColor.GOLD)
										.style(ChatColor.BOLD)
										.itemTooltip(event.getCurrentItem())
										.then(" item!")
										.color(ChatColor.YELLOW)
										.toJSONString();
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  FancyMessage.deserialize(item).send(player);				   
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		  return;
							}
						}else if(currentType.getType().equals(ItemTypes.BOW)){
							Bow detailItem = new Bow(event.getCurrentItem());
							if(event.getCursor().hasItemMeta()){
								event.setCancelled(true);
								int slots = rune.getSlotsFromRune(event.getCursor());
								detailItem.setSlots(detailItem.getSlots() + rune.getSlotsFromRune(event.getCursor()));
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
								String item = new FancyMessage("                     You added "+slots+" to ")
										.color(ChatColor.YELLOW)
										.then("this")
										.color(ChatColor.GOLD)
										.style(ChatColor.BOLD)
										.itemTooltip(event.getCurrentItem())
										.then(" item!")
										.color(ChatColor.YELLOW)
										.toJSONString();
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  FancyMessage.deserialize(item).send(player);				   
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		  return;
							}
						}else if(currentType.getType().equals(ItemTypes.WEAPON)){
							Weapon detailItem = new Weapon(event.getCurrentItem());
							if(event.getCursor().hasItemMeta()){
								event.setCancelled(true);
								int slots = rune.getSlotsFromRune(event.getCursor());
								detailItem.setSlots(detailItem.getSlots() + rune.getSlotsFromRune(event.getCursor()));
								event.setCurrentItem(detailItem.getItemStack());
								event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
								String item = new FancyMessage("                     You added "+slots+" to ")
										.color(ChatColor.YELLOW)
										.then("this")
										.color(ChatColor.GOLD)
										.style(ChatColor.BOLD)
										.itemTooltip(event.getCurrentItem())
										.then(" item!")
										.color(ChatColor.YELLOW)
										.toJSONString();
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  FancyMessage.deserialize(item).send(player);				   
					    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 50);
					    		  return;
							}
						}else{
							return;
						}
					}
					
					if(cursorType.getRuneType().equals(RuneType.RUNE)){
						
						if(event.getCurrentItem().equals(null) || event.getCurrentItem().equals(Material.AIR)){
							return;
						}
						
						if(currentType.getType().equals(ItemTypes.ARMOR)){
							
							Armor detailItem = new Armor(event.getCurrentItem());
							
							Map<String, Integer> values = rune.getRune(event.getCursor());
							Enchantments enchantment = null;
							int enchantLevel = 0;
							int enchantSuccess = 0;
							int enchantDestroy = 0;
							for(Entry<String, Integer> string : values.entrySet()){
								for(Enchantments enchantments : Enchantments.values()){
									if(enchantments.toString().equals(string.getKey().toUpperCase())){
										enchantment = Enchantments.valueOf(string.getKey().toUpperCase());
									}
								}
							}
							enchantSuccess = values.get("success");
							enchantDestroy = values.get("destroy");
							enchantLevel = values.get("level");
							
							HashMap<Enchantments, Integer> enchants = detailItem.getEnchantsAndLevel();
							if(enchants.containsKey(enchantment)){
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"This item already has an enchantment with the same name.");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return;
							}
							
							if(detailItem.getSlots()  <= 0){
								return;
							}
							
							if(detailItem.getSlots() > 0){
								
								if(enchantclass.getType(enchantment).equals(EnchantTypes.ARMOR)){
									event.setCancelled(true);
									ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
									event.setCurrentItem(item);
									event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
									return;
								}else if(enchantclass.getType(enchantment).equals(EnchantTypes.BOOTS)){
									if(currentType.getArmorType().equals(ArmorTypes.FEET)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else if(enchantclass.getType(enchantment).equals(EnchantTypes.LEGGINGS)){
									if(currentType.getArmorType().equals(ArmorTypes.LEGS)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else if(enchantclass.getType(enchantment).equals(EnchantTypes.CHESTPLATE)){
									if(currentType.getArmorType().equals(ArmorTypes.CHEST)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else if(enchantclass.getType(enchantment).equals(EnchantTypes.HELMET)){
									if(currentType.getArmorType().equals(ArmorTypes.HEAD)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else{
									event.setCancelled(true);
								}
								
								
							}
							player.updateInventory();
						}else if(currentType.getType().equals(ItemTypes.WEAPON)){
							
							Weapon detailItem = new Weapon(event.getCurrentItem());
							
							Map<String, Integer> values = rune.getRune(event.getCursor());
							Enchantments enchantment = null;
							int enchantLevel = 0;
							int enchantSuccess = 0;
							int enchantDestroy = 0;
							for(Entry<String, Integer> string : values.entrySet()){
								for(Enchantments enchantments : Enchantments.values()){
									if(enchantments.toString().equals(string.getKey().toUpperCase())){
										enchantment = Enchantments.valueOf(string.getKey().toUpperCase());
									}
								}
							}
							enchantSuccess = values.get("success");
							enchantDestroy = values.get("destroy");
							enchantLevel = values.get("level");
							
							HashMap<Enchantments, Integer> enchants = detailItem.getEnchantsAndLevel();
							if(enchants.containsKey(enchantment)){
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"This item already has an enchantment with the same name.");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return;
							}
							
							
							if(detailItem.getSlots()  <= 0){
								return;
							}
							
							if(detailItem.getSlots() > 0){
								
								
								if(enchantclass.getType(enchantment).equals(EnchantTypes.SWORD)){
									if(currentType.getType().equals(ItemTypes.WEAPON)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else{
									event.setCancelled(true);
									return;
								}
								
								
							}
							player.updateInventory();
						}else if(currentType.getType().equals(ItemTypes.BOW)){
							
							Bow detailItem = new Bow(event.getCurrentItem());
							
							Map<String, Integer> values = rune.getRune(event.getCursor());
							Enchantments enchantment = null;
							int enchantLevel = 0;
							int enchantSuccess = 0;
							int enchantDestroy = 0;
							for(Entry<String, Integer> string : values.entrySet()){
								for(Enchantments enchantments : Enchantments.values()){
									if(enchantments.toString().equals(string.getKey().toUpperCase())){
										enchantment = Enchantments.valueOf(string.getKey().toUpperCase());
									}
								}
							}
							enchantSuccess = values.get("success");
							enchantDestroy = values.get("destroy");
							enchantLevel = values.get("level");
							
							HashMap<Enchantments, Integer> enchants = detailItem.getEnchantsAndLevel();
							if(enchants.containsKey(enchantment)){
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		Message.sendCenteredMessage(player, ChatColor.YELLOW+"This item already has an enchantment with the same name.");
					    		Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return;
							}
							
							
							if(detailItem.getSlots()  <= 0){
								return;
							}
							
							if(detailItem.getSlots() > 0){
								
								if(enchantclass.getType(enchantment).equals(EnchantTypes.BOW)){
									if(currentType.getType().equals(ItemTypes.BOW)){
										event.setCancelled(true);
										ItemStack item = enchant(player, event.getCurrentItem(), enchantment, enchantLevel, enchantSuccess, enchantDestroy);
										event.setCurrentItem(item);
										event.setCursor(rune.setAmount(event.getCursor(), event.getCursor().getAmount() - 1));
										return;
									}
								}else{
									event.setCancelled(true);
									return;
								}
								
								
							}
							player.updateInventory();
						}else{
							return;
						}
						
						
						
						
					}
				}
				
			}
			
		}else{
			return;
		}
	}
	
	public ItemStack enchant(Player source, ItemStack item, Enchantments enchant, int level, int success, int destroy){
		ItemType type = new ItemType(item);
		if(type.getType().equals(ItemTypes.ARMOR)){
			Armor detailItem = new Armor(item);
			int random = new Random().nextInt(100);
			if(random <= success){
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				String items = new FancyMessage("                     You added "+ enchantclass.getName(enchant)+" to ")
						.color(ChatColor.YELLOW)
						.then("this")
						.color(ChatColor.GOLD)
						.style(ChatColor.BOLD)
						.itemTooltip(item)
						.then(" item!")
						.color(ChatColor.YELLOW)
						.toJSONString();
				FancyMessage.deserialize(items).send(source);		
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				source.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, source.getLocation(), 10);
				detailItem.setSlots(detailItem.getSlots() - 1);
				detailItem.addEnchant(enchant, level);
				item = detailItem.getItemStack();
			}else{
				int randDestroy = new Random().nextInt(100);
				if(randDestroy <= destroy){
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					String items = new FancyMessage("                     You have failed to add "+ enchantclass.getName(enchant)+" to ")
							.color(ChatColor.YELLOW)
							.then("this")
							.color(ChatColor.GOLD)
							.style(ChatColor.BOLD)
							.itemTooltip(item)
							.then(" item!")
							.color(ChatColor.YELLOW)
							.toJSONString();
					FancyMessage.deserialize(items).send(source);		
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					source.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, source.getLocation(), 10);
					if(detailItem.isProtected()){
						detailItem.unProtect();
						item = detailItem.getItemStack();
						return item;
					}
					return new ItemStack(Material.AIR, 1);
				}else{
					return item;
				}
			}
			return item;
		}
		if(type.getType().equals(ItemTypes.BOW)){
			Bow detailItem = new Bow(item);
			int random = new Random().nextInt(100);
			if(random <= success){
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				String items = new FancyMessage("                     You added "+ enchantclass.getName(enchant)+" to ")
						.color(ChatColor.YELLOW)
						.then("this")
						.color(ChatColor.GOLD)
						.style(ChatColor.BOLD)
						.itemTooltip(item)
						.then(" item!")
						.color(ChatColor.YELLOW)
						.toJSONString();
				FancyMessage.deserialize(items).send(source);		
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				source.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, source.getLocation(), 10);
				detailItem.setSlots(detailItem.getSlots() - 1);
				detailItem.addEnchant(enchant, level);
				item = detailItem.getItemStack();
			}else{
				int randDestroy = new Random().nextInt(100);
				if(randDestroy <= destroy){
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					String items = new FancyMessage("                     You have failed to add "+ enchantclass.getName(enchant)+" to ")
							.color(ChatColor.YELLOW)
							.then("this")
							.color(ChatColor.GOLD)
							.style(ChatColor.BOLD)
							.itemTooltip(item)
							.then(" item!")
							.color(ChatColor.YELLOW)
							.toJSONString();
					FancyMessage.deserialize(items).send(source);		
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					source.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, source.getLocation(), 10);
					if(detailItem.isProtected()){
						detailItem.unProtect();
						item = detailItem.getItemStack();
						return item;
					}
					return new ItemStack(Material.AIR, 1);
				}else{
					return item;
				}
			}
			return item;
		}
		if(type.getType().equals(ItemTypes.WEAPON)){
			Weapon detailItem = new Weapon(item);
			int random = new Random().nextInt(100);
			if(random <= success){
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				String items = new FancyMessage("                     You added "+ enchantclass.getName(enchant)+" to ")
						.color(ChatColor.YELLOW)
						.then("this")
						.color(ChatColor.GOLD)
						.style(ChatColor.BOLD)
						.itemTooltip(item)
						.then(" item!")
						.color(ChatColor.YELLOW)
						.toJSONString();
				FancyMessage.deserialize(items).send(source);		
				Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
				source.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, source.getLocation(), 10);
				detailItem.setSlots(detailItem.getSlots() - 1);
				detailItem.addEnchant(enchant, level);
				item = detailItem.getItemStack();
			}else{
				int randDestroy = new Random().nextInt(100);
				if(randDestroy <= destroy){
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					String items = new FancyMessage("                     You have failed to add "+ enchantclass.getName(enchant)+" to ")
							.color(ChatColor.YELLOW)
							.then("this")
							.color(ChatColor.GOLD)
							.style(ChatColor.BOLD)
							.itemTooltip(item)
							.then(" item!")
							.color(ChatColor.YELLOW)
							.toJSONString();
					FancyMessage.deserialize(items).send(source);		
					Message.sendCenteredMessage(source, ChatColor.GREEN+"----------------------------------------------------");
					source.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, source.getLocation(), 10);
					if(detailItem.isProtected()){
						detailItem.unProtect();
						item = detailItem.getItemStack();
						return item;
					}
					return new ItemStack(Material.AIR, 1);
				}else{
					return item;
				}
			}
			return item;
		}
		return item;
	}
	
}