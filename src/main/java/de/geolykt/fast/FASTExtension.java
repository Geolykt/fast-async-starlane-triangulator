package de.geolykt.fast;

import de.geolykt.starloader.api.Galimulator;
import de.geolykt.starloader.api.NamespacedKey;
import de.geolykt.starloader.api.registry.RegistryExpander;
import de.geolykt.starloader.mod.Extension;

public class FASTExtension extends Extension {
    @Override
    public void initialize() {
        RegistryExpander.addStarlaneGenerator(new NamespacedKey(this, "STARLANES_FAST_ASYNC"), "FAST_ASYNC", "FAST", () -> {
            FastAsynchronousStarlaneTriangulator.INSTANCE.connectStars(Galimulator.getStarList(), Galimulator.getMap().getWidth() / 2, Galimulator.getMap().getHeight() / 2);
        });
    }
}
