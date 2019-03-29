package com.cargotracker.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.cargotracker.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.cargotracker.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.Vendor.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShiperReceiverInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentType.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentMode.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.PaymentMode.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.CarrierDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.TrackingStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentInfo.class.getName() + ".shipmentInfoPODS", jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentInfo.class.getName() + ".shipperReceiverInfos", jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.ShipmentInfoPOD.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.State.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.State.class.getName() + ".cities", jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.cargotracker.domain.Country.class.getName() + ".states", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
