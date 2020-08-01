package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.Main;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class miracleAnvil implements MetadataValue {

    @Override
    public Object value() {
        return null;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public boolean asBoolean() {
        return true;
    }

    @Override
    public String asString() {
        return "build";
    }

    @Override
    public Plugin getOwningPlugin() {
        return Main.getInstance();
    }

    @Override
    public void invalidate() {

    }
}
