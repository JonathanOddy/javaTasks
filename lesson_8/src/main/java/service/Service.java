package service;

import annotations.Cache;

import java.util.Date;
import java.util.List;

import static annotations.CacheType.FILE;
import static annotations.CacheType.IN_MEMORY;

public interface Service {

    @Cache(cacheType = FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(cacheType = FILE, listSize = 100_000, zip = true)
    List<String> work(String item);

}
