package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class FLBCommandExecutor implements CommandExecutor {

	private FlatlandsBuilder plugin;
	
	public FLBCommandExecutor(FlatlandsBuilder plugin) {
		this.plugin = plugin;
	}

	public boolean isValidBlock(String block){
		Material mat = Material.matchMaterial(block);

		return mat != null && mat.isBlock();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		if (sender instanceof Player == false){
			plugin.log.infoMSG("The FlatlandsBuilder commands can only be used in game.");
			return true;
		}
		
		final Player player = (Player) sender;
		
		if (!player.hasPermission("flatlandsbuilder.admin")){
			player.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			player.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.admin");
			return true;
		}
		
		if (true) {
				if(args.length == 1 && args[0].equalsIgnoreCase("version")) {
					player.sendMessage(ChatColor.GREEN + "The FlatlandsBuilder plugin is version " + pdFile.getVersion());
					return true;
				}else if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					player.sendMessage(ChatColor.WHITE + "Command Help - " + ChatColor.GREEN + "FlatlandsBuilder");
					player.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					player.sendMessage(ChatColor.RED + "Usage: /flb version" + ChatColor.GREEN + " - Gets Plugin Version.");
					player.sendMessage(ChatColor.RED + "Usage: /flb height <height>" + ChatColor.GREEN + " - Sets default generation height.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block1 <block_id>" + ChatColor.GREEN + " - Sets the default fill block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block2 <block_id>" + ChatColor.GREEN + " - Sets default border 1 block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block3 <block_id>" + ChatColor.GREEN + " - Sets default border 2 block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb mode <mode>" + ChatColor.GREEN + " - Sets default generation mode.");
				}else if(args.length == 2 && args[0].equalsIgnoreCase("height")){		// Sets height variable in the config file.
					if (args[1] != null){
						int height = 0;
						try{
							height = Integer.parseInt(args[1]);
							
							if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
								plugin.log.warningMSG("Invalid height '" + height + "'. New height not set.");
								player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height '" + height + "'. New height not set.");
							}else{
								plugin.log.warningMSG("New height '" + height + "' set.");
								player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New height '" + height + "' set.");
								plugin.conf.set("height", height);
								plugin.saveSettings();
							}
				        }catch (Exception e){
				            plugin.log.warningMSG("Invalid height must be a number (Integer). New height not set.");
				            player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height must be a number (Integer). New height not set.");
				        }
					}else{
						plugin.log.warningMSG("No value given ignoring command. New height not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New height not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("mode")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String modeSelect = args[1].toLowerCase();
			        		
							List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5","schematic");
			        		
			        		if (genModechk.contains(modeSelect)){
								if (modeSelect.equalsIgnoreCase("schematic")){
									plugin.log.warningMSG("Generation Mode '" + modeSelect + "' NOT yet implemented . New Generation Mode not set.");
									player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Generation Mode '" + modeSelect + "' NOT yet implemented . New Generation Mode not set.");
								}else{
									plugin.log.warningMSG("New  Generation Mode '" + modeSelect + "' set.");
									player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New Generation Mode '" + modeSelect + "' set.");
									plugin.conf.set("mode", modeSelect);
									plugin.saveSettings();
								}
			        		}else{
			        			plugin.log.warningMSG("Invalid Generation Mode '" + modeSelect + "'. New Generation Mode not set.");
								player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid  Generation Mode '" + modeSelect + "'. New Generation Mode not set.");
			        		}
			
				        }catch (Exception e){
				            plugin.log.warningMSG("Invalid Generation Mode must be either [normal, grid, grid2, checkered, checkered2, schematic]. New Generation Mode not set.");
				            player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Generation Mode must be either [normal, grid, grid2, grid3, grid4, grid5, schematic]. New Generation Mode not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New Generation Mode not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Generation Mode not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block1")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New  fill block '" + block + "' set.");
								player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New fill block '" + block + "' set.");
								plugin.conf.set("block1", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid fill block. New fill block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid fill block. New fill block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New fill block not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New fill block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block2")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New  border a block '" + block + "' set.");
								player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border a block '" + block + "' set.");
								plugin.conf.set("block2", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid border a block. New border a block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid border a block. New border a block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New border a block not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border a block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block3")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New border b block '" + block + "' set.");
								player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border b block '" + block + "' set.");
								plugin.conf.set("block3", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid border b block. New border b block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid border b block. New border b block not set.");
				        		player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New border b block not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border b block not set.");
					}
				}else if (args.length < 1){
					player.sendMessage(ChatColor.RED + "Usage: /flb help for more information.");
				}else{
					player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Command. Try again.");
				}
		}
		return true;
	}
}
