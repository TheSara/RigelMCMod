package me.rigelmc.rigelmcmod.blocking;

import me.rigelmc.rigelmcmod.FreedomService;
import me.rigelmc.rigelmcmod.RigelMCMod;
import me.rigelmc.rigelmcmod.config.ConfigEntry;
import me.rigelmc.rigelmcmod.util.FUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class EventBlocker extends FreedomService
{

    public EventBlocker(RigelMCMod plugin)
    {
        super(plugin);
    }

    @Override
    protected void onStart()
    {
    }

    @Override
    protected void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBurn(BlockBurnEvent event)
    {
        if (!ConfigEntry.ALLOW_FIRE_SPREAD.getBoolean())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockIgnite(BlockIgniteEvent event)
    {
        if (!ConfigEntry.ALLOW_FIRE_PLACE.getBoolean())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFromTo(BlockFromToEvent event)
    {
        if (!ConfigEntry.ALLOW_FLUID_SPREAD.getBoolean())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if (!ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
        {
            event.setCancelled(true);
            return;
        }

        event.setYield(0.0F);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosionPrime(ExplosionPrimeEvent event)
    {
        if (!ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
        {
            event.setCancelled(true);
            return;
        }

        event.setRadius(ConfigEntry.EXPLOSIVE_RADIUS.getDouble().floatValue());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityCombust(EntityCombustEvent event)
    {
        if (!ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event)
    {
        if (ConfigEntry.AUTO_ENTITY_WIPE.getBoolean())
        {
            event.setDroppedExp(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if (ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
        {
            Projectile entity = event.getEntity();
            if (event.getEntityType() == EntityType.ARROW)
            {
                entity.getWorld().createExplosion(entity.getLocation(), 2F);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event)
    {
        switch (event.getCause())
        {
            case LAVA:
            {
                if (!ConfigEntry.ALLOW_LAVA_DAMAGE.getBoolean())
                {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (ConfigEntry.ENABLE_PET_PROTECT.getBoolean())
        {
            Entity entity = event.getEntity();
            if (entity instanceof Tameable)
            {
                if (((Tameable) entity).isTamed())
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        if (!ConfigEntry.AUTO_ENTITY_WIPE.getBoolean())
        {
            return;
        }

        if (event.getPlayer().getWorld().getEntities().size() > 750)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeavesDecay(LeavesDecayEvent event)
    {
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Player)
            {
                Player player = (Player) event.getDamager();
                if (player.getGameMode() == GameMode.CREATIVE && !plugin.al.isAdmin(player))
                {
                    FUtil.playerMsg(player, "Creative PvP is not allowed!", ChatColor.RED);
                    event.setCancelled(true);
                }
                if (plugin.esb.getEssentialsUser(player.getName()).isGodModeEnabled() && !plugin.al.isAdmin(player))
                {
                    FUtil.playerMsg(player, "God mode PvP is not allowed!", ChatColor.RED);
                    event.setCancelled(true);
                }
                
            }
            if (event.getDamager() instanceof Arrow)
            {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player)
                {
                    Player player = (Player) arrow.getShooter();
                    if (player.getGameMode() == GameMode.CREATIVE && !plugin.al.isAdmin(player))
                    {
                        FUtil.playerMsg(player, "Creative PvP is not allowed!", ChatColor.RED);
                        event.setCancelled(true);
                    }
                    if (plugin.esb.getEssentialsUser(player.getName()).isGodModeEnabled() && !plugin.al.isAdmin(player))
                    {
                        FUtil.playerMsg(player, "God mode PvP is not allowed!", ChatColor.RED);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onOpenBook(PlayerInteractEvent event)
    {
        ItemStack is = event.getItem();
        if (is != null && is.getType().equals(Material.WRITTEN_BOOK))
        {
            Player player = event.getPlayer();
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
            event.setCancelled(true);
            player.sendMessage(ChatColor.GRAY + "For security reasons opening written books has been disabled");
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onFireworkExplode(FireworkExplodeEvent event)
    {
        if (!ConfigEntry.ALLOW_FIREWORK_EXPLOSIONS.getBoolean())
        {
            event.setCancelled(true);
        }
    }
}
