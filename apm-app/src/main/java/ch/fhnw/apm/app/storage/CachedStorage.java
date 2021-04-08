package ch.fhnw.apm.app.storage;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class CachedStorage implements Storage {
    private final Map<Integer, String> cacheStorage;
    private Storage storageToBeCached;
    private int cacheSize;

    public CachedStorage(int cacheSize, Storage storageToBeCached) {
        this.cacheSize = cacheSize;
        this.storageToBeCached = storageToBeCached;
        this.cacheStorage = new ConcurrentHashMap<>();
    }

    private String valueFromCache(int id) {
        return cacheStorage.get(id);
    }

    @Override
    public boolean store(int id, String value) {
        if (value == null) {
            this.cacheStorage.remove(id);
            storageToBeCached.store(id, null);
        } else {
            this.storageToBeCached.store(id, value);
            if (cacheStorage.size() < this.cacheSize) {
                // Add to cache
                this.cacheStorage.put(id, value);
            } else {
                // Do the Random-Replacement
                int randomIndex = new Random().nextInt(this.cacheStorage.size());

                Object[] keys = this.cacheStorage.keySet().toArray();
                Object randomKey = keys[randomIndex];
                this.cacheStorage.remove(randomKey);
                this.cacheStorage.put(id, value);
            }
        }
        return true;
    }

    @Override
    public String load(int id) {
        String cachedValue = valueFromCache(id);
        if (cachedValue == null) {
            return storageToBeCached.load(id);
        } else {
            return cachedValue;
        }

    }
}