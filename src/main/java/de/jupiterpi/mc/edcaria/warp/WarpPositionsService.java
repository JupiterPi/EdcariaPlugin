package de.jupiterpi.mc.edcaria.warp;

import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.csv.CSVObjectsFile;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class WarpPositionsService {
    private final Path registryFilePath = Path.getRunningDirectory().file("warps.csv");

    private CSVObjectsFile<WarpPosition> registryFile;
    private List<WarpPosition> warpPositions;

    private void reload() {
        registryFile = new CSVObjectsFile<>(registryFilePath, WarpPosition.class);
        warpPositions = registryFile.getObjects();
    }
    public WarpPositionsService() {
        reload();
    }

    /* api */

    public void setPosition(String name, Location location) {
        WarpPosition position = getWarpPosition(name);
        if (position == null) {
            warpPositions.add(new WarpPosition(name, location));
        } else {
            position.set(name, location);
        }
        registryFile.writeObjects(warpPositions);
    }

    public Location getPosition(String name) {
        WarpPosition position = getWarpPosition(name);
        if (position == null) return null;
        else return position.getPosition();
    }

    private WarpPosition getWarpPosition(String name) {
        for (WarpPosition position : warpPositions) {
            if (position.getName().equals(name)) return position;
        }
        return null;
    }

    public List<String> getWarpPositions() {
        List<String> positions = new ArrayList<>();
        for (WarpPosition warpPosition : warpPositions) {
            positions.add(warpPosition.getName());
        }
        return positions;
    }
}