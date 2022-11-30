package com.github.sakakiaruka.cutall.cutall.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.*;

public class blockBreak implements Listener {

    private final int maxHeight = 40;
    private final int f_radius = 3;
    private final int degrees = 10;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getBlock().getType().name().contains("LOG")){
            //cut logs.
            Block block = event.getBlock();
            Player player = event.getPlayer();
            this.first(block,player);
        }
    }

    private void first(Block block,Player player){
        //
        Material material = block.getType();
        List<Block> blocks = new ArrayList<>();
        for(int h=block.getY();h<(h+maxHeight);h++){
            // height loop
            Block b = new Location(block.getWorld(),block.getX(),h,block.getY()).getBlock();
            if(!b.getType().equals(block.getType())){
                //an upper block doesn't match with a first cut log type.
                break;
            }

            //drop items (from those blocks)
            for(ItemStack i:b.getDrops()){
                player.getWorld().dropItemNaturally(player.getLocation(),i);
            }

            for(int r=1;r<=3;r++){
                //range loop
                for(double rad=0;rad<(2*Math.PI);rad+=(2*Math.PI/(360/degrees))){
                    //radian loop
                    double x = block.getX() + f_radius*Math.cos(rad);
                    double z = block.getZ() + f_radius*Math.sin(rad);
                    Location L = new Location(block.getWorld(),x,h,z);
                    Block target = L.getBlock();
                    if(target.getType().equals(material)){
                        //match cut block by a player.
                        blocks.add(target);
                    }
                }
            }
        }
        this.cut(blocks,player.getLocation());
    }

    private void cut(List<Block> blocks,Location L){
        for(Block b:blocks){
            for(ItemStack i:b.getDrops()){
                L.getWorld().dropItemNaturally(L,i);
            }
        }
    }
}
