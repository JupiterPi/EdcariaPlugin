package de.jupiterpi.mc.edcaria.warp;

import jupiterpi.tools.files.csv.CSVCastable;
import jupiterpi.tools.util.ToolsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WarpPosition implements CSVCastable {
    private String name;
    private Location position;

    public WarpPosition(String name, Location position) {
        set(name, position);
    }
    public void set(String name, Location position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Location getPosition() {
        return position;
    }

    /* csv */

    public WarpPosition(String[] f) {
        name = f[0];
        World world = Bukkit.getWorld(f[1]);
        String[] coordsStr = f[2].split(" ");
        double[] coords = new double[3];
        for (int i = 0; i < coordsStr.length; i++) {
            coords[i] = Double.parseDouble(coordsStr[i]);
        }
        position = new Location(world, coords[0], coords[1], coords[2]);
    }

    @Override
    public String[] toCSV() {
        double[] coords = new double[]{position.getX(), position.getY(), position.getZ()};
        String[] coordsStr = new String[3];
        for (int i = 0; i < coords.length; i++) {
            coordsStr[i] = Double.toString(coords[i]);
        }
        return new String[]{name, position.getWorld().getName(), ToolsUtil.appendWithSeparator(coordsStr, " ")};
    }
}
