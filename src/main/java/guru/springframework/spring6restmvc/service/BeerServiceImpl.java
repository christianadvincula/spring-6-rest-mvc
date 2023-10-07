package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Beer getBeerById(UUID id) {
        log.debug("getBeerById in Service: " + id);
        return beerMap.get(id);
    }

    @Override
    public Beer saveBeer(Beer beer) {
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(beer.getVersion())
                .beerStyle(beer.getBeerStyle())
                .beerName(beer.getBeerName())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public void updateById(UUID id, Beer beer) {
        Beer existing = beerMap.get(id);
        existing.setVersion(beer.getVersion());
        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setPrice(beer.getPrice());
        existing.setUpdateDate(LocalDateTime.now());

    }

    @Override
    public void deleteById(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchById(UUID id, Beer beer) {
        boolean updated = false;
        Beer existingBeer = beerMap.get(id);
        if(StringUtils.hasText(beer.getBeerName())) {
            existingBeer.setBeerName(beer.getBeerName());
            updated = true;
        }

        if(beer.getBeerStyle() != null) {
            existingBeer.setBeerStyle(beer.getBeerStyle());
            updated = true;
        }

        if(beer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
            updated = true;
        }

        if(beer.getQuantityOnHand() != null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            updated = true;
        }

        if(StringUtils.hasText(beer.getUpc())) {
            existingBeer.setUpc(beer.getUpc());
            updated = true;
        }

        if(beer.getVersion() != null) {
            existingBeer.setVersion(beer.getVersion());
            updated = true;
        }

        if(updated)
            existingBeer.setUpdateDate(LocalDateTime.now());
    }
}
