package service;

import annotations.Cache;

import java.time.LocalDate;
import java.util.List;

import static annotations.CacheType.FILE;
import static annotations.CacheType.IN_MEMORY;

public interface Service {

    @Cache(cacheType = FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class, Double.class})
    List<String> run(String item, Double value, LocalDate date);

    @Cache(cacheType = IN_MEMORY,  memoryCacheSize = 100_000)
    List<String> work(String item);

}
