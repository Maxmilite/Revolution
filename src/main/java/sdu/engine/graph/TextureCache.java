package sdu.engine.graph;

import sdu.Util;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    public static final String DEFAULT_TEXTURE = Util.getResourceDir() + "/textures/default/default_texture.png";
    private final Map<String, Texture> map;

    public TextureCache() {
        map = new HashMap<>();
        map.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public void cleanup() {
        map.values().forEach(Texture::cleanup);
    }

    public Texture createTexture(String path) {
        return map.computeIfAbsent(path, Texture::new);
    }

    public Texture getTexture(String path) {
        Texture texture = null;
        if (path != null) {
            texture = map.get(path);
        }
        if (texture == null) {
            texture = map.get(DEFAULT_TEXTURE);
        }
        return texture;
    }
}
