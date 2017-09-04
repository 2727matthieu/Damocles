package ca.damocles.utils;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;

public final class Book {

    private String title;
    private String author;
    private List<String> pages = new ArrayList<String>();
  
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
  
    public org.bukkit.inventory.ItemStack build() {
        ItemStack book = new ItemStack(Item.getById(387));
        NBTTagCompound tag = new NBTTagCompound();
        tag.set("generation", new NBTTagInt(0));
        tag.setString("author", author);
        tag.setString("title", title);
        NBTTagList pages = new NBTTagList();
        for (String page : this.pages) pages.add(new NBTTagString(page));
        tag.set("pages", pages);
        book.setTag(tag);
        return CraftItemStack.asBukkitCopy(book);
    }
  
    public void openBook(org.bukkit.inventory.ItemStack book, Player p) {
        int slot = p.getInventory().getHeldItemSlot();
        org.bukkit.inventory.ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, book);

       ByteBuf buf = Unpooled.buffer(256);
       buf.setByte(0, (byte)0);
       buf.writerIndex(1);
        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        p.getInventory().setItem(slot, old);
    }
    
    public void addPage(List<String> lines){
    	String page = "";
    	for(String s : lines){
    		page = page + s + "\n";
    	}
    	this.pages.add(page);
    }
    
}