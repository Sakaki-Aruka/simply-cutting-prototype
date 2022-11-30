package com.github.sakakiaruka.cutall.cutall;

import com.github.sakakiaruka.cutall.cutall.listener.blockBreak;
import org.bukkit.plugin.java.JavaPlugin;

public final class CutAll extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new blockBreak(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
