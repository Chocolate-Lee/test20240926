package com.example.foodieweb.initialize;

import com.example.foodiedata.mapper.MobileFoodFacilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeConfig implements ApplicationRunner {

    @Autowired
    private MobileFoodFacilityMapper mobileFoodFacilityMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mobileFoodFacilityMapper.initializeDatabase();
    }
}
